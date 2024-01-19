package fr.bankwiz.server.domain.model.data;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Transaction {
    private UUID id;
    private Integer decimalAmount;
    private String comment;
    private BankAccount bankAccount;
}
