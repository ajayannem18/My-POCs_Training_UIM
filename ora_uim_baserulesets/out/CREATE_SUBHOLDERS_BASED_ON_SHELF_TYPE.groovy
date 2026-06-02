//System.out.println("entered testRule");

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import oracle.communications.inventory.api.Activity;
import oracle.communications.inventory.api.LifeCycleManaged;
import oracle.communications.inventory.api.Versioned;
import oracle.communications.inventory.api.common.BaseInvManager;
import oracle.communications.inventory.api.common.EntityUtils;
import oracle.communications.inventory.api.consumer.AssignmentManager;
import oracle.communications.inventory.api.consumer.AssignmentSearchCrteria;
import oracle.communications.inventory.api.entity.BusinessInteraction;
import oracle.communications.inventory.api.entity.CharacteristicSpecification;
import oracle.communications.inventory.api.entity.CustomObject;
import oracle.communications.inventory.api.entity.Equipment;
import oracle.communications.inventory.api.entity.EquipmentAssignmentToPipeTerminationPoint;
import oracle.communications.inventory.api.entity.EquipmentCategory;
import oracle.communications.inventory.api.entity.EquipmentCharacteristic;
import oracle.communications.inventory.api.entity.EquipmentEquipmentRel;
import oracle.communications.inventory.api.entity.EquipmentHolder;
import oracle.communications.inventory.api.entity.EquipmentHolderCharacteristic;
import oracle.communications.inventory.api.entity.EquipmentHolderEquipmentRel;
import oracle.communications.inventory.api.entity.EquipmentHolderSpecification;
import oracle.communications.inventory.api.entity.EquipmentSpecification;
import oracle.communications.inventory.api.entity.GeographicPlace;
import oracle.communications.inventory.api.entity.PhysicalConnector;
import oracle.communications.inventory.api.entity.PhysicalConnectorAssignmentToPipeTerminationPoint;
import oracle.communications.inventory.api.entity.PhysicalConnectorCharacteristic;
import oracle.communications.inventory.api.entity.PhysicalDevice;
import oracle.communications.inventory.api.entity.PhysicalDeviceCharacteristic;
import oracle.communications.inventory.api.entity.PhysicalDeviceEquipmentRel;
import oracle.communications.inventory.api.entity.PhysicalDevicePhysicalDeviceRel;
import oracle.communications.inventory.api.entity.PhysicalDeviceSpecification;
import oracle.communications.inventory.api.entity.PhysicalPort;
import oracle.communications.inventory.api.entity.PhysicalPortAssignmentToPipeTerminationPoint;
import oracle.communications.inventory.api.entity.PhysicalPortCharacteristic;
import oracle.communications.inventory.api.entity.PhysicalPortSpecification;
import oracle.communications.inventory.api.entity.PipePipeTPRel;
import oracle.communications.inventory.api.entity.PipeTerminationPoint;
import oracle.communications.inventory.api.entity.ResourceAction;
import oracle.communications.inventory.api.entity.Specification;
import oracle.communications.inventory.api.entity.SpecificationRel;
import oracle.communications.inventory.api.entity.common.Assignment;
import oracle.communications.inventory.api.entity.common.CharValue;
import oracle.communications.inventory.api.entity.common.PhysicalResource;
import oracle.communications.inventory.api.entity.common.Resource;
import oracle.communications.inventory.api.entity.common.TopologyObject;
import oracle.communications.inventory.api.entity.utils.BusinessInteractionUtils;
import oracle.communications.inventory.api.equipment.EquipmentHolderSearchCriteria;
import oracle.communications.inventory.api.equipment.EquipmentManager;
import oracle.communications.inventory.api.equipment.EquipmentSearchCriteria;
import oracle.communications.inventory.api.equipment.ExtendedEquipmentSearchCriteria;
import oracle.communications.inventory.api.equipment.ExtendedPhysicalDeviceSearchCriteria;
import oracle.communications.inventory.api.equipment.ExtendedPhysicalPortSearchCriteria;
import oracle.communications.inventory.api.equipment.PhysicalConnectorSearchCriteria;
import oracle.communications.inventory.api.equipment.PhysicalDeviceSearchCriteria;
import oracle.communications.inventory.api.equipment.PhysicalPortSearchCriteria;
import oracle.communications.inventory.api.exception.BusinessInteractionCompleteException;
import oracle.communications.inventory.api.exception.LifeCycleConstraintViolationException;
import oracle.communications.inventory.api.exception.ValidationException;
import oracle.communications.inventory.api.framework.config.SystemConfig;
import oracle.communications.inventory.api.framework.logging.Log;
import oracle.communications.inventory.api.framework.logging.LogFactory;
import oracle.communications.inventory.api.framework.logging.impl.FeedbackProviderImpl;
import oracle.communications.inventory.api.framework.policy.RequestPolicyHelper;
import oracle.communications.inventory.api.framework.resource.MessageResource;
import oracle.communications.inventory.api.framework.security.UserEnvironmentFactory;
import oracle.communications.inventory.api.specification.SpecManager;
import oracle.communications.inventory.api.topology.TopologyMsgHandler;
import oracle.communications.inventory.api.util.Utils;
import oracle.communications.platform.persistence.Finder;
import oracle.communications.platform.persistence.PersistenceHelper;
import oracle.communications.platform.persistence.Persistent;
import oracle.communications.inventory.api.equipment.impl.EquipmentMover;
import oracle.communications.inventory.extensibility.extension.util.ExtensionPointRuleContext;


