package fr.bankwiz.server.domain.api;

import java.util.List;

import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.input.BankAccountCreationInput;
import fr.bankwiz.server.domain.model.other.GroupBankAccount;

public interface BankAccountApi {
    BankAccount createBankAccount(BankAccountCreationInput bankAccountCreationInput);

    List<GroupBankAccount> getAllBankAccount();
}
