package mapperClass;

import java.util.List;

import oracle.communications.inventory.api.common.BaseInvManager;
import oracle.communications.inventory.api.common.EntityUtils;
import oracle.communications.inventory.api.entity.LogicalDevice;
import oracle.communications.inventory.api.entity.Service;
import oracle.communications.inventory.api.entity.ServiceConfigurationItem;
import oracle.communications.inventory.api.entity.ServiceConfigurationVersion;
import oracle.communications.platform.logging.Log;
import oracle.communications.platform.logging.LogFactory;
import oracle.communications.inventory.api.service.ServiceConfigurationManager;
import oracle.communications.inventory.extensibility.extension.util.ExtensionPointContext;
import oracle.communications.inventory.xmlbeans.BusinessInteractionItemType;
import oracle.communications.inventory.xmlbeans.BusinessInteractionType;
import oracle.communications.inventory.xmlbeans.ParameterType;
import oracle.communications.platform.persistence.PersistenceHelper;

public class AccessRFSDesigner extends BaseInvManager {
	
	
	Log log = LogFactory.getLog(AccessRFSDesigner.class);
public void design (ExtensionPointContext context) throws Exception {
	
	ServiceConfigurationVersion cfsConfig=(ServiceConfigurationVersion) context.getArguments()[0];
	BusinessInteractionItemType biInteractionType=(BusinessInteractionItemType) context.getArguments()[1];
	if(cfsConfig==null) {
		log.validationException("Access_CFS is null", new java.lang.IllegalArgumentException(), "the CFS Configuation is null here");	
	}
	
	Service service=cfsConfig.getService();
	String serviceAction=biInteractionType.getService().getAction();
	System.out.println("Service Action is : "+serviceAction);
	if(serviceAction!=null&&serviceAction.equalsIgnoreCase("add")||serviceAction.equalsIgnoreCase("Create")) 
	{
		diesingAdd(cfsConfig,biInteractionType);
		System.out.println("Process Interaction Completed Succesfully");
	}
	
	else if(serviceAction!=null&&serviceAction.equalsIgnoreCase("update")||serviceAction.equalsIgnoreCase("modify")||serviceAction.equalsIgnoreCase("change"))
	{
		System.out.println("The service action from input is "+serviceAction);
		System.out.println("Calling modify service method");
		DesignServiceChange.modifyService(cfsConfig, biInteractionType);
		
	}
	
	else if(serviceAction != null && serviceAction.equalsIgnoreCase("suspend") || 
		        serviceAction.equalsIgnoreCase("suspendWithConfiguration")) {
		    System.out.println("Calling suspend design flow");

		    DesignServiceSuspend.suspendServiceCreateRFS(cfsConfig,(BusinessInteractionItemType) biInteractionType);
		    
		}
	
	
	
}



	
	
public static void diesingAdd(ServiceConfigurationVersion scvofCFS,BusinessInteractionItemType itemtype) throws Exception {
	//Getting values from input request
	System.out.println("getting all the params from request");
	String mediaType = null;
	String peDeviceHostName = null;
	String switchDeviceHostName = null;
	String oltDeviceHostName = null;
	if (null != itemtype && itemtype.getParameterList() != null) {
		List<ParameterType> paramList = itemtype.getParameterList();
		if (!paramList.isEmpty()) {
        System.out.println("Got the params list");
        for (ParameterType param : paramList) {
            if (param != null && param.getName() != null && param.getValue() != null) {
            	
               String value = param.getValue().newCursor().getTextValue();

                System.out.println("Extracted VALUE: " + value);

                if (param.getName().equals("MediaType")) {
                    mediaType = value;
                }
                if (param.getName().equals("PeDeviceName")) {
                    peDeviceHostName = value;
                }
                if (param.getName().equals("SwitchHostName")) {
                    switchDeviceHostName = value;
                }
                if (param.getName().equals("OltDeviceName")) {  
                    oltDeviceHostName = value;
                }
            }
        }

		}
	}

	System.out.println("Getting params succesful from request");
	
	//Creating RFS service
	System.out.println("Creating RFS service");
	Service rfsService=CreateRFSService.createRFSService(scvofCFS);
	System.out.println("Creating RFS service completed");

	//Creating RFS service configuration
	System.out.println("Creating  RFS service configuration");
	ServiceConfigurationVersion rfsConfig=CreateRfsServiceConfiguration.createRFSServiceConfiguration(rfsService);
	System.out.println("Creating  RFS service configuration completed");
	System.out.println("Getting config items for CFS");
	
	List<ServiceConfigurationItem> sciList=scvofCFS.getConfigItems();
	
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
		scMgr.assignResource(cfsConfigitem, rfsService, null, null);
		System.out.println("Mapping RFS service to CFS configuration item completed");

	}
	
