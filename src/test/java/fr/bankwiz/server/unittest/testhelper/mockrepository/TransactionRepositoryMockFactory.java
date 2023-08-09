package fr.bankwiz.server.unittest.testhelper.mockrepository;

import java.util.List;

import org.mockito.Mockito;

import fr.bankwiz.server.model.BankAccount;
import fr.bankwiz.server.model.Transaction;
import fr.bankwiz.server.repository.TransactionRepository;

public class TransactionRepositoryMockFactory
        extends AbstractRepositoryMockFactory<Transaction, TransactionRepository, Integer> {
    public TransactionRepositoryMockFactory() {
        super(TransactionRepository.class);
    }

    public TransactionRepositoryMockFactory mockFindAllByBankAccount(
            BankAccount bankAccount, List<Transaction> transactions) {
        Mockito.when(repository.findAllByBankAccount(bankAccount)).thenReturn(transactions);
        return this;
    }

    public TransactionRepositoryMockFactory mockFindAllByBankAccountIn(
            List<BankAccount> bankAccounts, List<Transaction> transactions) {
        Mockito.when(repository.findAllByBankAccountIn(bankAccounts)).thenReturn(transactions);
        return this;
    }
}
