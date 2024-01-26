package fr.bankwiz.server.infrastructure.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import fr.bankwiz.openapi.api.TransactionsApi;
import fr.bankwiz.openapi.model.CreateTransactionRequest;
import fr.bankwiz.openapi.model.TransactionDTO;
import fr.bankwiz.server.domain.model.data.Transaction;
import fr.bankwiz.server.domain.model.input.TransactionCreationInput;
import fr.bankwiz.server.infrastructure.service.TransactionInfraService;
import fr.bankwiz.server.infrastructure.transformer.TransactionTransformer;

@Controller
public class TransactionController implements TransactionsApi {

    private TransactionInfraService transactionInfraService;

    public TransactionController(TransactionInfraService transactionInfraService) {
        this.transactionInfraService = transactionInfraService;
    }

    @Override
    public ResponseEntity<TransactionDTO> createTransaction(CreateTransactionRequest createTransactionRequest) {
        final TransactionCreationInput transactionCreationInput = TransactionCreationInput.builder()
                .bankAccountId(createTransactionRequest.getBankAccountId())
                .decimalAmount(createTransactionRequest.getDecimalAmount())
                .comment(createTransactionRequest.getComment())
                .build();
        final Transaction transaction = this.transactionInfraService.createTransaction(transactionCreationInput);
        final TransactionDTO transactionDTO = TransactionTransformer.toTransactionDTO(transaction);
        return new ResponseEntity<>(transactionDTO, HttpStatus.CREATED);
    }
}
