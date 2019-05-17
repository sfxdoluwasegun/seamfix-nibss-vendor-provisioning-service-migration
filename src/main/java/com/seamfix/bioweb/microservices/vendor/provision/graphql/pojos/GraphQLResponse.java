package com.seamfix.bioweb.microservices.vendor.provision.graphql.pojos;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GraphQLResponse implements Serializable{
	
	private static final long serialVersionUID = 1308789937641826398L;
	
	public static final String SUCCESS_CODE = "00";
	public static final String ERROR_CODE = "01";
	
	public static final String ERROR_LICENSE_REQUEST = "An error occurred while making license request";
	public static final String ERROR_LICENSE_STATUS = "An error occurred while checking status of license";
	
	
	public static final String ERROR_MISSING_PROPERTY_CODE = "02";
	public static final String UNAUTHORIZED_ERROR_CODE = "401";
	
	public static final String UNAUTHORIZED_ERROR_MESSAGE = "You are not authenticated.";
	
	private String responseCode;
	
	private String responseDescription;
}
