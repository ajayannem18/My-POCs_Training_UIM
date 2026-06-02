package com.mobileService.designAndAssign;
import java.util.List;

import helperMethods.CreateRFSService;
import helperMethods.CreateRFSServiceConfiguration;
import helperMethods.FindOrCreateConfigItems;
import oracle.communications.inventory.api.entity.Service;
import oracle.communications.inventory.api.entity.ServiceConfigurationItem;
import oracle.communications.inventory.api.entity.ServiceConfigurationVersion;
import oracle.communications.inventory.api.exception.ValidationException;
import oracle.communications.inventory.api.service.ServiceConfigurationManager;
import oracle.communications.inventory.xmlbeans.BusinessInteractionItemType;
import oracle.communications.inventory.xmlbeans.ParameterType;
import oracle.communications.platform.persistence.PersistenceHelper;

public class CreateRFSDesignClass {
public static void createRFS(ServiceConfigurationVersion cfsScv,BusinessInteractionItemType biItem) throws ValidationException 
{
	String TelephoneNumber=null;
	String SIM=null;
	ServiceConfigurationItem telePhoneNumber=null;
	ServiceConfigurationItem sim=null;
	
	List<ParameterType> params=biItem.getParameterList();
	for(ParameterType param:params) {
		if(param.getName().equalsIgnoreCase(TelephoneNumber)) {
			TelephoneNumber=param.getName();
			System.out.println("Got the parameter with name : "+param.getName());
		}
		else if(param.getName().equalsIgnoreCase(SIM)) {
			SIM=param.getName();
			System.out.println("Got the parameter with name : "+param.getName());	
		}
	}
	
	ServiceConfigurationItem cfsscItem=FindOrCreateConfigItems.findOrCreateConfigItems(cfsScv, "RFS_Service_CI");
	System.out.println("cfs configuration item is : "+cfsscItem);
	System.out.println("cfs configuration item name is : "+cfsscItem.getName());

	//ServiceConfigurationItem cfsscItem=cfsScv.getConfigItems().iterator().next();
	System.out.println("CFS service configuration item is : "+cfsscItem);
	System.out.println("Calling create RFS service class");
	Service RFSService=CreateRFSService.rfsService(cfsScv, "Mobile_RFS_Service");
	System.out.println("RFS service created succesfully");
	System.out.println("Creating RFS service configuration");
	ServiceConfigurationVersion RFSScv=CreateRFSServiceConfiguration.rfsScv(RFSService,"Mobile_RFS_service_Configuration");
	System.out.println("RFS Service configuration created succesfully");
	
	if(cfsscItem.getName().equalsIgnoreCase("RFS_Service_CI")) {
		System.out.println("Found CFS config item with name : "+cfsscItem.getName());
		ServiceConfigurationManager scvMgr=PersistenceHelper.makeServiceConfigurationManager();
		System.out.println("Associating RFS Service to CFS Service config item");
		scvMgr.assignResource(cfsscItem, RFSService, null, null);
		System.out.println("Mapping RFS service to CFS configuration item completed");
		System.out.println("Finding Config items under RFS Service configuration");
		List<ServiceConfigurationItem> rfsConfigItems=RFSScv.getConfigItems();
		if(rfsConfigItems!=null) {
		for(ServiceConfigurationItem configItem:rfsConfigItems) {
			if(configItem.getName().equalsIgnoreCase(TelephoneNumber)) {
				telePhoneNumber=configItem;
				System.out.println("Found config item with name : "+configItem.getName());
			}
			else if(configItem.getName().equalsIgnoreCase(SIM)) {
				sim=configItem;
				System.out.println("Found config item with name : "+configItem.getName());

			}
			else {
			   System.out.println("No Required config items found under RFS service configuration");
			}
			
		}
		
		}
		else {System.out.println("No config items found under RFS Service Configuration");}
		
	}
	else {
		System.out.println("No RFS config Item found with name RFS_Service_CI");
	}
	
}

}