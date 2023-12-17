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
public class BankAccount extends GroupRelated {
    private String bankAccountName;
    private Integer decimalBaseAmount;

    @Builder
    public BankAccount(String bankAccountName, Integer decimalBaseAmount, UUID id, Group group) {
        super(id, group);
        this.bankAccountName = bankAccountName;
        this.decimalBaseAmount = decimalBaseAmount;
    }
}
