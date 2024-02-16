package fr.bankwiz.server.domain.model.data;

import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
@Setter
public class BankAccountDomain extends GroupRelatedDomain {
    private String bankAccountName;
    private Integer decimalBaseAmount;
    private CurrencyEnumDomain currency;

    @Builder
    public BankAccountDomain(
            String bankAccountName,
            Integer decimalBaseAmount,
            UUID id,
            GroupDomain group,
            CurrencyEnumDomain currency) {
        super(id, group);
        this.bankAccountName = bankAccountName;
        this.decimalBaseAmount = decimalBaseAmount;
        this.currency = currency;
    }

    public enum CurrencyEnumDomain {
        EUR,
        USD
    }
}
