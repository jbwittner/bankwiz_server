package fr.bankwiz.openapi.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import fr.bankwiz.openapi.model.BankAccountIndexDTO;
import fr.bankwiz.openapi.model.TransactionIndexDTO;
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
 * BankAccountTransactionsDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class BankAccountTransactionsDTO {

  private BankAccountIndexDTO bankAccountIndex;

  @Valid
  private List<@Valid TransactionIndexDTO> transactions = new ArrayList<>();

  public BankAccountTransactionsDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public BankAccountTransactionsDTO(BankAccountIndexDTO bankAccountIndex, List<@Valid TransactionIndexDTO> transactions) {
    this.bankAccountIndex = bankAccountIndex;
    this.transactions = transactions;
  }

  public BankAccountTransactionsDTO bankAccountIndex(BankAccountIndexDTO bankAccountIndex) {
    this.bankAccountIndex = bankAccountIndex;
    return this;
  }

  /**
   * Get bankAccountIndex
   * @return bankAccountIndex
  */
  @NotNull @Valid 
  @Schema(name = "BankAccountIndex", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("BankAccountIndex")
  public BankAccountIndexDTO getBankAccountIndex() {
    return bankAccountIndex;
  }

  public void setBankAccountIndex(BankAccountIndexDTO bankAccountIndex) {
    this.bankAccountIndex = bankAccountIndex;
  }

  public BankAccountTransactionsDTO transactions(List<@Valid TransactionIndexDTO> transactions) {
    this.transactions = transactions;
    return this;
  }

  public BankAccountTransactionsDTO addTransactionsItem(TransactionIndexDTO transactionsItem) {
    if (this.transactions == null) {
      this.transactions = new ArrayList<>();
    }
    this.transactions.add(transactionsItem);
    return this;
  }

  /**
   * Get transactions
   * @return transactions
  */
  @NotNull @Valid 
  @Schema(name = "Transactions", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("Transactions")
  public List<@Valid TransactionIndexDTO> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<@Valid TransactionIndexDTO> transactions) {
    this.transactions = transactions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BankAccountTransactionsDTO bankAccountTransactionsDTO = (BankAccountTransactionsDTO) o;
    return Objects.equals(this.bankAccountIndex, bankAccountTransactionsDTO.bankAccountIndex) &&
        Objects.equals(this.transactions, bankAccountTransactionsDTO.transactions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bankAccountIndex, transactions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BankAccountTransactionsDTO {\n");
    sb.append("    bankAccountIndex: ").append(toIndentedString(bankAccountIndex)).append("\n");
    sb.append("    transactions: ").append(toIndentedString(transactions)).append("\n");
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

