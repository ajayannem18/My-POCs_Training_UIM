package mapperClass;

import java.util.List;

import changeMapperClass.ChangeServiceMapperClass;
import oracle.communications.inventory.api.common.EntityUtils;
import oracle.communications.inventory.api.entity.Service;
import oracle.communications.inventory.api.entity.ServiceConfigurationItem;
import oracle.communications.inventory.api.entity.ServiceConfigurationVersion;
import oracle.communications.inventory.api.service.ServiceConfigurationManager;
import oracle.communications.inventory.api.service.ServiceManager;
import oracle.communications.inventory.extensibility.extension.util.ExtensionPointContext;
import oracle.communications.inventory.extensibility.extension.util.ExtensionPointRuleContext;
import oracle.communications.inventory.xmlbeans.BusinessInteractionItemType;
import oracle.communications.inventory.xmlbeans.BusinessInteractionType;
import oracle.communications.platform.persistence.PersistenceHelper;
import oracle.communications.platform.persistence.Persistent;

public class DesignServiceSuspend {

	
    public static void suspendService(ExtensionPointRuleContext context)
            throws Exception {
try {
    	
    	Service cfsService=(Service)context.getArguments()[0];
    	System.out.println("The  Service is : "+cfsService);
    	System.out.println("The  Service status  is : "+cfsService.getAdminState().getValueAsString());
        ServiceManager sm=PersistenceHelper.makeServiceManager();
    	
      
        
      
        // Step 2: Create a new configuration version before suspending
        // This ensures the configuration context is set, allowing RFS to be
        // picked up and transitioned alongside the CFS
        ServiceConfigurationVersion currentVersion = cfsService.getConfigurations().get(0);
        System.out.println("Current version of SCV "+currentVersion);
        String scvStatus=currentVersion.getAdminState().getValue();
        System.out.println("-----------------------------------");
        System.out.println("current version of SCV is : "+currentVersion);
       // boolean result=sm.suspendService(cfsService);
       
        ServiceConfigurationVersion cfsScv =ChangeServiceMapperClass.createNewRFSSCV(cfsService);
        System.out.println("Created new SCV for CFS : "+cfsScv);
        
        
       
//        ServiceConfigurationVersion newVersion = null;
//        if (currentVersion != null) {
//            // Create a new version based on existing — this is what cascades to RFS
//            newVersion = (ServiceConfigurationVersion) 
//                baseMgr.createConfigurationVersion(cfsService, currentVersion);
//        }
//        
//        // Step 3: Now suspend — with config context established, RFS will follow
//        boolean result = sm.suspendService(cfsService);
//         System.out.println("Service is suspended");
////        // Step 4: Complete the configuration version to finalize RFS state change
////        if (newVersion != null) {
//            baseMgr.completeConfigurationVersion(newVersion);
//        }
        
}
     catch (Exception e)
    {
	e.printStackTrace();
    }


 }
    
    
    ////////Create configurations
    public static void suspendServiceCreateRFS(ServiceConfigurationVersion cfsConfig,BusinessInteractionItemType biInteractionType) {
    	
    	try {
    		String action=biInteractionType.getService().getAction();
    		System.out.println("------------Executing suspendServiceCreateRFS method------------");
    
    		
    		System.out.println("Getting config items for CFS");
    		
    		List<ServiceConfigurationItem> sciList=cfsConfig.getConfigItems();
    		
    		ServiceConfigurationItem cfsConfigitem=null;
    		if(!sciList.isEmpty()) {
    			for(ServiceConfigurationItem sciItem:sciList) {
    				if(sciItem!=null&&sciItem.getName()!=null&&sciItem.getName().equalsIgnoreCase("RFS")) {
    					cfsConfigitem=sciItem;
    					System.out.println("Found config item under CFS configuration");
    					break;
    				}
    				
    			}
    		}
    		System.out.println("Mapping RFS service to CFS configuration item");

    		if(cfsConfigitem!=null) {
    			ServiceConfigurationManager scMgr=PersistenceHelper.makeServiceConfigurationManager();
    			System.out.println("Mapping RFS service to CFS configuration item completed");
    			 // Persistent rfsService=cfsConfigitem.getAssignment();
    			 Service rfsService =ChangeServiceMapperClass.getServiceFromAssignment(cfsConfigitem);
    			  
    			  System.out.println("RFS Service is : "+rfsService);
    			  System.out.println("Status of RFS service is "+rfsService.getAdminState().getValue());
    			//Getting RFS service configuration
    			  if(rfsService!=null) {
    			  System.out.println("Creating  RFS service configuration");
    			  ServiceConfigurationVersion rfsConfig =
    	                    ChangeServiceMapperClass.createNewRFSSCV(rfsService);
    			  ServiceManager sMgr=PersistenceHelper.makeServiceManager();
    					boolean suspend=  sMgr.suspendService(rfsService);
    					if(suspend==true) {
    						System.out.println("RFS service is suspended");
    						System.out.println("Latest RFS service status after suspend is "+rfsService.getAdminState().getValue());
    					}
    					else {
    						System.out.println("RFS service not suspended");
    					}
    					  
    				//ServiceConfigurationVersion rfsConfig=CreateRfsServiceConfiguration.createRFSServiceConfiguration(rfsService);
    				System.out.println("Creating  RFS service configuration completed");
    				
    				
    				if(rfsConfig!=null) {
    					ServiceConfigurationManager scMngr=PersistenceHelper.makeServiceConfigurationManager();
    					System.out.println("Getting RFS configuration Item");
    					ServiceConfigurationItem parentscItem=MapperClass.findServiceConfigurationItem(rfsConfig, "AccessDevice");
    					
    					System.out.println("Parent config item is : "+parentscItem);
    					EntityUtils.setValue(parentscItem, "service_action", action);


    				}
    				
    				
    			  }
    			  else {
    				  System.out.println("No RFS service found under CFS config item");
    			  }

    		}
    		else {
    			System.out.println("No config items found under CFS configuration");
    		}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    
    
    
	}
 
	
