/*13 May 2019
charles
*/
package com.seamfix.bioweb.microservices.vendor.provision.rest.client.endpoint;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.seamfix.bioweb.microservices.vendor.provision.pojo.StatusResponse;
import com.seamfix.bioweb.microservices.vendor.provision.pojo.SuccessResponse;
import com.seamfix.bioweb.microservices.vendor.provision.service.VendorProvisionService;

@Path("/")
public class DeviceProvisionEndPoint {

	@Named("deviceProvisionService")
	@Inject
	private VendorProvisionService deviceProvisionService;
	
	@POST
	@Path("/status")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public SuccessResponse<StatusResponse> isDeviceProvisioned(@Context HttpServletRequest httpServletRequest, @Context HttpServletResponse httpServletResponse, @FormParam("vendorId") String vendorId) {
		return deviceProvisionService.isDeviceProvisioned(vendorId);
	}
}
