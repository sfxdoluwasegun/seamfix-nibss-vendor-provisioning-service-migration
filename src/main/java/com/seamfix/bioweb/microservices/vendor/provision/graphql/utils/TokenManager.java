package com.seamfix.bioweb.microservices.vendor.provision.graphql.utils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seamfix.bioweb.microservices.vendor.provision.entities.enums.SettingsEnum;
import com.seamfix.bioweb.microservices.vendor.provision.repositories.DataRepository;
import com.seamfix.bioweb.microservices.vendor.provision.utils.SecurityUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Singleton
@Startup
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TokenManager implements Serializable{

	private static final long serialVersionUID = -2281842099375546197L;

	private String jwtKey;
	private static final String DELIMETER = "^";
	private static final String JWT_ISSUER = "BIOSMARTWEB";

	private static final int SECONDS_PER_MINUTE = 60;

	public static final String JWT_KEY = "JWT-KEY";

	protected static final Logger logger = LoggerFactory.getLogger(TokenManager.class);

	@Inject
	SecurityUtil securityUtil;

	@Inject
	@Named("dataRepository")
	private DataRepository dataRepository;

	@PostConstruct
	public void init(){
		if(StringUtils.isBlank(this.jwtKey)) {
			retrieveJwtKey();
		}
	}


	/**
	 * returns cookie associated with the identifier specified
	 * 
	 * @param request
	 * @param cookieIdentifier
	 * @return cookie object if any was found with identifier or null if none was
	 *         found
	 */
	public Cookie getCookieByName(HttpServletRequest request, String cookieIdentifier) {

		Cookie[] cookies = request.getCookies();

		if (cookies != null) {

			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase(cookieIdentifier)) {
					return cookie;
				}
			}

		}
		return null;
	}

	/**
	 * decrypts jwt token to return jwt subject
	 * 
	 * first checks
	 * 
	 * @param jwtToken
	 * @return
	 */
	public String decryptJwToken(String jwtToken) {

		Claims claims = getJwtClaims(jwtToken);

		if (claims == null) {
			//log.error("rejecting invalid jwt token");
			return StringUtils.EMPTY;
		}

		String encryptedSubject = claims.getSubject();
		String subject = securityUtil.decrypt(encryptedSubject);

		return subject;
	}

	/**
	 * 
	 * @param jwtToken
	 * @return
	 */
	private Claims getJwtClaims(String jwtToken) {

		Claims claims = null;

		try {
			claims = getClaim(jwtToken);

		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | UnsupportedEncodingException e) {
			logger.error("JWTOtherException: ", e.getMessage());
		}

		return claims;
	}


	/**
	 * 
	 * @param key
	 * @param jwtToken
	 * @return
	 * @throws ExpiredJwtException
	 * @throws UnsupportedJwtException
	 * @throws MalformedJwtException
	 * @throws IllegalArgumentException
	 * @throws UnsupportedEncodingException 
	 */
	private Claims getClaim(String jwtToken)
			throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, IllegalArgumentException, UnsupportedEncodingException {

		if (getJwtKey() == null) {
			return null;
		}

		try {
			return Jwts.parser().setSigningKey(getJwtKey().getBytes("UTF-8")).parseClaimsJws(jwtToken).getBody();
		} catch (SignatureException e) {
			logger.error("JWTSignatureException:", e.getMessage());
		}

		return null;
	}

	/**
	 * generates jwt token
	 * 
	 * @param email
	 * @param validityTime
	 *            in seconds
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String setJwtToken(String email) throws UnsupportedEncodingException {

		String tokenSubject = new StringBuilder(email).append(DELIMETER).append(System.currentTimeMillis()).toString();

		int validityTime = 30 * SECONDS_PER_MINUTE;
		String encryptedSubject = securityUtil.encrypt(tokenSubject);

		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		JwtBuilder jwtBuilder = Jwts.builder().setId(UUID.randomUUID().toString())
				.setIssuedAt(Timestamp.valueOf(LocalDateTime.now())).setSubject(encryptedSubject).setIssuer(JWT_ISSUER)
				.signWith(signatureAlgorithm, getJwtKey().getBytes("UTF-8"));

		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.SECOND, validityTime);
		Date expirationTime = calendar.getTime();

		jwtBuilder.setExpiration(expirationTime);
		return jwtBuilder.compact();

	}

	/**
	 * validates jwt token checks if the jwt token is valid with both the active key
	 * and the stale key
	 * 
	 * @param jwtToken
	 * @return true if the jwt is still valid
	 */
	public boolean validateJwtToken(String jwtToken) {

		if (jwtToken == null) {
			return false;
		}
		
		try {

			if (getClaim(jwtToken) == null) {
				return false;
			}

		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | UnsupportedEncodingException e) {
			logger.error("JWTOtherException:", e.getMessage());
		}

		return true;
	}

	public String getEmailFromToken(String jwtToken) {
		boolean validToken = validateJwtToken(jwtToken);
		if(!validToken) {
			return null;
		}
		String token = decryptJwToken(jwtToken);
		Pattern pattern = Pattern.compile(new StringBuilder("\\").append(DELIMETER).toString());
		String email = pattern.split(token)[0];
		return email;
	}

	public String getJwtKey() {
		return jwtKey;
	}

	public void setJwtKey(String jwtKey) {
		this.jwtKey = jwtKey;
	}

	public Map<String, String> retrieveHeaderValues(HttpServletRequest request, Map<String, String> requestHeaders) {
		Enumeration<String> values = request.getHeaderNames();
		
		if(requestHeaders == null) {
			requestHeaders = new ConcurrentHashMap<>();
		}
		while(values.hasMoreElements()) {
			String value = (String) values.nextElement();
			requestHeaders.put(value, request.getHeader(value));
		}
		return requestHeaders;
	}
	
	public Map<String, String> retrieveHeaderValues(HttpServletRequest request) {
		return retrieveHeaderValues(request, null);
	
	}

	private void retrieveJwtKey() {
		String jwtKey = dataRepository.getSettingValue(SettingsEnum.JWT_KEY);
		
		setJwtKey(jwtKey);
	}
}
