package fr.bankwiz.server.domain.api;

import java.util.UUID;

import fr.bankwiz.server.domain.model.data.TransactionDomain;
import fr.bankwiz.server.domain.model.input.TransactionCreationInput;
import fr.bankwiz.server.domain.model.input.UpdateTransactionInput;
import fr.bankwiz.server.domain.model.other.BankAccountTransactions;

public interface TransactionApi {
    TransactionDomain createTransaction(TransactionCreationInput transactionCreationInput);

    BankAccountTransactions getAllTransactionOfBankAccount(UUID bankaccountId);

    TransactionDomain updateTransaction(UUID bankaccountId, UpdateTransactionInput updateTransactionInput);

    void deleteTransaction(UUID bankaccountId);
}
