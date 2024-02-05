package fr.bankwiz.openapi.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fr.bankwiz.openapi.model.BankAccountIndexDTO;
import fr.bankwiz.openapi.model.GroupIndexDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * GroupBankAccountIndexDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class GroupBankAccountIndexDTO {

  private GroupIndexDTO groupeIndex;

  @Valid
  private List<@Valid BankAccountIndexDTO> bankAccountIndexList = new ArrayList<>();

  public GroupBankAccountIndexDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public GroupBankAccountIndexDTO(GroupIndexDTO groupeIndex, List<@Valid BankAccountIndexDTO> bankAccountIndexList) {
    this.groupeIndex = groupeIndex;
    this.bankAccountIndexList = bankAccountIndexList;
  }

  public GroupBankAccountIndexDTO groupeIndex(GroupIndexDTO groupeIndex) {
    this.groupeIndex = groupeIndex;
    return this;
  }

  /**
   * Get groupeIndex
   * @return groupeIndex
  */
  @NotNull @Valid 
  @Schema(name = "groupeIndex", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("groupeIndex")
  public GroupIndexDTO getGroupeIndex() {
    return groupeIndex;
  }

  public void setGroupeIndex(GroupIndexDTO groupeIndex) {
    this.groupeIndex = groupeIndex;
  }

  public GroupBankAccountIndexDTO bankAccountIndexList(List<@Valid BankAccountIndexDTO> bankAccountIndexList) {
    this.bankAccountIndexList = bankAccountIndexList;
    return this;
  }

  public GroupBankAccountIndexDTO addBankAccountIndexListItem(BankAccountIndexDTO bankAccountIndexListItem) {
    if (this.bankAccountIndexList == null) {
      this.bankAccountIndexList = new ArrayList<>();
    }
    this.bankAccountIndexList.add(bankAccountIndexListItem);
    return this;
  }

  /**
   * Get bankAccountIndexList
   * @return bankAccountIndexList
  */
  @NotNull @Valid 
  @Schema(name = "bankAccountIndexList", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("bankAccountIndexList")
  public List<@Valid BankAccountIndexDTO> getBankAccountIndexList() {
    return bankAccountIndexList;
  }

  public void setBankAccountIndexList(List<@Valid BankAccountIndexDTO> bankAccountIndexList) {
    this.bankAccountIndexList = bankAccountIndexList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GroupBankAccountIndexDTO groupBankAccountIndexDTO = (GroupBankAccountIndexDTO) o;
    return Objects.equals(this.groupeIndex, groupBankAccountIndexDTO.groupeIndex) &&
        Objects.equals(this.bankAccountIndexList, groupBankAccountIndexDTO.bankAccountIndexList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupeIndex, bankAccountIndexList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GroupBankAccountIndexDTO {\n");
    sb.append("    groupeIndex: ").append(toIndentedString(groupeIndex)).append("\n");
    sb.append("    bankAccountIndexList: ").append(toIndentedString(bankAccountIndexList)).append("\n");
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

