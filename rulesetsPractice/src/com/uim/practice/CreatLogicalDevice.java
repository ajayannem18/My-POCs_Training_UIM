package com.uim.practice;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import oracle.communications.inventory.api.entity.LogicalDevice;
import oracle.communications.inventory.api.entity.LogicalDeviceSpecification;
import oracle.communications.inventory.api.logicaldevice.LogicalDeviceManager;
import oracle.communications.inventory.api.logicaldevice.LogicalDeviceSearchCriteria;
import oracle.communications.platform.persistence.CriteriaItem;
import oracle.communications.platform.persistence.CriteriaOperator;
import oracle.communications.platform.persistence.Finder;
import oracle.communications.platform.persistence.PersistenceHelper;

public class CreatLogicalDevice {

	public static void createLD() throws IllegalStateException, SecurityException, SystemException {
		
	    UserTransaction ut=null;
		Finder f=PersistenceHelper.makeFinder();
		LogicalDeviceManager ldMgr=PersistenceHelper.makeLogicalDeviceManager();
		String spec="test_LD1";
		String name="sample_Logical_Device_1";
		
		
		try {
			
			ut=PersistenceHelper.makePersistenceManager().getTransaction();
		    ut.begin();
		    LogicalDevice ld=null;
		    
			LogicalDeviceSpecification ldSpec=f.findByName(LogicalDeviceSpecification.class, spec).iterator().next();
			System.out.println("Logical device spec found with name : "+ldSpec.getName());
			
			
			
			LogicalDeviceSearchCriteria ldSearch=ldMgr.makeLogicalDeviceSearchCriteria();
			CriteriaItem cItem=ldSearch.makeCriteriaItem();
			cItem.setValue(name);
			cItem.setOperator(CriteriaOperator.EQUALS);
			ldSearch.setName(cItem);
			ldSearch.setLogicalDeviceSpecification(ldSpec);
			 ld=ldMgr.findLogicalDevice(ldSearch).get(0);
			if(ld.equals(null)) {
				System.out.println("No logical device exist in UIM with name : "+name+" ..Creating new Logical device with : "+name);
			 ld=ldMgr.makeLogicalDevice();
			ld.setSpecification(ldSpec);
			ld.setName(name);
			
			Collection<LogicalDevice> ldCollection=new ArrayList<>();
			ldCollection.add(ld);
			
			LogicalDevice ldList=ldMgr.createLogicalDevice(ldCollection).get(0);
			System.out.println("Logical device creation succesfully");
			System.out.println("The name of created Logical deviceis : "+ldList.getName());
			System.out.println("The id of created Logical device is : "+ldList.getId());
			
			ut.commit();
			}
			else {
				System.out.println("Logical device already exist with name : "+ld.getName()+" and ID : "+ld.getId()+" and status : "+ld.getAdminState().getValue());
			}
			
			
			
					}
		catch(Exception e) {
			e.printStackTrace();
			ut.rollback();
		}
		
	}
	
	
}
