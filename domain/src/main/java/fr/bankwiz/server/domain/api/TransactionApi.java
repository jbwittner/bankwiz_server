package fr.bankwiz.server.domain.api;

import java.util.UUID;

import fr.bankwiz.server.domain.model.data.TransactionDomain;
import fr.bankwiz.server.domain.model.input.TransactionCreationInputDomain;
import fr.bankwiz.server.domain.model.input.UpdateTransactionInputDomain;
import fr.bankwiz.server.domain.model.other.BankAccountTransactionsDomain;

public interface TransactionApi {
    TransactionDomain createTransaction(TransactionCreationInputDomain transactionCreationInput);

    BankAccountTransactionsDomain getAllTransactionOfBankAccount(UUID bankaccountId);

    TransactionDomain updateTransaction(UUID bankaccountId, UpdateTransactionInputDomain updateTransactionInput);

    void deleteTransaction(UUID bankaccountId);
}
