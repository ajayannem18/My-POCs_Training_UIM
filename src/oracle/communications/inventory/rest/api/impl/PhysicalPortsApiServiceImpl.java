package oracle.communications.inventory.rest.api.impl;

import oracle.communications.inventory.api.consumer.ConditionManager;
import oracle.communications.inventory.api.consumer.ConditionSearchCriteria;
import oracle.communications.inventory.api.consumer.ReservationManager;
import oracle.communications.inventory.api.entity.PhysicalDevice;
import oracle.communications.inventory.api.entity.PhysicalPort;
import oracle.communications.inventory.api.entity.PropertyLocation;
import oracle.communications.inventory.api.entity.common.Condition;
import oracle.communications.inventory.api.equipment.EquipmentManager;
import oracle.communications.inventory.api.equipment.PhysicalDeviceSearchCriteria;
import oracle.communications.inventory.api.framework.security.UserEnvironment;
import oracle.communications.inventory.rest.api.*;
import oracle.communications.inventory.rest.api.impl_helperClass.CreateReservationCondition;
import oracle.communications.inventory.rest.api.impl_helperClass.FindNearestPop;
import oracle.communications.inventory.rest.api.impl_helperClass.ReservePort;
import oracle.communications.inventory.rest.common.InventoryTransactionValue;
import oracle.communications.inventory.rest.configuration.RestUtils;
import oracle.communications.inventory.rest.model.ReservePortRequestType;
import oracle.communications.inventory.rest.model.ReservePortResponseType;
import oracle.communications.platform.persistence.CriteriaItem;
import oracle.communications.platform.persistence.CriteriaOperator;
import oracle.communications.platform.persistence.PersistenceHelper;

import java.util.Collection;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@RequestScoped
public class PhysicalPortsApiServiceImpl implements InventoryRootService, PhysicalPortsApiService {

    @Inject
    private RestUtils utils;

