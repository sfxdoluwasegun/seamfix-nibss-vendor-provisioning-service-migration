package com.seamfix.bioweb.microservices.vendor.provision.controllers;

import com.seamfix.bioweb.microservices.vendor.provision.entities.KycDealer;
import com.seamfix.bioweb.microservices.vendor.provision.entities.VendorProvision;
import com.seamfix.bioweb.microservices.vendor.provision.pojo.StatusResponse;
import com.seamfix.bioweb.microservices.vendor.provision.pojo.SuccessResponse;
import com.seamfix.bioweb.microservices.vendor.provision.pojo.VendorProvisionRequest;
import com.seamfix.bioweb.microservices.vendor.provision.pojo.VendorProvisionResponse;
import com.seamfix.bioweb.microservices.vendor.provision.repositories.VendorProvisionRepository;
import com.seamfix.bioweb.microservices.vendor.provision.rest.client.endpoint.VendorProvisionEndPoint;
import com.seamfix.bioweb.microservices.vendor.provision.service.VendorProvisionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class VendorProvisionEndpointTest {

    @Spy
    VendorProvisionService vendorProvisionService;

    @Mock
    VendorProvisionRepository vendorProvisionRepository;

    VendorProvisionEndPoint endpoint;

    @Before
    public void setUp() {
        endpoint = new VendorProvisionEndPoint();
    }


    @Test
    public void givenVendorId_whenGetValidProvisionedDeviceIsCalled_shouldReturnVendorIsBlacklistedMessage() {
        VendorProvisionRequest vendorProvisionRequest = new VendorProvisionRequest();
        vendorProvisionRequest.setVendorId("VENDORID");
        vendorProvisionRequest.setApplicationId("com.seamfix.nibss_apk");
        vendorProvisionRequest.setApplicationKey("1234tuwerew");

        VendorProvision vendorProvision = new VendorProvision();
        vendorProvision.setBlacklisted(true);
        when(vendorProvisionRepository.getValidProvisionedDevice(any(String.class), any(String.class), any(String.class))).thenReturn(vendorProvision);
        vendorProvisionService.setVendorProvisionRepository(vendorProvisionRepository);
        endpoint.setVendorProvisionService(vendorProvisionService);
        SuccessResponse<VendorProvisionResponse> response = endpoint.getValidProvisionedDevice(vendorProvisionRequest);
        assertEquals(response.getInfo().getStatus(), -3);
        assertEquals(response.getInfo().getMessage(), "Invalid vendor provisioning!");
    }

    @Test
    public void givenVendorId_whengetProvisionedDeviceIsCalled_shouldReturnVendorIsBlacklistedMessage() {
        String vendorId = "VENDORID";
        VendorProvisionRequest vendorProvisionRequest = new VendorProvisionRequest();
        vendorProvisionRequest.setVendorId(vendorId);
        VendorProvision vendorProvision = new VendorProvision();
        vendorProvision.setBlacklisted(true);
        KycDealer vendor = new KycDealer();
        vendor.setActive(true);
        vendorProvision.setVendor(vendor);
        when(vendorProvisionRepository.getProvisionedDevice(vendorId)).thenReturn(vendorProvision);
        vendorProvisionService.setVendorProvisionRepository(vendorProvisionRepository);
        endpoint.setVendorProvisionService(vendorProvisionService);
        SuccessResponse<StatusResponse> response = endpoint.getProvisionedDevice(vendorProvisionRequest);
        assertEquals(-5, response.getInfo().getStatus());
        assertEquals("Vendor application has been blacklisted!", response.getInfo().getMessage());
    }

}
