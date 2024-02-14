package fr.bankwiz.openapi.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * UpdateTransactionRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class UpdateTransactionRequest {

  private Integer decimalAmount;

  private String comment;

  public UpdateTransactionRequest decimalAmount(Integer decimalAmount) {
    this.decimalAmount = decimalAmount;
    return this;
  }

  /**
   * Get decimalAmount
   * @return decimalAmount
  */
  
  @Schema(name = "DecimalAmount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("DecimalAmount")
  public Integer getDecimalAmount() {
    return decimalAmount;
  }

  public void setDecimalAmount(Integer decimalAmount) {
    this.decimalAmount = decimalAmount;
  }

  public UpdateTransactionRequest comment(String comment) {
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
    UpdateTransactionRequest updateTransactionRequest = (UpdateTransactionRequest) o;
    return Objects.equals(this.decimalAmount, updateTransactionRequest.decimalAmount) &&
        Objects.equals(this.comment, updateTransactionRequest.comment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(decimalAmount, comment);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateTransactionRequest {\n");
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

