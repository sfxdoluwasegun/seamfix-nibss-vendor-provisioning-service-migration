package com.seamfix.bioweb.microservices.vendor.provision.utils;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.lang3.StringUtils;
import org.keyczar.Crypter;
import org.keyczar.exceptions.KeyczarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seamfix.bioweb.microservices.vendor.provision.constants.StringConstants;
import com.seamfix.bioweb.microservices.vendor.provision.pojo.HeaderIdentifier;

import sfx.crypto.CryptoReader;

@Singleton
@Startup
public class SecurityUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);
	
	private Crypter crypter;

	public static final String KEY_DATA_STORE_DIR = "map";
	
	@PostConstruct
	public void init(){
		StringBuffer keyFolderPathBufferString = new StringBuffer();
		keyFolderPathBufferString.append(System.getProperty(StringConstants.JBOSS_HOME_DIR))
		.append(File.separator)
		.append(StringConstants.WILD_FLY_BIN_DIR)
		.append(File.separator)
		.append(KEY_DATA_STORE_DIR);

		CryptoReader cryptoReader = new CryptoReader(keyFolderPathBufferString.toString());
		try {
			crypter = new Crypter(cryptoReader);
		} catch (KeyczarException e) {
			logger.error("Exception creating Crypter ", e);
		}
	}

	/**
	 * Encrypts string passed to it
	 *  
	 * @param stringToEncrypt
	 * @return encrypted string
	 */
	public String encrypt(String stringToEncrypt) {

		String encryptedString = "";

		if(crypter == null) {
			return encryptedString;
		}

		try {
			encryptedString =  crypter.encrypt(stringToEncrypt);
		} catch (KeyczarException e) {
			logger.error("Error Encrypting string : {}", stringToEncrypt, e);
		}

		return encryptedString;
	}

	/**
	 * Decrypts string passed to it
	 * 
	 * @param stringToDecrypt
	 * @return decrypted string
	 */
	public String decrypt(String stringToDecrypt) {

		String decryptedString = "";

		if(crypter == null) {
			return decryptedString;
		}

		try {
			decryptedString =  crypter.decrypt(stringToDecrypt);
		} catch (KeyczarException e) {
			logger.error("Error Decrypting string : {}", stringToDecrypt, e);
		}

		return decryptedString;
	}
	
	public HeaderIdentifier getIdentifier(HttpHeaders headers) {        
        String auth = headers.getHeaderString("sc-auth-key");
        String deviceId = headers.getHeaderString("Device-ID");
        String clientID = headers.getHeaderString("Client-ID");
        HeaderIdentifier identifier = new HeaderIdentifier();
        
        try {
            String message =  decrypt(auth); //TAG:MAC:UUID:APP-SIGN:AGENT-EMAIL
            String[] codes = message.split(":");
            identifier.setTag(codes[0]);
            String macAddress = codes[1];
            if(clientID != null && clientID.toLowerCase().contains("droid")){
                //mac address from droid client
                macAddress = macAddress.replaceAll("-", ":");
            }
            identifier.setMac(macAddress);
            identifier.setUserUUID(codes[2]);
            //these checks are done for existing systems
            
            int emailLength = 5;
            if (codes.length == emailLength) {
                identifier.setEmailAddress(codes[4]);
            }
            
            if(!StringUtils.isEmpty(deviceId)){
                String message2 = decrypt(deviceId); //:REF-DEVICE-ID:REALTIME-DEVICE-ID
                String[] codes2 = message2.split(":");
                identifier.setRefDeviceId(codes2[0]);
                identifier.setRealTimeDeviceId(codes2[1]);
            }
            logger.debug("header identifier: {}" , identifier);
        }catch (ArrayIndexOutOfBoundsException e) {
            logger.error("An error occurred while getting headerIdentifier", e);
        }
        return identifier;
    }
}
