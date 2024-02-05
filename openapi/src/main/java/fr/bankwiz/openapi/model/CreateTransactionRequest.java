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
 * CreateTransactionRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class CreateTransactionRequest {

  private UUID bankAccountId;

  private Integer decimalAmount;

  private String comment;

  public CreateTransactionRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreateTransactionRequest(UUID bankAccountId, Integer decimalAmount) {
    this.bankAccountId = bankAccountId;
    this.decimalAmount = decimalAmount;
  }

  public CreateTransactionRequest bankAccountId(UUID bankAccountId) {
    this.bankAccountId = bankAccountId;
    return this;
  }

  /**
   * Get bankAccountId
   * @return bankAccountId
  */
  @NotNull @Valid 
  @Schema(name = "BankAccountId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("BankAccountId")
  public UUID getBankAccountId() {
    return bankAccountId;
  }

  public void setBankAccountId(UUID bankAccountId) {
    this.bankAccountId = bankAccountId;
  }

  public CreateTransactionRequest decimalAmount(Integer decimalAmount) {
    this.decimalAmount = decimalAmount;
    return this;
  }

  /**
   * Get decimalAmount
   * @return decimalAmount
  */
  @NotNull 
  @Schema(name = "DecimalAmount", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("DecimalAmount")
  public Integer getDecimalAmount() {
    return decimalAmount;
  }

  public void setDecimalAmount(Integer decimalAmount) {
    this.decimalAmount = decimalAmount;
  }

  public CreateTransactionRequest comment(String comment) {
    this.comment = comment;
    return this;
  }

  /**
   * Get comment
   * @return comment
  */
  @Size(max = 255) 
  @Schema(name = "Comment", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("Comment")
  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateTransactionRequest createTransactionRequest = (CreateTransactionRequest) o;
    return Objects.equals(this.bankAccountId, createTransactionRequest.bankAccountId) &&
        Objects.equals(this.decimalAmount, createTransactionRequest.decimalAmount) &&
        Objects.equals(this.comment, createTransactionRequest.comment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bankAccountId, decimalAmount, comment);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateTransactionRequest {\n");
    sb.append("    bankAccountId: ").append(toIndentedString(bankAccountId)).append("\n");
    sb.append("    decimalAmount: ").append(toIndentedString(decimalAmount)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
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

