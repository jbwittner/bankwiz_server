package fr.bankwiz.server.service;

import fr.bankwiz.openapi.model.BankAccountCreationRequest;
import fr.bankwiz.openapi.model.BankAccountDTO;
import fr.bankwiz.openapi.model.BankAccountGroupDTO;
import fr.bankwiz.openapi.model.BankAccountUpdateRequest;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BankingAccountService {

    public BankAccountDTO addAccount(BankAccountCreationRequest bankAccountCreationRequest) {
        return null;
    }

    public void deleteAccount(Integer bankAccountId) {
    }

    public BankAccountDTO getAccount(Integer bankAccountId) {
        return null;
    }

    public List<BankAccountGroupDTO> getBankAccounts() {
        return null;
    }

    public BankAccountDTO updateAccount(Integer bankAccountId,BankAccountUpdateRequest bankAccountUpdateRequest) {
        return null;
    }

}
