package fr.bankwiz.server.infrastructure.api.controller;

import org.springframework.http.ResponseEntity;

import fr.bankwiz.openapi.api.TransactionsApi;
import fr.bankwiz.openapi.model.CreateTransactionRequest;
import fr.bankwiz.openapi.model.TransactionDTO;

public class TransactionController implements TransactionsApi {

    @Override
    public ResponseEntity<TransactionDTO> createTransaction(CreateTransactionRequest createTransactionRequest) {
        return TransactionsApi.super.createTransaction(createTransactionRequest);
    }
}
