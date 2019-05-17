/*17 May 2019
charles
*/
package com.seamfix.bioweb.microservices.vendor.provision.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.seamfix.bioweb.microservices.vendor.provision.entities.VendorProvision;
import com.seamfix.bioweb.microservices.vendor.provision.pojo.StatusResponse;
import com.seamfix.bioweb.microservices.vendor.provision.pojo.SuccessResponse;
import com.seamfix.bioweb.microservices.vendor.provision.repositories.VendorProvisionRepository;

@RunWith(MockitoJUnitRunner.class)
public class VendorProvisionServiceTest {
	
	@Spy
	private VendorProvisionService vendorProvisionService;
	
	@Mock
	private VendorProvisionRepository vendorProvisionRepository;
	
	
	@Test
    public void givenVendorId_whenIsDeviceProvisionedIsCalled_shouldReturnVendorNotProvisionedMessage() {
		String vendorId = "VENDORID";
		when(vendorProvisionRepository.getProvisionedDevice(vendorId)).thenReturn(null);
		vendorProvisionService.setVendorProvisionRepository(vendorProvisionRepository);
		SuccessResponse<StatusResponse> response = vendorProvisionService.isDeviceProvisioned(vendorId);
		assertEquals(response.getInfo().getStatus(), -1);
		assertEquals(response.getInfo().getMessage(), "Vendor has not been provisioned!");
	}
	
	@Test
    public void givenVendorId_whenIsDeviceProvisionedIsCalled_shouldReturnVendorProvisionedMessage() {
		String vendorId = "VENDORID";
		when(vendorProvisionRepository.getProvisionedDevice(vendorId)).thenReturn(new VendorProvision());
		vendorProvisionService.setVendorProvisionRepository(vendorProvisionRepository);
		SuccessResponse<StatusResponse> response = vendorProvisionService.isDeviceProvisioned(vendorId);
		assertEquals(response.getInfo().getStatus(), 0);
		assertEquals(response.getInfo().getMessage(), "Vendor has been provisioned!");
	}

}
