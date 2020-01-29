/*13 May 2019
charles
*/
package com.seamfix.bioweb.microservices.vendor.provision.rest.client.endpoint;

import com.seamfix.bioweb.microservices.vendor.provision.pojo.StatusResponse;
import com.seamfix.bioweb.microservices.vendor.provision.pojo.SuccessResponse;
import com.seamfix.bioweb.microservices.vendor.provision.pojo.VendorProvisionRequest;
import com.seamfix.bioweb.microservices.vendor.provision.pojo.VendorProvisionResponse;
import com.seamfix.bioweb.microservices.vendor.provision.service.VendorProvisionService;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    @PermitAll
    public SuccessResponse<VendorProvisionResponse> getValidProvisionedDevice(VendorProvisionRequest provisionRequest) {
        return vendorProvisionService.getValidProvisionedDevice(provisionRequest.getVendorId(), provisionRequest.getApplicationKey(), provisionRequest.getApplicationId());
    }

    @POST
    @Path("/status-validity")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public SuccessResponse<StatusResponse> getProvisionedDevice(VendorProvisionRequest provisionRequest) {
        return vendorProvisionService.getProvisionedDevice(provisionRequest.getVendorId());
    }

    public void setVendorProvisionService(VendorProvisionService vendorProvisionService) {
        this.vendorProvisionService = vendorProvisionService;
    }
}
