package fr.bankwiz.openapi.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.UUID;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * BankAccountUpdateRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class BankAccountUpdateRequest {

  private String bankAccountName;

  private UUID groupId;

  private Integer decimalBaseAmount;

  public BankAccountUpdateRequest bankAccountName(String bankAccountName) {
    this.bankAccountName = bankAccountName;
    return this;
  }

  /**
   * Get bankAccountName
   * @return bankAccountName
  */
  
  @Schema(name = "bankAccountName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("bankAccountName")
  public String getBankAccountName() {
    return bankAccountName;
  }

  public void setBankAccountName(String bankAccountName) {
    this.bankAccountName = bankAccountName;
  }

  public BankAccountUpdateRequest groupId(UUID groupId) {
    this.groupId = groupId;
    return this;
  }

  /**
   * Get groupId
   * @return groupId
  */
  @Valid 
  @Schema(name = "groupId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("groupId")
  public UUID getGroupId() {
    return groupId;
  }

  public void setGroupId(UUID groupId) {
    this.groupId = groupId;
  }

  public BankAccountUpdateRequest decimalBaseAmount(Integer decimalBaseAmount) {
    this.decimalBaseAmount = decimalBaseAmount;
    return this;
  }

  /**
   * Get decimalBaseAmount
   * @return decimalBaseAmount
  */
  
  @Schema(name = "decimalBaseAmount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("decimalBaseAmount")
  public Integer getDecimalBaseAmount() {
    return decimalBaseAmount;
  }

  public void setDecimalBaseAmount(Integer decimalBaseAmount) {
    this.decimalBaseAmount = decimalBaseAmount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BankAccountUpdateRequest bankAccountUpdateRequest = (BankAccountUpdateRequest) o;
    return Objects.equals(this.bankAccountName, bankAccountUpdateRequest.bankAccountName) &&
        Objects.equals(this.groupId, bankAccountUpdateRequest.groupId) &&
        Objects.equals(this.decimalBaseAmount, bankAccountUpdateRequest.decimalBaseAmount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bankAccountName, groupId, decimalBaseAmount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BankAccountUpdateRequest {\n");
    sb.append("    bankAccountName: ").append(toIndentedString(bankAccountName)).append("\n");
    sb.append("    groupId: ").append(toIndentedString(groupId)).append("\n");
    sb.append("    decimalBaseAmount: ").append(toIndentedString(decimalBaseAmount)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

