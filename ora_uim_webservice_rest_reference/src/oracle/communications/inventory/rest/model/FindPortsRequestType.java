package oracle.communications.inventory.rest.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * FindPortsRequestType
 */

public class FindPortsRequestType  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("serviceLocationCode")
  private String serviceLocationCode = null;

  @JsonProperty("networkLocationCode")
  private String networkLocationCode = null;

  public FindPortsRequestType serviceLocationCode(String serviceLocationCode) {
    this.serviceLocationCode = serviceLocationCode;
    return this;
  }

  /**
   * Service Location Entity Code
   * @return serviceLocationCode
  **/
  @ApiModelProperty(value = "Service Location Entity Code")
  
    public String getServiceLocationCode() {
    return serviceLocationCode;
  }

  public void setServiceLocationCode(String serviceLocationCode) {
    this.serviceLocationCode = serviceLocationCode;
  }

  public FindPortsRequestType networkLocationCode(String networkLocationCode) {
    this.networkLocationCode = networkLocationCode;
    return this;
  }

  /**
   * Network Location Entity Code
   * @return networkLocationCode
  **/
  @ApiModelProperty(value = "Network Location Entity Code")
  
    public String getNetworkLocationCode() {
    return networkLocationCode;
  }

  public void setNetworkLocationCode(String networkLocationCode) {
    this.networkLocationCode = networkLocationCode;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FindPortsRequestType findPortsRequest = (FindPortsRequestType) o;
    return Objects.equals(this.serviceLocationCode, findPortsRequest.serviceLocationCode) &&
        Objects.equals(this.networkLocationCode, findPortsRequest.networkLocationCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serviceLocationCode, networkLocationCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FindPortsRequestType {\n");
    
    sb.append("    serviceLocationCode: ").append(toIndentedString(serviceLocationCode)).append("\n");
    sb.append("    networkLocationCode: ").append(toIndentedString(networkLocationCode)).append("\n");
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
