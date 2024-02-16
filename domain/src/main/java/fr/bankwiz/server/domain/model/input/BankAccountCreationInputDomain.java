package fr.bankwiz.server.domain.model.input;

import java.util.UUID;

import fr.bankwiz.server.domain.model.data.BankAccountDomain.CurrencyEnumDomain;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BankAccountCreationInputDomain {
    private String bankAccountName;
    private UUID groupId;
    private Integer decimalBaseAmount;
    private CurrencyEnumDomain currency;
}
