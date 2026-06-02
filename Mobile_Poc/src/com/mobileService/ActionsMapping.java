package com.mobileService;

import oracle.communications.inventory.extensibility.extension.util.ExtensionPointRuleContext;

public class ActionsMapping {

	public String mapServiceActions(ExtensionPointRuleContext context) {

	    String action  =context.getArguments()[2].toString();

	    String mappedAction;

	    if (action.equalsIgnoreCase("add") || action.equalsIgnoreCase("create")) {
	        mappedAction = "create";
	    } else if (action.equalsIgnoreCase("update") || action.equalsIgnoreCase("modify") || action.equalsIgnoreCase("change")) {
	        mappedAction = "change";
	    } else if (action.equalsIgnoreCase("delete")) {
	        mappedAction = "disconnect";
	    } else {
	        throw new RuntimeException("Unsupported action: " + action);
	    }

	    System.out.println("FINAL MAPPED ACTION = " + mappedAction);

	    return mappedAction;
	}
		
	
	
}
