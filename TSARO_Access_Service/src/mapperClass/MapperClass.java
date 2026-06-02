package mapperClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import oracle.communications.inventory.api.entity.InventoryConfigurationSpec;
import oracle.communications.inventory.api.entity.LogicalDevice;
import oracle.communications.inventory.api.entity.LogicalDeviceSpecification;
import oracle.communications.inventory.api.entity.Service;
import oracle.communications.inventory.api.entity.ServiceConfigurationItem;
import oracle.communications.inventory.api.entity.ServiceConfigurationVersion;
import oracle.communications.inventory.api.entity.Specification;
import oracle.communications.inventory.api.entity.SpecificationRel;
import oracle.communications.inventory.api.exception.ValidationException;
import oracle.communications.inventory.api.logicaldevice.LogicalDeviceManager;
import oracle.communications.inventory.api.logicaldevice.LogicalDeviceSearchCriteria;
import oracle.communications.inventory.api.service.ServiceConfigurationManager;
import oracle.communications.inventory.api.specification.SpecManager;
import oracle.communications.platform.persistence.CriteriaItem;
import oracle.communications.platform.persistence.CriteriaOperator;
import oracle.communications.platform.persistence.Finder;
import oracle.communications.platform.persistence.PersistenceHelper;

public class MapperClass {

