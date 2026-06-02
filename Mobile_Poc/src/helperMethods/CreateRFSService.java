package helperMethods;

import java.util.Collection;
import java.util.LinkedList;

import oracle.communications.inventory.api.entity.Service;
import oracle.communications.inventory.api.entity.ServiceConfigurationVersion;
import oracle.communications.inventory.api.entity.ServiceSpecification;
import oracle.communications.inventory.api.exception.ValidationException;
import oracle.communications.inventory.api.service.ServiceManager;
import oracle.communications.platform.persistence.Finder;
import oracle.communications.platform.persistence.PersistenceHelper;

public class CreateRFSService {
	
	public static Service rfsService(ServiceConfigurationVersion CFSScv,String Spec) throws ValidationException {
		System.out.println("Executing RFS service Creation method");
		Finder f=PersistenceHelper.makeFinder();
	Service RFSService=null;
		ServiceSpecification rfsServiceSpec=(ServiceSpecification) f.findByName(ServiceSpecification.class, Spec).iterator().next();
        if(rfsServiceSpec!=null) {
        	System.out.println("RFS service creation specification found with name : "+rfsServiceSpec.getName());
        	ServiceManager sMgr=PersistenceHelper.makeServiceManager();
        	System.out.println("Making the RFS service");
        	Service rfsService=sMgr.makeService(Service.class);
        	rfsService.setName(CFSScv.getService().getName()+"_RFS");
        	System.out.println("Name for RFS service set succesfullly");
        	rfsService.setSpecification(rfsServiceSpec);
        	System.out.println("Specification for RFS service set successfully");
        	Collection<Service> services=new LinkedList<>();
        	services.add(rfsService);
        	 RFSService=sMgr.createService(services).iterator().next();
        	System.out.println("RFS service created Succesfully with name : "+RFSService.getName()
        	+" and status : "+RFSService.getAdminState().getValue());  	
        }
        
        
		return RFSService;

}
}