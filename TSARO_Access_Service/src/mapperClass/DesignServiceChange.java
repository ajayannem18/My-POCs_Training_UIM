package mapperClass;

import java.util.ArrayList;
import java.util.Collection;

import changeMapperClass.ChangeServiceMapperClass;
import oracle.communications.inventory.api.common.EntityUtils;
import oracle.communications.inventory.api.entity.LogicalDevice;
import oracle.communications.inventory.api.entity.Service;
import oracle.communications.inventory.api.entity.ServiceConfigurationItem;
import oracle.communications.inventory.api.entity.ServiceConfigurationVersion;
import oracle.communications.inventory.api.exception.ValidationException;
import oracle.communications.inventory.api.service.ServiceConfigurationManager;
import oracle.communications.inventory.xmlbeans.BusinessInteractionItemType;
import oracle.communications.inventory.xmlbeans.ParameterType;
import oracle.communications.platform.persistence.PersistenceHelper;

public class DesignServiceChange {

    public static void modifyService(ServiceConfigurationVersion scvofCFS,
                                     BusinessInteractionItemType itemtype)
            throws ValidationException {

        System.out.println("===== START modifyService =====");

        String mediaType = null;
        String peDeviceHostName = null;
        String switchDeviceHostName = null;
        String oltDeviceHostName = null;

        try {

            // ======================
            // READ INPUT
            // ======================
            for (ParameterType param : itemtype.getParameterList()) {

                if (param == null || param.getName() == null || param.getValue() == null)
                    continue;

                String value = param.getValue().newCursor().getTextValue();

                System.out.println("Param: " + param.getName() + " = " + value);

                switch (param.getName()) {
                    case "MediaType": mediaType = value; break;
                    case "PeDeviceName": peDeviceHostName = value; break;
                    case "SwitchHostName": switchDeviceHostName = value; break;
                    case "OltDeviceName":
                    case "OltDevceName": oltDeviceHostName = value; break;
                }
            }

            // ======================
            // FIND CFS
            // ======================
 System.out.println("We have the CFS Service Configuration with name : "+scvofCFS);           
            Service cfsService = scvofCFS.getService();
System.out.println("Got the CFS service with name "+cfsService.getName() +" and all the service datails are : "+cfsService);
            ServiceConfigurationItem cfsItem =
                    ChangeServiceMapperClass.findRfsConfigItem(scvofCFS, "RFS");
System.out.println("Got the CFS config Item with name RFS");

            Service rfsService =
                    ChangeServiceMapperClass.getServiceFromAssignment(cfsItem);
            System.out.println("Got the RFS service with name : "+rfsService.getName());

            // ======================
            // CREATE RFS CONFIG
            // ======================
            ServiceConfigurationVersion newRfsScv =
                    ChangeServiceMapperClass.createNewRFSSCV(rfsService);
          System.out.println("New RFS service configuration created succesfully");
            

            // ======================
            // CONFIG ITEMS
            // ======================
            ServiceConfigurationItem parentItem =
            ChangeServiceMapperClass.findOrCreateParentServiceConfigurationItem(newRfsScv, "accessDevice");
            ServiceConfigurationItem peItem =
            ChangeServiceMapperClass.findOrCreateChildServiceConfigurationItem(parentItem, "PeDevice");
            ServiceConfigurationItem switchItem=null;
            ServiceConfigurationItem oltItem =null;
            
            if(mediaType.equalsIgnoreCase("Fiber") ||mediaType.equalsIgnoreCase("Microwave")) 
            {
             switchItem =ChangeServiceMapperClass.findOrCreateChildServiceConfigurationItem(parentItem, "SwitchDevice");
            }
            else  if(mediaType=="Gpon") {
             oltItem =ChangeServiceMapperClass.findOrCreateChildServiceConfigurationItem(parentItem, "OLTDevice");
            }
            
            // ======================
            // UNASSIGN
            // ======================
            ServiceConfigurationManager scMgr = PersistenceHelper.makeServiceConfigurationManager();
            Collection<ServiceConfigurationItem> items = new ArrayList<>();

            if (peItem != null && peItem.getAssignment() != null) {
                items.add(peItem);
            }

            if (switchItem != null && switchItem.getAssignment() != null) {
                items.add(switchItem);
            }

            if (oltItem != null && oltItem.getAssignment() != null) {
                items.add(oltItem);
            }

            if (!items.isEmpty()) {
                scMgr.unallocateInventoryConfigurationItems(items);
            }

            // ======================
            // ASSIGN NEW
            // ======================
            if (peDeviceHostName != null) {
                LogicalDevice pe =MapperClass.findLogicalDeviceOrCreate(peDeviceHostName, "peDevice");
                scMgr.assignResource(peItem, pe, null, null);
            }
            if(mediaType.equalsIgnoreCase("Fiber") ||mediaType.equalsIgnoreCase("Microwave")) {

            if (switchDeviceHostName != null) {
                LogicalDevice sw =MapperClass.findLogicalDeviceOrCreate(switchDeviceHostName, "accessDevice");
                scMgr.assignResource(switchItem, sw, null, null);
            }
            }
            
            
            else if ("GPON".equalsIgnoreCase(mediaType) && oltDeviceHostName != null) {
                LogicalDevice olt =MapperClass.findLogicalDeviceOrCreate(oltDeviceHostName, "oltDevice");
                scMgr.assignResource(oltItem, olt, null, null);
            }

            EntityUtils.setValue(parentItem, "mediaType", mediaType);

            System.out.println("===== SUCCESS =====");

        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException("Modify Service Failed: " + e.getMessage());
        }
    }
}