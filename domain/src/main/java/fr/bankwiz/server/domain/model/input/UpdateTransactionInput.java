package fr.bankwiz.server.domain.model.input;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateTransactionInput {
    private Integer decimalAmount;
    private String comment;
}
