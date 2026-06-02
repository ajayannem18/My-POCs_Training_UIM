package com.mobileService.designAndAssign;

import oracle.communications.inventory.api.entity.ServiceConfigurationVersion;
import oracle.communications.inventory.extensibility.extension.util.ExtensionPointContext;
import oracle.communications.inventory.xmlbeans.BusinessInteractionItemType;
import oracle.communications.platform.exception.ValidationException;

public class DesignAndAssign {
public void design(ExtensionPointContext context) throws ValidationException, oracle.communications.inventory.api.exception.ValidationException {
	System.out.println("Running design and assign method");
	ServiceConfigurationVersion CFSscv=(ServiceConfigurationVersion) context.getArguments()[0];
	BusinessInteractionItemType biItem=(BusinessInteractionItemType) context.getArguments()[1];
	System.out.println("CFS service is : "+CFSscv.getService());
	System.out.println("CFS service configuration is : "+CFSscv);
	String action=biItem.getService().getAction();
    if(CFSscv!=null) {
    	String serviceStatus=CFSscv.getService().getAdminState().toString();
    	System.out.println("The CFS service status is :"+serviceStatus);
    	if(action!=null) {
    		if(action.equalsIgnoreCase("create")||action.equalsIgnoreCase("add")) {
    			CreateRFSDesignClass createRFSService=new CreateRFSDesignClass();
    			System.out.println("Executing RFS Service creation method");
    			createRFSService.createRFS(CFSscv,biItem);
    			System.out.println("Process Interaction executed succesfully");
    			
    		}
    		
    		
    	}
    	else {
    		throw new ValidationException("No cfs service action found");
    	}
    	
    }
    else {
    	throw new ValidationException("CFS configuration is null") ;
    }
	
	
}
}
