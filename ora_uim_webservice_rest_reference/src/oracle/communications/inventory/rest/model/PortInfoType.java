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
 * PortInfoType
 */

public class PortInfoType  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("portName")
  private String portName = null;

  @JsonProperty("portId")
  private String portId = null;

  @JsonProperty("portType")
  private String portType = null;

  @JsonProperty("lifecycleState")
  private String lifecycleState = null;

  public PortInfoType portName(String portName) {
    this.portName = portName;
    return this;
  }

  /**
   * Get portName
   * @return portName
  **/
  @ApiModelProperty(value = "")
  
    public String getPortName() {
    return portName;
  }

  public void setPortName(String portName) {
    this.portName = portName;
  }

  public PortInfoType portId(String portId) {
    this.portId = portId;
    return this;
  }

  /**
   * Get portId
   * @return portId
  **/
  @ApiModelProperty(value = "")
  
    public String getPortId() {
    return portId;
  }

  public void setPortId(String portId) {
    this.portId = portId;
  }

  public PortInfoType portType(String portType) {
    this.portType = portType;
    return this;
  }

  /**
   * Get portType
   * @return portType
  **/
  @ApiModelProperty(value = "")
  
    public String getPortType() {
    return portType;
  }

  public void setPortType(String portType) {
    this.portType = portType;
  }

  public PortInfoType lifecycleState(String lifecycleState) {
    this.lifecycleState = lifecycleState;
    return this;
  }

  /**
   * Get lifecycleState
   * @return lifecycleState
  **/
  @ApiModelProperty(value = "")
  
    public String getLifecycleState() {
    return lifecycleState;
  }

  public void setLifecycleState(String lifecycleState) {
    this.lifecycleState = lifecycleState;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PortInfoType portInfo = (PortInfoType) o;
    return Objects.equals(this.portName, portInfo.portName) &&
        Objects.equals(this.portId, portInfo.portId) &&
        Objects.equals(this.portType, portInfo.portType) &&
        Objects.equals(this.lifecycleState, portInfo.lifecycleState);
  }

  @Override
  public int hashCode() {
    return Objects.hash(portName, portId, portType, lifecycleState);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PortInfoType {\n");
    
    sb.append("    portName: ").append(toIndentedString(portName)).append("\n");
    sb.append("    portId: ").append(toIndentedString(portId)).append("\n");
    sb.append("    portType: ").append(toIndentedString(portType)).append("\n");
    sb.append("    lifecycleState: ").append(toIndentedString(lifecycleState)).append("\n");
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
