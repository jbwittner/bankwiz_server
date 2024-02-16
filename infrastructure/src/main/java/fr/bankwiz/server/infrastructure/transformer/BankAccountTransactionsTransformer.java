package fr.bankwiz.server.infrastructure.transformer;

import fr.bankwiz.openapi.model.BankAccountIndexDTO;
import fr.bankwiz.openapi.model.BankAccountTransactionsDTO;
import fr.bankwiz.server.domain.model.other.BankAccountTransactionsDomain;

public class BankAccountTransactionsTransformer {

    private BankAccountTransactionsTransformer() {}

    public static BankAccountTransactionsDTO toBankAccountTransactionDTO(
            BankAccountTransactionsDomain bankAccountTransactions) {
        final BankAccountIndexDTO bankAccountIndexDTO =
                BankAccountTransformer.toBankAccountIndexDTO(bankAccountTransactions.getBankAccount());
        final var transactionDTOs =
                TransactionTransformer.toTransactionIndexDTO(bankAccountTransactions.getTransactions());
        return new BankAccountTransactionsDTO(bankAccountIndexDTO, transactionDTOs);
    }
}
