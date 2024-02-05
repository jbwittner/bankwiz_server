package fr.bankwiz.openapi.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fr.bankwiz.openapi.model.UserGroupRightDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * GroupDetailsDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class GroupDetailsDTO {

  private UUID id;

  private String groupName;

  @Valid
  private List<@Valid UserGroupRightDTO> usersRights = new ArrayList<>();

  public GroupDetailsDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public GroupDetailsDTO(UUID id, String groupName, List<@Valid UserGroupRightDTO> usersRights) {
    this.id = id;
    this.groupName = groupName;
    this.usersRights = usersRights;
  }

  public GroupDetailsDTO id(UUID id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @NotNull @Valid 
  @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public GroupDetailsDTO groupName(String groupName) {
    this.groupName = groupName;
    return this;
  }

  /**
   * Get groupName
   * @return groupName
  */
  @NotNull 
  @Schema(name = "groupName", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("groupName")
  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public GroupDetailsDTO usersRights(List<@Valid UserGroupRightDTO> usersRights) {
    this.usersRights = usersRights;
    return this;
  }

  public GroupDetailsDTO addUsersRightsItem(UserGroupRightDTO usersRightsItem) {
    if (this.usersRights == null) {
      this.usersRights = new ArrayList<>();
    }
    this.usersRights.add(usersRightsItem);
    return this;
  }

  /**
   * Get usersRights
   * @return usersRights
  */
  @NotNull @Valid 
  @Schema(name = "usersRights", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("usersRights")
  public List<@Valid UserGroupRightDTO> getUsersRights() {
    return usersRights;
  }

  public void setUsersRights(List<@Valid UserGroupRightDTO> usersRights) {
    this.usersRights = usersRights;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GroupDetailsDTO groupDetailsDTO = (GroupDetailsDTO) o;
    return Objects.equals(this.id, groupDetailsDTO.id) &&
        Objects.equals(this.groupName, groupDetailsDTO.groupName) &&
        Objects.equals(this.usersRights, groupDetailsDTO.usersRights);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, groupName, usersRights);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GroupDetailsDTO {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    groupName: ").append(toIndentedString(groupName)).append("\n");
    sb.append("    usersRights: ").append(toIndentedString(usersRights)).append("\n");
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

