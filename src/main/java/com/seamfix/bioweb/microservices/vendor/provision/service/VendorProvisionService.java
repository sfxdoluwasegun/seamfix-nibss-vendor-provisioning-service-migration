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
import com.seamfix.bioweb.microservices.vendor.provision.pojo.VendorProvisionResponse;
import com.seamfix.bioweb.microservices.vendor.provision.repositories.VendorProvisionRepository;

@Dependent
@Named("vendorProvisionService")
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VendorProvisionService {
	
	@Named("vendorProvisionRepository")
	@Inject
	private VendorProvisionRepository vendorProvisionRepository;
	
	public void setVendorProvisionRepository(VendorProvisionRepository vendorProvisionRepository) {
		this.vendorProvisionRepository = vendorProvisionRepository;
	}

	public SuccessResponse<VendorProvisionResponse> getValidProvisionedDevice(String vendorId) {
		SuccessResponse< VendorProvisionResponse> response = new SuccessResponse<>();
		VendorProvisionResponse status = new VendorProvisionResponse();
		status.setStatus(-1);
		status.setMessage("Vendor has not been provisioned!");
		
		VendorProvision vendorProvision = vendorProvisionRepository.getValidProvisionedDevice(vendorId);
		if(vendorProvision != null) {
			status.setVendorProvision(vendorProvision);
			status.setStatus(0);
			status.setMessage("Vendor has been provisioned!");
		}
		
		response.setInfo(status);
		response.setSuccessful(true);
		return response;
	}
	
	public SuccessResponse<VendorProvisionResponse> getValidProvisionedDevice(String vendorId,String appKey, String appId) {
		SuccessResponse< VendorProvisionResponse> response = new SuccessResponse<>();
		VendorProvisionResponse status = new VendorProvisionResponse();
		status.setStatus(-1);
		status.setMessage("Vendor has not been provisioned!");
		
		VendorProvision vendorProvision = vendorProvisionRepository.getValidProvisionedDevice(vendorId, appKey, appId);
		if(vendorProvision != null) {
			status.setVendorProvision(vendorProvision);
			status.setStatus(0);
			status.setMessage("Vendor has been provisioned!");
		}
		
		if(!vendorProvision.isActive()) {
			status.setStatus(-2);
			status.setMessage("Vendor provision has been suspended!");
			response.setInfo(status);
			response.setSuccessful(true);
			return response;
		}
		
		if(vendorProvision.getVendor() == null) {
			status.setStatus(-3);
			status.setMessage("Invalid vendor provisioning!");
			response.setInfo(status);
			response.setSuccessful(true);
			return response;
		}
		
		if(!vendorProvision.getVendor().isActive()) {
			status.setStatus(-4);
			status.setMessage("Vendor has been deactivated!");
			response.setInfo(status);
			response.setSuccessful(true);
			return response;
		}
		
		if(vendorProvision.getBlacklisted()) {
			status.setStatus(-5);
			status.setMessage("Vendor application has been blacklisted!");
			response.setInfo(status);
			response.setSuccessful(true);
			return response;
		}
		
		response.setInfo(status);
		response.setSuccessful(true);
		return response;
	}
	
	@SuppressWarnings("CPD-START")
	public SuccessResponse<StatusResponse> getProvisionedDevice(String vendorId) {
		SuccessResponse< StatusResponse> response = new SuccessResponse<>();
		StatusResponse status = new StatusResponse();
		status.setStatus(0);
		status.setMessage("Vendor has been provisioned!");
		
		VendorProvision vendorProvision = vendorProvisionRepository.getProvisionedDevice(vendorId);
		if(vendorProvision == null) {
			status.setStatus(-1);
			status.setMessage("Vendor has not been provisioned!");
			response.setInfo(status);
			response.setSuccessful(true);
			return response;
		}
		
		if(!vendorProvision.isActive()) {
			status.setStatus(-2);
			status.setMessage("Vendor provision has been suspended!");
			response.setInfo(status);
			response.setSuccessful(true);
			return response;
		}
		
		if(vendorProvision.getVendor() == null) {
			status.setStatus(-3);
			status.setMessage("Invalid vendor provisioning!");
			response.setInfo(status);
			response.setSuccessful(true);
			return response;
		}
		
		if(!vendorProvision.getVendor().isActive()) {
			status.setStatus(-4);
			status.setMessage("Vendor has been deactivated!");
			response.setInfo(status);
			response.setSuccessful(true);
			return response;
		}
		
		if(vendorProvision.getBlacklisted()) {
			status.setStatus(-5);
			status.setMessage("Vendor application has been blacklisted!");
			response.setInfo(status);
			response.setSuccessful(true);
			return response;
		}
		
		response.setInfo(status);
		response.setSuccessful(true);
		return response;
	}

}
