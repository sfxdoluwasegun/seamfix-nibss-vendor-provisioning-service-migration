package com.seamfix.bioweb.microservices.vendor.provision.graphql;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.seamfix.bioweb.microservices.vendor.provision.graphql.pojos.GraphQLUser;

import graphql.servlet.GraphQLContext;

public class AuthContext extends GraphQLContext {

	private final GraphQLUser user;

	private final Optional<HttpServletRequest> request;

	public AuthContext(GraphQLUser user, Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
		super(request, response);
		this.user = user;
		this.request = request;
	}

	public GraphQLUser getUser() {
		return user;
	}

	public Optional<HttpServletRequest> getRequest(){
		return request;
	}
}
