package fr.bankwiz.server.infrastructure.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import fr.bankwiz.openapi.api.BankaccountApi;
import fr.bankwiz.openapi.model.BankAccountCreationRequest;
import fr.bankwiz.openapi.model.BankAccountIndexDTO;
import fr.bankwiz.openapi.model.GroupBankAcccountIndexDTO;
import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.input.BankAccountCreationInput;
import fr.bankwiz.server.domain.model.other.GroupBankAccount;
import fr.bankwiz.server.infrastructure.service.BankAccountInfraService;
import fr.bankwiz.server.infrastructure.transformer.BankAccountTransformer;
import fr.bankwiz.server.infrastructure.transformer.GroupBankAccountTransformer;

@Controller
public class BankAccountController implements BankaccountApi {

    private BankAccountInfraService bankAccountInfraService;

    public BankAccountController(BankAccountInfraService bankAccountInfraService) {
        this.bankAccountInfraService = bankAccountInfraService;
    }

    @Override
    public ResponseEntity<BankAccountIndexDTO> createBankAccount(
            BankAccountCreationRequest bankAccountCreationRequest) {
        BankAccountCreationInput bankAccountCreationInput = BankAccountCreationInput.builder()
                .bankAccountName(bankAccountCreationRequest.getBankAccountName())
                .decimalBaseAmount(bankAccountCreationRequest.getDecimalBaseAmount())
                .groupId(bankAccountCreationRequest.getGroupId())
                .build();
        BankAccount bankAccount = this.bankAccountInfraService.createBankAccount(bankAccountCreationInput);
        BankAccountIndexDTO bankAccountIndexDTO = BankAccountTransformer.toBankAccountIndexDTO(bankAccount);
        return new ResponseEntity<>(bankAccountIndexDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<GroupBankAcccountIndexDTO>> getAllBankAccount() {
        final List<GroupBankAccount> groupBankAccounts = this.bankAccountInfraService.getAllBankAccount();
        final List<GroupBankAcccountIndexDTO> groupBankAcccountIndexDTOs =
                GroupBankAccountTransformer.toGroupBankAcccountIndexDTO(groupBankAccounts);
        return new ResponseEntity<>(groupBankAcccountIndexDTOs, HttpStatus.OK);
    }
}