	//1)Finding the specification
	public static Specification findSpec(String specName) {
		
		Specification spec=null;
		Finder f=PersistenceHelper.makeFinder();
		Collection<Specification> specCollection=f.findByName(Specification.class,specName );
		if(!specCollection.isEmpty()) {
return specCollection.iterator().next();
		}
		
		return spec;
	}
	
	
//2)finding RFS service configuration specification	
public static Specification findRFSServiceConfigurationSpec(String specName) {
		
	InventoryConfigurationSpec spec=null;
		Finder f=PersistenceHelper.makeFinder();
		Collection<InventoryConfigurationSpec> scSpecCollection=f.findByName(InventoryConfigurationSpec.class,specName );
		if(!scSpecCollection.isEmpty()) {
return scSpecCollection.iterator().next();
		}
		
		return spec;
	}

//3)Finding Logical device 
public static LogicalDevice findLogicalDeviceOrCreate(String name,String ldspec) throws ValidationException {
	
	LogicalDeviceManager ldMngr=PersistenceHelper.makeLogicalDeviceManager();
	System.out.println("Executing serach criteria for finding logical device specification");
	LogicalDeviceSearchCriteria ldSearch=ldMngr.makeLogicalDeviceSearchCriteria();
	CriteriaItem cItem=ldSearch.makeCriteriaItem();
	cItem.setOperator(CriteriaOperator.EQUALS);
	cItem.setValue(name);
	ldSearch.setName(cItem);
	System.out.println("Finding the logical device specification with name : "+ldspec);
	LogicalDeviceSpecification ldSpec=(LogicalDeviceSpecification) findSpec(ldspec);
	ldSearch.setLogicalDeviceSpecification(ldSpec);
	ldSearch.setCriteriaItem(cItem);
	List<LogicalDevice> ldFind=ldMngr.findLogicalDevice(ldSearch);
	if(!ldFind.isEmpty()) {
		System.out.println("Logical device found : "+ldFind.get(0).getName().trim());
		return ldFind.get(0);
	}
	
	else {
		System.out.println("Logical device not found with name : "+name+" and specification "+ldspec +" Creating Logical device with : "+name+" and specification "+ldspec);
		LogicalDevice ldCreate=ldMngr.makeLogicalDevice();
		ldCreate.setSpecification(ldSpec);
		ldCreate.setName(name);
		List<LogicalDevice> lDListToCreate=new ArrayList<>();
		lDListToCreate.add(ldCreate);
		List<LogicalDevice> ldCreatedListList=ldMngr.createLogicalDevice(lDListToCreate);
		System.out.println("lodical device created succesfully with name :"+name);
		if(!ldCreatedListList.isEmpty()) {
			return ldCreatedListList.get(0);
		}

	}
	return null;
		
}

//4)Finding the service configuration items

public static ServiceConfigurationItem findServiceConfigurationItem(ServiceConfigurationVersion scv,
		String scItemName) {
System.out.println("finding service configuration item ");
	if (scv.getConfigItems() != null) {
		for (ServiceConfigurationItem serConItem : scv.getConfigItems()) {
			if (null != serConItem && null != serConItem.getName()
					&& serConItem.getName().equalsIgnoreCase(scItemName)) {
				System.out.println("found service configuration item");
				return serConItem;
			}

		}

	}

	return null;
}

public static ServiceConfigurationItem findOrCreateChildConfigItem(ServiceConfigurationItem parentConfigItem,
        String childConfigItemName) {

    ServiceConfigurationItem existingItem = null;

    try {
        // Check for existing child configuration items
        List<ServiceConfigurationItem> childConfigItemsList = parentConfigItem.getChildConfigItems();
        System.out.println("Child config Item List: " + childConfigItemsList);

        // Look for the specific child configuration item with the matching name and label
        for (ServiceConfigurationItem sci : childConfigItemsList) {
            if (sci.getName().equals(childConfigItemName)) {
                existingItem = sci; // Found the existing item
                System.out.println("Found existing config Item: " + sci);
                return existingItem;  // Return the found item
            }
        }
        System.out.println("Child Config item with name "+childConfigItemName+" not exist .Creating child config item with name "+childConfigItemName);
        // If not found, create a new child config item
        ServiceConfigurationManager designer = PersistenceHelper.makeServiceConfigurationManager();
        InventoryConfigurationSpec configSpec = getConfigItemSpecByName(parentConfigItem, childConfigItemName);

        // Only create a new item if not already found
        Collection<?> childConfigItems = designer.createConfigurationItems(parentConfigItem, configSpec, 1);
        if (childConfigItems != null && !childConfigItems.isEmpty()) {
            existingItem = (ServiceConfigurationItem) childConfigItems.iterator().next();
            System.out.println("Created new config item: " + existingItem);
        }

    } catch (Exception ex) {
        System.out.println("Error: " + ex.getLocalizedMessage());
    }

    // Return the found or newly created item
    return existingItem;
}

public static InventoryConfigurationSpec getConfigItemSpecByName(ServiceConfigurationItem parentConfigItem,

		String configItemName) {



	InventoryConfigurationSpec sChild = null;

	SpecManager specManager = PersistenceHelper.makeSpecManager();

	List<SpecificationRel> specRels = null;

	try {

		specRels = specManager.getSpecificationRels(parentConfigItem.getConfigSpec(), null, true, 0);

	} catch (Exception ex1) {

		System.out.println("Error......" + ex1.getLocalizedMessage());

	}

	if (specRels != null) {

		for (SpecificationRel relItems : specRels) {

			if (relItems != null) {

				Specification child = relItems.getChild();

				if (child != null && child.getName().equalsIgnoreCase(configItemName.trim())) {

					sChild = (InventoryConfigurationSpec) child;

				}

			}

		}

	}

	

	return sChild;

}

public static ServiceConfigurationItem createconfigItem(ServiceConfigurationVersion scv,String configItemName) throws Exception {

	 ServiceConfigurationItem parentItem=(ServiceConfigurationItem) scv.getConfigItemTypeConfig();

	 ServiceConfigurationManager scvMgr=PersistenceHelper.makeServiceConfigurationManager();

	 InventoryConfigurationSpec scvItemspec=(InventoryConfigurationSpec) findSpec(configItemName);

	Collection<?> ItemList= scvMgr.createConfigurationItems(parentItem, scvItemspec, 1);

	if(!ItemList.isEmpty()) {

	ServiceConfigurationItem newItem=(ServiceConfigurationItem) ItemList.iterator().next();

	newItem.setName(configItemName);

	newItem.setLabel(configItemName);

	return newItem;

	}

	 return  null;

}
	
}





