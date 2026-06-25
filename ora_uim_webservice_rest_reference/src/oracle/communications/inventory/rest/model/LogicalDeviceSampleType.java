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
*/

package oracle.communications.inventory.rest.model;

import java.util.Objects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import oracle.communications.inventory.rest.model.GroupRefType;
import oracle.communications.inventory.rest.model.LogicalDeviceType;
import oracle.communications.inventory.rest.model.PlaceRefType;
import oracle.communications.inventory.rest.model.RoleType;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
 
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * A Sample of IP.
 */
@ApiModel(description = "A Sample of IP.")
public class LogicalDeviceSampleType  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("logicalDevice")
  private LogicalDeviceType logicalDevice = null;

  @JsonProperty("inventoryGroup")
  @Valid
  private List<GroupRefType> inventoryGroup = null;

  public LogicalDeviceSampleType logicalDevice(LogicalDeviceType logicalDevice) {
    this.logicalDevice = logicalDevice;
    return this;
  }

  /**
   * Get logicalDevice
   * @return logicalDevice
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public LogicalDeviceType getLogicalDevice() {
    return logicalDevice;
  }

  public void setLogicalDevice(LogicalDeviceType logicalDevice) {
    this.logicalDevice = logicalDevice;
  }

  public LogicalDeviceSampleType inventoryGroup(List<GroupRefType> inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
    return this;
  }

  public LogicalDeviceSampleType addInventoryGroupItem(GroupRefType inventoryGroupItem) {
    if (this.inventoryGroup == null) {
      this.inventoryGroup = new ArrayList<GroupRefType>();
    }
    this.inventoryGroup.add(inventoryGroupItem);
    return this;
  }

  /**
   * The list of associated geographic places.
   * @return inventoryGroup
  **/
  @ApiModelProperty(value = "The list of associated geographic places.")
      @Valid
    public List<GroupRefType> getInventoryGroup() {
    return inventoryGroup;
  }

  public void setInventoryGroup(List<GroupRefType> inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LogicalDeviceSampleType logicalDeviceSample = (LogicalDeviceSampleType) o;
    return Objects.equals(this.logicalDevice, logicalDeviceSample.logicalDevice) &&
        Objects.equals(this.inventoryGroup, logicalDeviceSample.inventoryGroup);
  }

  @Override
  public int hashCode() {
    return Objects.hash(logicalDevice, inventoryGroup);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LogicalDeviceSampleType {\n");
    
    sb.append("    logicalDevice: ").append(toIndentedString(logicalDevice)).append("\n");
    sb.append("    inventoryGroup: ").append(toIndentedString(inventoryGroup)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
