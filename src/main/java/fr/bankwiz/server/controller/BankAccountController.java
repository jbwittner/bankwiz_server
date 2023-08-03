package fr.bankwiz.server.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import fr.bankwiz.openapi.api.BankAccountApi;
import fr.bankwiz.openapi.model.BankAccountCreationRequest;
import fr.bankwiz.openapi.model.BankAccountDTO;
import fr.bankwiz.openapi.model.BankAccountGroupDTO;
import fr.bankwiz.openapi.model.BankAccountUpdateRequest;
import fr.bankwiz.server.service.BankAccountService;

@RestController
public class BankAccountController implements BankAccountApi {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Override
    public ResponseEntity<BankAccountDTO> addAccount(BankAccountCreationRequest bankAccountCreationRequest) {
        final BankAccountDTO result = bankAccountService.addAccount(bankAccountCreationRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteAccount(Integer bankAccountId) {
        bankAccountService.deleteAccount(bankAccountId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BankAccountDTO> getAccount(Integer bankAccountId) {
        final BankAccountDTO result = bankAccountService.getAccount(bankAccountId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<BankAccountGroupDTO>> getBankAccounts() {
        final List<BankAccountGroupDTO> result = bankAccountService.getBankAccounts();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BankAccountDTO> updateAccount(
            Integer bankAccountId, BankAccountUpdateRequest bankAccountUpdateRequest) {
        final BankAccountDTO result = bankAccountService.updateAccount(bankAccountId, bankAccountUpdateRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
