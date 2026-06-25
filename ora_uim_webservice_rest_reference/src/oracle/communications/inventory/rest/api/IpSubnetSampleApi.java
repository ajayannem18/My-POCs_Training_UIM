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
import oracle.communications.inventory.rest.api.IpSubnetSampleApiService;

import io.swagger.annotations.ApiParam;

import oracle.communications.inventory.rest.model.ErrorType;
import oracle.communications.inventory.rest.model.IpSubnetSampleType;

import java.util.Map;
import java.util.List;

import java.io.InputStream;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.inject.Inject;

import javax.validation.constraints.*;
@Path("/customInventoryManagement/v4/ipSubnetSample")


@io.swagger.annotations.Api(description = "the ipSubnetSample API")
public class IpSubnetSampleApi  {

    @Inject IpSubnetSampleApiService service;

    @POST
    @Path("/{id}/{task}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Process a Subnet", notes = "This operation processes a Subnet entity. Possible tasks are PARTITION, JOIN IP subnet that matches the specified ID. The ID for IP subnets is a composite of three different attributes: <b>IPAddress-PrefixLength-IPAddressDomainName</b> <p>The following values are the possible tasks to process a Subnet&colon;<p> <ul> <li>partition: It partitions the subnet with the provided cidrNumber and subnetNum.</li> <li>join: It Joins the subnet with the provided cidrNumber.</li> </ul> ", response = IpSubnetSampleType.class, tags={ "Sample IpSubnet", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 201, message = "The IPSubnet was updated successfully.", response = IpSubnetSampleType.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "The request isn't valid.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "You aren't authorized to make this request.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "The request is forbidden.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "The resources wasn't found.", response = ErrorType.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "An internal server error occurred.", response = ErrorType.class) })
    public Response processSubnet(@ApiParam(value = "The IpSubnet to be updated." ,required=true) IpSubnetSampleType body, @PathParam("id") String id, @PathParam("task") String task,  @QueryParam("fields") String fields, @Min(0) @Max(10)  @QueryParam("depth") Integer depth,  @QueryParam("expand") String expand,@Context SecurityContext securityContext)
    {
        return service.processSubnet(body,id,task,fields,depth,expand,securityContext);
    }
}
