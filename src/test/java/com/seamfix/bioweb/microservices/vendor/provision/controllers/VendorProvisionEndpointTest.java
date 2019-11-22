package com.seamfix.bioweb.microservices.vendor.provision.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.seamfix.bioweb.microservices.vendor.provision.entities.KycDealer;
import com.seamfix.bioweb.microservices.vendor.provision.entities.VendorProvision;
import com.seamfix.bioweb.microservices.vendor.provision.pojo.StatusResponse;
import com.seamfix.bioweb.microservices.vendor.provision.pojo.SuccessResponse;
import com.seamfix.bioweb.microservices.vendor.provision.pojo.VendorProvisionResponse;
import com.seamfix.bioweb.microservices.vendor.provision.repositories.VendorProvisionRepository;
import com.seamfix.bioweb.microservices.vendor.provision.rest.client.endpoint.VendorProvisionEndPoint;
import com.seamfix.bioweb.microservices.vendor.provision.service.VendorProvisionService;

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
		String vendorId = "VENDORID";
		String appId = "com.seamfix.nibss_apk";
		String appKey = "1234tuwerew";
		VendorProvision vendorProvision = new VendorProvision();
		vendorProvision.setBlacklisted(true);
		when(vendorProvisionRepository.getValidProvisionedDevice(any(String.class), any(String.class), any(String.class))).thenReturn(vendorProvision);
		vendorProvisionService.setVendorProvisionRepository(vendorProvisionRepository);
		endpoint.setVendorProvisionService(vendorProvisionService);
		SuccessResponse<VendorProvisionResponse> response = endpoint.getValidProvisionedDevice(mock(HttpServletRequest.class), mock(HttpServletResponse.class), vendorId, appId, appKey);
		assertEquals(response.getInfo().getStatus(), -1);
		assertEquals(response.getInfo().getMessage(), "Vendor application has been blacklisted!");
	}
	
	@Test
    public void givenVendorId_whengetProvisionedDeviceIsCalled_shouldReturnVendorIsBlacklistedMessage() {
		String vendorId = "VENDORID";
		VendorProvision vendorProvision = new VendorProvision();
		vendorProvision.setBlacklisted(true);
		KycDealer vendor = new KycDealer();
		vendor.setActive(true);
		vendorProvision.setVendor(vendor);
		when(vendorProvisionRepository.getProvisionedDevice(vendorId)).thenReturn(vendorProvision);
		vendorProvisionService.setVendorProvisionRepository(vendorProvisionRepository);
		endpoint.setVendorProvisionService(vendorProvisionService);
		SuccessResponse<StatusResponse> response = endpoint.getProvisionedDevice(mock(HttpServletRequest.class), mock(HttpServletResponse.class), vendorId);
		assertEquals(-5, response.getInfo().getStatus());
		assertEquals("Vendor application has been blacklisted!", response.getInfo().getMessage());
	}
	
}
