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
 * A inventoryGroup to associate with the resource.
 */
@ApiModel(description = "A inventoryGroup to associate with the resource.")

public class GroupRefType  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("href")
  private String href = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("igSpecification")
  private SpecificationType igSpecification = null;

  public GroupRefType id(String id) {
    this.id = id;
    return this;
  }

  /**
   * The ID of the ig.
   * @return id
  **/
  @ApiModelProperty(required = true, value = "The ID of the ig.")
      @NotNull

    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public GroupRefType href(String href) {
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

  public GroupRefType name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of the ig.
   * @return name
  **/
  @ApiModelProperty(value = "The name of the ig.")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public GroupRefType igSpecification(SpecificationType igSpecification) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GroupRefType groupRef = (GroupRefType) o;
    return Objects.equals(this.id, groupRef.id) &&
        Objects.equals(this.href, groupRef.href) &&
        Objects.equals(this.name, groupRef.name) &&
        Objects.equals(this.igSpecification, groupRef.igSpecification);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, href, name, igSpecification);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GroupRefType {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    href: ").append(toIndentedString(href)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    igSpecification: ").append(toIndentedString(igSpecification)).append("\n");
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
