package com.seamfix.bioweb.microservices.vendor.provision.entities.enums;

public enum HeartbeatSourceEnum {
	BIO_SMART("BIO-SMART"),
    GEO_TRACKER("GIO-TRACKER");

    private String name;

    HeartbeatSourceEnum(String name) {
        this.name = name;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    
}
