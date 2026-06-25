package oracle.communications.inventory.rest.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import oracle.communications.inventory.rest.model.DeviceInfoType;
import java.io.Serializable;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * FindPortsResponseType
 */
@Validated
public class FindPortsResponseType  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("locationCode")
  private String locationCode = null;

  @JsonProperty("devices")
  @Valid
  private List<DeviceInfoType> devices = null;

  public FindPortsResponseType locationCode(String locationCode) {
    this.locationCode = locationCode;
    return this;
  }

  /**
   * Get locationCode
   * @return locationCode
  **/
  @ApiModelProperty(value = "")
  
    public String getLocationCode() {
    return locationCode;
  }

  public void setLocationCode(String locationCode) {
    this.locationCode = locationCode;
  }

  public FindPortsResponseType devices(List<DeviceInfoType> devices) {
    this.devices = devices;
    return this;
  }

  public FindPortsResponseType addDevicesItem(DeviceInfoType devicesItem) {
    if (this.devices == null) {
      this.devices = new ArrayList<DeviceInfoType>();
    }
    this.devices.add(devicesItem);
    return this;
  }

  /**
   * Get devices
   * @return devices
  **/
  @ApiModelProperty(value = "")
      @Valid
    public List<DeviceInfoType> getDevices() {
    return devices;
  }

  public void setDevices(List<DeviceInfoType> devices) {
    this.devices = devices;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FindPortsResponseType findPortsResponse = (FindPortsResponseType) o;
    return Objects.equals(this.locationCode, findPortsResponse.locationCode) &&
        Objects.equals(this.devices, findPortsResponse.devices);
  }

  @Override
  public int hashCode() {
    return Objects.hash(locationCode, devices);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FindPortsResponseType {\n");
    
    sb.append("    locationCode: ").append(toIndentedString(locationCode)).append("\n");
    sb.append("    devices: ").append(toIndentedString(devices)).append("\n");
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
