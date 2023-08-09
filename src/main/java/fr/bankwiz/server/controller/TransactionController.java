package fr.bankwiz.server.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import fr.bankwiz.openapi.api.TransactionApi;
import fr.bankwiz.openapi.model.TransactionCreationRequest;
import fr.bankwiz.openapi.model.TransactionDTO;
import fr.bankwiz.openapi.model.TransactionUpdateRequest;
import fr.bankwiz.server.service.TransactionService;

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
    public ResponseEntity<Void> deleteTransaction(Integer transactionId) {
        transactionService.deleteTransaction(transactionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransactionDTO> getTransaction(Integer transactionId) {
        final TransactionDTO result = transactionService.getTransaction(transactionId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransactionDTO> updateTransaction(
            Integer transactionId, TransactionUpdateRequest transactionUpdateRequest) {
        final TransactionDTO result = transactionService.updateTransaction(transactionId, transactionUpdateRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TransactionDTO>> getTransactionsByBankAccount(Integer bankAccountId) {
        final List<TransactionDTO> result = transactionService.getTransactionsByBankAccount(bankAccountId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TransactionDTO>> getTransactionsByGroup(Integer groupId) {
        final List<TransactionDTO> result = transactionService.getTransactionsByGroup(groupId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
