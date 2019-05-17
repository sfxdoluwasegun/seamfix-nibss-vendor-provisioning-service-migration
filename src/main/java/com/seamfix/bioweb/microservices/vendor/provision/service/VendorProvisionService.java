/*13 May 2019
charles
*/
package com.seamfix.bioweb.microservices.vendor.provision.service;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import com.seamfix.bioweb.microservices.vendor.provision.entities.VendorProvision;
import com.seamfix.bioweb.microservices.vendor.provision.pojo.StatusResponse;
import com.seamfix.bioweb.microservices.vendor.provision.pojo.SuccessResponse;
import com.seamfix.bioweb.microservices.vendor.provision.repositories.VendorProvisionRepository;

@Dependent
@Named("deviceProvisionService")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VendorProvisionService {
	
	@Named("vendorProvisionRepository")
	@Inject
	private VendorProvisionRepository vendorProvisionRepository;
	
	public void setVendorProvisionRepository(VendorProvisionRepository vendorProvisionRepository) {
		this.vendorProvisionRepository = vendorProvisionRepository;
	}

	public SuccessResponse<StatusResponse> isDeviceProvisioned(String vendorId) {
		SuccessResponse< StatusResponse> response = new SuccessResponse<>();
		StatusResponse status = new StatusResponse();
		status.setStatus(-1);
		status.setMessage("Vendor has not been provisioned!");
		
		VendorProvision deviceProvision = vendorProvisionRepository.getProvisionedDevice(vendorId);
		if(deviceProvision != null) {
			status.setStatus(0);
			status.setMessage("Vendor has been provisioned!");
		}
		
		response.setInfo(status);
		response.setSuccessful(true);
		return response;
	}

}
