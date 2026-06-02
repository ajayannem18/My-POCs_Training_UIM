<?xml version="1.0" encoding="UTF-8"?>
<com:modelEntity xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:com="http://www.mslv.com/studio/core/model/common" xmlns:data="http://www.oracle.com/communications/studio/core/model/common/data" xmlns:inv="http://www.mslv.com/studio/inventory/model/specification" xmlns="http://www.mslv.com/studio/inventory/model/specification" xmlns:layout="http://xmlns.oracle.com/communications/sce/poms/model/layout" xmlns:poms="http://xmlns.oracle.com/communications/sce/poms/model/poms" xsi:type="inv:BusinessInteractionSpecificationType" name="AccessService_BI">
  <com:saveVersion>2</com:saveVersion>
  <com:baseEntityRef>
    <com:entity>BusinessInteraction</com:entity>
    <com:entityType>inventoryEntity</com:entityType>
    <com:relationship>unknown</com:relationship>
  </com:baseEntityRef>
  <com:id>chOuA5tDSPqG8Xo443ZypQ</com:id>
  <data:dataElementNode virtual="true">
    <com:id>chOuA5tDSPqG8Xo443ZypQ</com:id>
    <com:elementType>oracle.communications.studio.model.data.StudioModelDataElement</com:elementType>
    <data:name>AccessService_BI</data:name>
    <data:displayName lang="[default]">AccessService_BI</data:displayName>
    <data:dataElement virtual="false">
      <com:id>Id2-yvmWRwydWqfDIwCehg</com:id>
      <com:elementType>oracle.communications.studio.model.data.StudioModelDataElement</com:elementType>
      <data:name>MediaType</data:name>
      <data:displayName lang="[default]">Media Type</data:displayName>
      <data:displayName lang="en"></data:displayName>
      <data:baseElement>
        <com:entity>TSARO_Access_Service</com:entity>
        <com:entityType>xsd</com:entityType>
        <com:relationship>oracle.communications.studio.model.data.baseType</com:relationship>
        <com:element>MediaType</com:element>
        <com:elementType>com.mslv.studio.core.data.dictionary.node</com:elementType>
        <com:elementName>MediaType</com:elementName>
      </data:baseElement>
      <data:primitiveType>string</data:primitiveType>
    </data:dataElement>
    <data:dataElement virtual="false">
      <com:id>dxq+MdQjQX+h3l2hG0y3+A</com:id>
      <com:elementType>oracle.communications.studio.model.data.StudioModelDataElement</com:elementType>
      <data:name>PeDeviceName</data:name>
      <data:displayName lang="[default]">Pe Device Name</data:displayName>
      <data:displayName lang="en"></data:displayName>
      <data:baseElement>
        <com:entity>TSARO_Access_Service</com:entity>
        <com:entityType>xsd</com:entityType>
        <com:relationship>oracle.communications.studio.model.data.baseType</com:relationship>
        <com:element>PeDeviceName</com:element>
        <com:elementType>com.mslv.studio.core.data.dictionary.node</com:elementType>
        <com:elementName>PeDeviceName</com:elementName>
      </data:baseElement>
      <data:primitiveType>string</data:primitiveType>
    </data:dataElement>
    <data:dataElement virtual="false">
      <com:id>YWh6WG5+RNuhon7HH9Jg+w</com:id>
      <com:elementType>oracle.communications.studio.model.data.StudioModelDataElement</com:elementType>
      <data:name>SwitchHostName</data:name>
      <data:displayName lang="[default]">Switch Host Name</data:displayName>
      <data:displayName lang="en"></data:displayName>
      <data:baseElement>
        <com:entity>TSARO_Access_Service</com:entity>
        <com:entityType>xsd</com:entityType>
        <com:relationship>oracle.communications.studio.model.data.baseType</com:relationship>
        <com:element>SwitchHostName</com:element>
        <com:elementType>com.mslv.studio.core.data.dictionary.node</com:elementType>
        <com:elementName>SwitchHostName</com:elementName>
      </data:baseElement>
      <data:primitiveType>string</data:primitiveType>
    </data:dataElement>
    <data:dataElement virtual="false">
      <com:id>Xzzsa6shQ7OznlvA8iWskw</com:id>
      <com:elementType>oracle.communications.studio.model.data.StudioModelDataElement</com:elementType>
      <data:name>OltDevceName</data:name>
      <data:displayName lang="[default]">Olt Devce Name</data:displayName>
      <data:displayName lang="en"></data:displayName>
      <data:baseElement>
        <com:entity>TSARO_Access_Service</com:entity>
        <com:entityType>xsd</com:entityType>
        <com:relationship>oracle.communications.studio.model.data.baseType</com:relationship>
        <com:element>OltDevceName</com:element>
        <com:elementType>com.mslv.studio.core.data.dictionary.node</com:elementType>
        <com:elementName>OltDevceName</com:elementName>
      </data:baseElement>
      <data:primitiveType>string</data:primitiveType>
    </data:dataElement>
    <data:primitiveType>none</data:primitiveType>
  </data:dataElementNode>
  <data:dataElementDetails xsi:type="data:dataElementCommonDetail">
    <com:id>cd3bZzlRQU+3mq7aPPZqUQ</com:id>
    <com:elementType>oracle.communications.studio.model.data.StudioModelDataElementCommonDetails</com:elementType>
    <data:dataElementId>chOuA5tDSPqG8Xo443ZypQ</data:dataElementId>
    <data:defaultValue></data:defaultValue>
    <data:key></data:key>
    <data:deprecated>false</data:deprecated>
    <data:sensitive>false</data:sensitive>
    <data:minLength>0</data:minLength>
    <data:maxLength>40</data:maxLength>
    <data:minMultiplicity>0</data:minMultiplicity>
    <data:maxMultiplicity>-1</data:maxMultiplicity>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="layout:uiConfigurationType">
    <com:id>crvIe4WyS1Cnz0HH0mvUYw</com:id>
    <com:elementType>oracle.communications.sce.poms.model.data.LayoutDetailType</com:elementType>
    <data:dataElementId>chOuA5tDSPqG8Xo443ZypQ</data:dataElementId>
    <layout:layout>
      <layout:page>Business Interaction Editor</layout:page>
      <layout:panel>Business Interaction Edit Panel</layout:panel>
    </layout:layout>
    <layout:layout>
      <layout:page>Business Interaction Summary</layout:page>
      <layout:panel>Business Interaction Summary Panel</layout:panel>
    </layout:layout>
    <layout:layout>
      <layout:page>Orchestration Request Editor</layout:page>
      <layout:panel>Orchestration Request Edit Panel</layout:panel>
    </layout:layout>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="data:dataElementCommonDetail">
    <com:id>NquIcemGRrqeeHPYlre0bA</com:id>
    <com:elementType>oracle.communications.studio.model.data.StudioModelDataElementCommonDetails</com:elementType>
    <data:dataElementId>Id2-yvmWRwydWqfDIwCehg</data:dataElementId>
    <data:key></data:key>
    <data:deprecated>false</data:deprecated>
    <data:internal>false</data:internal>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="data:dataElementEnumerationDetail">
    <com:id>gkowrnSuSjWAxFqPbMspEA</com:id>
    <com:elementType>oracle.communications.studio.model.data.StudioModelDataElementEnumeration</com:elementType>
    <data:dataElementId>Id2-yvmWRwydWqfDIwCehg</data:dataElementId>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="data:dataElementInformationDetails">
    <com:id>TqotLDTjQUKDP5OVKeWnMQ</com:id>
    <com:elementType>oracle.communications.studio.model.data.StudioModelDataElementInformation</com:elementType>
    <data:dataElementId>Id2-yvmWRwydWqfDIwCehg</data:dataElementId>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="layout:hintDetail">
    <com:id>aAAigIlyT6+2-cyi1T23zw</com:id>
    <com:elementType>oracle.communications.sce.poms.model.data.UIHintsDetail</com:elementType>
    <data:dataElementId>Id2-yvmWRwydWqfDIwCehg</data:dataElementId>
    <layout:hints/>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="poms:valueComplexType" linkEnabled="false" overrideDictionaryValues="false" queryEnabled="false" valueEnabled="false">
    <com:id>pc4T6YPtSr6tE6TcjvQBMw</com:id>
    <com:elementType>oracle.communications.sce.poms.model.data.ValueElementDetail</com:elementType>
    <data:dataElementId>Id2-yvmWRwydWqfDIwCehg</data:dataElementId>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="inv:CharElementDetailType">
    <com:id>Mzr1vANIRc2vrZYYaj1OXg</com:id>
    <com:elementType>oracle.communications.studio.inventory.model.data.StudioModelDataElementCharDetailType</com:elementType>
    <data:dataElementId>Id2-yvmWRwydWqfDIwCehg</data:dataElementId>
    <inv:startTimestamp>0</inv:startTimestamp>
    <inv:endTimestamp>0</inv:endTimestamp>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="data:dataElementCommonDetail">
    <com:id>Tz4kwEVpTca1rO+n1AGgVQ</com:id>
    <com:elementType>oracle.communications.studio.model.data.StudioModelDataElementCommonDetails</com:elementType>
    <data:dataElementId>dxq+MdQjQX+h3l2hG0y3+A</data:dataElementId>
    <data:key></data:key>
    <data:deprecated>false</data:deprecated>
    <data:internal>false</data:internal>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="data:dataElementEnumerationDetail">
    <com:id>Ro8fr4BjRZGqSI1pQfTEJQ</com:id>
    <com:elementType>oracle.communications.studio.model.data.StudioModelDataElementEnumeration</com:elementType>
    <data:dataElementId>dxq+MdQjQX+h3l2hG0y3+A</data:dataElementId>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="data:dataElementInformationDetails">
    <com:id>8LS8TjAASCChogxT4Hv6Ww</com:id>
    <com:elementType>oracle.communications.studio.model.data.StudioModelDataElementInformation</com:elementType>
    <data:dataElementId>dxq+MdQjQX+h3l2hG0y3+A</data:dataElementId>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="layout:hintDetail">
    <com:id>o7EbfCKVSVGzzFN1rM8VEQ</com:id>
    <com:elementType>oracle.communications.sce.poms.model.data.UIHintsDetail</com:elementType>
    <data:dataElementId>dxq+MdQjQX+h3l2hG0y3+A</data:dataElementId>
    <layout:hints/>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="poms:valueComplexType" linkEnabled="false" overrideDictionaryValues="false" queryEnabled="false" valueEnabled="false">
    <com:id>TsbEob58SMOGxywiTSeOPQ</com:id>
    <com:elementType>oracle.communications.sce.poms.model.data.ValueElementDetail</com:elementType>
    <data:dataElementId>dxq+MdQjQX+h3l2hG0y3+A</data:dataElementId>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="inv:CharElementDetailType">
    <com:id>yXeB58XDSS6OAfGxbC3lOQ</com:id>
    <com:elementType>oracle.communications.studio.inventory.model.data.StudioModelDataElementCharDetailType</com:elementType>
    <data:dataElementId>dxq+MdQjQX+h3l2hG0y3+A</data:dataElementId>
    <inv:startTimestamp>0</inv:startTimestamp>
    <inv:endTimestamp>0</inv:endTimestamp>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="data:dataElementCommonDetail">
    <com:id>VpjKGLFjR-mkVlBIKEY0ZQ</com:id>
    <com:elementType>oracle.communications.studio.model.data.StudioModelDataElementCommonDetails</com:elementType>
    <data:dataElementId>YWh6WG5+RNuhon7HH9Jg+w</data:dataElementId>
    <data:key></data:key>
    <data:deprecated>false</data:deprecated>
    <data:internal>false</data:internal>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="data:dataElementEnumerationDetail">
    <com:id>Bd2wmMNORIKdAESO6F2IFA</com:id>
    <com:elementType>oracle.communications.studio.model.data.StudioModelDataElementEnumeration</com:elementType>
    <data:dataElementId>YWh6WG5+RNuhon7HH9Jg+w</data:dataElementId>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="data:dataElementInformationDetails">
    <com:id>Xqlb2a02Q6WLFlLgPahY7w</com:id>
    <com:elementType>oracle.communications.studio.model.data.StudioModelDataElementInformation</com:elementType>
    <data:dataElementId>YWh6WG5+RNuhon7HH9Jg+w</data:dataElementId>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="layout:hintDetail">
    <com:id>OpDXwFvKSdujJq5kJrRV0g</com:id>
    <com:elementType>oracle.communications.sce.poms.model.data.UIHintsDetail</com:elementType>
    <data:dataElementId>YWh6WG5+RNuhon7HH9Jg+w</data:dataElementId>
    <layout:hints/>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="poms:valueComplexType" linkEnabled="false" overrideDictionaryValues="false" queryEnabled="false" valueEnabled="false">
    <com:id>5m167t6YTuGye+m2mik-2w</com:id>
    <com:elementType>oracle.communications.sce.poms.model.data.ValueElementDetail</com:elementType>
    <data:dataElementId>YWh6WG5+RNuhon7HH9Jg+w</data:dataElementId>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="inv:CharElementDetailType">
    <com:id>6KfpG4WpSSWP5l91SIdZ5w</com:id>
    <com:elementType>oracle.communications.studio.inventory.model.data.StudioModelDataElementCharDetailType</com:elementType>
    <data:dataElementId>YWh6WG5+RNuhon7HH9Jg+w</data:dataElementId>
    <inv:startTimestamp>0</inv:startTimestamp>
    <inv:endTimestamp>0</inv:endTimestamp>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="data:dataElementCommonDetail">
    <com:id>AxCRo0eYROuCkaUxnKgapg</com:id>
    <com:elementType>oracle.communications.studio.model.data.StudioModelDataElementCommonDetails</com:elementType>
    <data:dataElementId>Xzzsa6shQ7OznlvA8iWskw</data:dataElementId>
    <data:key></data:key>
    <data:deprecated>false</data:deprecated>
    <data:internal>false</data:internal>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="data:dataElementEnumerationDetail">
    <com:id>jov5SvE4QRaiiwu21+P0ig</com:id>
    <com:elementType>oracle.communications.studio.model.data.StudioModelDataElementEnumeration</com:elementType>
    <data:dataElementId>Xzzsa6shQ7OznlvA8iWskw</data:dataElementId>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="data:dataElementInformationDetails">
    <com:id>VaV+GBYFQPuoBrsBmoSmNw</com:id>
    <com:elementType>oracle.communications.studio.model.data.StudioModelDataElementInformation</com:elementType>
    <data:dataElementId>Xzzsa6shQ7OznlvA8iWskw</data:dataElementId>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="layout:hintDetail">
    <com:id>2XHNsXhZSPWytTFF5uJw1w</com:id>
    <com:elementType>oracle.communications.sce.poms.model.data.UIHintsDetail</com:elementType>
    <data:dataElementId>Xzzsa6shQ7OznlvA8iWskw</data:dataElementId>
    <layout:hints/>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="poms:valueComplexType" linkEnabled="false" overrideDictionaryValues="false" queryEnabled="false" valueEnabled="false">
    <com:id>WVk4HHi9T-2OYUp4ICVAVA</com:id>
    <com:elementType>oracle.communications.sce.poms.model.data.ValueElementDetail</com:elementType>
    <data:dataElementId>Xzzsa6shQ7OznlvA8iWskw</data:dataElementId>
  </data:dataElementDetails>
  <data:dataElementDetails xsi:type="inv:CharElementDetailType">
    <com:id>t5nqJ2J8Q8K+TjfwE2WUeQ</com:id>
    <com:elementType>oracle.communications.studio.inventory.model.data.StudioModelDataElementCharDetailType</com:elementType>
    <data:dataElementId>Xzzsa6shQ7OznlvA8iWskw</data:dataElementId>
    <inv:startTimestamp>0</inv:startTimestamp>
    <inv:endTimestamp>0</inv:endTimestamp>
  </data:dataElementDetails>
  <inv:rule>
    <com:entity>ValidateXML</com:entity>
    <com:entityType>rst</com:entityType>
    <com:relationship>com.mslv.studio.inventory.specification.REL_RULE_TRIGGER</com:relationship>
  </inv:rule>
  <inv:entityType>BusinessInteraction</inv:entityType>
</com:modelEntity>