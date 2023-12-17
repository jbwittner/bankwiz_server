package fr.bankwiz.server.infrastructure.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.bankwiz.server.domain.api.BankAccountApi;
import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.input.BankAccountCreationInput;
import fr.bankwiz.server.domain.model.input.BankAccountUpdateInput;
import fr.bankwiz.server.domain.model.other.GroupBankAccount;

@Service
public class BankAccountInfraService {

    private final BankAccountApi bankAccountApi;

    public BankAccountInfraService(BankAccountApi bankAccountApi) {
        this.bankAccountApi = bankAccountApi;
    }

    @Transactional
    public BankAccount createBankAccount(BankAccountCreationInput bankAccountCreationInput) {
        return this.bankAccountApi.createBankAccount(bankAccountCreationInput);
    }

    @Transactional(readOnly = true)
    public List<GroupBankAccount> getAllBankAccount() {
        return this.bankAccountApi.getAllBankAccount();
    }

    @Transactional
    public void deleteBankAccount(UUID uuid) {
        this.bankAccountApi.deleteBankAccount(uuid);
    }

    @Transactional
    public BankAccount updateBankAccount(UUID uuid, BankAccountUpdateInput bankAccountUpdateInput) {
        return this.bankAccountApi.updateBankAccount(uuid, bankAccountUpdateInput);
    }
}
