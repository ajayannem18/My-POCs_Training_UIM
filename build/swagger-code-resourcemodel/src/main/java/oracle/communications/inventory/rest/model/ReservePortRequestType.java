package oracle.communications.inventory.rest.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ReservePortRequestType
 */
@Validated
public class ReservePortRequestType  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("customerLatitude")
  private Double customerLatitude = null;

  @JsonProperty("customerLongitude")
  private Double customerLongitude = null;

  @JsonProperty("description")
  private String description = null;

  public ReservePortRequestType customerLatitude(Double customerLatitude) {
    this.customerLatitude = customerLatitude;
    return this;
  }

  /**
   * Get customerLatitude
   * @return customerLatitude
  **/
  @ApiModelProperty(example = "17.44319536777247", required = true, value = "")
      @NotNull

    public Double getCustomerLatitude() {
    return customerLatitude;
  }

  public void setCustomerLatitude(Double customerLatitude) {
    this.customerLatitude = customerLatitude;
  }

  public ReservePortRequestType customerLongitude(Double customerLongitude) {
    this.customerLongitude = customerLongitude;
    return this;
  }

  /**
   * Get customerLongitude
   * @return customerLongitude
  **/
  @ApiModelProperty(example = "78.37898402212943", required = true, value = "")
      @NotNull

    public Double getCustomerLongitude() {
    return customerLongitude;
  }

  public void setCustomerLongitude(Double customerLongitude) {
    this.customerLongitude = customerLongitude;
  }

  public ReservePortRequestType description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(example = "Reserve Port for Feasibility", required = true, value = "")
      @NotNull

    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReservePortRequestType reservePortRequest = (ReservePortRequestType) o;
    return Objects.equals(this.customerLatitude, reservePortRequest.customerLatitude) &&
        Objects.equals(this.customerLongitude, reservePortRequest.customerLongitude) &&
        Objects.equals(this.description, reservePortRequest.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(customerLatitude, customerLongitude, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReservePortRequestType {\n");
    
    sb.append("    customerLatitude: ").append(toIndentedString(customerLatitude)).append("\n");
    sb.append("    customerLongitude: ").append(toIndentedString(customerLongitude)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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
