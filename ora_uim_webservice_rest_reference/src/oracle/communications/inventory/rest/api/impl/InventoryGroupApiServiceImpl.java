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
import oracle.communications.inventory.rest.model.GroupItemRefType;
import oracle.communications.inventory.rest.model.InventoryGroupType;

import java.util.List;
import java.util.Set;

import java.io.InputStream;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import java.util.List;
import java.math.BigDecimal;

import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import java.io.InputStream;

import java.net.URLEncoder;

import java.util.Objects;

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
import oracle.communications.inventory.api.group.InventoryGroupManager;
import oracle.communications.inventory.api.logicaldevice.LogicalDeviceManager;
import oracle.communications.inventory.api.util.Utils;
import oracle.communications.inventory.api.common.EntityUtils;

import oracle.communications.platform.persistence.*;
import oracle.communications.inventory.api.entity.*;
import oracle.communications.inventory.api.entity.common.*;
import oracle.communications.inventory.api.specification.*;
import oracle.communications.inventory.rest.model.SpecificationType;

import oracle.communications.inventory.api.TimePeriod;
import oracle.communications.inventory.api.common.AttachmentManager;
import oracle.communications.inventory.api.group.*;
import java.util.function.Predicate;


@RequestScoped
public class InventoryGroupApiServiceImpl implements InventoryRootService, InventoryGroupApiService {
	
    private static Log log = LogFactory.getLog(InventoryGroupApiServiceImpl.class);
    
    @Inject
    private RestUtils restUtils;
    @Inject
    private RestCustomUtils restCustomUtils;
    
