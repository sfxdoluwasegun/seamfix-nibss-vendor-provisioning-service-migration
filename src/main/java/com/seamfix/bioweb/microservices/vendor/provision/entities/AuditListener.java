package com.seamfix.bioweb.microservices.vendor.provision.entities;


import org.hibernate.envers.RevisionListener;

/**
 * Used by envers for audit purposes
 * @author nuke
 *
 */
public class AuditListener implements RevisionListener {

//	private static final String LOCAL_HOST_IP = "127.0.0.1";
	private static final String LOCAL_HOST_IP = "";

	@Override
	public void newRevision(Object entity) {
		
		AuditEntity auditEntity = (AuditEntity) entity;
		
		auditEntity.setOrbitaId(0L);
		auditEntity.setEmailAddress("services@sfkyc.com");
		auditEntity.setRemoteAddress(LOCAL_HOST_IP);
		auditEntity.setRemoteHost(LOCAL_HOST_IP);
		auditEntity.setUserAgent("System");
		auditEntity.setCurrentURL("localhost");
	}

}
