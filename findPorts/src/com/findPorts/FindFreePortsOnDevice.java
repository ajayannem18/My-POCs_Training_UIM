package com.findPorts;

import java.util.List;

import javax.transaction.UserTransaction;

import oracle.communications.inventory.api.entity.DeviceInterface;
import oracle.communications.inventory.api.entity.DeviceInterfacePhysicalPortRel;
import oracle.communications.inventory.api.entity.LogicalDevice;
import oracle.communications.inventory.api.entity.PhysicalPort;
import oracle.communications.inventory.api.logicaldevice.LogicalDeviceManager;
import oracle.communications.inventory.api.logicaldevice.LogicalDeviceSearchCriteria;
import oracle.communications.platform.persistence.CriteriaItem;
import oracle.communications.platform.persistence.CriteriaOperator;
import oracle.communications.platform.persistence.Finder;
import oracle.communications.platform.persistence.PersistenceHelper;

public class FindFreePortsOnDevice {
public static void findfreePortsOnDevice() throws Exception {
	System.out.println("Executing find ports method .....");
	UserTransaction ut=null;
	Finder f=null;
	try{
		ut=PersistenceHelper.makePersistenceManager().getTransaction();
		ut.begin();
		f=PersistenceHelper.makeFinder();
		String ldName="smoketest_LD1";
		LogicalDeviceManager ldMgr=PersistenceHelper.makeLogicalDeviceManager();
        LogicalDeviceSearchCriteria ldSearch=ldMgr.makeLogicalDeviceSearchCriteria();
        CriteriaItem cItem=ldSearch.makeCriteriaItem();
        cItem.setValue(ldName);
        cItem.setOperator(CriteriaOperator.EQUALS);
        ldSearch.setName(cItem);
        System.out.println("Search criteria set successfully");
        LogicalDevice ld=ldMgr.findLogicalDevice(ldSearch).iterator().next();
        if(ld!=null) {
        System.out.println("Found Logical device with name : "+ld.getName());
       List<DeviceInterface> deviceInterfaces= ld.getDeviceInterfaces();
        if(deviceInterfaces!=null) {
        	for(DeviceInterface di:deviceInterfaces) {
        		System.out.println("Found Device iterface with name : "+di.getName());
        	List<DeviceInterfacePhysicalPortRel> portsRel=di.getPhysicalPort();
        	for(DeviceInterfacePhysicalPortRel portRel:portsRel) {
        		PhysicalPort port=portRel.getPhysicalPort();
        		System.out.println("Found phisical port with name : "+port.getName()); 		
        	}
        	
        	
        	}
        }
        
        
        }
        else {
        	System.out.println("NO device found with name : "+ldName);
        }
		ut.commit();
		
	}
	catch (Exception e){
		ut.rollback();
		e.printStackTrace();
		
	}
	finally {
		System.out.println("Transaction completed");
	}
	
	
}
}
