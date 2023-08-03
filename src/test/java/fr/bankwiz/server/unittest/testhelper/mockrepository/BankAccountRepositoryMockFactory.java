package fr.bankwiz.server.unittest.testhelper.mockrepository;

import fr.bankwiz.server.model.BankAccount;
import fr.bankwiz.server.repository.BankAccountRepository;

public class BankAccountRepositoryMockFactory
        extends AbstractRepositoryMockFactory<BankAccount, BankAccountRepository, Integer> {
    public BankAccountRepositoryMockFactory() {
        super(BankAccountRepository.class);
    }
}
