package helperMethods;

import java.util.Collection;
import java.util.List;

import oracle.communications.inventory.api.entity.InventoryConfigurationSpec;
import oracle.communications.inventory.api.entity.ServiceConfigurationItem;
import oracle.communications.inventory.api.entity.ServiceConfigurationVersion;
import oracle.communications.inventory.api.entity.common.InventoryConfigurationItem;
import oracle.communications.inventory.api.exception.ValidationException;
import oracle.communications.inventory.api.service.ServiceConfigurationManager;
import oracle.communications.platform.persistence.Finder;
import oracle.communications.platform.persistence.PersistenceHelper;

public class FindOrCreateConfigItems {
public static ServiceConfigurationItem findOrCreateConfigItems(ServiceConfigurationVersion scv,String name) throws ValidationException {
	System.out.println("Running findOrCreate Service Configuration Item method");
	ServiceConfigurationItem scItem=null;
	List<ServiceConfigurationItem> scItems=scv.getConfigItems();
	for(ServiceConfigurationItem item:scItems) {
		if(item.getName().equalsIgnoreCase(name)) {
			System.out.println("Found the service Configuration Item with name : "+name);
			scItem=item;
		}
		else {
			InventoryConfigurationItem invItem=scv.getConfigItemTypeConfig();
			System.out.println("inv Item of getConfigItemTypeConfig is : "+invItem.getName());
			ServiceConfigurationManager scMgr=PersistenceHelper.makeServiceConfigurationManager();
			Finder f=PersistenceHelper.makeFinder();
			ServiceConfigurationItem itemSpec=f.findByName(ServiceConfigurationItem.class, name).iterator().next();
			if(itemSpec!=null) {
				System.out.println("Service Configuration item specification found with name : "+name);
				Collection<?> ItemList=scMgr.createConfigurationItems(itemSpec, (InventoryConfigurationSpec) invItem, 1);
				if(!ItemList.isEmpty()) {

					 scItem=(ServiceConfigurationItem) ItemList.iterator().next();

					 scItem.setName(name);

					 scItem.setLabel(name);

					return scItem;

					}
			}
			else {
				System.out.println("No service configuration item specification found with name : "+name);
				
			}
		}
	}
	return scItem;
	
}
}