//--------------------------------------------------------------------
// RULE CODE
//--------------------------------------------------------------------

EquipmentHolder equipmentHolder = (EquipmentHolder)ruleParameters[0];
Equipment card = (Equipment)ruleParameters[1];
boolean validateRelationship = (ruleParameters.size()<2 || ruleParameters[2]==null )? false : (boolean)ruleParameters[2];
return extendAddEquipmentToEquipmentHolders(equipmentHolder, card, validateRelationship);
//--------------------------------------------------------------------
// FUNCTION
//--------------------------------------------------------------------
def Equipment extendAddEquipmentToEquipmentHolders(EquipmentHolder parentHolder, Equipment childEquipment, boolean validateRel) throws ValidationException
{
	//Initializing control variables
	String commonCardName="commonCard";
	HashMap<String, HashSet<String>> validSubholdersForShelfMap = new HashMap<String, HashSet<String>>();
	validSubholdersForShelfMap.put("shelf_type1", new HashSet<String>(Arrays.asList("holder_type1 -a", "holder_type1-b")));
	validSubholdersForShelfMap.put("shelf_type2", new HashSet<String>(Arrays.asList("holder_type2 - a", "holder_type2 - b")));
	//Number of new subHolders to create for each valid relationship
	int noOfHoldersToCreate = 1;

	//Check whether we are in correct card and shelf spec - extra care required to avoid a non ending recursion loop
	if(parentHolder.getEquipment().getSpecification().getEquipmentCategory()== EquipmentCategory.SHELF
	&& childEquipment.getSpecification().getEquipmentCategory() == EquipmentCategory.CARD
	&& childEquipment.getSpecification().getName().equals(commonCardName)
	&& validSubholdersForShelfMap.containsKey(parentHolder.getEquipment().getSpecification().getName()))
	{

		try{
			//Sync the objects with DB
			if (childEquipment != null)
				childEquipment = childEquipment.refresh();

			if (parentHolder != null)
				parentHolder = parentHolder.refresh();

			//Obtain all sub holder spec related to the common card equipment
			EquipmentManager eqMgr = PersistenceHelper.makeEquipmentManager();
			childEquipment.setSpecification(eqMgr.refresh(childEquipment.getSpecification()));
			Collection<SpecificationRel> specRels = childEquipment.getSpecification().getRelatedSpecs();
			Collection<SpecificationRel> eqhSpecRels = new ArrayList<SpecificationRel> ();
			if(specRels!=null) {
				for (SpecificationRel specRel : specRels)
				{
					if (specRel.getChild() instanceof EquipmentHolderSpecification)
					{
						SpecManager sm = PersistenceHelper.makeSpecManager();
						if (sm.isSpecificationActive(specRel.getChild()))
						{
							eqhSpecRels.add(specRel);
						}
					}
				}
			}

			if(!Utils.isEmpty(eqhSpecRels)){

				//to store all EQHolders to be created
				ArrayList<EquipmentHolder> eqhCreationList = new ArrayList<EquipmentHolder> ();

				for (SpecificationRel eqhSpecRel : eqhSpecRels)
				{

					EquipmentHolderSpecification eqhSpec = (EquipmentHolderSpecification) eqhSpecRel.getChild();
					if(validSubholdersForShelfMap.get(parentHolder.getEquipment().getSpecification().getName())!=null
					&& validSubholdersForShelfMap.get(parentHolder.getEquipment().getSpecification().getName()).contains(eqhSpec.getName())) {
						//create required number of subHolders for a given specification (we may add maximum allowed validation as well)
						for (int i = 0; i < noOfHoldersToCreate; i++)
						{
							EquipmentHolder eqh = eqMgr.makeEquipmentHolder();
							eqhSpec = eqhSpec.refresh();
							eqh.setSpecification(eqhSpec);
							eqhCreationList.add(eqh);
						}
					}
				}
				//API call to create subholder for the card
				List<EquipmentHolder> createdEqHolders = eqMgr.createEquipmentHolders(childEquipment, eqhCreationList);

				//SAMPLE CODE TO Create sub cards and attach to the subholder
				/**if(createdEqHolders!=null) {
					for(EquipmentHolder eh : createdEqHolders) {
						Collection<SpecificationRel> specificationRels = eh.getSpecification().getRelatedSpecs();

						if(specificationRels!=null) {
							for(SpecificationRel specificationRel : specificationRels) {

								if(specificationRel.getChild() instanceof EquipmentSpecification) {
									EquipmentSpecification subCardSpec = (EquipmentSpecification)specificationRel.getChild();
									Equipment subCard = eqMgr.makeEquipment();
									subCardSpec.refresh();
									subCard.setSpecification(subCardSpec);
									Equipment createdSubCard = eqMgr.createEquipment(Collections.singletonList(subCard)).get(0);
									eqMgr.addEquipmentToEquipmentHolders(eh, createdSubCard, false);
								}
							}
						}
					}
				}
				**/

			}

		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	//System.out.println("exiting testRule");
	return childEquipment;
}