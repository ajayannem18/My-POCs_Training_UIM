package com.uim.practice;

import oracle.communications.inventory.api.entity.TelephoneNumberSpecification;
import oracle.communications.platform.persistence.Finder;
import oracle.communications.platform.persistence.PersistenceHelper;

public class TN_FindOrCreation {

	public static void findOrCreateTn() {
		Finder f=PersistenceHelper.makeFinder();
		String spec="sampleTN";
		TelephoneNumberSpecification tnSpec=f.findByName(TelephoneNumberSpecification.class,spec ).iterator().next();
		System.out.println("Found telephone number specification with name : "+tnSpec.toString());
		
		
	}
	
}
