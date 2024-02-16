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
 * CurrencyIndexDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class CurrencyIndexDTO {

  private String currencyIsoCode3;

  private String displayName;

  private String symbol;

  public CurrencyIndexDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CurrencyIndexDTO(String currencyIsoCode3, String displayName) {
    this.currencyIsoCode3 = currencyIsoCode3;
    this.displayName = displayName;
  }

  public CurrencyIndexDTO currencyIsoCode3(String currencyIsoCode3) {
    this.currencyIsoCode3 = currencyIsoCode3;
    return this;
  }

  /**
   * Get currencyIsoCode3
   * @return currencyIsoCode3
  */
  @NotNull 
  @Schema(name = "currencyIsoCode3", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("currencyIsoCode3")
  public String getCurrencyIsoCode3() {
    return currencyIsoCode3;
  }

  public void setCurrencyIsoCode3(String currencyIsoCode3) {
    this.currencyIsoCode3 = currencyIsoCode3;
  }

  public CurrencyIndexDTO displayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  /**
   * Get displayName
   * @return displayName
  */
  @NotNull 
  @Schema(name = "displayName", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("displayName")
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public CurrencyIndexDTO symbol(String symbol) {
    this.symbol = symbol;
    return this;
  }

  /**
   * Get symbol
   * @return symbol
  */
  
  @Schema(name = "symbol", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("symbol")
  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CurrencyIndexDTO currencyIndexDTO = (CurrencyIndexDTO) o;
    return Objects.equals(this.currencyIsoCode3, currencyIndexDTO.currencyIsoCode3) &&
        Objects.equals(this.displayName, currencyIndexDTO.displayName) &&
        Objects.equals(this.symbol, currencyIndexDTO.symbol);
  }

  @Override
  public int hashCode() {
    return Objects.hash(currencyIsoCode3, displayName, symbol);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CurrencyIndexDTO {\n");
    sb.append("    currencyIsoCode3: ").append(toIndentedString(currencyIsoCode3)).append("\n");
    sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
    sb.append("    symbol: ").append(toIndentedString(symbol)).append("\n");
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

