package fr.bankwiz.server.domain.api;

import java.util.List;
import java.util.UUID;

import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.input.BankAccountCreationInput;
import fr.bankwiz.server.domain.model.input.BankAccountUpdateInput;
import fr.bankwiz.server.domain.model.other.GroupBankAccount;

public interface BankAccountApi {
    BankAccount createBankAccount(BankAccountCreationInput bankAccountCreationInput);

    BankAccount updateBankAccount(UUID bankAccountId, BankAccountUpdateInput bankAccountUpdateInput);

    List<GroupBankAccount> getAllBankAccount();

    void deleteBankAccount(UUID bankAccountId);
}
