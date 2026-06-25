/*
**
** Copyright (c) 2023, Oracle and/or its affiliates. All rights reserved.
**
** The Universal Permissive License (UPL), Version 1.0
**
** Subject to the condition set forth below, permission is hereby granted to any
** person obtaining a copy of this software, associated documentation and/or data
** (collectively the "Software"), free of charge and under any and all copyright
** rights in the Software, and any and all patent rights owned or freely
** licensable by each licensor hereunder covering either (i) the unmodified
** Software as contributed to or provided by such licensor, or (ii) the Larger
** Works (as defined below), to deal in both
** 
** (a) the Software, and
** (b) any piece of software and/or hardware listed in the lrgrwrks.txt file if
** one is included with the Software (each a "Larger Work" to which the Software
** is contributed by such licensors),
** 
** without restriction, including without limitation the rights to copy, create
** derivative works of, display, perform, and distribute the Software and make,
** use, sell, offer for sale, import, export, have made, and have sold the
** Software and the Larger Work(s), and to sublicense the foregoing rights on
** either these or other terms.
** 
** This license is subject to the following condition:
** The above copyright notice and either this complete permission notice or at
** a minimum a reference to the UPL must be included in all copies or
** substantial portions of the Software.
** 
** THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
** IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
** FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
** AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
** LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
** OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
** SOFTWARE.

** DESCRIPTION
**  Swagger is generating additional attribute in serialization for '@' attribute, including Mixin to Ignore it.

** PRIVATE CLASSES
**  <list of private classes defined - with one-line descriptions>
 */

package oracle.communications.inventory.rest.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import oracle.communications.inventory.rest.model.*;

/**
 * Mixin as the custom resource type is not recognised
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "@type", visible = true )
@JsonSubTypes({
  @JsonSubTypes.Type(value = NetworkAddressDomainType.class, name = "NetworkAddressDomain"),
  @JsonSubTypes.Type(value = IPSubnetType.class, name = "IPSubnet"),
  @JsonSubTypes.Type(value = IPV4AddressType.class, name = "IPV4Address"),
  @JsonSubTypes.Type(value = EquipmentType.class, name = "Equipment"),
  @JsonSubTypes.Type(value = CustomObjectType.class, name = "CustomObject"),
  @JsonSubTypes.Type(value = DeviceInterfaceType.class, name = "DeviceInterface"),
  @JsonSubTypes.Type(value = PhysicalResourceType.class, name = "PhysicalResource"),
  @JsonSubTypes.Type(value = IPAddressType.class, name = "IPAddress"),
  @JsonSubTypes.Type(value = PhysicalPortType.class, name = "PhysicalPort"),
  @JsonSubTypes.Type(value = CustomNetworkAddressType.class, name = "CustomNetworkAddress"),
  @JsonSubTypes.Type(value = IPV6AddressType.class, name = "IPV6Address"),
  @JsonSubTypes.Type(value = FlowIdentifierType.class, name = "FlowIdentifier"),
  @JsonSubTypes.Type(value = PhysicalDeviceType.class, name = "PhysicalDevice"),
  @JsonSubTypes.Type(value = LogicalDeviceAccountType.class, name = "LogicalDeviceAccount"),
  @JsonSubTypes.Type(value = TelephoneNumberType.class, name = "TelephoneNumber"),
  @JsonSubTypes.Type(value = EquipmentHolderType.class, name = "EquipmentHolder"),
  @JsonSubTypes.Type(value = PhysicalConnectorType.class, name = "PhysicalConnector"),
  @JsonSubTypes.Type(value = LogicalDeviceType.class, name = "LogicalDevice"),
  @JsonSubTypes.Type(value = LogicalResourceType.class, name = "LogicalResource"),
  @JsonSubTypes.Type(value = IPNetworkType.class, name = "IPNetwork"),
  @JsonSubTypes.Type(value = ConnectivityType.class, name = "Connectivity"),
  @JsonSubTypes.Type(value = PipeType.class, name = "Pipe"),
  @JsonSubTypes.Type(value = PipeTerminationPointType.class, name = "PipeTerminationPoint"),
  @JsonSubTypes.Type(value = LogicalDeviceSampleType.class, name = "LogicalDeviceSample"),
})
public abstract class ResourceBaseMixin {
}
