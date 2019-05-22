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

import com.seamfix.bioweb.microservices.vendor.provision.entities.KycDealer;
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
    public void givenVendorId_whenGetValidProvisionedDeviceIsCalled_shouldReturnVendorProvisionedMessage() {
		String vendorId = "VENDORID";
		when(vendorProvisionRepository.getValidProvisionedDevice(vendorId)).thenReturn(new VendorProvision());
		vendorProvisionService.setVendorProvisionRepository(vendorProvisionRepository);
		SuccessResponse<StatusResponse> response = vendorProvisionService.getValidProvisionedDevice(vendorId);
		assertEquals(response.getInfo().getStatus(), 0);
		assertEquals(response.getInfo().getMessage(), "Vendor has been provisioned!");
	}
		
	@Test
    public void givenVendorId_whenGetValidProvisionedDeviceIsCalled_shouldReturnVendorNotProvisionedMessage() {
		String vendorId = "VENDORID";
		when(vendorProvisionRepository.getValidProvisionedDevice(vendorId)).thenReturn(null);
		vendorProvisionService.setVendorProvisionRepository(vendorProvisionRepository);
		SuccessResponse<StatusResponse> response = vendorProvisionService.getValidProvisionedDevice(vendorId);
		assertEquals(response.getInfo().getStatus(), -1);
		assertEquals(response.getInfo().getMessage(), "Vendor has not been provisioned!");
	}
	
	
	@Test
    public void givenVendorId_whenGetProvisionedDeviceIsCalled_shouldReturnSuspendedVendorProvisionedMessage() {
		String vendorId = "VENDORID";
		VendorProvision vendorProvision = new VendorProvision();
		vendorProvision.setActive(false);
		when(vendorProvisionRepository.getProvisionedDevice(vendorId)).thenReturn(vendorProvision);
		vendorProvisionService.setVendorProvisionRepository(vendorProvisionRepository);
		SuccessResponse<StatusResponse> response = vendorProvisionService.getProvisionedDevice(vendorId);
		assertEquals(response.getInfo().getStatus(), -2);
		assertEquals(response.getInfo().getMessage(), "Vendor provision has been suspended!");
	}
	
	@Test
    public void givenVendorId_whenGetProvisionedDeviceIsCalled_shouldReturnInvalidVendorProvisionedMessage() {
		String vendorId = "VENDORID";
		VendorProvision vendorProvision = new VendorProvision();
		vendorProvision.setActive(true);
		when(vendorProvisionRepository.getProvisionedDevice(vendorId)).thenReturn(vendorProvision);
		vendorProvisionService.setVendorProvisionRepository(vendorProvisionRepository);
		SuccessResponse<StatusResponse> response = vendorProvisionService.getProvisionedDevice(vendorId);
		assertEquals(response.getInfo().getStatus(), -3);
		assertEquals(response.getInfo().getMessage(), "Invalid vendor provisioning!");
	}
	
	@Test
    public void givenVendorId_whenGetProvisionedDeviceIsCalled_shouldReturnDeactivatedVendorProvisionedMessage() {
		String vendorId = "VENDORID";
		VendorProvision vendorProvision = new VendorProvision();
		KycDealer vendor = new KycDealer();
		vendor.setActive(false);
		vendorProvision.setVendor(vendor);
		when(vendorProvisionRepository.getProvisionedDevice(vendorId)).thenReturn(vendorProvision);
		vendorProvisionService.setVendorProvisionRepository(vendorProvisionRepository);
		SuccessResponse<StatusResponse> response = vendorProvisionService.getProvisionedDevice(vendorId);
		assertEquals(response.getInfo().getStatus(), -4);
		assertEquals(response.getInfo().getMessage(), "Vendor has been deactivated!");
	}

}