      public Response createInventoryGroup(InventoryGroupType body,SecurityContext securityContext)
       {
      // do some magic!
     // return Response.ok().entity("magic!"/*new ApiResponseMessage(ApiResponseMessage.OK, "magic!")*/).build();
          debugLog(log, "Entered createInventoryGroup");
          InventoryGroupType ig =
        		  createIgType(body,  restUtils);
          Response response = Response.status(Response.Status.CREATED)
                             .entity(ig)
                             .build();
              response.getHeaders().add("Last-Modified",restUtils.getLastModifiedDate()); 
          return response;
  }
      public InventoryGroupType createIgType(InventoryGroupType inputIG, RestUtils restUtils) {

          debugLog(log, "Entered createIgType");
          ResourceInputValidator.validatePlaceInput(inputIG.getPlace());

          UserEnvironment userEnvironment = null;
          InventoryTransactionValue transValue = null;
          InventoryGroupManager lgManager = PersistenceHelper.makeInventoryGroupManager();
		  InventoryGroup result = null;
		  InventoryGroupType resource = null;
          try {
              userEnvironment = startUserEnvironment(restUtils.getHttpRequest());
              transValue = startTransaction();
              transValue.setUserEnvironment(userEnvironment);
              InventoryGroup ig = lgManager.makeInventoryGroup();
              this.populateIG(ig, inputIG);
              result = lgManager.createInventoryGroup(ig);
              lgManager.flushTransaction();
              //handle IG relationships
              if (!Utils.isEmpty(inputIG.getParentGroupRef()) && result != null) {
            	  result = processParentGroupRelationships(result, inputIG.getParentGroupRef());
                  lgManager.flushTransaction();
              }
				
				//handle GroupItems Association
              if (!Utils.isEmpty(inputIG.getInventoryGroupItems()) && result != null) {
            	  result = processGroupItemsAssociations(result, inputIG.getInventoryGroupItems());
                  lgManager.flushTransaction();
              }
               if (!Utils.isEmpty(inputIG.getPlace()) && result != null) {
                      processIGPlaceAssociation(result, inputIG.getPlace());
               }
               
               resource = toInventoryGroup(result, restUtils);
              
              //Group Items
              InventoryGroupEntitySearchCriteria igSearchCriteria = lgManager.makeInventoryGroupEntitySearchCriteria();
              igSearchCriteria.setInventoryGroup((InventoryGroup)result);
              igSearchCriteria.setRange(0, 50);
              List<InvGroupRef> invGroupRefs = lgManager.findInvGroupRefsForInventoryGroup(igSearchCriteria);
              List<GroupItemRefType> gpItemRefs = getInventoryGroupItems(invGroupRefs,restUtils);                  
              if (invGroupRefs.size() > 0) {
                 resource.setInventoryGroupItems(gpItemRefs);
                // resource.addInventoryGroupItemsItem(gpItemRef);
            }
        } catch (ValidationException ve) {
            throw new InternalServerException(ve);
        } catch (Exception e) {
            throw new InternalServerException("rest.genericError", "", e.getMessage());
        } finally {
            commitOrRollback(transValue);
            debugLog(log, "Exited createIgType");
        }
        return resource;
    }
      public Response deleteSampleIG(String id,SecurityContext securityContext)
       {
      // do some magic!
      //return Response.ok().entity("magic!"/*new ApiResponseMessage(ApiResponseMessage.OK, "magic!")*/).build();
    	  if (Utils.isEmpty(id)){
        	  throw new InvalidRequestException("restws.invalidDeletionRequest",
                      "",
                      restUtils.getQueryString());
          }
          //Business identifier is mentioned in documentation
          validateBusinessIdentifierForIG(id);
          String igName = id.substring(id.indexOf("-") + 1); // id after removing business identifier
          this.deleteSampleInventoryGroupByName(igName);
          return Response.status(Response.Status.NO_CONTENT).build();
  }
      public void deleteSampleInventoryGroupByName(String igName) {
          debugLog(log, "Entered deleteSampleInventoryGroupByName: " + igName);

          //find inventory resource
          InventoryTransactionValue transValue = null;
          UserEnvironment userEnvironment = startUserEnvironment(restUtils.getHttpRequest());
          transValue = startTransaction();
          transValue.setUserEnvironment(userEnvironment);
          try {
        	  InventoryGroup ig = EntityUtils.findEntityByName(InventoryGroup.class, igName);
              if (ig == null)
                  throw new DataNotFoundException("rest.entity.notFoundWithID", "", "InventoryGroup", igName);

              InventoryGroupManager manager = PersistenceHelper.makeInventoryGroupManager();
              manager.deleteInventoryGroup(ig);
          } catch (ValidationException ve) {
              throw new InternalServerException(ve);
          } finally {
              commitOrRollback(transValue);
          }
          debugLog(log, "Exited deleteSampleInventoryGroupByName:" + igName);
      }
      public Response retrieveSampleIg(String id,SecurityContext securityContext)
       {
      // do some magic!
      //return Response.ok().entity("magic!"/*new ApiResponseMessage(ApiResponseMessage.OK, "magic!")*/).build();
          debugLog(log, "Entered retrieveSampleIg");
          InventoryGroupType igResource =
              (InventoryGroupType) findIGByName(id, restUtils);
          debugLog(log, "Exited retrieveSampleIg");
          return Response.status(Response.Status.OK).header("Last-Modified",restUtils.getLastModifiedDate()).entity(igResource).build();
      }
    public Response updateInventoryGroup(InventoryGroupType body,String id,SecurityContext securityContext)
     {
        debugLog(log, "Entered updateInventoryGroup");

        InventoryGroupType igType = mergeInventoryGroupById(id, body, restUtils);
        Response response = Response.status(Response.Status.OK)
                           .entity(igType)
                           .build();
                response.getHeaders().add("Last-Modified",restUtils.getLastModifiedDate()); 
        return response;           
    }
    public InventoryGroupType mergeInventoryGroupById(String id, InventoryGroupType inputIG, RestUtils restUtils) {

        debugLog(log, "Entered mergeInventoryGroupById");
        ResourceInputValidator.validatePlaceInput(inputIG.getPlace());

        UserEnvironment userEnvironment = null;
        InventoryTransactionValue transValue = null;
        InventoryGroupManager lgManager = PersistenceHelper.makeInventoryGroupManager();
                InventoryGroup updatedIG = null;
                InventoryGroupType currInventoryGroupType = null;
                InventoryGroupType updatedInventoryGroupType = null;
        try {
            userEnvironment = startUserEnvironment(restUtils.getHttpRequest());
            transValue = startTransaction();
            transValue.setUserEnvironment(userEnvironment);
            
            validateBusinessIdentifierForIG(id);
            String igName = id.substring(id.indexOf("-") + 1);
            InventoryGroup inventoryGrp =  EntityUtils.findEntityByName(InventoryGroup.class, igName);
            if (inventoryGrp == null)
                throw new DataNotFoundException("rest.entity.notFoundWithID", "", "InventoryGroup", id);
            
            
            currInventoryGroupType = this.toInventoryGroup(inventoryGrp, restUtils);
            
            //Group Items
            InventoryGroupEntitySearchCriteria currIgSearchCriteria = lgManager.makeInventoryGroupEntitySearchCriteria();
            currIgSearchCriteria.setInventoryGroup((InventoryGroup)inventoryGrp);
            currIgSearchCriteria.setRange(0, 50);
            List<InvGroupRef> currInvGroupRefes = lgManager.findInvGroupRefsForInventoryGroup(currIgSearchCriteria);
            List<GroupItemRefType> currGpItemRefs = getInventoryGroupItems(currInvGroupRefes,restUtils);                  
            if (currInvGroupRefes.size() > 0) {
               currInventoryGroupType.setInventoryGroupItems(currGpItemRefs); 
               // resource.addInventoryGroupItemsItem(gpItemRef);
            }
            //update
            if (inputIG.getName() != null){
                inventoryGrp.setName(inputIG.getName());
            }
            if (inputIG.getDescription() != null){
                inventoryGrp.setName(inputIG.getDescription());
            }
            if (inputIG.getStartDate() != null && inputIG.getEndDate() != null){
                TimePeriod tp = new TimePeriod(inputIG.getStartDate(),inputIG.getEndDate());
                inventoryGrp.setValidFor(tp);
            } else if (inputIG.getStartDate() != null){
                TimePeriod tp = new TimePeriod(inputIG.getStartDate(),inventoryGrp.getValidFor().getEndDate());
                inventoryGrp.setValidFor(tp);
            } else if (inputIG.getEndDate() != null) {
                TimePeriod tp = new TimePeriod(inventoryGrp.getValidFor().getStartDate(),inputIG.getEndDate());
                inventoryGrp.setValidFor(tp);
            }
            updatedIG = lgManager.updateInventoryGroup(inventoryGrp);
            
			if (inputIG.getParentGroupRef() != null) {
				 updatedIG = mergeParentGroupRelationships(updatedIG, currInventoryGroupType.getParentGroupRef(), inputIG.getParentGroupRef());
                lgManager.flushTransaction();
            }
			
			if (inputIG.getInventoryGroupItems() != null) {
				 updatedIG = mergeGroupItemsAssociations(updatedIG, currInventoryGroupType.getInventoryGroupItems(), inputIG.getInventoryGroupItems());
                lgManager.flushTransaction();
            }
			
            if (inputIG.getPlace() != null) {
                mergePlaceAssociations(updatedIG, currInventoryGroupType.getPlace(), inputIG.getPlace());
                lgManager.flushTransaction();
            }            
            updatedInventoryGroupType = toInventoryGroup(updatedIG, restUtils);
            
            //Group Items
            InventoryGroupEntitySearchCriteria igSearchCriteria = lgManager.makeInventoryGroupEntitySearchCriteria();
            igSearchCriteria.setInventoryGroup((InventoryGroup)updatedIG);
            igSearchCriteria.setRange(0, 50);
            List<InvGroupRef> invGroupRefs = lgManager.findInvGroupRefsForInventoryGroup(igSearchCriteria);
            List<GroupItemRefType> gpItemRefs = getInventoryGroupItems(invGroupRefs,restUtils);                  
            if (invGroupRefs.size() > 0) {
               updatedInventoryGroupType.setInventoryGroupItems(gpItemRefs); 
               // resource.addInventoryGroupItemsItem(gpItemRef);
            }
        } catch (ValidationException ve) {
           throw new InternalServerException("",ve.getMessage(),"");
        } finally {
            commitOrRollback(transValue);
            debugLog(log, "Exited mergeInventoryGroupById");
        }
        return updatedInventoryGroupType;
    }
      public InventoryGroupType findIGByName(String id, RestUtils restUtils) {
          
           //find inventory resource
           InventoryTransactionValue transValue = null;
           UserEnvironment userEnvironment = startUserEnvironment(restUtils.getHttpRequest());
           transValue = startTransaction();
           transValue.setUserEnvironment(userEnvironment);
           InventoryGroupManager igManager = PersistenceHelper.makeInventoryGroupManager();
           InventoryGroupType resource = null;
           Persistent pc = null;
           try {
        	   if (Utils.isEmpty(id)){
             	  throw new InvalidRequestException("restws.invalidDeletionRequest",
                           "",
                           restUtils.getQueryString());
               }
               validateBusinessIdentifierForIG(id);
               String igName = id.substring(id.indexOf("-") + 1); // id after removing business identifier
               pc = EntityUtils.findEntityByName(InventoryGroup.class, igName);
               if (pc == null)
                   throw new DataNotFoundException("rest.entity.notFoundWithID", "", "InventoryGroup", igName);
               resource = this.toInventoryGroup(pc, restUtils);
               
               //Group Items
               InventoryGroupEntitySearchCriteria igSearchCriteria = igManager.makeInventoryGroupEntitySearchCriteria();
               igSearchCriteria.setInventoryGroup((InventoryGroup)pc);
               igSearchCriteria.setRange(0, 50);
               List<InvGroupRef> invGroupRefs = igManager.findInvGroupRefsForInventoryGroup(igSearchCriteria);
               List<GroupItemRefType> gpItemRefs = getInventoryGroupItems(invGroupRefs,restUtils);                  
               if (invGroupRefs.size() > 0) {
                  resource.setInventoryGroupItems(gpItemRefs); 
                  // resource.addInventoryGroupItemsItem(gpItemRef);
               }
           } catch (ValidationException ve) {
   			    throw new InternalServerException(ve);
           } finally {
               commitOrRollback(transValue);
           }
           return resource;
  }
      public InventoryGroupType toInventoryGroup(Persistent entity,  RestUtils restUtils) {
          debugLog(log, "Entered toInventoryGroup for " + entity.getOid());
          ResourceResponseMappingUtils map = new ResourceResponseMappingUtils(restUtils.getBaseURL());
          InventoryGroupType result = new InventoryGroupType();
          if (entity == null)
              return null;
          if (entity instanceof InventoryGroup) {
        	  InventoryGroup ig = (InventoryGroup) entity;
        	  debugLog(log, "Entered toInventoryGroup " + ig.getName());
              result.setId("38" + "-" + ig.getName());
              String href = restCustomUtils.getCustomBaseURL()+ restCustomUtils.getCustomBaseUri() + "/inventoryGroup" + "/" + "38-" + ig.getName();
              result.setHref(href);
              result.setName(ig.getName());
              result.setDescription(ig.getDescription());
              Specification spec = ig.getSpecification();
              if( spec == null )
                  return null;
              SpecificationType specResult = new SpecificationType();
              specResult.setName( spec.getName() );
              specResult.setId(spec.getName() );
              //specResult.setHref(restUtils.getBaseURL() + "/specification/" + URLEncoder.encode(spec.getName()).replace("+", "%20"));
              specResult.setDescription( spec.getDescription() );
              specResult.setVersion("1");
              TimePeriod validFor = spec.getValidFor();
              if( validFor != null && validFor.getStartDate() != null)
                  specResult.setStartDate( validFor.getStartDate());
              if ( validFor != null && validFor.getEndDate() != null)
                      specResult.setEndDate( validFor.getEndDate());
              if ( validFor != null && validFor.getEndDate() == null)
                      specResult.setEndDate( oracle.communications.platform.util.Utils.getMaxDate());
              result.setIgSpecification(specResult);
              result.setStartDate(ig.getValidFor().getStartDate());
              result.setEndDate(ig.getValidFor().getEndDate());
              if (!Utils.isEmpty(ig.getPlace())) {
                  List<PlaceRefType> places = new ArrayList<PlaceRefType>();
                  for (PlaceInventoryGroupRel placeRel : ig.getPlace()) {               	  
                      places.add(this.toIgPlace(placeRel.getGeographicPlace(), placeRel.getFromEntityRoleKey(),
                                         placeRel.getToEntityRoleKey(), restUtils));
                  }
                  result.setPlace(places);
              }
              Set<InvGroupRel> parents = ig.getParentGroups();
              List<GroupRefType> parentGroupRef = new ArrayList<GroupRefType>();;
              for(InvGroupRel parent : parents){
            	  parentGroupRef.add(toGroupRef(parent.getParentInvGroup(), restUtils));
       	  
               }
              result.setParentGroupRef(parentGroupRef);     
          }
          return result;
      }
      public GroupRefType toGroupRef(InventoryGroup ig,  RestUtils restUtils) {
    	  GroupRefType result = new GroupRefType();
          ResourceResponseMappingUtils map = new ResourceResponseMappingUtils(restUtils.getBaseURL());
        	  debugLog(log, "Entered toGroupRef " + ig.getName());
              if (ig == null)
                  return null;
              result.setId("38" + "-" + ig.getName());
              String href = restCustomUtils.getCustomBaseURL() + restCustomUtils.getCustomBaseUri() + "/inventoryGroup" + "/" + "38-" + ig.getName();
              result.setHref(href);
              result.setName(ig.getName());
          Specification spec = ig.getSpecification();
          if( spec == null )
              return null;
              SpecificationType specResult = new SpecificationType();
              specResult.setName( spec.getName() );
              specResult.setId(spec.getName() );
             // specResult.setHref(restUtils.getBaseURL() + "/specification/" + URLEncoder.encode(spec.getName()).replace("+", "%20"));
              specResult.setDescription( spec.getDescription() );
              specResult.setVersion("1");
              TimePeriod validFor = spec.getValidFor();
              if( validFor != null && validFor.getStartDate() != null)
                  specResult.setStartDate( validFor.getStartDate());
              if ( validFor != null && validFor.getEndDate() != null)
                      specResult.setEndDate( validFor.getEndDate());
              if ( validFor != null && validFor.getEndDate() == null)
                      specResult.setEndDate( oracle.communications.platform.util.Utils.getMaxDate());
              result.setIgSpecification(specResult);  
              return result;
      }
      public PlaceRefType toIgPlace(Place place, String roleOid, String resourceOid, RestUtils restUtils) {
          if(place == null)
              return null;
          PlaceRefType result = new PlaceRefType();
          
          if (place instanceof PropertyLocation) {
                  PropertyLocation loc = (PropertyLocation) place;
                  if (loc.isNetworkLocation()) {
                      result.setId(loc.getNetworkLocationCode());
                      result.setHref(restUtils.getBaseURL() + "/place/" + loc.getNetworkLocationCode());
                  } else {
                      result.setId(place.getName());
                      result.setHref(restUtils.getBaseURL() + "/place/" + place.getName());  
                  }
                  result.setAtReferredType(PlaceReferredTypeEnumType.PROPERTYLOCATION);
                  result.setName(place.getName());
          }
          else {
              result.setId(place.getId());
              result.setHref(restUtils.getBaseURL() + "/place/" + place.getId());
              //MODIFY BELOW
              result.setAtReferredType(PlaceReferredTypeEnumType.fromValue(place.getClass().getSimpleName().substring(0, place.getClass().getSimpleName().length() -3)));
              result.setName(place.getName());
              result.setRole(getRoleName(roleOid));
              result.setReferrerRole(getRoleName(resourceOid));
          }

          return result;
      }
      protected String getRoleName(String roleOid) {
          if (roleOid != null) {
              PersistenceManager pm = PersistenceHelper.makePersistenceManager();
              InventoryRole invRole = (InventoryRole) pm.getObjectById(roleOid);
              if (null != invRole)
                  return invRole.getSpecification().getName();
          }
          return null;
      }     
      private static void populateIG(InventoryGroup resource, InventoryGroupType source) throws ValidationException {
    	  SpecManager specMgr = null;
          specMgr = PersistenceHelper.makeSpecManager();
  		SpecificationType sourceSpec = source.getIgSpecification();
          InventoryGroupSpecification spec = specMgr.findSpecification(InventoryGroupSpecification.class, sourceSpec.getId());
          if (spec == null)
              throw new InternalServerException("rest.entity.invalidSpecification", "", sourceSpec.getId());
          resource.setName(source.getName());
          resource.setDescription(source.getDescription());
          resource.setSpecification(spec);
          TimePeriod tp = new TimePeriod(source.getStartDate(),source.getEndDate());
          resource.setValidFor(tp);
      }
    public static void processIGPlaceAssociation(Persistent persistentObj,
                                               List<? extends PlaceRefType> placeTypes) throws ValidationException {
        AttachmentManager mgr = PersistenceHelper.makeAttachmentManager();


        for (PlaceRefType placeType : placeTypes) {
            GeographicPlace place = EntityUtils.findEntityById(GeographicPlace.class, placeType.getId());

            if (place == null) {
                log.error("", "Place does not exist with ID " + placeType.getId());
                throw new InvalidRequestException("rest.resource.invalidPlaceID", "", placeType.getId());
            }
            Involvement involvement = null;
            involvement =
                PersistenceHelper.makeAttachmentManager().makeRel(PlaceInventoryGroupRel.class);

            InventoryRole fromRole = null;
            InventoryRole toRole = null;

            if (placeType.getReferrerRole() != null && persistentObj instanceof RoleEnabled) {
                Set<InventoryRole> roles = ((RoleEnabled) persistentObj).getInvRoles();
                for (InventoryRole role : roles) {
                    if (role.getSpecification()
                            .getName()
                            .equalsIgnoreCase(placeType.getReferrerRole())) {
                        toRole = role;
                        break;
                    }
                }
            }

            if (placeType.getReferrerRole() != null && toRole == null) {
                log.error("", "Invalid To Role Specified in Involvement - " + placeType.getReferrerRole() + " for ID" +placeType.getId());
                
                throw new InvalidRequestException("rest.resource.invalidToRole", "", placeType.getReferrerRole());
            }

            if (placeType.getRole() != null) {
                Set<PlaceRole> roles = place.getInvRoles();
                for (PlaceRole role : roles) {
                    if (role.getSpecification()
                            .getName()
                            .equalsIgnoreCase(placeType.getRole())) {
                        fromRole = role;
                        break;
                    }
                }
            }

            if (placeType.getRole() != null && fromRole == null) {
                log.error("", "Invalid From Role Specified in Involvement - " + placeType.getRole() + " for ID" +
                          placeType.getId());
                throw new InvalidRequestException("rest.resource.invalidFromRole", "", placeType.getRole());
            }

            if (fromRole != null)
                involvement.setFromEntityRoleKey(fromRole.getOid());
            if (toRole != null)
                involvement.setToEntityRoleKey(toRole.getOid());

            involvement.setToEntity(persistentObj);
            involvement.setFromEntity(place);
            mgr.createRel(involvement);
            mgr.flushTransaction();
        }
    }    
      public Response processAssociationToInventoryGroup(GroupItemRefType body,String id,String task,SecurityContext securityContext)
       {
      // do some magic!
     // return Response.ok().entity("magic!"/*new ApiResponseMessage(ApiResponseMessage.OK, "magic!")*/).build();
		debugLog(log, "Entered processAssociationToInventoryGroup");
        UserEnvironment userEnvironment = null;
        InventoryTransactionValue transValue = null;
       // InventoryGroup result = null;
		//InventoryGroupType resource = null;
        String resourceid = body.getId();
		boolean isParty = false;
		String resourceType = null;
		Persistent pc = null;
		Persistent[] persistents = new Persistent[1];
		InventoryGroupType resource = null;
		try {
            userEnvironment = startUserEnvironment(restUtils.getHttpRequest());
            transValue = startTransaction();
            transValue.setUserEnvironment(userEnvironment);
            InventoryGroupManager igManager = PersistenceHelper.makeInventoryGroupManager();
		    validateBusinessIdentifierForIG(id);
			String igName = id.substring(id.indexOf("-") + 1); // id after removing business identifier
			Persistent ig = EntityUtils.findEntityByName(InventoryGroup.class, igName);
			
			if (ig == null)
				throw new DataNotFoundException("rest.entity.notFoundWithID", "", "InventoryGroup", igName);
			// We have valid ig, check for resource.
			if (resourceid.contains("-")) {
			   int locationid = Integer.parseInt(resourceid.substring(0, resourceid.indexOf("-")));
                           if (locationid == 50){
                               resourceType = "PropertyLocation";
                               resourceid = resourceid.substring(resourceid.indexOf("-") + 1);
                           } else  {
				resourceType = ResourceUtils.getResourceType(resourceid);
                           }
			} else {
				//party do not have businessidentifier.
				isParty = true;
				resourceType = "Party";
			}
			if (resourceType.equals("Party")) {
			    pc = EntityUtils.findEntityById(Party.class, resourceid);
			} else if (resourceType.equals("PropertyLocation")) {
			    pc = EntityUtils.findEntityByName(PropertyLocation.class, resourceid);
			} else {
                            pc = ResourceUtils.findResource(resourceid, ResourceUtils.toResourceTypeEnumType(resourceType));
                        }
			if (pc == null)
				throw new DataNotFoundException("rest.entity.notFoundWithID", "", resourceType, resourceid);
			persistents[0] = (Persistent)pc;
            if(GroupTaskEnumType.ADD.toString().equals(task)){
            	igManager.associatePersistentToInventoryGroup((InventoryGroup)ig, pc);
           }
           else if(GroupTaskEnumType.REMOVE.toString().equals(task)){ 
               List<GroupEnabled> list = new ArrayList<GroupEnabled>();
               if (pc instanceof LogicalDevice) {
            	   list.add((LogicalDevice) pc);
               } else if (pc instanceof CustomNetworkAddress) {
            	   list.add((CustomNetworkAddress) pc);            	   
               } else if (pc instanceof CustomObject) {
            	   list.add((CustomObject) pc);
               } else if (pc instanceof Equipment) {
            	   list.add((Equipment) pc);
               } else if (pc instanceof FlowIdentifier) {
            	   list.add((FlowIdentifier) pc);
               } else if (pc instanceof LogicalDeviceAccount) {
            	   list.add((LogicalDeviceAccount) pc);
               } else if (pc instanceof PhysicalDevice) {
            	   list.add((PhysicalDevice) pc);
               } else if (pc instanceof TelephoneNumber) {
            	   list.add((TelephoneNumber) pc);
               } else if (pc instanceof IPv4Address) {
            	   list.add((IPv4Address) pc);
               } else if (pc instanceof IPv6Address) {
            	   list.add((IPv6Address) pc);
               } else if (pc instanceof IPSubnet) {
            	   list.add((IPSubnet) pc);
               } else if (pc instanceof Pipe) {
            	   list.add((Pipe) pc);
               } else if (pc instanceof PipeTerminationPoint) {
            	   list.add((PipeTerminationPoint) pc);
               } else if (pc instanceof Party) {
            	   list.add((Party) pc);
               } else if (pc instanceof PropertyLocation) {
            	   list.add((PropertyLocation) pc);
               } else {
             	  throw new InvalidRequestException("rest.resource.notSupported", "", resourceType);
               }
               //service didn't cover , property location is in placereference , party is not a resource so its covered as association.
               EntityUtils.disassociateFromInventoryGroup((InventoryGroup)ig, list);       	   
           } else {
        	   throw new DataNotFoundException("rest.entity.InvalidTask", "", task);
           }
            igManager.flushTransaction();
            resource = toInventoryGroup((InventoryGroup)ig, restUtils);
            
            //Group Items
            InventoryGroupEntitySearchCriteria igSearchCriteria = igManager.makeInventoryGroupEntitySearchCriteria();
            igSearchCriteria.setInventoryGroup((InventoryGroup)ig);
            igSearchCriteria.setRange(0, 50);
            List<InvGroupRef> invGroupRefs = igManager.findInvGroupRefsForInventoryGroup(igSearchCriteria);
            List<GroupItemRefType> gpItemRefs = getInventoryGroupItems(invGroupRefs,restUtils);                  
            if (invGroupRefs.size() > 0) {
               resource.setInventoryGroupItems(gpItemRefs); 
               // resource.addInventoryGroupItemsItem(gpItemRef);
            }
		} catch (StringIndexOutOfBoundsException | NumberFormatException e) {
			throw new InvalidRequestException("rest.entity.notFoundWithID", "", "InventoryGroup", id);
		} catch (ValidationException ve) {
			throw new InternalServerException(ve);
		} finally {
			commitOrRollback(transValue);
		}
		Response response = Response.status(Response.Status.OK).entity(resource).build();
		response.getHeaders().add("Last-Modified", restUtils.getLastModifiedDate());
		return response;
  }
      public GroupItemRefType toGroupItemRef(Persistent resource,  RestUtils restUtils) throws ValidationException {
    	  GroupItemRefType result = new GroupItemRefType();
          ResourceResponseMappingUtils map = new ResourceResponseMappingUtils(restUtils.getBaseURL());
              debugLog(log, "Entered GroupItemRefType ");
              RootEntity resourceEntity = null;
              if (resource == null)
                  return null;
              if (resource instanceof LogicalDevice) {
                  result.setId(BusinessObjectType.LogicalDevice.getValue() + "-" + ((LogicalDevice) resource).getId());
                  resourceEntity = (LogicalDevice) resource;
              } else if (resource instanceof CustomNetworkAddress) {
            	  result.setId(BusinessObjectType.CustomNetworkAddress.getValue() + "-" + ((CustomNetworkAddress) resource).getId());
            	  resourceEntity = (CustomNetworkAddress) resource;
              } else if (resource instanceof CustomObject) {
            	  result.setId(BusinessObjectType.CustomObject.getValue() + "-" + ((CustomObject) resource).getId());
            	  resourceEntity = (CustomObject) resource;
              } else if (resource instanceof Equipment) {
            	  result.setId(BusinessObjectType.Equipment.getValue() + "-" + ((Equipment) resource).getId());
            	  resourceEntity = (Equipment) resource;
              } else if (resource instanceof FlowIdentifier) {
            	  result.setId(BusinessObjectType.FlowIdentifier.getValue() + "-" + ((FlowIdentifier) resource).getId());
            	  resourceEntity = (FlowIdentifier) resource;
              } else if (resource instanceof LogicalDeviceAccount) {
            	  result.setId(BusinessObjectType.LogicalDeviceAccount.getValue() + "-" + ((LogicalDeviceAccount) resource).getId());
            	  resourceEntity = (LogicalDeviceAccount) resource;
              } else if (resource instanceof PhysicalDevice) {
            	  result.setId(BusinessObjectType.PhysicalDevice.getValue() + "-" + ((PhysicalDevice) resource).getId());
            	  resourceEntity = (PhysicalDevice) resource;
              } else if (resource instanceof TelephoneNumber) {
            	  result.setId(BusinessObjectType.TelephoneNumber.getValue() + "-" + ((TelephoneNumber) resource).getId());
            	  resourceEntity = (TelephoneNumber) resource;
              } else if (resource instanceof IPv4Address) {
            	  result.setId(BusinessObjectType.IPv4Address.getValue() + "-" + ((IPv4Address) resource).getId());
            	  resourceEntity = (IPv4Address) resource;
              } else if (resource instanceof IPv6Address) {
            	  result.setId(BusinessObjectType.IPv6Address.getValue() + "-" + ((IPv6Address) resource).getId());
            	  resourceEntity = (IPv6Address) resource;
              } else if (resource instanceof IPSubnet) {
            	  result.setId(BusinessObjectType.IPSubnet.getValue() + "-" + ((IPSubnet) resource).getId());
            	  resourceEntity = (IPSubnet) resource;
              } else if (resource instanceof Pipe) {
            	  result.setId(BusinessObjectType.Pipe.getValue() + "-" + ((Pipe) resource).getId());
            	  resourceEntity = (Pipe) resource;
              } else if (resource instanceof PipeTerminationPoint) {
            	  result.setId(BusinessObjectType.PipeTerminationPoint.getValue() + "-" + ((PipeTerminationPoint) resource).getId());
            	  resourceEntity = (PipeTerminationPoint) resource;
              } else if (resource instanceof Party) {
            	  result.setId(((Party) resource).getId());
            	  resourceEntity = (Party) resource;
              } else if (resource instanceof PropertyLocation) {
            	  result.setId("50-" + ((PropertyLocation) resource).getId());
            	  resourceEntity = (PropertyLocation) resource;
              }    
              if(resource instanceof Party) {
            	  result.setHref(map.getBaseUrl() + "/party/" + resourceEntity.getId());  
            	  result.setInventoryGroupItemType("Party");
              } else if (resource instanceof PropertyLocation){
                  result.setHref(map.getBaseUrl() + "/place/50-" + resourceEntity.getName());  
                  result.setInventoryGroupItemType("PropertyLocation");
              } else {
                  String href = map.entityToHref(resourceEntity);
            	  result.setHref(href);
                  result.setInventoryGroupItemType(ResourceUtils.getResourceType(result.getId()));
              }
              result.setName(resourceEntity.getName());
          Specification spec = resourceEntity.getSpecification();
          if( spec != null ) {
              SpecificationType specResult = new SpecificationType();
              specResult.setName( spec.getName() );
              specResult.setId(spec.getName() );
             // specResult.setHref(restUtils.getBaseURL() + "/specification/" + URLEncoder.encode(spec.getName()).replace("+", "%20"));
              specResult.setDescription( spec.getDescription() );
              specResult.setVersion("1");
              TimePeriod validFor = spec.getValidFor();
              if( validFor != null && validFor.getStartDate() != null)
                  specResult.setStartDate( validFor.getStartDate());
              if ( validFor != null && validFor.getEndDate() != null)
                      specResult.setEndDate( validFor.getEndDate());
              if ( validFor != null && validFor.getEndDate() == null)
                      specResult.setEndDate( oracle.communications.platform.util.Utils.getMaxDate());
              result.setGrpSpecification(specResult);
          }
              return result;
      } 
    public static void validateBusinessIdentifierForIG(String id) {

        if (id == null)
            throw new InvalidRequestException("rest.entity.missingID", "");
        int businessIdentifier;
        try {
            businessIdentifier = Integer.parseInt(id.substring(0, id.indexOf("-")));
        } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
            throw new InvalidRequestException("rest.entity.notFoundWithID",
                                              "",
                                              "InventoryGroup", id);
        }
        //38 is inventoryGroup BI ID
        if (businessIdentifier != 38)
            throw new InvalidRequestException("rest.entity.notFoundWithID", "Invalid BusinessIdentifier",
                        "InventoryGroup", id);
    }

    public List<GroupItemRefType> getInventoryGroupItems(List<InvGroupRef> invGroupRefs,  RestUtils restUtils) throws ValidationException{
        List<GroupItemRefType> gpItemRefs = new ArrayList<GroupItemRefType>();
        if (invGroupRefs.size() > 0) {
            for(InvGroupRef relation : invGroupRefs){
                if (relation.getLogicalDevice() != null) {
                    gpItemRefs.add(toGroupItemRef((Persistent)relation.getLogicalDevice(), restUtils)); 
                } else if (relation.getCustomNetworkAddress() != null) {
                    gpItemRefs.add(toGroupItemRef((Persistent)relation.getCustomNetworkAddress(), restUtils));                 
                } else if (relation.getCustomObject() != null) {
                    gpItemRefs.add(toGroupItemRef((Persistent)relation.getCustomObject(), restUtils)); 
                } else if (relation.getEquipment() != null) {
                    gpItemRefs.add(toGroupItemRef((Persistent)relation.getEquipment(), restUtils)); 
                } else if (relation.getFlowIdentifier() != null) {
                    gpItemRefs.add(toGroupItemRef((Persistent)relation.getFlowIdentifier(), restUtils)); 
                } else if (relation.getLogicalDeviceAccount() != null) {
                    gpItemRefs.add(toGroupItemRef((Persistent)relation.getLogicalDeviceAccount(), restUtils)); 
                } else if (relation.getPhysicalDevice() != null) {
                    gpItemRefs.add(toGroupItemRef((Persistent)relation.getPhysicalDevice(), restUtils)); 
                } else if (relation.getTelephoneNumber() != null) {
                    gpItemRefs.add(toGroupItemRef((Persistent)relation.getTelephoneNumber(), restUtils)); 
                } else if (relation.getIPAddress() != null ) {
                    gpItemRefs.add(toGroupItemRef((Persistent)relation.getIPAddress(), restUtils)); 
                } else if (relation.getIPSubnet() != null) {
                    gpItemRefs.add(toGroupItemRef((Persistent)relation.getIPSubnet(), restUtils)); 
                } else if (relation.getPipe() != null) {
                    gpItemRefs.add(toGroupItemRef((Persistent)relation.getPipe(), restUtils)); 
                } else if (relation.getPipeTerminationPoint() != null) {
                    gpItemRefs.add(toGroupItemRef((Persistent)relation.getPipeTerminationPoint(), restUtils)); 
                }  else if (relation.getParty() != null) {
                    gpItemRefs.add(toGroupItemRef((Persistent)relation.getParty(), restUtils)); 
                }   else if (relation.getPropertyLocation() != null) {
                    gpItemRefs.add(toGroupItemRef((Persistent)relation.getPropertyLocation(), restUtils)); 
                }                     
             }
        }
        return gpItemRefs;
    }

	public static InventoryGroup mergeParentGroupRelationships(InventoryGroup resource,
			List<GroupRefType> currInventoryGroupRef, List<GroupRefType> inputInventoryGroupRef)
			throws ValidationException {
		// analyse the difference
		List<GroupRefType> toAdd = new ArrayList<GroupRefType>();
		List<GroupRefType> toDelete = new ArrayList<GroupRefType>();

		if (currInventoryGroupRef == null || Utils.isEmpty(currInventoryGroupRef)) {
			toAdd.addAll(inputInventoryGroupRef);
		} else if (inputInventoryGroupRef == null || inputInventoryGroupRef.isEmpty()) {
			toDelete.addAll(currInventoryGroupRef);
		} else {
			toAdd = getDiffList(inputInventoryGroupRef, isInventoryGroupMatch(currInventoryGroupRef));
			toDelete = getDiffList(currInventoryGroupRef, isInventoryGroupMatch(inputInventoryGroupRef));
		}
		InventoryGroupManager invGrpManager = PersistenceHelper.makeInventoryGroupManager();
		if (!Utils.isEmpty(toDelete)) {
			for (GroupRefType grpType : toDelete) {
				String id = grpType.getId();
				String groupName = id.substring(id.indexOf("-") + 1);
				InventoryGroup entity = EntityUtils.findEntityByName(InventoryGroup.class, groupName);
				if (entity == null) {
					log.error("", "InventoryGroup with name" + groupName + " is not found unassociate.");
					throw new DataNotFoundException("rest.entity.notFoundWithID", "", "InventoryGroup", groupName);
				} else {
					InvGroupRel[] groups = new InvGroupRel[1];
					Set<InvGroupRel> igRel = entity.getChildGroups();
					InventoryGroup childGroup = null;
					for (InvGroupRel idRel : igRel) {
						childGroup = (InventoryGroup) idRel.getChildInvGroup();
						if (childGroup.getEntityId() == resource.getEntityId()) {
							groups[0] = idRel;
							break;
						}
					}
					invGrpManager.deleteInvGroupRel(groups);
					invGrpManager.flushTransaction();
				}
			}
		}

		if (!Utils.isEmpty(toAdd)) {
			for (GroupRefType grpType : toAdd) {
				String id = grpType.getId();
				if (Utils.isEmpty(id))
					continue;
				String groupName = id.substring(id.indexOf("-") + 1);
				InventoryGroup entity = EntityUtils.findEntityByName(InventoryGroup.class, groupName);
				if (entity == null) {
					log.error("", "InventoryGroup with name" + groupName + " is not found associate.");
					throw new DataNotFoundException("rest.entity.notFoundWithID", "", "InventoryGroup", groupName);
				} else {
					InventoryGroup[] groups = new InventoryGroup[] { entity };
					invGrpManager.createRelatedGroups(resource, true, true, groups);
					invGrpManager.flushTransaction();
				}
			}
		}
		return resource;
	}

	private static Predicate<GroupRefType> isInventoryGroupMatch(List<GroupRefType> relGroup) {
		return e -> relGroup.stream().noneMatch(input -> e.getName().equals(input.getName()));
	}

	public static InventoryGroup mergeGroupItemsAssociations(InventoryGroup resource,
			List<GroupItemRefType> currInventoryGroupItems, List<GroupItemRefType> inputInventoryGroupItems)
			throws ValidationException {
          //analyse the difference
		List<GroupItemRefType> toAdd = new ArrayList<GroupItemRefType>();
		List<GroupItemRefType> toDelete = new ArrayList<GroupItemRefType>();

		if (currInventoryGroupItems == null || Utils.isEmpty(currInventoryGroupItems)) {
			toAdd.addAll(inputInventoryGroupItems);
		} else if (inputInventoryGroupItems == null || inputInventoryGroupItems.isEmpty()) {
			toDelete.addAll(currInventoryGroupItems);
		} else {
			toAdd = getDiffList(inputInventoryGroupItems, isInventoryGroupItemMatch(currInventoryGroupItems));
			toDelete = getDiffList(currInventoryGroupItems, isInventoryGroupItemMatch(inputInventoryGroupItems));
		}
		if (!Utils.isEmpty(toDelete)) {
			for (GroupItemRefType grpType : toDelete) {
				boolean isParty = false;
				String resourceType = null;
				Persistent pc = null;
				Persistent[] persistents = new Persistent[1];
				String id = grpType.getId();
				if (id.contains("-")) {
					int locationid = Integer.parseInt(id.substring(0, id.indexOf("-")));
					if (locationid == 50) {
						resourceType = "PropertyLocation";
						id = id.substring(id.indexOf("-") + 1);
					} else {
						resourceType = ResourceUtils.getResourceType(id);
					}
				} else {
                   //party do not have businessidentifier.
					isParty = true;
					resourceType = "Party";
				}
				if (resourceType.equals("Party")) {
					pc = EntityUtils.findEntityById(Party.class, id);
				} else if (resourceType.equals("PropertyLocation")) {
					pc = EntityUtils.findEntityByName(PropertyLocation.class, id);
				} else {
					pc = ResourceUtils.findResource(id, ResourceUtils.toResourceTypeEnumType(resourceType));
				}
				if (pc == null)
					throw new DataNotFoundException("rest.entity.notFoundWithID", "", resourceType, id);
				persistents[0] = (Persistent) pc;
				List<GroupEnabled> list = new ArrayList<GroupEnabled>();
				if (pc instanceof LogicalDevice) {
					list.add((LogicalDevice) pc);
				} else if (pc instanceof CustomNetworkAddress) {
					list.add((CustomNetworkAddress) pc);
				} else if (pc instanceof CustomObject) {
					list.add((CustomObject) pc);
				} else if (pc instanceof Equipment) {
					list.add((Equipment) pc);
				} else if (pc instanceof FlowIdentifier) {
					list.add((FlowIdentifier) pc);
				} else if (pc instanceof LogicalDeviceAccount) {
					list.add((LogicalDeviceAccount) pc);
				} else if (pc instanceof PhysicalDevice) {
					list.add((PhysicalDevice) pc);
				} else if (pc instanceof TelephoneNumber) {
					list.add((TelephoneNumber) pc);
				} else if (pc instanceof IPv4Address) {
					list.add((IPv4Address) pc);
				} else if (pc instanceof IPv6Address) {
					list.add((IPv6Address) pc);
				} else if (pc instanceof IPSubnet) {
					list.add((IPSubnet) pc);
				} else if (pc instanceof Pipe) {
					list.add((Pipe) pc);
				} else if (pc instanceof PipeTerminationPoint) {
					list.add((PipeTerminationPoint) pc);
				} else if (pc instanceof Party) {
					list.add((Party) pc);
				} else if (pc instanceof PropertyLocation) {
					list.add((PropertyLocation) pc);
				} else {
					throw new InvalidRequestException("rest.resource.notSupported", "", resourceType);
				}
                    //service didn't cover , property location is in placereference , party is not a resource so its covered as association.
				EntityUtils.disassociateFromInventoryGroup((InventoryGroup) resource, list);
			}
		}

		if (!Utils.isEmpty(toAdd)) {
			for (GroupItemRefType grpType : toAdd) {
				boolean isParty = false;
				String resourceType = null;
				Persistent pc = null;
				Persistent[] persistents = new Persistent[1];
				String id = grpType.getId();
				if (id.contains("-")) {
					int locationid = Integer.parseInt(id.substring(0, id.indexOf("-")));
					if (locationid == 50) {
						resourceType = "PropertyLocation";
						id = id.substring(id.indexOf("-") + 1);
					} else {
						resourceType = ResourceUtils.getResourceType(id);
					}
				} else {
                //party do not have businessidentifier.
					isParty = true;
					resourceType = "Party";
				}
				if (resourceType.equals("Party")) {
					pc = EntityUtils.findEntityById(Party.class, id);
				} else if (resourceType.equals("PropertyLocation")) {
					pc = EntityUtils.findEntityByName(PropertyLocation.class, id);
				} else {
					pc = ResourceUtils.findResource(id, ResourceUtils.toResourceTypeEnumType(resourceType));
				}
				if (pc == null)
					throw new DataNotFoundException("rest.entity.notFoundWithID", "", resourceType, id);
				persistents[0] = (Persistent) pc;
				List<GroupEnabled> list = new ArrayList<GroupEnabled>();
				if (pc instanceof LogicalDevice) {
					list.add((LogicalDevice) pc);
				} else if (pc instanceof CustomNetworkAddress) {
					list.add((CustomNetworkAddress) pc);
				} else if (pc instanceof CustomObject) {
					list.add((CustomObject) pc);
				} else if (pc instanceof Equipment) {
					list.add((Equipment) pc);
				} else if (pc instanceof FlowIdentifier) {
					list.add((FlowIdentifier) pc);
				} else if (pc instanceof LogicalDeviceAccount) {
					list.add((LogicalDeviceAccount) pc);
				} else if (pc instanceof PhysicalDevice) {
					list.add((PhysicalDevice) pc);
				} else if (pc instanceof TelephoneNumber) {
					list.add((TelephoneNumber) pc);
				} else if (pc instanceof IPv4Address) {
					list.add((IPv4Address) pc);
				} else if (pc instanceof IPv6Address) {
					list.add((IPv6Address) pc);
				} else if (pc instanceof IPSubnet) {
					list.add((IPSubnet) pc);
				} else if (pc instanceof Pipe) {
					list.add((Pipe) pc);
				} else if (pc instanceof PipeTerminationPoint) {
					list.add((PipeTerminationPoint) pc);
				} else if (pc instanceof Party) {
					list.add((Party) pc);
				} else if (pc instanceof PropertyLocation) {
					list.add((PropertyLocation) pc);
				} else {
					throw new InvalidRequestException("rest.resource.notSupported", "", resourceType);
				}
               //service didn't cover , property location is in placereference , party is not a resource so its covered as association.
				EntityUtils.associateToInventoryGroup((InventoryGroup) resource, list);
			}
		}
		return resource;
	}

	private static Predicate<GroupItemRefType> isInventoryGroupItemMatch(List<GroupItemRefType> relGroupItem) {
		return e -> relGroupItem.stream()
				.noneMatch(input -> e.getInventoryGroupItemType().equals(input.getInventoryGroupItemType())
						&& e.getId().equals(input.getId()));
	}
	
    public static InventoryGroup processParentGroupRelationships(InventoryGroup ig,List<GroupRefType> inputInventoryGroupRef) throws ValidationException {
		   
		   ig = mergeParentGroupRelationships(ig, null, inputInventoryGroupRef);
		   return ig;
		   }
    
	
    public static InventoryGroup processGroupItemsAssociations(InventoryGroup ig,List<GroupItemRefType> inputInventoryGroupItems) throws ValidationException {
		   
		   ig = mergeGroupItemsAssociations(ig, null, inputInventoryGroupItems);
		   return ig;
		   
		   } 

    private static <T> List<T> getDiffList(List<T> inputList, Predicate<T> diffPredicate) {

        return inputList.stream()
                        .filter(diffPredicate)
                        .collect(Collectors.toList());
    }
    public static Persistent mergePlaceAssociations(Persistent resource, List<PlaceRefType> currPlaces,
                                                    List<PlaceRefType> inputPlaces) throws ValidationException {
        //analyse the difference
        List<PlaceRefType> toAdd = new ArrayList<PlaceRefType>();
        List<PlaceRefType> toDelete = new ArrayList<PlaceRefType>();

        if (Utils.isEmpty(currPlaces)) {
            toAdd.addAll(inputPlaces);
        } else if (inputPlaces.isEmpty()) {
            toDelete.addAll(currPlaces);
        } else {
            toAdd = getDiffList(inputPlaces, isPlaceMatch(currPlaces));
            toDelete = getDiffList(currPlaces, isPlaceMatch(inputPlaces));
        }
        if (!Utils.isEmpty(toDelete)) {
            for (PlaceRefType placeType : toDelete) {
                GeographicPlace place = EntityUtils.findEntityById(GeographicPlace.class, placeType.getId());
                EntityUtils.disassociateFromPlace(place, resource, placeType.getRole(), placeType.getReferrerRole());
            }
        }

        if (!Utils.isEmpty(toAdd)) {
            processIGPlaceAssociation(resource, toAdd);
        }
        return resource;
    }   
    private static Predicate<PlaceRefType> isPlaceMatch(List<PlaceRefType> places) {
        return e -> places.stream()
               .noneMatch(input -> (input.getId().equals(e.getId()) && Objects.equals(input.getRole(), e.getRole()) &&
                                    Objects.equals(input.getReferrerRole(), e.getReferrerRole())));
    }
}

