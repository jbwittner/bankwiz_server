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
 * TransactionDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class TransactionDTO {

  private UUID transactionId;

  private UUID bankAccountId;

  private Integer decimalAmount;

  private String comment;

  public TransactionDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public TransactionDTO(UUID transactionId, UUID bankAccountId, Integer decimalAmount) {
    this.transactionId = transactionId;
    this.bankAccountId = bankAccountId;
    this.decimalAmount = decimalAmount;
  }

  public TransactionDTO transactionId(UUID transactionId) {
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

  public TransactionDTO bankAccountId(UUID bankAccountId) {
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

  public TransactionDTO decimalAmount(Integer decimalAmount) {
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

  public TransactionDTO comment(String comment) {
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
    TransactionDTO transactionDTO = (TransactionDTO) o;
    return Objects.equals(this.transactionId, transactionDTO.transactionId) &&
        Objects.equals(this.bankAccountId, transactionDTO.bankAccountId) &&
        Objects.equals(this.decimalAmount, transactionDTO.decimalAmount) &&
        Objects.equals(this.comment, transactionDTO.comment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionId, bankAccountId, decimalAmount, comment);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TransactionDTO {\n");
    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
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

