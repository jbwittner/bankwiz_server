package fr.bankwiz.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.bankwiz.server.model.BankAccount;
import fr.bankwiz.server.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllByBankAccount(BankAccount bankAccount);

    List<Transaction> findAllByBankAccountIn(List<BankAccount> bankAccounts);
}
