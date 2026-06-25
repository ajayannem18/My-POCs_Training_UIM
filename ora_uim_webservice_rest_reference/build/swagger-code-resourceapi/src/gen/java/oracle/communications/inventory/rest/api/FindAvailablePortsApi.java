package oracle.communications.inventory.rest.api;

import oracle.communications.inventory.rest.model.*;
import oracle.communications.inventory.rest.api.FindAvailablePortsApiService;

import io.swagger.annotations.ApiParam;

import oracle.communications.inventory.rest.model.FindPortsResponseType;

import java.util.Map;
import java.util.List;

import java.io.InputStream;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.inject.Inject;

import javax.validation.constraints.*;
@Path("/customInventoryManagement/v3/findAvailablePorts")


@io.swagger.annotations.Api(description = "the findAvailablePorts API")
public class FindAvailablePortsApi  {

    @Inject FindAvailablePortsApiService service;

    @GET
    
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Find Available Ports", notes = "Retrieves all logical devices associated with the given Service Location or Network Location and returns available ports. ", response = FindPortsResponseType.class, tags={ "Device Port Management", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Available ports retrieved successfully", response = FindPortsResponseType.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid request", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Location not found", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Void.class) })
    public Response findAvailablePorts(  @QueryParam("serviceLocationCode") String serviceLocationCode,  @QueryParam("networkLocationCode") String networkLocationCode,@Context SecurityContext securityContext)
    {
        return service.findAvailablePorts(serviceLocationCode,networkLocationCode,securityContext);
    }
}
