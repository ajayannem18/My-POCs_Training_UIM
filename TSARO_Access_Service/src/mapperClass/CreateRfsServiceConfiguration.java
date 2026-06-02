package mapperClass;

import java.util.Date;

import oracle.communications.inventory.api.entity.InventoryConfigurationSpec;
import oracle.communications.inventory.api.entity.Service;
import oracle.communications.inventory.api.entity.ServiceConfigurationVersion;
import oracle.communications.inventory.api.entity.common.InventoryConfigurationVersion;
import oracle.communications.inventory.api.exception.ValidationException;
import oracle.communications.inventory.api.service.ServiceConfigurationManager;
import oracle.communications.platform.persistence.PersistenceHelper;

public class CreateRfsServiceConfiguration {
public static ServiceConfigurationVersion  createRFSServiceConfiguration(Service rfsserv) throws ValidationException {
	ServiceConfigurationManager scMngr=PersistenceHelper.makeServiceConfigurationManager();
	
	System.out.println("Making RFS service configuration");
	ServiceConfigurationVersion scv=scMngr.makeConfigurationVersion(rfsserv);
	
	System.out.println("calling find specification method");
	InventoryConfigurationSpec invspec=(InventoryConfigurationSpec) MapperClass.findRFSServiceConfigurationSpec("Access_RFS");
	System.out.println(invspec.getName());
	System.out.println("RFS spec name set successfully");
	scv.setName(invspec.getName()+"_RFS");
	scv.setEffDate(new Date());
	
	System.out.println("Creating RFS service configuration");
	InventoryConfigurationVersion createdConfig=scMngr.createConfigurationVersion(rfsserv, scv, invspec);
	
	return (ServiceConfigurationVersion) createdConfig;
}
}