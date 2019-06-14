package com.seamfix.bioweb.microservices.vendor.provision.pojo;

import com.seamfix.bioweb.microservices.vendor.provision.entities.VendorProvision;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorProvisionResponse extends StatusResponse {

	private VendorProvision vendorProvision;
}
