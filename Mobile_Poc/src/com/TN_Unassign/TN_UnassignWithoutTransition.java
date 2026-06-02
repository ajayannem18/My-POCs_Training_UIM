package com.TN_Unassign;

import oracle.communications.inventory.api.entity.Specification;
import oracle.communications.inventory.api.entity.TelephoneNumber;

public class TN_UnassignWithoutTransition {
public static String tnUnassign( 
	 TelephoneNumber telephoneNumber,String currentState,String businessAction )
     {
	System.out.println("Executing TN Unassign method");
     // Get the specification of this TN
     Specification spec = telephoneNumber.getSpecification();
     System.out.println("Telephone number spec is : "+spec);
     // Only skip TRANSITIONAL for "usTelephoneNumber" spec
     if (spec != null && 
         "IND_TelephoneNumber".equals(spec.getName())) {
         
         // If current state is TRANSITIONAL,
         // skip it and go directly to target state
         if ("TRANSITIONAL".equals(currentState)) {
             
             if ("ASSIGN".equals(businessAction)) {
                 return "ASSIGNED"; // Skip TRANSITIONAL → go to ASSIGNED
             }
             if ("UNASSIGN".equals(businessAction)) {
                 return "UNASSIGNED"; // Skip TRANSITIONAL → go to UNASSIGNED
             }
         }
     }
     
     // For all other specs, use default behavior
     return null; // null means use default transition
}
}

