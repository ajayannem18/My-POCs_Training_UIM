package com.uim.practice;


import java.util.ArrayList;
import java.util.Collection;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import oracle.communications.inventory.api.entity.Service;
import oracle.communications.inventory.api.entity.ServiceSpecification;
import oracle.communications.inventory.api.service.ServiceManager;
import oracle.communications.platform.persistence.Finder;
import oracle.communications.platform.persistence.PersistenceHelper;

public class CreateService {

	public static void createServivce()  {

		Finder f=null;
		UserTransaction ut=null;
		String name="Access_CFS";
		String ServiceName="sampleService";
		try {
			ut=PersistenceHelper.makePersistenceManager().getTransaction();
			ut.begin();
			f=PersistenceHelper.makeFinder();
			ServiceSpecification sSpec=(ServiceSpecification) f.findByName(ServiceSpecification.class, name).iterator().next();
			if(sSpec!=null) {
			System.out.println("Service Specification found with name : "+sSpec.getName());
			
			ServiceManager sMgr=PersistenceHelper.makeServiceManager();
			
			Service serv=sMgr.makeService(Service.class);
			serv.setName(ServiceName);
			serv.setSpecification(sSpec);
			serv.setDescription("This is sample service");
			
			Collection<Service> serviceCollection=new ArrayList<>();
			serviceCollection.add(serv);
			
			Service service=sMgr.createService(serviceCollection).get(0);
			System.out.println("Service created successfully with name : "+service.getName()+" , description : "+
			service.getDescription()+" , status : "+service.getAdminState().getValue());
			
			}
			else {
				System.out.println("No Service specification found with name : "+name);
			}
			
			ut.commit();
			
		}
		
	catch(Exception e) {
			try {
				ut.rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		e.printStackTrace();
	}
	
}
}