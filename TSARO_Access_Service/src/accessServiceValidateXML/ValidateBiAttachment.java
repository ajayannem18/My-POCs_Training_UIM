package accessServiceValidateXML;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.xmlbeans.XmlException;

import oracle.communications.inventory.api.entity.BusinessInteraction;
import oracle.communications.inventory.api.entity.BusinessInteractionAttachment;
import oracle.communications.inventory.api.framework.logging.Log;
import oracle.communications.inventory.extensibility.extension.util.ExtensionPointContext;
import oracle.communications.inventory.xmlbeans.BusinessInteractionItemType;
import oracle.communications.inventory.xmlbeans.InteractionDocument;
import oracle.communications.inventory.xmlbeans.ParameterType;
import oracle.communications.platform.exception.ValidationException;

public class ValidateBiAttachment {

    @SuppressWarnings("unlikely-arg-type")
    public void validateRequest(ExtensionPointContext context, Log log)
            throws ValidationException, XmlException {

        log.info("ValidateAccessCaptureRequest", "validateRequest started");

        BusinessInteraction businessInteraction =
                (BusinessInteraction) context.getArguments()[0];

        BusinessInteractionAttachment biAttachment =
                (BusinessInteractionAttachment) context.getArguments()[1];

        if (businessInteraction == null) {
            throw new ValidationException("businessInteraction is empty");
        }

        if (biAttachment == null) {
            throw new ValidationException("BusinessInteractionAttachment is empty");
        }

        String requestXml = biAttachment.convertContentToString();

        if (requestXml == null || requestXml.trim().isEmpty()) {
            throw new ValidationException("Attachment content is empty");
        }

        InteractionDocument doc = InteractionDocument.Factory.parse(requestXml);

        if (doc.getInteraction() == null
                || doc.getInteraction().getBody() == null) {
            throw new ValidationException("Invalid interaction XML structure");
        }

        List<BusinessInteractionItemType> biItemTypeList =
                doc.getInteraction().getBody().getItemList();

        if (biItemTypeList == null || biItemTypeList.isEmpty()) {
            throw new ValidationException("No items found in interaction body");
        }

        //  Mandatory parameters from your SOAP request
        Set<String> mandatoryParams = new HashSet<>(Arrays.asList(
                "MediaType",
                "PeDeviceName",
                "SwitchHostName",
                "OltDevceName"   // keep exact spelling from XML
        ));

        for (BusinessInteractionItemType biItemType : biItemTypeList) {

            if (biItemType == null || biItemType.getParameterList() == null) {
                throw new ValidationException("Parameter list is missing in item");
            }

            List<ParameterType> paramList = biItemType.getParameterList();

            if (paramList.isEmpty()) {
                throw new ValidationException("Parameter list is empty");
            }

            // Track found parameters
            Set<String> foundParams = new HashSet<>();

            for (ParameterType param : paramList) {
                if (param != null && param.getName() != null) {

                    String paramName = param.getName().trim();
                    foundParams.add(paramName);
                }
            }

            // Check missing parameters
            for (String mandatoryParam : mandatoryParams) {
                if (!foundParams.contains(mandatoryParam)) {
                    throw new ValidationException(
                            mandatoryParam + " parameter is mandatory"
                    );
                }
            }
        }

        log.info("ValidateAccessCaptureRequest", "validateRequest ended");
    }
}