package oracle.communications.inventory.rest.api.impl_helperClass;

import java.util.ArrayList;
import java.util.List;

import oracle.communications.inventory.api.consumer.ConditionManager;
import oracle.communications.inventory.api.entity.ConditionType;
import oracle.communications.inventory.api.entity.PhysicalPort;
import oracle.communications.inventory.api.entity.PhysicalPortCondition;
import oracle.communications.inventory.api.entity.common.Condition;
import oracle.communications.platform.persistence.PersistenceHelper;

public class CreateReservationCondition {
	 public static void createCondition(PhysicalPort port,
             long reservationNumber)
throws Exception {

System.out.println("=======================================");
System.out.println("Creating Condition on Port");
System.out.println("=======================================");

if (port == null) {
throw new Exception("Physical Port is NULL");
}

ConditionManager conditionManager =
PersistenceHelper.makeConditionManager();

PhysicalPortCondition condition =
conditionManager.makeCondition(PhysicalPortCondition.class);

condition.setPhysicalPort(port);

condition.setType(ConditionType.INFORMATIONAL);

condition.setReason("for Reservation");
List<Condition> list =new ArrayList<Condition>();

list.add(condition);

conditionManager.createConditions(list);

System.out.println("Condition Created Successfully");
System.out.println("Port ID            : " + port.getId());
System.out.println("Reservation Number : " + reservationNumber);
System.out.println("Condition Type     : INFORMATIONAL");
System.out.println("Reason             : " + reservationNumber);
System.out.println("=======================================");
}
}
