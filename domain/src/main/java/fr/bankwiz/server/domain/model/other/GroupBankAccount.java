package fr.bankwiz.server.domain.model.other;

import java.util.List;

import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Group;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class GroupBankAccount {
    private List<BankAccount> bankAccounts;
    private Group group;
}
