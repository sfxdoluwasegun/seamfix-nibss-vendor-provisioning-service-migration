package com.seamfix.bioweb.microservices.vendor.provision.graphql.filters;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seamfix.bioweb.microservices.vendor.provision.constants.StringConstants;
import com.seamfix.bioweb.microservices.vendor.provision.graphql.utils.TokenManager;

import io.jsonwebtoken.JwtException;

@WebFilter(urlPatterns = {StringConstants.GRAPH_QL_SERVLET_PATH})
public class CorsFilter implements Filter {

	public static final Logger logger = LoggerFactory.getLogger(CorsFilter.class);
	
	@Inject
	TokenManager tokenManager;

	public final static String AUTHORIZATION_HEADER_KEY = "Authorization";

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
	      
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        
        // Authorize (allow) all domains to consume the content
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers", "Content-Type, User-Agent, User-UUID, token, Authorization, X-Requested-With, sc-auth-key, Device-ID, Client-ID, locale");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Expose-Headers", "token, locale");
        
        ((HttpServletResponse) servletResponse).addHeader("locale", request.getHeader("locale"));
        
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        // For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
        if (request.getMethod().equals("OPTIONS")) {
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }
        Map<String, String> requestHeaders = new ConcurrentHashMap<>();
        requestHeaders = tokenManager.retrieveHeaderValues(request, requestHeaders);
        String bearer = requestHeaders.get("Authorization");
        if(StringUtils.isBlank(bearer)) {
        	((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
        	return;
        }
        
        String token = bearer.replace("Bearer ", "");
    	boolean isValid = false;
		try {
			isValid = this.tokenManager.validateJwtToken(token);
			if(!isValid) {
        		((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
        		return;
        	}
        	
        	String email = this.tokenManager.getEmailFromToken(token);
    		String newToken = this.tokenManager.setJwtToken(email);
    		((HttpServletResponse) servletResponse).addHeader("token", newToken);
	        
	        // pass the request along the filter chain
	        chain.doFilter(request, servletResponse);
		} catch (IllegalStateException |IOException | ServletException| JwtException e) {
			((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
		}
		
  }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}