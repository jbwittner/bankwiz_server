package fr.bankwiz.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.bankwiz.server.model.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {}
