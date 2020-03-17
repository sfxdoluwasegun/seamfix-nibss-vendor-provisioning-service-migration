/*13 May 2019
charles
*/
package com.seamfix.bioweb.microservices.vendor.provision.rest.client.endpoint;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.seamfix.bioweb.microservices.vendor.provision.pojo.StatusResponse;
import com.seamfix.bioweb.microservices.vendor.provision.pojo.SuccessResponse;
import com.seamfix.bioweb.microservices.vendor.provision.pojo.VendorProvisionResponse;
import com.seamfix.bioweb.microservices.vendor.provision.service.VendorProvisionService;

@Path("/")
public class VendorProvisionEndPoint {

	@Named("vendorProvisionService")
	@Inject
	private VendorProvisionService vendorProvisionService;
	
	@GET
	@Path("/ping")
	@Produces("text/plain")
	@PermitAll
	public Response doGet() {
		return Response.ok("Vendor provisioning service is up and running!").build();
	}
	
	@POST
	@Path("/status")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@PermitAll
	public SuccessResponse<VendorProvisionResponse> getValidProvisionedDevice(@Context HttpServletRequest httpServletRequest, @Context HttpServletResponse httpServletResponse , @FormParam("vendorId") String vendorId, @FormParam("applicationKey") String appKey, @FormParam("applicationId") String appId) {
		return vendorProvisionService.getValidProvisionedDevice(vendorId, appKey, appId);
	}
	
	@POST
	@Path("/status-validity")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@PermitAll
	public SuccessResponse<StatusResponse> getProvisionedDevice(@Context HttpServletRequest httpServletRequest, @Context HttpServletResponse httpServletResponse, @FormParam("vendorId") String vendorId) {
		return vendorProvisionService.getProvisionedDevice(vendorId);
	}

	public void setVendorProvisionService(VendorProvisionService vendorProvisionService) {
		this.vendorProvisionService = vendorProvisionService;
	}
}
