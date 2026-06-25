/*
**
** Copyright (c) 2023, Oracle and/or its affiliates. All rights reserved.
**
** The Universal Permissive License (UPL), Version 1.0
**
** Subject to the condition set forth below, permission is hereby granted to any
** person obtaining a copy of this software, associated documentation and/or data
** (collectively the "Software"), free of charge and under any and all copyright
** rights in the Software, and any and all patent rights owned or freely
** licensable by each licensor hereunder covering either (i) the unmodified
** Software as contributed to or provided by such licensor, or (ii) the Larger
** Works (as defined below), to deal in both
** 
** (a) the Software, and
** (b) any piece of software and/or hardware listed in the lrgrwrks.txt file if
** one is included with the Software (each a "Larger Work" to which the Software
** is contributed by such licensors),
** 
** without restriction, including without limitation the rights to copy, create
** derivative works of, display, perform, and distribute the Software and make,
** use, sell, offer for sale, import, export, have made, and have sold the
** Software and the Larger Work(s), and to sublicense the foregoing rights on
** either these or other terms.
** 
** This license is subject to the following condition:
** The above copyright notice and either this complete permission notice or at
** a minimum a reference to the UPL must be included in all copies or
** substantial portions of the Software.
** 
** THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
** IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
** FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
** AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
** LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
** OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
** SOFTWARE.
*/

package oracle.communications.inventory.rest.api;

import oracle.communications.inventory.rest.model.*;
import oracle.communications.inventory.rest.api.InventoryGroupApiService;

import io.swagger.annotations.ApiParam;

import oracle.communications.inventory.rest.model.ErrorType;
import oracle.communications.inventory.rest.model.GroupItemRefType;
import oracle.communications.inventory.rest.model.InventoryGroupType;

import java.util.Map;
import java.util.List;

import java.io.InputStream;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.inject.Inject;

import javax.validation.constraints.*;
@Path("/customInventoryManagement/v4/inventoryGroup")


@io.swagger.annotations.Api(description = "the inventoryGroup API")
public class InventoryGroupApi  {

    @Inject InventoryGroupApiService service;

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Create Ig", notes = "Creates a inventory group with the given details ", response = InventoryGroupType.class, responseContainer = "List", tags={ "Sample Inventory Group", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 201, message = "The ig were created successfully.", response = InventoryGroupType.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "The request isn't valid.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "You aren't authorized to make this request.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "The request is forbidden.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "An internal server error occurred.", response = ErrorType.class) })
    public Response createInventoryGroup(@ApiParam(value = "The ig to create." ,required=true) InventoryGroupType body,@Context SecurityContext securityContext)
    {
        return service.createInventoryGroup(body,securityContext);
    }
    @DELETE
    @Path("/{id}")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Delete a IG", notes = "Deletes the ig that matches the specified ID.", response = Void.class, tags={ "Sample Inventory Group", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 204, message = "The IG was deleted successfully.", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "The request isn't valid.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "You aren't authorized to make this request.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "The request is forbidden.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "The logical device wasn't found.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "An internal server error occurred.", response = ErrorType.class) })
    public Response deleteSampleIG( @PathParam("id") String id,@Context SecurityContext securityContext)
    {
        return service.deleteSampleIG(id,securityContext);
    }
    @POST
    @Path("/{id}/groupItem/{task}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Associate Resource to Ig", notes = "Associates a resource to inventory group with the given details ", response = InventoryGroupType.class, tags={ "Sample Inventory Group", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "The ig was returned successfully.", response = InventoryGroupType.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "The request isn't valid.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "You aren't authorized to make this request.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "The request is forbidden.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "The logical device wasn't found.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "An internal server error occurred.", response = ErrorType.class) })
    public Response processAssociationToInventoryGroup(@ApiParam(value = "The Group Item to be associate or unassociated." ,required=true) GroupItemRefType body, @PathParam("id") String id, @PathParam("task") String task,@Context SecurityContext securityContext)
    {
        return service.processAssociationToInventoryGroup(body,id,task,securityContext);
    }
    @GET
    @Path("/{id}")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Get a ig by ID", notes = "Gets the ig that matches the specified ID. ", response = InventoryGroupType.class, tags={ "Sample Inventory Group", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "The ig was returned successfully.", response = InventoryGroupType.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "The request isn't valid.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "You aren't authorized to make this request.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "The request is forbidden.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "The logical device wasn't found.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "An internal server error occurred.", response = ErrorType.class) })
    public Response retrieveSampleIg( @PathParam("id") String id,@Context SecurityContext securityContext)
    {
        return service.retrieveSampleIg(id,securityContext);
    }
    @PATCH
    @Path("/{id}")
    @Consumes({ "application/merge-patch+json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "update Ig", notes = "Updates a inventory group with the given details ", response = InventoryGroupType.class, tags={ "Sample Inventory Group", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "The IG was updated successfully.", response = InventoryGroupType.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "The request isn't valid.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "You aren't authorized to make this request.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "The request is forbidden.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "An internal server error occurred.", response = ErrorType.class) })
    public Response updateInventoryGroup(@ApiParam(value = "The ig to updated." ,required=true) InventoryGroupType body, @PathParam("id") String id,@Context SecurityContext securityContext)
    {
        return service.updateInventoryGroup(body,id,securityContext);
    }
}
