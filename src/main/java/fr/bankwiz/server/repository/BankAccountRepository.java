package fr.bankwiz.server.repository;

import fr.bankwiz.server.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {}
