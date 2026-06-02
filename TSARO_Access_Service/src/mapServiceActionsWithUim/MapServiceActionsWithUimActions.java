package mapServiceActionsWithUim;

import oracle.communications.inventory.extensibility.extension.util.ExtensionPointRuleContext;


public class MapServiceActionsWithUimActions {

	public String mapServiceActions(ExtensionPointRuleContext context) {
		
		String action= (String) context.getArguments()[2];
		System.out.println("input action is : "+action);
		String convertedAction=null;
		if(action != null)
		{
			if(action.equalsIgnoreCase("add")||action.equalsIgnoreCase("create"))
			{
				System.out.println("Converting action from "+action+" to create");
				return "create";
			}
			
			else if(action.equalsIgnoreCase("update")||action.equalsIgnoreCase("modify")||action.equalsIgnoreCase("change"))
			{
				System.out.println("Converting action from "+action+" to change");

				return "change";
			}
			
			else if(action.equalsIgnoreCase("suspend"))
			{
				System.out.println("Converting action from "+action+" to suspendWithConfiguration");

				return "suspendWithConfiguration";
			}
			
			else if(action.equalsIgnoreCase("resume"))
			{
				System.out.println("Converting action from "+action+" to resumeWithConfiguration");

				return "resumeWithConfiguration";
			}
			
			else if(action.equalsIgnoreCase("delete"))
			{
				System.out.println("Converting action from "+action+" to disconnect");

				return "disconnect";
			}
			
		}
		return convertedAction;
		
	}
	
}