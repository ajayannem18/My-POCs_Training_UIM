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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import oracle.communications.inventory.rest.model.GroupItemRefType;
import oracle.communications.inventory.rest.model.GroupRefType;
import oracle.communications.inventory.rest.model.PlaceRefType;
import oracle.communications.inventory.rest.model.SpecificationType;
import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * A inventoryGroup to associate with the resource.
 */
@ApiModel(description = "A inventoryGroup to associate with the resource.")

public class InventoryGroupType  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("href")
  private String href = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("igSpecification")
  private SpecificationType igSpecification = null;

  @JsonProperty("startDate")
  private Date startDate = null;

  @JsonProperty("endDate")
  private Date endDate = null;

  @JsonProperty("place")
  @Valid
  private List<PlaceRefType> place = null;

  @JsonProperty("parentGroupRef")
  @Valid
  private List<GroupRefType> parentGroupRef = null;

  @JsonProperty("inventoryGroupItems")
  @Valid
  private List<GroupItemRefType> inventoryGroupItems = null;

  public InventoryGroupType id(String id) {
    this.id = id;
    return this;
  }

  /**
   * The ID of the ig.
   * @return id
  **/
  @ApiModelProperty(readOnly = true, value = "The ID of the ig.")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public InventoryGroupType href(String href) {
    this.href = href;
    return this;
  }

  /**
   * The URI for the ig.
   * @return href
  **/
  @ApiModelProperty(value = "The URI for the ig.")
  
    public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public InventoryGroupType name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of the resource.
   * @return name
  **/
  @ApiModelProperty(value = "The name of the resource.")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public InventoryGroupType description(String description) {
    this.description = description;
    return this;
  }

  /**
   * A free-text description for the resource.
   * @return description
  **/
  @ApiModelProperty(value = "A free-text description for the resource.")
  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public InventoryGroupType igSpecification(SpecificationType igSpecification) {
    this.igSpecification = igSpecification;
    return this;
  }

  /**
   * Get igSpecification
   * @return igSpecification
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public SpecificationType getIgSpecification() {
    return igSpecification;
  }

  public void setIgSpecification(SpecificationType igSpecification) {
    this.igSpecification = igSpecification;
  }

  public InventoryGroupType startDate(Date startDate) {
    this.startDate = startDate;
    return this;
  }

  /**
   * The date and time when the time period starts.
   * @return startDate
  **/
  @ApiModelProperty(value = "The date and time when the time period starts.")
  
    @Valid
    public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public InventoryGroupType endDate(Date endDate) {
    this.endDate = endDate;
    return this;
  }

  /**
   * The date and time when the time period ends.
   * @return endDate
  **/
  @ApiModelProperty(value = "The date and time when the time period ends.")
  
    @Valid
    public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public InventoryGroupType place(List<PlaceRefType> place) {
    this.place = place;
    return this;
  }

  public InventoryGroupType addPlaceItem(PlaceRefType placeItem) {
    if (this.place == null) {
      this.place = new ArrayList<PlaceRefType>();
    }
    this.place.add(placeItem);
    return this;
  }

  /**
   * The list of associated geographic places.
   * @return place
  **/
  @ApiModelProperty(value = "The list of associated geographic places.")
      @Valid
    public List<PlaceRefType> getPlace() {
    return place;
  }

  public void setPlace(List<PlaceRefType> place) {
    this.place = place;
  }

  public InventoryGroupType parentGroupRef(List<GroupRefType> parentGroupRef) {
    this.parentGroupRef = parentGroupRef;
    return this;
  }

  public InventoryGroupType addParentGroupRefItem(GroupRefType parentGroupRefItem) {
    if (this.parentGroupRef == null) {
      this.parentGroupRef = new ArrayList<GroupRefType>();
    }
    this.parentGroupRef.add(parentGroupRefItem);
    return this;
  }

  /**
   * Get parentGroupRef
   * @return parentGroupRef
  **/
  @ApiModelProperty(value = "")
      @Valid
    public List<GroupRefType> getParentGroupRef() {
    return parentGroupRef;
  }

  public void setParentGroupRef(List<GroupRefType> parentGroupRef) {
    this.parentGroupRef = parentGroupRef;
  }

  public InventoryGroupType inventoryGroupItems(List<GroupItemRefType> inventoryGroupItems) {
    this.inventoryGroupItems = inventoryGroupItems;
    return this;
  }

  public InventoryGroupType addInventoryGroupItemsItem(GroupItemRefType inventoryGroupItemsItem) {
    if (this.inventoryGroupItems == null) {
      this.inventoryGroupItems = new ArrayList<GroupItemRefType>();
    }
    this.inventoryGroupItems.add(inventoryGroupItemsItem);
    return this;
  }

  /**
   * The list of associated inventory group items.
   * @return inventoryGroupItems
  **/
  @ApiModelProperty(value = "The list of associated inventory group items.")
      @Valid
    public List<GroupItemRefType> getInventoryGroupItems() {
    return inventoryGroupItems;
  }

  public void setInventoryGroupItems(List<GroupItemRefType> inventoryGroupItems) {
    this.inventoryGroupItems = inventoryGroupItems;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InventoryGroupType inventoryGroup = (InventoryGroupType) o;
    return Objects.equals(this.id, inventoryGroup.id) &&
        Objects.equals(this.href, inventoryGroup.href) &&
        Objects.equals(this.name, inventoryGroup.name) &&
        Objects.equals(this.description, inventoryGroup.description) &&
        Objects.equals(this.igSpecification, inventoryGroup.igSpecification) &&
        Objects.equals(this.startDate, inventoryGroup.startDate) &&
        Objects.equals(this.endDate, inventoryGroup.endDate) &&
        Objects.equals(this.place, inventoryGroup.place) &&
        Objects.equals(this.parentGroupRef, inventoryGroup.parentGroupRef) &&
        Objects.equals(this.inventoryGroupItems, inventoryGroup.inventoryGroupItems);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, href, name, description, igSpecification, startDate, endDate, place, parentGroupRef, inventoryGroupItems);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InventoryGroupType {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    igSpecification: ").append(toIndentedString(igSpecification)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
    sb.append("    place: ").append(toIndentedString(place)).append("\n");
    sb.append("    parentGroupRef: ").append(toIndentedString(parentGroupRef)).append("\n");
    sb.append("    inventoryGroupItems: ").append(toIndentedString(inventoryGroupItems)).append("\n");
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
