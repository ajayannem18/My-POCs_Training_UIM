package com.mobileService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.xmlbeans.XmlException;

import oracle.communications.inventory.api.entity.BusinessInteraction;
import oracle.communications.inventory.api.entity.BusinessInteractionAttachment;
import oracle.communications.inventory.extensibility.extension.util.ExtensionPointContext;
import oracle.communications.inventory.xmlbeans.BusinessInteractionItemType;
import oracle.communications.inventory.xmlbeans.InteractionDocument;
import oracle.communications.inventory.xmlbeans.ParameterType;
import oracle.communications.platform.exception.ValidationException;

public class ValidateOSMXml {
	public void validateXML(ExtensionPointContext context) throws ValidationException, XmlException {

	    System.out.println("=== validateXML started ===");

	    BusinessInteraction bi = (BusinessInteraction) context.getArguments()[0];
	    BusinessInteractionAttachment biAttach = (BusinessInteractionAttachment) context.getArguments()[1];

	    if (bi == null) {
	        System.out.println("ERROR: BusinessInteraction is null");
	        throw new ValidationException("BusinessInteraction is empty");
	    }

	    if (biAttach == null) {
	        System.out.println("ERROR: Attachment is null");
	        throw new ValidationException("Bi Attachment is empty");
	    }

	    String requestXML = biAttach.convertContentToString();
	    System.out.println("Attachment XML: " + requestXML);

	    if (requestXML == null || requestXML.trim().isEmpty()) {
	        System.out.println("ERROR: XML content is empty");
	        throw new ValidationException("Attachment content is empty");
	    }

	    InteractionDocument doc = InteractionDocument.Factory.parse(requestXML);
	    System.out.println("XML parsed successfully");

	    if (doc.getInteraction() == null || doc.getInteraction().getBody() == null) {
	        System.out.println("ERROR: Invalid XML structure");
	        throw new ValidationException("Invalid interaction XML structure");
	    }

	    List<BusinessInteractionItemType> biItemTypeList =
	            doc.getInteraction().getBody().getItemList();

	    if (biItemTypeList == null || biItemTypeList.isEmpty()) {
	        System.out.println("ERROR: No items found");
	        throw new ValidationException("No items found in interaction body");
	    }

	    Set<String> mandatoryParams = new HashSet<>(Arrays.asList("TelephoneNumber", "SIM"));

	    for (BusinessInteractionItemType biItemType : biItemTypeList) {

	        if (biItemType == null || biItemType.getParameterList() == null) {
	            System.out.println("ERROR: Parameter list missing in item");
	            throw new ValidationException("Parameter list is missing in item");
	        }

	        List<ParameterType> paramList = biItemType.getParameterList();

	        if (paramList.isEmpty()) {
	            System.out.println("ERROR: Parameter list is empty");
	            throw new ValidationException("Parameter list is empty");
	        }

	        Set<String> foundParams = new HashSet<>();

	        for (ParameterType param : paramList) {
	            if (param != null && param.getName() != null) {
	                String paramName = param.getName().trim();
	                System.out.println("Found parameter: " + paramName);
	                foundParams.add(paramName);
	            }
	        }

	        for (String mandatoryParam : mandatoryParams) {
	            if (!foundParams.contains(mandatoryParam)) {
	                System.out.println("ERROR: Missing parameter -> " + mandatoryParam);
	                throw new ValidationException(mandatoryParam + " parameter is mandatory");
	            } else {
	                System.out.println("Verified parameter: " + mandatoryParam);
	            }
	        }
	    }

	    System.out.println("=== validateXML completed successfully ===");
	}

}