    public Response reserveFeasibilityPort(ReservePortRequestType body, SecurityContext securityContext) {

        System.out.println("========== Started executing reserveFeasibilityPort method ==========");

        ReservePortResponseType response = new ReservePortResponseType();

        Double lat = null;
        Double lon = null;
        String description = null;

        // ---------- Request body null check ----------
        if (body == null) {
            System.out.println("Request body is null. Aborting.");
            response.setSuccess(false);
            response.setDescription("Request body is mandatory.");
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }

        // ---------- Latitude validation ----------
        System.out.println("Validating customer latitude...");
        if (body.getCustomerLatitude() != null
                && !body.getCustomerLatitude().isNaN()
                && body.getCustomerLatitude() >= -90
                && body.getCustomerLatitude() <= 90) {

            lat = body.getCustomerLatitude();
            System.out.println("Customer latitude is valid : " + lat);

        } else {
            System.out.println("Invalid or missing customer latitude in request : " + body.getCustomerLatitude());
            response.setSuccess(false);
            response.setDescription("The customer latitude is mandatory and must be between -90 and 90.");
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }

        // ---------- Longitude validation ----------
        System.out.println("Validating customer longitude...");
        if (body.getCustomerLongitude() != null
                && !body.getCustomerLongitude().isNaN()
                && body.getCustomerLongitude() >= -180
                && body.getCustomerLongitude() <= 180) {

            lon = body.getCustomerLongitude();
            System.out.println("Customer longitude is valid : " + lon);

        } else {
            System.out.println("Invalid or missing customer longitude in request : " + body.getCustomerLongitude());
            response.setSuccess(false);
            response.setDescription("The customer longitude is mandatory and must be between -180 and 180.");
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }

        // ---------- Description validation ----------
        System.out.println("Validating description...");
        if (body.getDescription() != null && !body.getDescription().trim().isEmpty()) {
            description = body.getDescription();
            System.out.println("Description is valid : " + description);
        } else {
            System.out.println("Invalid or missing description in request.");
            response.setSuccess(false);
            response.setDescription("The description is mandatory and cannot be empty.");
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }

        System.out.println("All input validations passed. lat=" + lat + ", lon=" + lon + ", description=" + description);

        UserEnvironment ue = null;
        InventoryTransactionValue ut = null;
        PhysicalDevice currentPd = null;
        PhysicalPort selectedPort = null;

        ConditionManager conditionManager = PersistenceHelper.makeConditionManager();

        try {
            System.out.println("Starting user environment and transaction...");
            ue = startUserEnvironment(utils.getHttpRequest());
            ut = startTransaction();
            ut.setUserEnvironment(ue);
            System.out.println("User environment and transaction set successfully.");

            System.out.println("Calling findNearestPop with lat=" + lat + ", lon=" + lon);
            PropertyLocation nearestPopLocation = FindNearestPop.findNearestPop(lat, lon);

            String networkLocationCode = null;
            if (nearestPopLocation != null) {
                networkLocationCode = nearestPopLocation.getNetworkLocationCode();
                System.out.println("Nearest property location found. networkLocationCode = " + networkLocationCode);
            } else {
                System.out.println("No nearest property location found for the customer's coordinates.");
                response.setSuccess(false);
                response.setDescription("Nearest property location not found for customer.");
                return Response.status(Response.Status.NOT_FOUND).entity(response).build();
            }

            if (networkLocationCode == null || networkLocationCode.isEmpty()) {
                System.out.println("Network location code near the customer is null/empty.");
                response.setSuccess(false);
                response.setDescription("No network location code found near the customer.");
                return Response.status(Response.Status.NOT_FOUND).entity(response).build();
            }

            System.out.println("Searching physical devices at networkLocationCode = " + networkLocationCode);
            EquipmentManager equipMgr = PersistenceHelper.makeEquipmentManager();
            PhysicalDeviceSearchCriteria pdSearchCriteria = equipMgr.makePhysicalDeviceSearchCriteria();
            CriteriaItem cItem = pdSearchCriteria.makeCriteriaItem();
            cItem.setValue(networkLocationCode);
            cItem.setOperator(CriteriaOperator.EQUALS);
            pdSearchCriteria.setNetworkLocationCode(cItem);
            System.out.println("Search criteria set successfully to find physical devices at pop : " + networkLocationCode);

            List<PhysicalDevice> physicalDevices = equipMgr.findPhysicalDevices(pdSearchCriteria);

            if (physicalDevices == null || physicalDevices.isEmpty()) {
                System.out.println("No physical devices found at networkLocationCode = " + networkLocationCode);
                response.setSuccess(false);
                response.setDescription("No physical devices found.");
                return Response.status(Response.Status.NOT_FOUND).entity(response).build();
            }

            System.out.println(physicalDevices.size() + " physical device(s) found at given pop location.");

            // ---------- Scan devices/ports, stop at the FIRST usable port ----------
            deviceLoop:
            for (PhysicalDevice physicaldevice : physicalDevices) {
                System.out.println("---- Processing physical device : " + physicaldevice.getName() + " ----");

                List<PhysicalPort> ports = physicaldevice.getPhysicalPorts();

                if (ports == null || ports.isEmpty()) {
                    System.out.println("No physical ports found on physical device " + physicaldevice.getName()
                            + ". Skipping to next device.");
                    continue;
                }

                System.out.println(ports.size() + " port(s) found on device " + physicaldevice.getName());

                for (PhysicalPort port : ports) {
                    System.out.println("Checking port ID : " + port.getId());

                    boolean assigned = port.getCurrentAssignment() != null;

                    if (assigned) {
                        System.out.println("Port ID " + port.getId() + " is already ASSIGNED. Skipping.");
                        continue;
                    }

                    // Only hit the condition lookup if the port is otherwise free.
                    ConditionSearchCriteria criteria = conditionManager.makeConditionSearchCriteria();
                    CriteriaItem item = criteria.makeCriteriaItem();
                    item.setValue(port);
                    item.setOperator(CriteriaOperator.EQUALS);
                    criteria.setResource(item);

                    Collection<? extends Condition> conditions = conditionManager.findConditions(criteria);
                    boolean hasCondition = conditions != null && !conditions.isEmpty();

                    if (hasCondition) {
                        System.out.println("Port ID " + port.getId() + " already has a condition. Skipping.");
                        continue;
                    }

                    // Found it — no assignment, no condition. Stop scanning.
                    System.out.println("Port ID " + port.getId() + " is FREE and has no condition. Selecting it.");
                    selectedPort = port;
                    currentPd = physicaldevice;
                    break deviceLoop;
                }

                System.out.println("---- Finished processing physical device : " + physicaldevice.getName()
                        + " (no eligible port found yet) ----");
            }

            if (selectedPort == null) {
                System.out.println("No available (unassigned, condition-free) port found at pop location "
                        + networkLocationCode);
                response.setSuccess(false);
                response.setDescription("No available ports found on any physical device at this location.");
                return Response.status(Response.Status.NOT_FOUND).entity(response).build();
            }

            System.out.println("Selected port ID : " + selectedPort.getId()
                    + " on device " + currentPd.getName()
                    + " at pop location " + networkLocationCode);

            System.out.println("Reserving port ID : " + selectedPort.getId());
            long reservationNumber = ReservePort.reservePort(selectedPort);
            System.out.println("Port reserved successfully. Reservation Number : " + reservationNumber);

            System.out.println("Creating reservation condition for port ID : " + selectedPort.getId());
            CreateReservationCondition.createCondition(selectedPort, reservationNumber);
            System.out.println("Reservation condition created successfully.");

            response.setSuccess(true);
            response.setDescription("Reservation successful");
            response.setReservationNumber(reservationNumber);
            response.setPhysicalDevice(currentPd.getName());
            response.setResourceId(selectedPort.getId());
            response.setPopLocation(nearestPopLocation.getNetworkLocationCode());

            System.out.println("Response built successfully : " + response.getDescription());

        } catch (Exception e) {
            System.out.println("Exception occurred while processing reserveFeasibilityPort : " + e.getMessage());
            e.printStackTrace();
            response.setSuccess(false);
            response.setDescription("Internal error while processing reservation request.");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();

        } finally {
            if (ut != null) {
                System.out.println("Committing/rolling back transaction...");
                commitOrRollback(ut);
                System.out.println("Transaction commit/rollback completed.");
            } else {
                System.out.println("No transaction was started; skipping commit/rollback.");
            }
            System.out.println("========== Completed executing reserveFeasibilityPort method ==========");
        }

        return Response.ok().entity(response).build();
    }
}