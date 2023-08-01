package fr.bankwiz.server.controller;

import fr.bankwiz.openapi.api.BankAccountApi;
import fr.bankwiz.openapi.model.BankAccountCreationRequest;
import fr.bankwiz.openapi.model.BankAccountDTO;
import fr.bankwiz.openapi.model.BankAccountGroupDTO;
import fr.bankwiz.openapi.model.BankAccountUpdateRequest;
import fr.bankwiz.server.service.BankingAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BankingAccountController implements BankAccountApi {

    private final BankingAccountService bankingAccountService;

    public BankingAccountController(BankingAccountService bankingAccountService){
        this.bankingAccountService = bankingAccountService;
    }

    @Override
    public ResponseEntity<BankAccountDTO> addAccount(BankAccountCreationRequest bankAccountCreationRequest) {
        final BankAccountDTO result = bankingAccountService.addAccount(bankAccountCreationRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteAccount(Integer bankAccountId) {
        bankingAccountService.deleteAccount(bankAccountId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BankAccountDTO> getAccount(Integer bankAccountId) {
        final BankAccountDTO result = bankingAccountService.getAccount(bankAccountId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<BankAccountGroupDTO>> getBankAccounts() {
        final List<BankAccountGroupDTO> result = bankingAccountService.getBankAccounts();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BankAccountDTO> updateAccount(Integer bankAccountId, BankAccountUpdateRequest bankAccountUpdateRequest) {
        final BankAccountDTO result = bankingAccountService.updateAccount(bankAccountId, bankAccountUpdateRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
