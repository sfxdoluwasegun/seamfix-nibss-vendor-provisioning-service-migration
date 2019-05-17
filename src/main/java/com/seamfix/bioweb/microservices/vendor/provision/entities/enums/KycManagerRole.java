package com.seamfix.bioweb.microservices.vendor.provision.entities.enums;

public enum KycManagerRole {
	SYSTEM("System Admin"),
	ADMIN("KYC Admin"),
	SUPPORT("KYC Support Manager"), // Field Application Support
	TECH("KYC Tech"), // Technical Staff @IBM
	KZC("KYC Zonal Cordinator"), // KYC Coordinators
	KDM("KYC District Manager"), // KYC District Managers
	FSA("KYC FSA"), // Field Support Admin
	SALES("SALES"), // Sales Team
	AGENT("KYC Agent"),
	CSD("Customer Service"),
	FRAUD("Fraud Unit"),
	MARKETING("Marketing Unit"),
	USER("KYC USER"),
	FOREIGN_NATIONAL_ROLE("FOREIGN_NATIONAL_ROLE"),
	CORPORATE_ROLE("CORPORATE_ROLE"),
	ADDITIONAL_SIM("ADDITIONAL_SIM"),
	SUPERVISOR("SUPERVISOR"),
	REGISTRATION_UPDATE("REGISTRATION UPDATE"),
	SRAA("SRAA");

	private String tag;

	private KycManagerRole(String tag) {
		this.tag = tag;
	}

	public String getText() {
		return this.name();
	}
	public String getTag() {
		return this.tag;
	}

}