	if(rfsConfig!=null) {
		ServiceConfigurationManager scMngr=PersistenceHelper.makeServiceConfigurationManager();
		System.out.println("Getting RFS configuration Item");
		ServiceConfigurationItem parentscItem=MapperClass.findServiceConfigurationItem(rfsConfig, "AccessDevice");

		if (null != parentscItem)
		{
			System.out.println("executing find or create child config item method");
		ServiceConfigurationItem peDeviceItem=MapperClass.findOrCreateChildConfigItem(parentscItem, "PeDevice");
		
		   if(null!=peDeviceItem)
		   {
			   if (null != peDeviceHostName) {
					LogicalDevice peDevice = MapperClass.findLogicalDeviceOrCreate(peDeviceHostName, "peDevice");
					scMngr.assignResource(peDeviceItem, peDevice, null, null);
					EntityUtils.setValue(peDeviceItem, "peDeviceName", peDeviceHostName);
					System.out.println("peDevice assigned succesfully");
				}

			
		   }
		   
		   if (null != mediaType) {
			   if(mediaType.equalsIgnoreCase("fiber")||mediaType.equalsIgnoreCase("Microwave"))

 {
                    System.out.println("The mediaType is Fiber .Assigning switch device");
					ServiceConfigurationItem switchDeviceItem=MapperClass.findOrCreateChildConfigItem(parentscItem, "SwitchDevice");
					if (null != switchDeviceItem)
					{
						if (null != switchDeviceHostName) 
						{
							LogicalDevice switchDevice = MapperClass.findLogicalDeviceOrCreate(switchDeviceHostName,"accessDevice");
							scMngr.assignResource(switchDeviceItem, switchDevice, null, null);
							System.out.println("switch Device assigned succesfully");
							EntityUtils.setValue(parentscItem, "mwType", "No");
							EntityUtils.setValue(parentscItem, "mediaType", mediaType);
						}
						if(mediaType.equalsIgnoreCase("fiber"))
						{
							EntityUtils.setValue(parentscItem, "mwType", "no");
							EntityUtils.setValue(parentscItem, "mediaType", mediaType);
						}
						else if(mediaType.equalsIgnoreCase("Microwave")) 
						{
							EntityUtils.setValue(parentscItem, "mwType", "Yes");
							EntityUtils.setValue(parentscItem, "mediaType", mediaType);
						}
					}

	}
		    
			   else if(mediaType.equalsIgnoreCase("GPON")) {
					
           System.out.println("The mediaType is from input is GPON . Finding OLTDevice Item...");
           ServiceConfigurationItem oltDeviceItem=MapperClass.findOrCreateChildConfigItem(parentscItem, "OLTDevice");
			if (null != oltDeviceItem) {
				if (null != oltDeviceHostName) {
					System.out.println("Found OLTDevice Item with name "+oltDeviceItem+" .Finding the logical device with oltDevice Specification");
					LogicalDevice oltDevice = MapperClass.findLogicalDeviceOrCreate(oltDeviceHostName,"oltDevice");
					System.out.println("Assigning the logical device "+oltDevice+" to configuration item "+oltDeviceItem);
					scMngr.assignResource(oltDeviceItem, oltDevice, null, null);
					System.out.println(oltDevice+" device assigned succesfully");
					EntityUtils.setValue(parentscItem, "mwType", "NO");
					EntityUtils.setValue(parentscItem, "mediaType", mediaType);
				                               }			 
			                }
				   }

			
		   
		}
		
		
	}
	
	
	
}

}

}
