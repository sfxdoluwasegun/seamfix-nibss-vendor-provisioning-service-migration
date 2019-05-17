package com.seamfix.bioweb.microservices.vendor.provision.utils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seamfix.kyc.microservices.utilities.cache.AbstractBaseBioCache;

/**
 * High level abstraction for low level general memcached activities
 * Used for all service calls with the exception of: sim swap, settings and biodata
 * @author nuke
 *
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Named("bioCache")
@Lock(LockType.READ)
public class BioCache extends AbstractBaseBioCache {

	private static final Logger logger = LoggerFactory.getLogger(BioCache.class);
	
	@Inject
	AppProperties appProps;

	@PostConstruct
	public void init(){
		logger.debug("Initializing Cache Mechanism");
		addressList = appProps.getProperty("cache-server-list", "localhost:11211");
		connect();
	}

	@PreDestroy
	public void end(){

	}

	@Override
	public String getCachePrefix() {
		return "License_MS_";
	}

}
