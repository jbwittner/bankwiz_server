package fr.bankwiz.openapi.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import fr.bankwiz.openapi.model.UserDTO;
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
 * UserGroupRightDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class UserGroupRightDTO {

  private UUID groupRightId;

  private UserDTO user;

  private UserGroupRightEnum right;

  public UserGroupRightDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public UserGroupRightDTO(UUID groupRightId, UserDTO user, UserGroupRightEnum right) {
    this.groupRightId = groupRightId;
    this.user = user;
    this.right = right;
  }

  public UserGroupRightDTO groupRightId(UUID groupRightId) {
    this.groupRightId = groupRightId;
    return this;
  }

  /**
   * Get groupRightId
   * @return groupRightId
  */
  @NotNull @Valid 
  @Schema(name = "groupRightId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("groupRightId")
  public UUID getGroupRightId() {
    return groupRightId;
  }

  public void setGroupRightId(UUID groupRightId) {
    this.groupRightId = groupRightId;
  }

  public UserGroupRightDTO user(UserDTO user) {
    this.user = user;
    return this;
  }

  /**
   * Get user
   * @return user
  */
  @NotNull @Valid 
  @Schema(name = "user", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("user")
  public UserDTO getUser() {
    return user;
  }

  public void setUser(UserDTO user) {
    this.user = user;
  }

  public UserGroupRightDTO right(UserGroupRightEnum right) {
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
    UserGroupRightDTO userGroupRightDTO = (UserGroupRightDTO) o;
    return Objects.equals(this.groupRightId, userGroupRightDTO.groupRightId) &&
        Objects.equals(this.user, userGroupRightDTO.user) &&
        Objects.equals(this.right, userGroupRightDTO.right);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupRightId, user, right);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserGroupRightDTO {\n");
    sb.append("    groupRightId: ").append(toIndentedString(groupRightId)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
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

