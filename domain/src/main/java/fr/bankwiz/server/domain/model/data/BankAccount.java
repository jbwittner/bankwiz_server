package fr.bankwiz.server.domain.model.data;

import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class BankAccount {
    private UUID id;
    private String bankAccountName;
    private Group group;
    private Integer decimalBaseAmount;
}
