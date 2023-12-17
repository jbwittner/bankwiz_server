package fr.bankwiz.server.domain.model.input;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BankAccountUpdateInput {
    private String bankAccountName;
    private UUID groupId;
    private Integer decimalBaseAmount;
}
