package oracle.communications.inventory.rest.api;

import oracle.communications.inventory.rest.model.*;
import oracle.communications.inventory.rest.api.PhysicalPortsApiService;

import io.swagger.annotations.ApiParam;

import oracle.communications.inventory.rest.model.ReservePortRequestType;
import oracle.communications.inventory.rest.model.ReservePortResponseType;

import java.util.Map;
import java.util.List;

import java.io.InputStream;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.inject.Inject;

import javax.validation.constraints.*;
@Path("/customInventoryManagement/v3/physicalPorts")


@io.swagger.annotations.Api(description = "the physicalPorts API")
public class PhysicalPortsApi  {

    @Inject PhysicalPortsApiService service;

    @POST
    @Path("/reserveFeasibility")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Reserve a Physical Port for Feasibility", notes = "Finds the nearest POP using customer latitude and longitude. Searches all Physical Devices in that POP. Filters ports that are:   - Not Assigned   - Do not have any active Condition. Reserves the first available port. Creates an INFORMATIONAL Condition. Returns Reservation Number and Port ID. ", response = ReservePortResponseType.class, tags={ "Physical Port", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Reservation completed successfully", response = ReservePortResponseType.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid Request", response = ReservePortResponseType.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "No Free Port Available", response = ReservePortResponseType.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = ReservePortResponseType.class) })
    public Response reserveFeasibilityPort(@ApiParam(value = "" ,required=true) ReservePortRequestType body,@Context SecurityContext securityContext)
    {
        return service.reserveFeasibilityPort(body,securityContext);
    }
}
