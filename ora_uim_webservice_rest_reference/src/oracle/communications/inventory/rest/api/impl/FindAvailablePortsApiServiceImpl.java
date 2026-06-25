package oracle.communications.inventory.rest.api.impl;

import oracle.communications.inventory.api.entity.DeviceInterface;
import oracle.communications.inventory.api.entity.LogicalDevice;
import oracle.communications.inventory.api.framework.logging.Log;
import oracle.communications.inventory.api.framework.logging.LogFactory;
import oracle.communications.inventory.api.framework.security.UserEnvironment;
import oracle.communications.inventory.api.logicaldevice.LogicalDeviceManager;
import oracle.communications.inventory.api.logicaldevice.LogicalDeviceSearchCriteria;
import oracle.communications.inventory.rest.api.*;
import oracle.communications.inventory.rest.common.InventoryTransactionValue;
import oracle.communications.inventory.rest.configuration.RestUtils;
import oracle.communications.inventory.rest.exceptions.InvalidRequestException;
import oracle.communications.inventory.rest.model.DeviceInfoType;
import oracle.communications.inventory.rest.model.FindPortsResponseType;
import oracle.communications.inventory.rest.model.PortInfoType;
import oracle.communications.platform.persistence.CriteriaItem;
import oracle.communications.platform.persistence.CriteriaOperator;
import oracle.communications.platform.persistence.PersistenceHelper;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@RequestScoped
public class FindAvailablePortsApiServiceImpl implements InventoryRootService ,FindAvailablePortsApiService {
	private static Log log = LogFactory.getLog(FindAvailablePortsApiServiceImpl.class);
	@Inject
	private RestUtils restUtils;
	public Response findAvailablePorts(String serviceLocationCode, String networkLocationCode, SecurityContext securityContext) {

	    log.info("FindAvailablePorts", "Entered method");

	    if ((serviceLocationCode == null || serviceLocationCode.trim().isEmpty())
	            && (networkLocationCode == null || networkLocationCode.trim().isEmpty())) {

	        throw new InvalidRequestException(
	                "rest.invalidRequest",
	                "",
	                "Either serviceLocationCode or networkLocationCode must be provided");
	    }

	    InventoryTransactionValue ut = null;
	    FindPortsResponseType response = new FindPortsResponseType();

	    try {
	        UserEnvironment ue = startUserEnvironment(restUtils.getHttpRequest());
	        ut = startTransaction();
	        ut.setUserEnvironment(ue);

	        List<DeviceInfoType> deviceInfoList = new ArrayList<>();

	        LogicalDeviceManager ldMgr = PersistenceHelper.makeLogicalDeviceManager();
	        LogicalDeviceSearchCriteria ldCriteria = ldMgr.makeLogicalDeviceSearchCriteria();

	        if (serviceLocationCode != null && !serviceLocationCode.trim().isEmpty()) {
	            CriteriaItem cItem = ldCriteria.makeCriteriaItem();
	            cItem.setValue(serviceLocationCode);
	            cItem.setOperator(CriteriaOperator.CONTAINS_IGNORE_CASE);
	            ldCriteria.setServiceLocation(cItem);
	        }

	        List<LogicalDevice> logicalDevices = ldMgr.findLogicalDevice(ldCriteria);

	        if (logicalDevices != null && !logicalDevices.isEmpty()) {

	            for (LogicalDevice ld : logicalDevices) {

	                List<PortInfoType> availablePorts = new ArrayList<>();
	                DeviceInfoType deviceInfo = new DeviceInfoType();

	                List<DeviceInterface> deviceInterfaces = ld.getDeviceInterfaces();

	                if (deviceInterfaces != null && !deviceInterfaces.isEmpty()) {

	                    for (DeviceInterface di : deviceInterfaces) {

	                        if (di.getCurrentAssignment() == null) {
	                            PortInfoType port = new PortInfoType();
	                            port.setPortName(di.getName());
	                            port.setPortId(di.getId());
	                            if (di.getSpecification() != null) {
	                                port.setPortType(
	                                    di.getSpecification().getName());
	                            }

	                            if (di.getAdminState() != null) {
	                                port.setLifecycleState(
	                                    di.getAdminState().getValue());
	                            }

	                            availablePorts.add(port);
	                        }
	                    }

	                    if (!availablePorts.isEmpty()) {
	                        deviceInfo.setDeviceName(ld.getName());
	                        deviceInfo.setDeviceId(ld.getId());
	                        deviceInfo.setAvailablePorts(availablePorts);
	                        deviceInfo.setAvailablePortCount(availablePorts.size());

	                        deviceInfoList.add(deviceInfo);
	                    }
	                } else {
	                    log.info("FindAvailablePorts", "No interfaces for device: " + ld.getName());
	                }
	            }
	        }

	        response.setDevices(deviceInfoList);
	        response.setLocationCode(serviceLocationCode);

	        return Response.status(Response.Status.OK)
	                .entity(response)
	                .header("Last-Modified", restUtils.getLastModifiedDate())
	                .build();

	    } catch (Exception e) {
	        log.error("Error in findAvailablePorts", e);

	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                .entity("Internal server error")
	                .build();

	    } finally {
	        commitOrRollback(ut);
	        log.info("FindAvailablePorts", "Transaction completed");
	    }
	}
	
}
