package fr.bankwiz.server.domain.api;

import java.util.List;
import java.util.UUID;

import fr.bankwiz.server.domain.model.data.BankAccountDomain;
import fr.bankwiz.server.domain.model.input.BankAccountCreationInputDomain;
import fr.bankwiz.server.domain.model.input.BankAccountUpdateInputDomain;
import fr.bankwiz.server.domain.model.other.GroupBankAccountDomain;

public interface BankAccountApi {
    BankAccountDomain createBankAccount(BankAccountCreationInputDomain bankAccountCreationInput);

    BankAccountDomain updateBankAccount(UUID bankAccountId, BankAccountUpdateInputDomain bankAccountUpdateInput);

    List<GroupBankAccountDomain> getAllBankAccount();

    void deleteBankAccount(UUID bankAccountId);
}
