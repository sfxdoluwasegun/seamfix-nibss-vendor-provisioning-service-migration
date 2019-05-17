package com.seamfix.bioweb.microservices.vendor.provision.rest.security;

import java.util.Arrays;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.keyczar.Crypter;
import org.keyczar.exceptions.KeyczarException;

import com.seamfix.bioweb.microservices.vendor.provision.pojo.HeaderIdentifier;
import com.seamfix.bioweb.microservices.vendor.provision.repositories.DataRepository;
import com.seamfix.bioweb.microservices.vendor.provision.utils.AppProperties;
import com.seamfix.bioweb.microservices.vendor.provision.utils.BioCache;
import com.seamfix.bioweb.microservices.vendor.provision.utils.CryptController;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings({"PMD.GodClass"})
@Stateless
@Slf4j
public class IntrusionAnalyzer {


	@Inject
	@Named("bioCache")
	private BioCache cache;

	@Inject
	@Named("dataRepository")
	private DataRepository dataRepository;

	@Inject
	private CryptController cryptControl;
	
	@Inject
	@Named("appProperties")
	private AppProperties appProps;

	protected static final String AUTH_HEADER_KEY = "#D8CK>HIGH<LOW>#";
	private static final String VERSION_ = "VERSION_";

	protected String scUuid;
	private String agent;
	private String clientId;
	private String deviceId;

	private String method;

	/**
	 * Flags whether a request should proceed or fail
	 * @param req
	 * @param request
	 * @return true for clean request false for otherwise
	 */
	public boolean analyze(HttpServletRequest req, ContainerRequestContext request){
		log.warn("method: {}; User-Agent: {}; Client-ID: {}; scUuid: {}; deviceId: {}", method, agent, clientId, scUuid, deviceId);

		
		return true;
	}

	protected boolean isAppSignValid(String[] codes){
		if(!checkAppSignature()){
			//we're not checking signature for this app version
			return true;
		}
		String appSign = getClientSignature(codes); //base64 encoding of client signature
		if(appSign == null){
			return false;
		}
		String serverSign = getAppSignature();
		log.debug("App sign from client: {}; from server: {}", appSign, serverSign); 
		return appSign.equalsIgnoreCase(serverSign);
	}

	protected String getClientSignature(String[] codes){
		int clientSignatureIndex = 3;
		if(codes.length < (clientSignatureIndex + 1)){
			log.error("Request does not contain an app signature {}", agent);
			return null;
		}

		return codes[clientSignatureIndex];
	}

	/**
	 * gets a hash of the app signature from the settings table
	 * @return
	 */
	protected String getAppSignature(){
		try {
			return dataRepository.getSettingValue(agent + "-APP-SIGN", "").trim();
		} catch (PersistenceException | SecurityException | IllegalStateException | HibernateException e) {
			log.error("An error occurred while getting app signature",e);
		}
		return null;
	}

	/**
	 * checks app signature based on the kit version
	 * @return
	 */
	protected boolean checkAppSignature(){
		//check cache to determine if to check for app signature
		Boolean checkVersion = cache.getItem(VERSION_ + agent.replaceAll(".", "_"), Boolean.class);
		if(checkVersion == null){
			//get check status for app version
			String appVersions = appProps.getProperty("allowed-app-sign-versions", "");
			log.debug("Versions to check app version for: {}", appVersions);
			boolean check = Arrays.asList(appVersions.split(" ")).contains(agent);
			log.debug("Check app version for {}? {}", agent, check);

			//add status to cache
			cache.setItem(VERSION_ + agent.replaceAll(".", "_"), check, 5 * 60); //cache for 5 minutes

			return check;
		}

		return false;
	}

	

	protected String getRemoteAddress(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}

	protected boolean checkV1Header(String auth, String userAgent){
		return null != userAgent;
	}

	public HeaderIdentifier getIdentifier(HttpHeaders headers) {        
		String auth = headers.getHeaderString("sc-auth-key");
		String deviceId = headers.getHeaderString("Device-ID");
		String clientID = headers.getHeaderString("Client-ID");
		HeaderIdentifier identifier = new HeaderIdentifier();
		Crypter crypter = cryptControl.getCrypter();
		try {
			String message =  crypter.decrypt(auth); //TAG:MAC:UUID:APP-SIGN:AGENT-EMAIL
			String[] codes = message.split(":");
			identifier.setTag(codes[0]);
			String macAddress = codes[1];
			if(clientID != null && clientID.toLowerCase().contains("droid")){
				//mac address from droid client
				macAddress = macAddress.replaceAll("-", ":");
			}
			identifier.setMac(macAddress);
			identifier.setUserUUID(codes[2]);
			
			int emailIndex = 4;
			
			//these checks are done for existing systems
			if (codes.length == (emailIndex + 1)) {
				identifier.setEmailAddress(codes[emailIndex]);
			}

			if(!StringUtils.isEmpty(deviceId)){
				String message2 = crypter.decrypt(deviceId); //:REF-DEVICE-ID:REALTIME-DEVICE-ID
				String[] codes2 = message2.split(":");
				identifier.setRefDeviceId(codes2[0]);
				identifier.setRealTimeDeviceId(codes2[1]);
			}
			log.debug("header identifier: {}", identifier);
		} catch (KeyczarException e) {
			log.error("KeyczarException", e);
		} catch (ArrayIndexOutOfBoundsException e) {
			log.error("ArrayIndexOutOfBoundsException", e);
		}
		return identifier;
	}

}