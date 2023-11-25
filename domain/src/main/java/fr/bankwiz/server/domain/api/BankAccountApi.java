package fr.bankwiz.server.domain.api;

import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.input.BankAccountCreationInput;

public interface BankAccountApi {
    BankAccount createBankAccount(BankAccountCreationInput bankAccountCreationInput);
}
