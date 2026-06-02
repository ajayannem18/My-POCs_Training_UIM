package helperMethods;

import java.util.Date;

import oracle.communications.inventory.api.entity.InventoryConfigurationSpec;
import oracle.communications.inventory.api.entity.Service;
import oracle.communications.inventory.api.entity.ServiceConfigurationVersion;
import oracle.communications.inventory.api.entity.Specification;
import oracle.communications.inventory.api.exception.ValidationException;
import oracle.communications.inventory.api.service.ServiceConfigurationManager;
import oracle.communications.platform.persistence.Finder;
import oracle.communications.platform.persistence.PersistenceHelper;

public class CreateRFSServiceConfiguration {
public static ServiceConfigurationVersion rfsScv(Service RFSService,String scvSpec) throws ValidationException {
	ServiceConfigurationVersion RFSScv=null;
	Finder f=PersistenceHelper.makeFinder();
	InventoryConfigurationSpec spec=(InventoryConfigurationSpec) f.findByName(Specification.class,scvSpec).iterator().next();
	if(spec!=null) {
		System.out.println("Found RFS Service Configuration specification with name : "+spec.getName());
	    ServiceConfigurationManager scvMgr=PersistenceHelper.makeServiceConfigurationManager();
	    ServiceConfigurationVersion scv=scvMgr.makeConfigurationVersion(RFSService);
	    System.out.println("Making service configuration version");
	    scv.setName(spec.getName()+"_RFS");
		scv.setEffDate(new Date());
		System.out.println("name and date set succesfully to service configuration version");
	    RFSScv=(ServiceConfigurationVersion) scvMgr.createConfigurationVersion(RFSService, scv, spec);
	    System.out.println("RFS Service Configuration Created succesfully");
	}
	else {
		throw new NullPointerException("No Service Configuration Found");
		}
	
	return RFSScv;
}
}
