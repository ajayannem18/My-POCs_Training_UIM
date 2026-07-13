package oracle.communications.inventory.rest.api.impl_helperClass;

import java.util.ArrayList;
import java.util.Collection;

import oracle.communications.inventory.api.consumer.ReservationManager;
import oracle.communications.inventory.api.entity.PhysicalPort;
import oracle.communications.inventory.api.entity.PhysicalPortReservation;
import oracle.communications.inventory.api.entity.ReservationType;
import oracle.communications.inventory.api.entity.ReservedForType;
import oracle.communications.inventory.api.entity.common.ConsumableResource;
import oracle.communications.platform.persistence.PersistenceHelper;

public class ReservePort {
	 public static long reservePort(PhysicalPort freePort) throws Exception {

	        System.out.println("==============================================");
	        System.out.println("Reservation Started");
	        System.out.println("==============================================");

	        ReservationManager reservationManager = PersistenceHelper.makeReservationManager();
	        //PhysicalPortReservation reservation =reservationManager.makeReservation(PhysicalPortReservation.class);
	        PhysicalPortReservation reservation =reservationManager.makeReservation(freePort);
	        reservation.setPhysicalPort(freePort);
	        reservation.setReservationType(ReservationType.SHORTTERM);
	        reservation.setReservedFor("FEASIBILITY");
	        reservation.setReservedForType(ReservedForType.CUSTOMER);
	        reservation.setReason("Reserved during feasibility");
	        Collection<ConsumableResource> resources =new ArrayList<ConsumableResource>();
	        resources.add(freePort);
	        long reservationNumber =reservationManager.reserveResource(resources, reservation);
	        System.out.println("Reservation Successful");
	        System.out.println("Reservation Number : " + reservationNumber);
	        System.out.println("Port ID              : "+ freePort.getId());
	        System.out.println("Reservation Type     : "+ reservation.getReservationType());
	        System.out.println("Reserved For         : "+ reservation.getReservedFor());
	        System.out.println("Reserved For Type    : "+ reservation.getReservedForType());
	        System.out.println("Reason               : "+ reservation.getReason());
	        System.out.println("Calling reserveResource...");
	        return reservationNumber;
	    }
}
