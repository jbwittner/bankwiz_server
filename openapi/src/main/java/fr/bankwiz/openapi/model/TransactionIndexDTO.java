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
 * TransactionIndexDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class TransactionIndexDTO {

  private UUID transactionId;

  private Integer decimalAmount;

  private String comment;

  public TransactionIndexDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TransactionIndexDTO(UUID transactionId, Integer decimalAmount) {
    this.transactionId = transactionId;
    this.decimalAmount = decimalAmount;
  }

  public TransactionIndexDTO transactionId(UUID transactionId) {
    this.transactionId = transactionId;
    return this;
  }

  /**
   * Get transactionId
   * @return transactionId
  */
  @NotNull @Valid 
  @Schema(name = "TransactionId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("TransactionId")
  public UUID getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(UUID transactionId) {
    this.transactionId = transactionId;
  }

  public TransactionIndexDTO decimalAmount(Integer decimalAmount) {
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

  public TransactionIndexDTO comment(String comment) {
    this.comment = comment;
    return this;
  }

  /**
   * Get comment
   * @return comment
  */
  
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
    TransactionIndexDTO transactionIndexDTO = (TransactionIndexDTO) o;
    return Objects.equals(this.transactionId, transactionIndexDTO.transactionId) &&
        Objects.equals(this.decimalAmount, transactionIndexDTO.decimalAmount) &&
        Objects.equals(this.comment, transactionIndexDTO.comment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionId, decimalAmount, comment);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionIndexDTO {\n");
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
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

