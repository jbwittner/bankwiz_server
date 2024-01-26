package fr.bankwiz.server.domain.model.input;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TransactionCreationInput {
    private UUID bankAccountId;
    private Integer decimalAmount;
    private String comment;
}
