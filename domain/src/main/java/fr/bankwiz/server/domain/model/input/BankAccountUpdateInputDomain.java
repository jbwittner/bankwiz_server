package fr.bankwiz.server.domain.model.input;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BankAccountUpdateInputDomain {
    private String bankAccountName;
    private UUID groupId;
    private Integer decimalBaseAmount;
}
