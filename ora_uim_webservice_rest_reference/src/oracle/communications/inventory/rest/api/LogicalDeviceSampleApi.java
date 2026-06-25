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
import oracle.communications.inventory.rest.api.LogicalDeviceSampleApiService;

import io.swagger.annotations.ApiParam;

import oracle.communications.inventory.rest.model.ErrorType;
import oracle.communications.inventory.rest.model.LogicalDeviceSampleType;

import java.util.Map;
import java.util.List;

import java.io.InputStream;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.inject.Inject;

import javax.validation.constraints.*;
@Path("/customInventoryManagement/v4/logicalDeviceSample")


@io.swagger.annotations.Api(description = "the logicalDeviceSample API")
public class LogicalDeviceSampleApi  {

    @Inject LogicalDeviceSampleApiService service;

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Create Logical Devices", notes = "Associate IG to LD ", response = LogicalDeviceSampleType.class, responseContainer = "List", tags={ "Sample Logical Device", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 201, message = "The logical devices were created successfully.", response = LogicalDeviceSampleType.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "The request isn't valid.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "You aren't authorized to make this request.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "The request is forbidden.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "An internal server error occurred.", response = ErrorType.class) })
    public Response createSampleLogicalDevice(@ApiParam(value = "The logical device to create." ,required=true) LogicalDeviceSampleType body,@Context SecurityContext securityContext)
    {
        return service.createSampleLogicalDevice(body,securityContext);
    }
    @DELETE
    @Path("/{id}")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Delete a Logical Device", notes = "Deletes the logical device that matches the specified ID.", response = Void.class, tags={ "Sample Logical Device", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 204, message = "The logical device was deleted successfully.", response = Void.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "The request isn't valid.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "You aren't authorized to make this request.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "The request is forbidden.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "The logical device wasn't found.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "An internal server error occurred.", response = ErrorType.class) })
    public Response deleteSampleLogicalDevice( @PathParam("id") String id,@Context SecurityContext securityContext)
    {
        return service.deleteSampleLogicalDevice(id,securityContext);
    }
    @GET
    @Path("/{id}")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Get a Logical Device by ID", notes = "Gets the logical device that matches the specified ID. <p>Attribute selection is enabled for all first-level attributes.<p> Optionally, use the <b>expand</b> parameter to specify any resource relationships (resourceRelationship.PARENT, resourceRelationship.CHILD, resourceRelationship.ASSOCIATES, resourceRelationship.INVOLVE, resourceRelationship.MAPPEDTO, resourceRelationship.ASSIGN) that you want to see details for. By default, you'll just see links for these.<p> Optionally, use the <b>depth</b> parameter to expand the level of referenced entities. If <b>depth=0</b>, all referenced entities in RootEntity objects will contain only the ID, HREF, and @type. If <b>depth=N</b>, it expands reference objects of related entities recursively, and the last level contains only the references. The default is <b>depth=0</b>. <p> ", response = LogicalDeviceSampleType.class, tags={ "Sample Logical Device", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "The logical device was returned successfully.", response = LogicalDeviceSampleType.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "The request isn't valid.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "You aren't authorized to make this request.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "The request is forbidden.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "The logical device wasn't found.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "An internal server error occurred.", response = ErrorType.class) })
    public Response retrieveSampleLogicalDevice( @PathParam("id") String id,  @QueryParam("fields") String fields, @Min(0) @Max(10)  @QueryParam("depth") Integer depth,  @QueryParam("expand") String expand,@Context SecurityContext securityContext)
    {
        return service.retrieveSampleLogicalDevice(id,fields,depth,expand,securityContext);
    }
    @POST
    @Path("/{id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Update Logical Devices", notes = "Associate IG to LD ", response = LogicalDeviceSampleType.class, tags={ "Sample Logical Device", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "The logical device was updated successfully.", response = LogicalDeviceSampleType.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "The request isn't valid.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "You aren't authorized to make this request.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "The request is forbidden.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "An internal server error occurred.", response = ErrorType.class) })
    public Response updateSampleLogicalDevice(@ApiParam(value = "The logical device to create." ,required=true) LogicalDeviceSampleType body, @PathParam("id") String id,@Context SecurityContext securityContext)
    {
        return service.updateSampleLogicalDevice(body,id,securityContext);
    }
}
