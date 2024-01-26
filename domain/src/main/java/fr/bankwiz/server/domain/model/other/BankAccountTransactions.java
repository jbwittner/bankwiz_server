package fr.bankwiz.server.domain.model.other;

import java.util.List;

import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Transaction;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BankAccountTransactions {
    private BankAccount bankAccount;
    private List<Transaction> transactions;
}
