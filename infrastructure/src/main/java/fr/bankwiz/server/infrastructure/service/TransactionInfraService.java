package fr.bankwiz.server.infrastructure.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.bankwiz.server.domain.api.TransactionApi;
import fr.bankwiz.server.domain.model.data.Transaction;
import fr.bankwiz.server.domain.model.input.TransactionCreationInput;

@Service
public class TransactionInfraService implements TransactionApi {

    TransactionApi transactionApi;

    public TransactionInfraService(TransactionApi transactionApi) {
        this.transactionApi = transactionApi;
    }

    @Transactional
    public Transaction createTransaction(TransactionCreationInput transactionCreationInput) {
        return this.transactionApi.createTransaction(transactionCreationInput);
    }
}
