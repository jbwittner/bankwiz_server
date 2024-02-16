package fr.bankwiz.server.domain.model.input;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateTransactionInputDomain {
    private Integer decimalAmount;
    private String comment;
}
