package com.seamfix.bioweb.microservices.vendor.provision.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mugwu
 * @since 28-01-2020 8:22 PM
 **/
@Getter
@Setter
public class VendorProvisionRequest {

    private String vendorId;
    private String applicationKey;
    private String applicationId;
}
