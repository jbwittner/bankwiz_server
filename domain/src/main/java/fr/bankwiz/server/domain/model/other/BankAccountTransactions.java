package fr.bankwiz.server.domain.model.other;

import java.util.List;

import fr.bankwiz.server.domain.model.data.BankAccountDomain;
import fr.bankwiz.server.domain.model.data.Transaction;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BankAccountTransactions {
    private BankAccountDomain bankAccount;
    private List<Transaction> transactions;
}
