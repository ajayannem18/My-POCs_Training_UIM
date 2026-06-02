package com.findNetwork;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.transaction.UserTransaction;

import oracle.communications.inventory.api.entity.LogicalDevice;
import oracle.communications.inventory.api.entity.LogicalDeviceAccount;
import oracle.communications.inventory.api.entity.NetNodeLogicalDeviceRel;
import oracle.communications.inventory.api.entity.NetNodeNetworkRel;
import oracle.communications.inventory.api.entity.Network;
import oracle.communications.inventory.api.entity.NetworkNode;
import oracle.communications.inventory.api.entity.TelephoneNumber;
import oracle.communications.inventory.api.logicaldevice.LogicalDeviceManager;
import oracle.communications.inventory.api.logicaldevice.account.LogicalDeviceAccountManager;
import oracle.communications.inventory.api.network.NetworkManager;
import oracle.communications.inventory.api.network.NetworkSearchCriteria;
import oracle.communications.platform.persistence.CriteriaItem;
import oracle.communications.platform.persistence.CriteriaOperator;
import oracle.communications.platform.persistence.Finder;
import oracle.communications.platform.persistence.PersistenceHelper;


public class FindNetwork {

    public static void findNetworkAndNodes() {

        System.out.println(
                "Executing find network and nodes method");

        UserTransaction ut = null;
        Finder f = null;

        try {

            // Initialize transaction
            ut = PersistenceHelper
                    .makePersistenceManager()
                    .getTransaction();

            f = PersistenceHelper.makeFinder();

            ut.begin();

            // Create Network Manager
            NetworkManager nMgr =
                    PersistenceHelper.makeNetworkManager();

            String networkName = "Ring_Network";

            // Build search criteria
            NetworkSearchCriteria networkSearchCriteria =
                    nMgr.makeNetworkSearchCriteria();

            CriteriaItem cItem =
                    networkSearchCriteria.makeCriteriaItem();

            cItem.setValue(networkName);
            cItem.setOperator(CriteriaOperator.EQUALS);

            networkSearchCriteria.setName(cItem);

            System.out.println(
                    "Search criteria set successfully");

            // Find networks
            Collection<Network> networks =
                    nMgr.findNetwork(networkSearchCriteria);

            // Validate network result
            if (networks == null || networks.isEmpty()) {

                System.out.println(
                        "No network found with name : "
                                + networkName);

                ut.commit();
                return;
            }

            // Get first network
            Network network =
                    networks.iterator().next();

            if (network == null) {

                System.out.println(
                        "Network object is null");

                ut.commit();
                return;
            }

            System.out.println(
                    "Found network with name : "
                            + network.getName());

            // Fetch Network Node Relationships
            Set<NetNodeNetworkRel> nodeRels =
                    network.getNetworkNetNodes();

            // Validate node relationships
            if (nodeRels == null || nodeRels.isEmpty()) {

                System.out.println(
                        "No Network Nodes associated with network : "
                                + network.getName());

                ut.commit();
                return;
            }

            System.out.println(
                    "Found Network Node Relationships count : "
                            + nodeRels.size());

            // Loop through node relationships
            for (NetNodeNetworkRel nodeRel : nodeRels) {

                if (nodeRel == null) {

                    System.out.println(
                            "Node Relationship is null");

                    continue;
                }

                System.out.println(
                        "Processing Network Node Relationship");

                // Fetch Network Node
                NetworkNode networkNode =
                        nodeRel.getNetworkNode();

                if (networkNode == null) {

                    System.out.println(
                            "Network Node is null");

                    continue;
                }

                System.out.println(
                        "Found Network Node : "
                                + networkNode.getName());

                // Fetch Logical Device Relationships
                List<NetNodeLogicalDeviceRel> ldNodeRels =
                        networkNode.getLogicalDevice();

                // Validate logical device relationships
                if (ldNodeRels == null
                        || ldNodeRels.isEmpty()) {

                    System.out.println(
                            "No Logical Devices associated with Network Node : "
                                    + networkNode.getName());

                    continue;
                }

                System.out.println(
                        "Found Logical Device Relationships count : "
                                + ldNodeRels.size());

                // Loop through logical device relationships
                for (NetNodeLogicalDeviceRel ldRel : ldNodeRels) {

                    if (ldRel == null) {

                        System.out.println(
                                "Logical Device Relationship is null");

                        continue;
                    }

                    // Fetch Logical Device
                    LogicalDevice ld =
                            ldRel.getLogicalDevice();

                    if (ld == null) {

                        System.out.println(
                                "Logical Device is null");

                        continue;
                    }

                    System.out.println(
                            "Found Logical Device with name : "
                                    + ld.getName());
                    
                    
                    
                   
                    
                    
                }
            }

            
            
          
            
            
            
            
            
            
            
            
            ut.commit();

            System.out.println(
                    "Transaction committed successfully");

        } catch (Exception e) {

            System.out.println(
                    "Exception occurred while processing");

            e.printStackTrace();

            try {

                if (ut != null) {

                    ut.rollback();

                    System.out.println(
                            "Transaction rolled back successfully");
                }

            } catch (Exception rollbackEx) {

                System.out.println(
                        "Rollback failed");

                rollbackEx.printStackTrace();
            }

        } finally {

            try {

                if (f != null) {
                    f.close();
                }

            } catch (Exception closeEx) {

                System.out.println(
                        "Failed to close Finder");

                closeEx.printStackTrace();
            }

            System.out.println(
                    "Find network and nodes method executed successfully");
        }
    }
}