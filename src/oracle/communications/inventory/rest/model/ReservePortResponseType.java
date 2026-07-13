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
 * ReservePortResponseType
 */

public class ReservePortResponseType  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("success")
  private Boolean success = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("reservationNumber")
  private Long reservationNumber = null;

  @JsonProperty("resourceId")
  private String resourceId = null;

  @JsonProperty("physicalDevice")
  private String physicalDevice = null;

  @JsonProperty("popLocation")
  private String popLocation = null;

  @JsonProperty("reservationType")
  private String reservationType = null;

  @JsonProperty("reservedFor")
  private String reservedFor = null;

  @JsonProperty("reservedForType")
  private String reservedForType = null;

  @JsonProperty("conditionType")
  private String conditionType = null;

  @JsonProperty("conditionReason")
  private String conditionReason = null;

  public ReservePortResponseType success(Boolean success) {
    this.success = success;
    return this;
  }

  /**
   * Get success
   * @return success
  **/
  @ApiModelProperty(example = "true", value = "")
  
    public Boolean isSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public ReservePortResponseType description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(example = "Reserved Successfully", value = "")
  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ReservePortResponseType reservationNumber(Long reservationNumber) {
    this.reservationNumber = reservationNumber;
    return this;
  }

  /**
   * Get reservationNumber
   * @return reservationNumber
  **/
  @ApiModelProperty(example = "75001", value = "")
  
    public Long getReservationNumber() {
    return reservationNumber;
  }

  public void setReservationNumber(Long reservationNumber) {
    this.reservationNumber = reservationNumber;
  }

  public ReservePortResponseType resourceId(String resourceId) {
    this.resourceId = resourceId;
    return this;
  }

  /**
   * Get resourceId
   * @return resourceId
  **/
  @ApiModelProperty(example = "225006-225002", value = "")
  
    public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  public ReservePortResponseType physicalDevice(String physicalDevice) {
    this.physicalDevice = physicalDevice;
    return this;
  }

  /**
   * Get physicalDevice
   * @return physicalDevice
  **/
  @ApiModelProperty(example = "cat8000v.cisco.com", value = "")
  
    public String getPhysicalDevice() {
    return physicalDevice;
  }

  public void setPhysicalDevice(String physicalDevice) {
    this.physicalDevice = physicalDevice;
  }

  public ReservePortResponseType popLocation(String popLocation) {
    this.popLocation = popLocation;
    return this;
  }

  /**
   * Get popLocation
   * @return popLocation
  **/
  @ApiModelProperty(example = "5A-MINDSPACE", value = "")
  
    public String getPopLocation() {
    return popLocation;
  }

  public void setPopLocation(String popLocation) {
    this.popLocation = popLocation;
  }

  public ReservePortResponseType reservationType(String reservationType) {
    this.reservationType = reservationType;
    return this;
  }

  /**
   * Get reservationType
   * @return reservationType
  **/
  @ApiModelProperty(example = "SHORTTERM", value = "")
  
    public String getReservationType() {
    return reservationType;
  }

  public void setReservationType(String reservationType) {
    this.reservationType = reservationType;
  }

  public ReservePortResponseType reservedFor(String reservedFor) {
    this.reservedFor = reservedFor;
    return this;
  }

  /**
   * Get reservedFor
   * @return reservedFor
  **/
  @ApiModelProperty(example = "FEASIBILITY", value = "")
  
    public String getReservedFor() {
    return reservedFor;
  }

  public void setReservedFor(String reservedFor) {
    this.reservedFor = reservedFor;
  }

  public ReservePortResponseType reservedForType(String reservedForType) {
    this.reservedForType = reservedForType;
    return this;
  }

  /**
   * Get reservedForType
   * @return reservedForType
  **/
  @ApiModelProperty(example = "CUSTOMER", value = "")
  
    public String getReservedForType() {
    return reservedForType;
  }

  public void setReservedForType(String reservedForType) {
    this.reservedForType = reservedForType;
  }

  public ReservePortResponseType conditionType(String conditionType) {
    this.conditionType = conditionType;
    return this;
  }

  /**
   * Get conditionType
   * @return conditionType
  **/
  @ApiModelProperty(example = "INFORMATIONAL", value = "")
  
    public String getConditionType() {
    return conditionType;
  }

  public void setConditionType(String conditionType) {
    this.conditionType = conditionType;
  }

  public ReservePortResponseType conditionReason(String conditionReason) {
    this.conditionReason = conditionReason;
    return this;
  }

  /**
   * Get conditionReason
   * @return conditionReason
  **/
  @ApiModelProperty(example = "75001", value = "")
  
    public String getConditionReason() {
    return conditionReason;
  }

  public void setConditionReason(String conditionReason) {
    this.conditionReason = conditionReason;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReservePortResponseType reservePortResponse = (ReservePortResponseType) o;
    return Objects.equals(this.success, reservePortResponse.success) &&
        Objects.equals(this.description, reservePortResponse.description) &&
        Objects.equals(this.reservationNumber, reservePortResponse.reservationNumber) &&
        Objects.equals(this.resourceId, reservePortResponse.resourceId) &&
        Objects.equals(this.physicalDevice, reservePortResponse.physicalDevice) &&
        Objects.equals(this.popLocation, reservePortResponse.popLocation) &&
        Objects.equals(this.reservationType, reservePortResponse.reservationType) &&
        Objects.equals(this.reservedFor, reservePortResponse.reservedFor) &&
        Objects.equals(this.reservedForType, reservePortResponse.reservedForType) &&
        Objects.equals(this.conditionType, reservePortResponse.conditionType) &&
        Objects.equals(this.conditionReason, reservePortResponse.conditionReason);
  }

  @Override
  public int hashCode() {
    return Objects.hash(success, description, reservationNumber, resourceId, physicalDevice, popLocation, reservationType, reservedFor, reservedForType, conditionType, conditionReason);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReservePortResponseType {\n");
    
    sb.append("    success: ").append(toIndentedString(success)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    reservationNumber: ").append(toIndentedString(reservationNumber)).append("\n");
    sb.append("    resourceId: ").append(toIndentedString(resourceId)).append("\n");
    sb.append("    physicalDevice: ").append(toIndentedString(physicalDevice)).append("\n");
    sb.append("    popLocation: ").append(toIndentedString(popLocation)).append("\n");
    sb.append("    reservationType: ").append(toIndentedString(reservationType)).append("\n");
    sb.append("    reservedFor: ").append(toIndentedString(reservedFor)).append("\n");
    sb.append("    reservedForType: ").append(toIndentedString(reservedForType)).append("\n");
    sb.append("    conditionType: ").append(toIndentedString(conditionType)).append("\n");
    sb.append("    conditionReason: ").append(toIndentedString(conditionReason)).append("\n");
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
