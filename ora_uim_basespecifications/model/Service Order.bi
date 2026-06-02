<?xml version="1.0" encoding="UTF-8"?>
<com:modelEntity xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:com="http://www.mslv.com/studio/core/model/common" xmlns:data="http://www.oracle.com/communications/studio/core/model/common/data" xmlns:inv="http://www.mslv.com/studio/inventory/model/specification" xmlns="http://www.mslv.com/studio/inventory/model/specification" xsi:type="inv:BusinessInteractionSpecificationType" name="Service Order">
  <com:saveVersion>2</com:saveVersion>
  <com:baseEntityRef>
    <com:entity>BusinessInteraction</com:entity>
    <com:entityType>inventoryEntity</com:entityType>
    <com:relationship>unknown</com:relationship>
  </com:baseEntityRef>
  <com:id>---EI4Il--+QIHcF+RJWwg</com:id>
  <data:dataElementNode virtual="true">
    <com:id>---EI4Il--+QIHcF+RJWwg</com:id>
    <com:elementType>oracle.communications.studio.model.data.StudioModelDataElement</com:elementType>
    <data:name>Service Order</data:name>
    <data:displayName lang="[default]">Service Order</data:displayName>
    <data:primitiveType>none</data:primitiveType>
  </data:dataElementNode>
  <data:dataElementDetails xsi:type="data:dataElementCommonDetail">
    <com:id>---X---X--+KauZG--+kSQ</com:id>
    <com:elementType>oracle.communications.studio.model.data.StudioModelDataElementCommonDetails</com:elementType>
    <data:dataElementId>---EI4Il--+QIHcF+RJWwg</data:dataElementId>
    <data:defaultValue></data:defaultValue>
    <data:key></data:key>
    <data:deprecated>false</data:deprecated>
    <data:abstract>false</data:abstract>
    <data:internal>false</data:internal>
    <data:sensitive>false</data:sensitive>
    <data:minLength>0</data:minLength>
    <data:maxLength>-1</data:maxLength>
    <data:minMultiplicity>0</data:minMultiplicity>
    <data:maxMultiplicity>-1</data:maxMultiplicity>
  </data:dataElementDetails>
  <inv:rule>
    <com:entity>ValidateXMLInput</com:entity>
    <com:entityType>rst</com:entityType>
    <com:relationship>com.mslv.studio.inventory.specification.REL_RULE_TRIGGER</com:relationship>
  </inv:rule>
  <inv:entityType>BusinessInteraction</inv:entityType>
</com:modelEntity>