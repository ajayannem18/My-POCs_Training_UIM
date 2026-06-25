package oracle.communications.inventory.rest.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import oracle.communications.inventory.rest.model.PortInfoType;
import java.io.Serializable;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * DeviceInfoType
 */
@Validated
public class DeviceInfoType  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("deviceName")
  private String deviceName = null;

  @JsonProperty("deviceId")
  private String deviceId = null;

  @JsonProperty("availablePortCount")
  private Integer availablePortCount = null;

  @JsonProperty("availablePorts")
  @Valid
  private List<PortInfoType> availablePorts = null;

  public DeviceInfoType deviceName(String deviceName) {
    this.deviceName = deviceName;
    return this;
  }

  /**
   * Get deviceName
   * @return deviceName
  **/
  @ApiModelProperty(value = "")
  
    public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public DeviceInfoType deviceId(String deviceId) {
    this.deviceId = deviceId;
    return this;
  }

  /**
   * Get deviceId
   * @return deviceId
  **/
  @ApiModelProperty(value = "")
  
    public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public DeviceInfoType availablePortCount(Integer availablePortCount) {
    this.availablePortCount = availablePortCount;
    return this;
  }

  /**
   * Get availablePortCount
   * @return availablePortCount
  **/
  @ApiModelProperty(value = "")
  
    public Integer getAvailablePortCount() {
    return availablePortCount;
  }

  public void setAvailablePortCount(Integer availablePortCount) {
    this.availablePortCount = availablePortCount;
  }

  public DeviceInfoType availablePorts(List<PortInfoType> availablePorts) {
    this.availablePorts = availablePorts;
    return this;
  }

  public DeviceInfoType addAvailablePortsItem(PortInfoType availablePortsItem) {
    if (this.availablePorts == null) {
      this.availablePorts = new ArrayList<PortInfoType>();
    }
    this.availablePorts.add(availablePortsItem);
    return this;
  }

  /**
   * Get availablePorts
   * @return availablePorts
  **/
  @ApiModelProperty(value = "")
      @Valid
    public List<PortInfoType> getAvailablePorts() {
    return availablePorts;
  }

  public void setAvailablePorts(List<PortInfoType> availablePorts) {
    this.availablePorts = availablePorts;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeviceInfoType deviceInfo = (DeviceInfoType) o;
    return Objects.equals(this.deviceName, deviceInfo.deviceName) &&
        Objects.equals(this.deviceId, deviceInfo.deviceId) &&
        Objects.equals(this.availablePortCount, deviceInfo.availablePortCount) &&
        Objects.equals(this.availablePorts, deviceInfo.availablePorts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deviceName, deviceId, availablePortCount, availablePorts);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceInfoType {\n");
    
    sb.append("    deviceName: ").append(toIndentedString(deviceName)).append("\n");
    sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
    sb.append("    availablePortCount: ").append(toIndentedString(availablePortCount)).append("\n");
    sb.append("    availablePorts: ").append(toIndentedString(availablePorts)).append("\n");
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
