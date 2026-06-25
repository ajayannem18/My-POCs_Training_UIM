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
import oracle.communications.inventory.rest.model.LogicalDeviceSampleType;

import java.util.List;
import java.math.BigDecimal;

import java.util.ArrayList;
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
import oracle.communications.inventory.api.group.InventoryGroupManager;
import oracle.communications.inventory.api.util.Utils;
import oracle.communications.inventory.api.common.EntityUtils;

import oracle.communications.platform.persistence.*;
import oracle.communications.inventory.api.entity.*;


@RequestScoped
public class LogicalDeviceSampleApiServiceImpl implements InventoryRootService, LogicalDeviceSampleApiService {
	
    private static Log log = LogFactory.getLog(LogicalDeviceSampleApiServiceImpl.class);
    
    @Inject
    private RestUtils restUtils;
    
    @Inject
    private InventoryGroupApiServiceImpl InventoryGroupApiService; 
    
    
      public Response createSampleLogicalDevice(LogicalDeviceSampleType body,SecurityContext securityContext)
       {
      // do some magic!
      return Response.ok().entity("magic!"/*new ApiResponseMessage(ApiResponseMessage.OK, "magic!")*/).build();
  }
      public Response deleteSampleLogicalDevice(String id,SecurityContext securityContext)
       {
      // do some magic!
     // return Response.ok().entity("magic!"/*new ApiResponseMessage(ApiResponseMessage.OK, "magic!")*/).build();
          if (Utils.isEmpty(id)){
        	  throw new InvalidRequestException("restws.invalidDeletionRequest",
                      "",
                      restUtils.getQueryString());
          }
          //Business identifier is mentioned in documentation
          ResourceInputValidator.validateBusinessIdentifier(id, ResourceTypeEnumType.LOGICALDEVICE);
          String resourceId = id.substring(id.indexOf("-") + 1); // id after removing business identifier
          this.deleteSampleLogicalDeviceById(resourceId);
          return Response.status(Response.Status.NO_CONTENT).build();
  }
      public void deleteSampleLogicalDeviceById(String logicalDeviceId) {
          debugLog(log, "Entered deleteSampleLogicalDeviceById: " + logicalDeviceId);

          //find inventory resource
          InventoryTransactionValue transValue = null;
          UserEnvironment userEnvironment = startUserEnvironment(restUtils.getHttpRequest());
          transValue = startTransaction();
          transValue.setUserEnvironment(userEnvironment);
          try {
              LogicalDevice ld = EntityUtils.findEntityById(LogicalDevice.class, logicalDeviceId);
              if (ld == null)
                  throw new DataNotFoundException("rest.entity.notFoundWithID", "", "LogicalDevice", logicalDeviceId);

              LogicalDeviceManager manager = PersistenceHelper.makeLogicalDeviceManager();
              manager.deleteLogicalDevice(Collections.singleton(ld));
          } catch (ValidationException ve) {
              throw new InternalServerException(ve);
          } finally {
              commitOrRollback(transValue);
          }
          debugLog(log, "Exited deleteLogicalDeviceById:" + logicalDeviceId);

      }
      public Response retrieveSampleLogicalDevice(String id,String fields,Integer depth,String expand,SecurityContext securityContext)
       {
      // do some magic!
     // return Response.ok().entity("magic!"/*new ApiResponseMessage(ApiResponseMessage.OK, "magic!")*/).build();
          debugLog(log, "Entered retrieveSampleLogicalDevice");
          LogicalDeviceSampleType ldSampleResource = new LogicalDeviceSampleType();
          SearchParams searchParams = new SearchParams(fields, depth, expand);
          LogicalDeviceType ldResource =
              (LogicalDeviceType) findResourceEntityById(id, ResourceTypeEnumType.LOGICALDEVICE, searchParams, restUtils);      
          debugLog(log, "Exited retrieveSampleLogicalDevice");
         // ldSampleResource.setId(ldResource.getId());
         // ldSampleResource.setHref(ldResource.getHref());
         // ldSampleResource.set_atType(ResourceTypeEnumType.LOGICALDEVICE);
         // ldSampleResource.setName(ldResource.getName());
         // ldSampleResource.setResourceSpecification(ldResource.getResourceSpecification());
         // return Response.status(Response.Status.OK).header("Last-Modified",restUtils.getLastModifiedDate()).entity(ldSampleResource).build(); 
          return Response.status(Response.Status.OK).header("Last-Modified",restUtils.getLastModifiedDate()).entity(ldResource).build();         	  
  }
      public Response updateSampleLogicalDevice(LogicalDeviceSampleType body,String id,SecurityContext securityContext)
       {
      // do some magic!
      //return Response.ok().entity("magic!"/*new ApiResponseMessage(ApiResponseMessage.OK, "magic!")*/).build();
          debugLog(log, "Entered updateSampleLogicalDevice");
          ResourceInputValidator.validateBusinessIdentifier(id, ResourceTypeEnumType.LOGICALDEVICE);
          InputValidator.validateQueryString(restUtils.getParameterMap());

          String logicalDeviceId = id.substring(id.indexOf("-") + 1);
          LogicalDevice ld = EntityUtils.findEntityById(LogicalDevice.class, logicalDeviceId);
          if (ld == null)
              throw new DataNotFoundException("rest.entity.notFoundWithID", "", "logicalDevice", id);
          LogicalDeviceSampleType ldType = updateSampleLogicalDeviceById(ld, body, restUtils);
          Response response = Response.status(Response.Status.OK)
                             .entity(ldType)
                             .build();
          response.getHeaders().add("Last-Modified",restUtils.getLastModifiedDate()); 
          return response; 
  }
   public LogicalDeviceSampleType updateSampleLogicalDeviceById(LogicalDevice ld, LogicalDeviceSampleType inputLD, RestUtils restUtils) {
       UserEnvironment userEnvironment = null;
       InventoryTransactionValue transValue = null;
		LogicalDeviceType ldType = inputLD.getLogicalDevice();
		LogicalDeviceSampleType ldSampleType = new LogicalDeviceSampleType();
        SearchParams searchParams = null;
       try {
           userEnvironment = startUserEnvironment(restUtils.getHttpRequest());
           transValue = startTransaction();
           transValue.setUserEnvironment(userEnvironment);

           LogicalDeviceType currLDType =
               (LogicalDeviceType) new ResourceResponseMappingUtils(restUtils.getBaseURL())
               .toResourceBase(ld, new SearchParams(null, null, null), true, restUtils);

           List<LogicalDevice> lds = new ArrayList<LogicalDevice>();
           LogicalDeviceManager logicaldeviceManager = PersistenceHelper.makeLogicalDeviceManager();
           LogicalDevice updatedLD = ld;

           //handle ig relationships
           //List<GroupRefType> outputgrpRef = inputLD.getInventoryGroup();
		  if (inputLD.getInventoryGroup() != null) {
	           InventoryGroupManager invGrpManager =
	                   PersistenceHelper.makeInventoryGroupManager();
	           //               entity =
               //invGrpManager.associateInventoryGroupsToPersistent(entity, groups);
		      List<GroupRefType> grpRef = inputLD.getInventoryGroup();
		      Collection<InventoryGroup> grplist = new ArrayList<InventoryGroup>();
		      InventoryGroup ig = null;
		      for (GroupRefType relatedResource : grpRef) {
		    	  ig = EntityUtils.findEntityByName(InventoryGroup.class, relatedResource.getName());
		    	  grplist.add(ig);	  
		      }
		      if (grplist != null & grplist.size() >0) {
		    	  InventoryGroup[] invlist = grplist.toArray(new InventoryGroup[grplist.size()]);
		    	  invGrpManager.associateInventoryGroupsToPersistent(ld, invlist);
		          invGrpManager.flushTransaction();
		      }
		      logicaldeviceManager.flushTransaction();
			  if (invGrpManager.getInventoryGroupsForResource(ld) != null) {
				  List<InventoryGroup> inventoryGroups = invGrpManager.getInventoryGroupsForResource(ld);
				  for (InventoryGroup ref : inventoryGroups) {
				  GroupRefType gp = InventoryGroupApiService.toGroupRef(ref,restUtils);
				  ldSampleType.addInventoryGroupItem(gp);
				  }
			  }
	       }

           // convert created LogicalDevice to outputLDType
		//  LogicalDeviceType updatedLDType = (LogicalDeviceType) new ResourceResponseMappingUtils(restUtils.getBaseURL())
                 // .toResourceBase(updatedLD, searchParams, true,restUtils);
		  
		  ldSampleType.setLogicalDevice(currLDType);
		  } catch (Exception ve) {
			  log.error(ve.getMessage(),new ValidationException());
       } finally {
           commitOrRollback(transValue);
       }
	   return ldSampleType;
   }
}
