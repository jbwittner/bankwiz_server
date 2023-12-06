package fr.bankwiz.server.infrastructure.transformer;

import java.util.List;

import fr.bankwiz.openapi.model.BankAccountIndexDTO;
import fr.bankwiz.openapi.model.GroupBankAcccountIndexDTO;
import fr.bankwiz.openapi.model.GroupIndexDTO;
import fr.bankwiz.server.domain.model.other.GroupBankAccount;

public class GroupBankAccountTransformer {

    private GroupBankAccountTransformer() {}

    public static GroupBankAcccountIndexDTO toGroupBankAcccountIndexDTO(final GroupBankAccount groupBankAccount) {
        final GroupIndexDTO groupIndexDTO = GroupTransformer.toGroupIndexDTO(groupBankAccount.getGroup());
        final List<BankAccountIndexDTO> bankAccountIndexList = BankAccountTransformer.toBankAccountIndexDTO(groupBankAccount.getBankAccounts());
        return new GroupBankAcccountIndexDTO(groupIndexDTO, bankAccountIndexList);
    }

    public static List<GroupBankAcccountIndexDTO> toGroupBankAcccountIndexDTO(final List<GroupBankAccount> groupBankAccounts) {
        return groupBankAccounts.stream().map(GroupBankAccountTransformer::toGroupBankAcccountIndexDTO).toList();
    }
    
}
