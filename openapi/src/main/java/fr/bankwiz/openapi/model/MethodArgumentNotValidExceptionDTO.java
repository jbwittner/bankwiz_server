package fr.bankwiz.openapi.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * MethodArgumentNotValidExceptionDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class MethodArgumentNotValidExceptionDTO {

  private Integer status;

  private String details;

  private String exception;

  private String message;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime timestamp;

  private String field;

  private String objectName;

  public MethodArgumentNotValidExceptionDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public MethodArgumentNotValidExceptionDTO(Integer status, String details, String exception, String message, OffsetDateTime timestamp, String field, String objectName) {
    this.status = status;
    this.details = details;
    this.exception = exception;
    this.message = message;
    this.timestamp = timestamp;
    this.field = field;
    this.objectName = objectName;
  }

  public MethodArgumentNotValidExceptionDTO status(Integer status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  */
  @NotNull 
  @Schema(name = "status", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("status")
  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public MethodArgumentNotValidExceptionDTO details(String details) {
    this.details = details;
    return this;
  }

  /**
   * Get details
   * @return details
  */
  @NotNull 
  @Schema(name = "details", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("details")
  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public MethodArgumentNotValidExceptionDTO exception(String exception) {
    this.exception = exception;
    return this;
  }

  /**
   * Get exception
   * @return exception
  */
  @NotNull 
  @Schema(name = "exception", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("exception")
  public String getException() {
    return exception;
  }

  public void setException(String exception) {
    this.exception = exception;
  }

  public MethodArgumentNotValidExceptionDTO message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
  */
  @NotNull 
  @Schema(name = "message", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public MethodArgumentNotValidExceptionDTO timestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Get timestamp
   * @return timestamp
  */
  @NotNull @Valid 
  @Schema(name = "timestamp", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("timestamp")
  public OffsetDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public MethodArgumentNotValidExceptionDTO field(String field) {
    this.field = field;
    return this;
  }

  /**
   * Get field
   * @return field
  */
  @NotNull 
  @Schema(name = "field", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("field")
  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public MethodArgumentNotValidExceptionDTO objectName(String objectName) {
    this.objectName = objectName;
    return this;
  }

  /**
   * Get objectName
   * @return objectName
  */
  @NotNull 
  @Schema(name = "objectName", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("objectName")
  public String getObjectName() {
    return objectName;
  }

  public void setObjectName(String objectName) {
    this.objectName = objectName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MethodArgumentNotValidExceptionDTO methodArgumentNotValidExceptionDTO = (MethodArgumentNotValidExceptionDTO) o;
    return Objects.equals(this.status, methodArgumentNotValidExceptionDTO.status) &&
        Objects.equals(this.details, methodArgumentNotValidExceptionDTO.details) &&
        Objects.equals(this.exception, methodArgumentNotValidExceptionDTO.exception) &&
        Objects.equals(this.message, methodArgumentNotValidExceptionDTO.message) &&
        Objects.equals(this.timestamp, methodArgumentNotValidExceptionDTO.timestamp) &&
        Objects.equals(this.field, methodArgumentNotValidExceptionDTO.field) &&
        Objects.equals(this.objectName, methodArgumentNotValidExceptionDTO.objectName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, details, exception, message, timestamp, field, objectName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MethodArgumentNotValidExceptionDTO {\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    details: ").append(toIndentedString(details)).append("\n");
    sb.append("    exception: ").append(toIndentedString(exception)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    field: ").append(toIndentedString(field)).append("\n");
    sb.append("    objectName: ").append(toIndentedString(objectName)).append("\n");
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

