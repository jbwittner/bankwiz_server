package fr.bankwiz.server.infrastructure.api.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import fr.bankwiz.openapi.api.TransactionApi;
import fr.bankwiz.openapi.model.BankAccountTransactionsDTO;
import fr.bankwiz.openapi.model.CreateTransactionRequest;
import fr.bankwiz.openapi.model.TransactionDTO;
import fr.bankwiz.openapi.model.UpdateTransactionRequest;
import fr.bankwiz.server.domain.model.data.Transaction;
import fr.bankwiz.server.domain.model.input.TransactionCreationInput;
import fr.bankwiz.server.domain.model.input.UpdateTransactionInput;
import fr.bankwiz.server.domain.model.other.BankAccountTransactions;
import fr.bankwiz.server.infrastructure.service.TransactionInfraService;
import fr.bankwiz.server.infrastructure.transformer.BankAccountTransactionsTransformer;
import fr.bankwiz.server.infrastructure.transformer.TransactionTransformer;

@Controller
public class TransactionController implements TransactionApi {

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

    @Override
    public ResponseEntity<BankAccountTransactionsDTO> getAllTransactionOfBankAccount(UUID bankaccountId) {
        final BankAccountTransactions bankAccountTransactions =
                this.transactionInfraService.getAllTransactionOfBankAccount(bankaccountId);
        final BankAccountTransactionsDTO bankAccountTransactionsDTO =
                BankAccountTransactionsTransformer.toBankAccountTransactionDTO(bankAccountTransactions);
        return new ResponseEntity<>(bankAccountTransactionsDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransactionDTO> updateTransaction(
            UUID transactionId, UpdateTransactionRequest updateTransactionRequest) {
        final UpdateTransactionInput updateTransactionInput = UpdateTransactionInput.builder()
                .decimalAmount(updateTransactionRequest.getDecimalAmount())
                .comment(updateTransactionRequest.getComment())
                .build();
        final Transaction transaction =
                this.transactionInfraService.updateTransaction(transactionId, updateTransactionInput);
        final TransactionDTO transactionDTO = TransactionTransformer.toTransactionDTO(transaction);
        return new ResponseEntity<>(transactionDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteTransaction(UUID transactionId) {
        this.transactionInfraService.deleteTransaction(transactionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
