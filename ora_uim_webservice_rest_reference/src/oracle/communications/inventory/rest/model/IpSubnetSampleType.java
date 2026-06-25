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
import oracle.communications.inventory.rest.model.IPSubnetType;
import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * A Sample of IP.
 */
@ApiModel(description = "A Sample of IP.")
public class IpSubnetSampleType  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("subnet")
  private IPSubnetType subnet = null;

  @JsonProperty("cidrNumber")
  private Integer cidrNumber = null;

  @JsonProperty("subnetNum")
  private Integer subnetNum = null;

  public IpSubnetSampleType subnet(IPSubnetType subnet) {
    this.subnet = subnet;
    return this;
  }

  /**
   * Get subnet
   * @return subnet
  **/
  @ApiModelProperty(value = "")
  
    @Valid
    public IPSubnetType getSubnet() {
    return subnet;
  }

  public void setSubnet(IPSubnetType subnet) {
    this.subnet = subnet;
  }

  public IpSubnetSampleType cidrNumber(Integer cidrNumber) {
    this.cidrNumber = cidrNumber;
    return this;
  }

  /**
   * Partition or Join PrefixLength.
   * @return cidrNumber
  **/
  @ApiModelProperty(value = "Partition or Join PrefixLength.")
  
    public Integer getCidrNumber() {
    return cidrNumber;
  }

  public void setCidrNumber(Integer cidrNumber) {
    this.cidrNumber = cidrNumber;
  }

  public IpSubnetSampleType subnetNum(Integer subnetNum) {
    this.subnetNum = subnetNum;
    return this;
  }

  /**
   * The number of subnets for partitions.
   * @return subnetNum
  **/
  @ApiModelProperty(value = "The number of subnets for partitions.")
  
    public Integer getSubnetNum() {
    return subnetNum;
  }

  public void setSubnetNum(Integer subnetNum) {
    this.subnetNum = subnetNum;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IpSubnetSampleType ipSubnetSample = (IpSubnetSampleType) o;
    return Objects.equals(this.subnet, ipSubnetSample.subnet) &&
        Objects.equals(this.cidrNumber, ipSubnetSample.cidrNumber) &&
        Objects.equals(this.subnetNum, ipSubnetSample.subnetNum);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subnet, cidrNumber, subnetNum);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IpSubnetSampleType {\n");
    
    sb.append("    subnet: ").append(toIndentedString(subnet)).append("\n");
    sb.append("    cidrNumber: ").append(toIndentedString(cidrNumber)).append("\n");
    sb.append("    subnetNum: ").append(toIndentedString(subnetNum)).append("\n");
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
