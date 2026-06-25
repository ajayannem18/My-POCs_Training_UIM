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

package oracle.communications.inventory.rest.api.impl;

import oracle.communications.inventory.rest.api.*;
import oracle.communications.inventory.rest.model.*;

import oracle.communications.inventory.rest.model.ErrorType;
import oracle.communications.inventory.rest.model.IpSubnetSampleType;

import java.util.List;
import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import java.io.InputStream;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import javax.inject.Inject;
import oracle.communications.inventory.rest.configuration.RestUtils;

import oracle.communications.inventory.rest.common.*;
import oracle.communications.inventory.rest.exceptions.*;
import oracle.communications.inventory.rest.mapping.*;
import oracle.communications.inventory.rest.constants.*;
import oracle.communications.inventory.rest.configuration.*;
import oracle.communications.inventory.rest.search.*;
import oracle.communications.inventory.rest.validator.*;

import oracle.communications.inventory.api.framework.config.SystemConfig;
import oracle.communications.inventory.api.framework.logging.Log;
import oracle.communications.inventory.api.framework.logging.LogFactory;
import oracle.communications.inventory.api.exception.ValidationException;
import oracle.communications.inventory.api.framework.security.UserEnvironment;
import oracle.communications.inventory.api.logicaldevice.LogicalDeviceManager;
import oracle.communications.inventory.api.util.Utils;
import oracle.communications.inventory.api.common.EntityUtils;
import oracle.communications.inventory.api.ip.IPNetworkManager;

import oracle.communications.platform.persistence.*;
import oracle.communications.inventory.api.entity.*;


@RequestScoped
public class IpSubnetSampleApiServiceImpl implements InventoryRootService,IpSubnetSampleApiService {
    private static Log log = LogFactory.getLog(IpSubnetSampleApiServiceImpl.class);
    
    @Inject
    private RestUtils restUtils;  
    
	public Response processSubnet(IpSubnetSampleType body,String id,String task,String fields,Integer depth,String expand,SecurityContext securityContext)
       {
      // do some magic!
     // return Response.ok().entity("magic!"/*new ApiResponseMessage(ApiResponseMessage.OK, "magic!")*/).build();
    	  debugLog(log, "Entered processSubnet");
          SearchParams searchParams = new SearchParams(fields, depth, expand);
          if(task == null || !(Arrays.asList(SubnetTaskEnumType.PARTITION.toString(), SubnetTaskEnumType.JOIN.toString()).contains(task)))
             throw new DataNotFoundException("rest.entity.InvalidTask", "", task);
          ResourceInputValidator.validateBusinessIdentifier(id, ResourceTypeEnumType.IPSUBNET);
          String resourceId = id.substring(id.indexOf("-") + 1);
//          IPSubnetType subnetResource =
//                  (IPSubnetType) findResourceEntityById(id, ResourceTypeEnumType.IPSUBNET, searchParams, restUtils);
          IpSubnetSampleType ipSubnetSample = new IpSubnetSampleType();
          IPSubnetType subnetResource = new IPSubnetType();
          IPSubnet ipSubnet = null;
    	  UserEnvironment userEnvironment = null;
          InventoryTransactionValue transValue = null;
          //
//          Collection<IPSubnet> subnetList = new ArrayList<IPSubnet>();
//          IPSubnet ipsubnet = null;
          try {
              userEnvironment =  startUserEnvironment(restUtils.getHttpRequest());
              transValue = startTransaction();
              transValue.setUserEnvironment( userEnvironment );
              IPNetworkManager manager = PersistenceHelper.makeIPNetworkManager();
              

              ipSubnet = EntityUtils.findIPSubnetbyID(resourceId);
             // ipSubnet = EntityUtils.findEntityById(IPSubnet.class, resourceId);
                 
                  if (ipSubnet == null)
                      throw new DataNotFoundException("rest.entity.notFoundWithID", "", "IPSubnet", resourceId);
                  
                  if(SubnetTaskEnumType.PARTITION.toString().equals(task)){
                	   manager.partition(ipSubnet, body.getCidrNumber(), body.getSubnetNum());
                  }
                  else if(SubnetTaskEnumType.JOIN.toString().equals(task)){
                	   manager.join(ipSubnet, body.getCidrNumber());
                  }
                  manager.flushTransaction();
                  subnetResource = (IPSubnetType) new ResourceResponseMappingUtils(restUtils.getBaseURL())
                                     .toResourceBase(ipSubnet, new SearchParams(null, null, null), true, restUtils);
                  ipSubnetSample.setSubnet(subnetResource);
                  //partitions already shown in IPSubnet get.                 
                  
          } catch (ValidationException ve) {
              throw new InternalServerException(ve);
          }
          finally
          {
              commitOrRollback( transValue );
          }
          if( log.isDebugEnabled() )
            debugLog(log, "Exited processService with task");
          
          return Response.status(Response.Status.OK)
                         .header("Location", (subnetResource != null && subnetResource.getHref() != null) ? subnetResource.getHref().replace(" ", "%20") : "")
                     .entity(ipSubnetSample)
                     .build();
  }
}
