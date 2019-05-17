package com.seamfix.bioweb.microservices.vendor.provision.utils;


import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

import org.keyczar.Crypter;
import org.keyczar.exceptions.KeyczarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sfx.crypto.CryptoReader;

@Singleton
public class CryptController{

	private static final Logger logger = LoggerFactory.getLogger(CryptController.class);
	
	private Crypter crypter;

	@PostConstruct
	public void init(){
		CryptoReader crptoReader = new CryptoReader("map");
		try {
			crypter = new Crypter(crptoReader);
		} catch (KeyczarException e) {
			logger.error("Exception ", e);
		}
	}

	public Crypter getCrypter() {
		return crypter;
	}

        
    public static void main(String[] args) {
        try {
            CryptController cryptController = new CryptController();
            cryptController.init();
            logger.debug(cryptController.crypter.encrypt("SAMSUNG-359514063519042:SAMSUNG-359514063519042"));
            logger.debug(cryptController.crypter.decrypt("AHVZ0xhsL2YdwjgNWakIqzhp_lOngt7hkkhOAU9utLHCKcBVfFG-8FfmJyD65sDJwqk_UN7DciMTfMpeqrMwEH48LCDO_X-_GDxu7Cp7zuILEOUejPRzl0Y"));
        } catch (KeyczarException ex) {
        	logger.error("An error occurred",ex);
        }
    }
}
