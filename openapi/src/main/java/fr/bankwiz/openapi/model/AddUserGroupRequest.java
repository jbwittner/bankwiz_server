package fr.bankwiz.openapi.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import fr.bankwiz.openapi.model.UserGroupRightEnum;
import java.util.UUID;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * AddUserGroupRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class AddUserGroupRequest {

  private UUID userId;

  private UserGroupRightEnum right;

  public AddUserGroupRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public AddUserGroupRequest(UUID userId, UserGroupRightEnum right) {
    this.userId = userId;
    this.right = right;
  }

  public AddUserGroupRequest userId(UUID userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
  */
  @NotNull @Valid 
  @Schema(name = "userId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("userId")
  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public AddUserGroupRequest right(UserGroupRightEnum right) {
    this.right = right;
    return this;
  }

  /**
   * Get right
   * @return right
  */
  @NotNull @Valid 
  @Schema(name = "right", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("right")
  public UserGroupRightEnum getRight() {
    return right;
  }

  public void setRight(UserGroupRightEnum right) {
    this.right = right;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddUserGroupRequest addUserGroupRequest = (AddUserGroupRequest) o;
    return Objects.equals(this.userId, addUserGroupRequest.userId) &&
        Objects.equals(this.right, addUserGroupRequest.right);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, right);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddUserGroupRequest {\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    right: ").append(toIndentedString(right)).append("\n");
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

