package com.seamfix.bioweb.microservices.vendor.provision.rest.security;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

public class KsSecurityContext implements SecurityContext{

	private KsPrincipal user;
	private boolean secure;

	public KsSecurityContext() {
		super();
	}

	public KsSecurityContext(KsPrincipal user, boolean secure) {
		this.user = user;
		this.secure = secure;
	}

	@Override
	public Principal getUserPrincipal() {
		return user;
	}

	@Override
	public boolean isUserInRole(String role) {
		if(user == null) {
			return false;
		}
		for(String r: user.getRoles()){
			if(r.equals(role)){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isSecure() {
		return secure;
	}

	@Override
	public String getAuthenticationScheme() {
		return "KYC";
	}
}
