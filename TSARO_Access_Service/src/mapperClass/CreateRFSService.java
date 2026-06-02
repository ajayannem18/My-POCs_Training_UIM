package mapperClass;

import java.util.ArrayList;
import java.util.List;

import oracle.communications.inventory.api.entity.Service;
import oracle.communications.inventory.api.entity.ServiceConfigurationVersion;
import oracle.communications.inventory.api.entity.ServiceSpecification;
import oracle.communications.inventory.api.exception.ValidationException;
import oracle.communications.inventory.api.service.ServiceManager;
import oracle.communications.platform.persistence.PersistenceHelper;

public class CreateRFSService {

	public static Service createRFSService(ServiceConfigurationVersion scv) throws ValidationException {
		
		ServiceManager rfsServMgr=PersistenceHelper.makeServiceManager();
		Service rfsService =null;
		
		System.out.println(" making the service : ");
		Service serv=rfsServMgr.makeService(Service.class);
		serv.setName(scv.getService().getSpecification().getName()+"_RFS");
		System.out.println("name set successfully to service");
		
		System.out.println("Calling find specification method");
		serv.setSpecification((ServiceSpecification) MapperClass.findSpec("Access_RFS"));
		System.out.println("specification set succesfully");
		
		List<Service> makeServices=new ArrayList<Service>();
		makeServices.add(serv);
		System.out.println("Creating the service");
		List<Service> createdService=rfsServMgr.createService(makeServices);
		rfsService=createdService.get(0);
		
		return rfsService;
		
		
	}
	
	
}
