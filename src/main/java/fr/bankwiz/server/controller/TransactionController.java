package fr.bankwiz.server.controller;

import fr.bankwiz.openapi.api.TransactionApi;
import fr.bankwiz.openapi.model.TransactionCreationRequest;
import fr.bankwiz.openapi.model.TransactionDTO;
import fr.bankwiz.openapi.model.TransactionUpdateRequest;
import fr.bankwiz.server.service.TransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController implements TransactionApi {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public ResponseEntity<TransactionDTO> addTransaction(TransactionCreationRequest transactionCreationRequest) {
        final TransactionDTO result = transactionService.addTransaction(transactionCreationRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteTransaction(@Min(1L) Integer transactionId) {
        transactionService.deleteTransaction(transactionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransactionDTO> getTransaction(@Min(1L) Integer transactionId) {
        final TransactionDTO result = transactionService.getTransaction(transactionId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransactionDTO> updateTransaction(
            @Min(1L) Integer transactionId,
            @Valid TransactionUpdateRequest transactionUpdateRequest
    ) {
        final TransactionDTO result = transactionService.updateTransaction(transactionId, transactionUpdateRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TransactionDTO>> getTransactionsByBankAccount(
            @NotNull @Min(1L) @Valid Integer bankAccountId
    ) {
        final List<TransactionDTO> result = transactionService.getTransactionsByBankAccount(bankAccountId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TransactionDTO>> getTransactionsByGroup(
            @NotNull @Min(1L) @Valid Integer groupId
    ) {
        final List<TransactionDTO> result = transactionService.getTransactionsByGroup(groupId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}