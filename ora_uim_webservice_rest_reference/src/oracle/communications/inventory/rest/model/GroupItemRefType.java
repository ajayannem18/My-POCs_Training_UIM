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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import oracle.communications.inventory.rest.model.SpecificationType;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * A  resource to associate with the inventoryGroup.
 */
@ApiModel(description = "A  resource to associate with the inventoryGroup.")
public class GroupItemRefType  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("inventoryGroupItemType")
  private String inventoryGroupItemType = null;

  @JsonProperty("href")
  private String href = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("grpSpecification")
  private SpecificationType grpSpecification = null;

  public GroupItemRefType id(String id) {
    this.id = id;
    return this;
  }

  /**
   * The ID of the groupItem appended with its BusinessIdentifier.
   * @return id
  **/
  @ApiModelProperty(required = true, value = "The ID of the groupItem appended with its BusinessIdentifier.")
      @NotNull

    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public GroupItemRefType inventoryGroupItemType(String inventoryGroupItemType) {
    this.inventoryGroupItemType = inventoryGroupItemType;
    return this;
  }

  /**
   * Get inventoryGroupItemType
   * @return inventoryGroupItemType
  **/
  @ApiModelProperty(readOnly = true, value = "")
  
    public String getInventoryGroupItemType() {
    return inventoryGroupItemType;
  }

  public void setInventoryGroupItemType(String inventoryGroupItemType) {
    this.inventoryGroupItemType = inventoryGroupItemType;
  }

  public GroupItemRefType href(String href) {
    this.href = href;
    return this;
  }

  /**
   * The URI for the groupItem.
   * @return href
  **/
  @ApiModelProperty(readOnly = true, value = "The URI for the groupItem.")
  
    public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public GroupItemRefType name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of the groupItem.
   * @return name
  **/
  @ApiModelProperty(value = "The name of the groupItem.")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public GroupItemRefType grpSpecification(SpecificationType grpSpecification) {
    this.grpSpecification = grpSpecification;
    return this;
  }

  /**
   * Get grpSpecification
   * @return grpSpecification
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public SpecificationType getGrpSpecification() {
    return grpSpecification;
  }

  public void setGrpSpecification(SpecificationType grpSpecification) {
    this.grpSpecification = grpSpecification;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GroupItemRefType groupItemRef = (GroupItemRefType) o;
    return Objects.equals(this.id, groupItemRef.id) &&
        Objects.equals(this.inventoryGroupItemType, groupItemRef.inventoryGroupItemType) &&
        Objects.equals(this.href, groupItemRef.href) &&
        Objects.equals(this.name, groupItemRef.name) &&
        Objects.equals(this.grpSpecification, groupItemRef.grpSpecification);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, inventoryGroupItemType, href, name, grpSpecification);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GroupItemRefType {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    inventoryGroupItemType: ").append(toIndentedString(inventoryGroupItemType)).append("\n");
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    grpSpecification: ").append(toIndentedString(grpSpecification)).append("\n");
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
