package com.seamfix.bioweb.microservices.vendor.provision.rest.filter;

/**
 * @author dawuzi
 */

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Set;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import com.seamfix.bioweb.microservices.vendor.provision.constants.StringConstants;
import com.seamfix.bioweb.microservices.vendor.provision.rest.security.IntrusionAnalyzer;
import com.seamfix.bioweb.microservices.vendor.provision.rest.security.KsPrincipal;
import com.seamfix.bioweb.microservices.vendor.provision.rest.security.KsSecurityContext;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class KsSecurityFilter implements ContainerRequestFilter {

	@Context
	protected HttpServletRequest req;
	
	@Context
	UriInfo uriInfo;
	
	@Context
	ResourceInfo resourceInfo;

	@Inject
	protected IntrusionAnalyzer analyzer;
	
	private static final String FORBIDDEN = "Forbidden";

	@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
	@Override
	public void filter(ContainerRequestContext request) throws IOException {
		
		String scUuid = request.getHeaderString("User-UUID");
		String client = request.getHeaderString("Client-ID");

		KsPrincipal user = getPrincipal(scUuid, client);
		
		boolean secure = uriInfo.getAbsolutePath().toString().toLowerCase().startsWith("https");
		
		boolean authenticated = analyzer.analyze(req, request);
		
		if(authenticated) {
			request.setSecurityContext(new KsSecurityContext(user, secure));
		} else {
//			based on the specification the SecurityContext set in the ContainerRequestContext should return null for the 
//			getUserPrincipal when the current request has not been authenticated which is the case in this else block
			request.setSecurityContext(new KsSecurityContext(null, secure));
		}
		
		Method resourceMethod = resourceInfo.getResourceMethod();

		if(resourceMethod.isAnnotationPresent(PermitAll.class)) {
			return;
		}
		
		if(resourceMethod.isAnnotationPresent(DenyAll.class)) {
			request.abortWith(Response.status(Status.FORBIDDEN).entity(FORBIDDEN).build());
			return;
		}
		
		Class<?> resourceClass = resourceInfo.getResourceClass();
		
		if(resourceClass.isAnnotationPresent(PermitAll.class) && !resourceMethod.isAnnotationPresent(RolesAllowed.class)) {
			return;
		}
		
		if(resourceClass.isAnnotationPresent(DenyAll.class) && !resourceMethod.isAnnotationPresent(RolesAllowed.class)) {
			request.abortWith(Response.status(Status.FORBIDDEN).entity(FORBIDDEN).build());
			return;
		}
		
//		for end points that are not PermitAll or DenyAll the user needs to be even authenticated for a start before checking roles
		
		if(user == null || !authenticated) {
			request.abortWith(Response.status(Status.UNAUTHORIZED).entity("Not Authorized").build());
			return;
		}
		
		RolesAllowed methodRolesAllowed = resourceMethod.getAnnotation(RolesAllowed.class);
		
		if(methodRolesAllowed != null) {
			if (!isUserInRoles(methodRolesAllowed.value(), user)) {
				request.abortWith(Response.status(Status.FORBIDDEN).entity(FORBIDDEN).build());
			}
			return ;
		}
		
		RolesAllowed classRolesAllowed = resourceClass.getAnnotation(RolesAllowed.class);
		
		if(classRolesAllowed != null && !isUserInRoles(classRolesAllowed.value(), user)) {
			request.abortWith(Response.status(Status.FORBIDDEN).entity(FORBIDDEN).build());
		}  
		
	}

	private boolean isUserInRoles(String[] validRoles, KsPrincipal user) {
		
		if(validRoles == null || user == null || user.getRoles() == null) {
			return false;
		}
		
		Set<String> userRoles = user.getRoles();
		
		for(String validRole : validRoles) {
			if(userRoles.contains(validRole)) {
				return true;
			}
		}
		
		return false;
	}

	protected KsPrincipal getPrincipal(String uuid, String client) {
		if (uuid != null) {
			
			KsPrincipal principal = new KsPrincipal(uuid);
			
			principal.addRole(StringConstants.SMART_CLIENT);
			
			return principal;
		
		} else if (client != null) {
			
			KsPrincipal principal = new KsPrincipal(client.toLowerCase());
			
			principal.addRole(client.toLowerCase());
			
			return principal;
		
		} else {
			
			KsPrincipal principal = new KsPrincipal("guest");
			
			principal.addRole("guest");
			
			return principal;
		}
	}

}
