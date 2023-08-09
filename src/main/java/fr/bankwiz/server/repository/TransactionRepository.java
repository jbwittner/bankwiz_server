package fr.bankwiz.server.repository;

import fr.bankwiz.server.model.BankAccount;
import fr.bankwiz.server.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllByBankAccount(BankAccount bankAccount);
    List<Transaction> findAllByBankAccountIn(List<BankAccount> bankAccounts);
}
