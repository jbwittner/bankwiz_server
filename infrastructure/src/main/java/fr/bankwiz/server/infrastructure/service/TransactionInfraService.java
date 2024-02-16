package fr.bankwiz.server.infrastructure.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.bankwiz.server.domain.api.TransactionApi;
import fr.bankwiz.server.domain.model.data.TransactionDomain;
import fr.bankwiz.server.domain.model.input.TransactionCreationInput;
import fr.bankwiz.server.domain.model.input.UpdateTransactionInput;
import fr.bankwiz.server.domain.model.other.BankAccountTransactions;

@Service
public class TransactionInfraService implements TransactionApi {

    TransactionApi transactionApi;

    public TransactionInfraService(TransactionApi transactionApi) {
        this.transactionApi = transactionApi;
    }

    @Transactional
    public TransactionDomain createTransaction(TransactionCreationInput transactionCreationInput) {
        return this.transactionApi.createTransaction(transactionCreationInput);
    }

    @Transactional(readOnly = true)
    public BankAccountTransactions getAllTransactionOfBankAccount(UUID bankaccountId) {
        return this.transactionApi.getAllTransactionOfBankAccount(bankaccountId);
    }

    @Transactional
    public TransactionDomain updateTransaction(UUID bankaccountId, UpdateTransactionInput updateTransactionInput) {
        return this.transactionApi.updateTransaction(bankaccountId, updateTransactionInput);
    }

    @Transactional
    public void deleteTransaction(UUID bankaccountId) {
        this.transactionApi.deleteTransaction(bankaccountId);
    }
}
