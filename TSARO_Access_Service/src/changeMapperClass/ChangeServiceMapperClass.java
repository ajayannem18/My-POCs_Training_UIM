package changeMapperClass;

import java.util.Date;
import java.util.List;

import mapperClass.MapperClass;
import oracle.communications.inventory.api.entity.BusinessInteractionState;
import oracle.communications.inventory.api.entity.Service;
import oracle.communications.inventory.api.entity.ServiceConfigurationItem;
import oracle.communications.inventory.api.entity.ServiceConfigurationVersion;
import oracle.communications.inventory.api.entity.common.InventoryConfigurationItem;
import oracle.communications.inventory.api.entity.common.InventoryConfigurationVersion;
import oracle.communications.inventory.api.exception.ValidationException;
import oracle.communications.inventory.api.service.ServiceConfigurationManager;
import oracle.communications.inventory.api.util.Utils;
import oracle.communications.platform.persistence.PersistenceHelper;
import oracle.communications.platform.persistence.Persistent;

public class ChangeServiceMapperClass {

    // =========================
    // FIND CFS CONFIG (IN_PROGRESS)
    // =========================
    public static ServiceConfigurationVersion findServiceConfiguration(Service serv)
            throws ValidationException {

        System.out.println("Finding IN_PROGRESS CFS config");

        ServiceConfigurationManager scm =
                PersistenceHelper.makeServiceConfigurationManager();

        List<InventoryConfigurationVersion> configs =
                scm.getEntityConfigurationVersions(serv, BusinessInteractionState.IN_PROGRESS);

        if (!Utils.isEmpty(configs)) {
            InventoryConfigurationVersion invConfig = configs.get(0);
            System.out.println("Found config: " + invConfig.getName());
            return (ServiceConfigurationVersion) invConfig;
        }

        throw new ValidationException("No IN_PROGRESS configuration found for service");
    }

    // =========================
    // FIND RFS CONFIG ITEM
    // =========================
    public static ServiceConfigurationItem findRfsConfigItem(ServiceConfigurationVersion scv,
                                                             String name) {

        if (scv == null || name == null) return null;

        for (InventoryConfigurationItem item : scv.getConfigItems()) {
            if (item.getName() != null &&
                    item.getName().equalsIgnoreCase(name)) {
                return (ServiceConfigurationItem) item;
            }
        }
        return null;
    }

    // =========================
    // GET RFS SERVICE FROM ASSIGNMENT
    // =========================
    public static Service getServiceFromAssignment(ServiceConfigurationItem cfsItem)
            throws ValidationException {

        if (cfsItem == null || cfsItem.getAssignment() == null) {
            throw new ValidationException("No assignment found on CFS config item");
        }

        Persistent assignment = cfsItem.getAssignment();

        // ✅ Handle both types
        if (assignment instanceof oracle.communications.inventory.api.entity.ServiceAssignment) {
            return ((oracle.communications.inventory.api.entity.ServiceAssignment) assignment).getService();
        }

        if (assignment instanceof oracle.communications.platform.entity.impl.ServiceAssignmentToServiceDAO) {
            return ((oracle.communications.platform.entity.impl.ServiceAssignmentToServiceDAO) assignment).getService();
        }

        throw new ValidationException("Unsupported assignment type: " + assignment.getClass().getName());
    }

    // =========================
    // CREATE NEW RFS CONFIG VERSION
    // =========================
    public static ServiceConfigurationVersion createNewRFSSCV(Service rfsService)
            throws ValidationException {

        ServiceConfigurationManager scMgr =
                PersistenceHelper.makeServiceConfigurationManager();

        ServiceConfigurationVersion newScv =
                (ServiceConfigurationVersion) scMgr.makeConfigurationVersion(rfsService);
newScv.setEffDate(new Date ());

ServiceConfigurationVersion newSc=(ServiceConfigurationVersion) scMgr.createConfigurationVersion(rfsService, newScv);

        if (newSc == null) {
            throw new ValidationException("Failed to create new RFS config");
        }

        System.out.println("Created NEW RFS SCV: "
                + newSc.getName() + " Version: " + newSc.getVersionNumber());

        return newSc;
    }

    // =========================
    // FIXED PARENT METHOD (NO RECURSION)
    // =========================
    public static ServiceConfigurationItem findOrCreateParentServiceConfigurationItem(
            ServiceConfigurationVersion scv,
            String configItemName) throws Exception {

        if (scv == null || configItemName == null) {
            throw new ValidationException("SCV or configItemName is null");
        }

        System.out.println("Finding parent config item: " + configItemName);

        ServiceConfigurationItem existingItem =
                MapperClass.findServiceConfigurationItem(scv, configItemName);

        if (existingItem != null) {
            return existingItem;
        }

        System.out.println("Parent not found → creating");

        ServiceConfigurationItem newItem =
                MapperClass.createconfigItem(scv, configItemName);

        if (newItem == null) {
            throw new ValidationException("Failed to create parent config item");
        }

        return newItem;
    }

    // =========================
    // CHILD METHOD
    // =========================
    public static ServiceConfigurationItem findOrCreateChildServiceConfigurationItem(
            ServiceConfigurationItem parentItem,
            String childName) throws ValidationException {

        if (parentItem == null || childName == null) {
            throw new ValidationException("Parent or childName is null");
        }

        System.out.println("Finding child config item: " + childName);

        ServiceConfigurationItem childItem =
                MapperClass.findOrCreateChildConfigItem(parentItem, childName);

        if (childItem != null) {
            return childItem;
        }

        throw new ValidationException("Failed to create/find child config item");
    }
    
   
}