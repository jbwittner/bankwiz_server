package fr.bankwiz.server.domain.model.other;

import java.util.List;

import fr.bankwiz.server.domain.model.data.BankAccountDomain;
import fr.bankwiz.server.domain.model.data.Group;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GroupBankAccount {
    private List<BankAccountDomain> bankAccounts;
    private Group group;
}
