package fr.bankwiz.server.domain.service;

import java.util.List;
import java.util.UUID;

import fr.bankwiz.server.domain.api.TransactionApi;
import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Transaction;
import fr.bankwiz.server.domain.model.input.TransactionCreationInput;
import fr.bankwiz.server.domain.model.other.BankAccountTransactions;
import fr.bankwiz.server.domain.spi.BankAccountSpi;
import fr.bankwiz.server.domain.spi.TransactionSpi;
import fr.bankwiz.server.domain.tools.CheckRightTools;

public class TransactionDomainService implements TransactionApi {

    private final TransactionSpi transactionSpi;
    private final BankAccountSpi bankAccountSpi;
    private final CheckRightTools checkRightTools;

    public TransactionDomainService(
            TransactionSpi transactionSpi, BankAccountSpi bankAccountSpi, CheckRightTools checkRightTools) {
        this.transactionSpi = transactionSpi;
        this.bankAccountSpi = bankAccountSpi;
        this.checkRightTools = checkRightTools;
    }

    @Override
    public Transaction createTransaction(TransactionCreationInput transactionCreationInput) {

        final BankAccount bankAccount = this.bankAccountSpi.getById(transactionCreationInput.getBankAccountId());

        this.checkRightTools.checkCurrentUserCanWrite(bankAccount.getGroup());

        final Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID())
                .bankAccount(bankAccount)
                .comment(transactionCreationInput.getComment())
                .decimalAmount(transactionCreationInput.getDecimalAmount())
                .build();

        return this.transactionSpi.save(transaction);
    }

    @Override
    public BankAccountTransactions getAllTransactionOfBankAccount(UUID bankaccountId) {
        final BankAccount bankAccount = this.bankAccountSpi.getById(bankaccountId);
        this.checkRightTools.checkCurrentUserCanRead(bankAccount.getGroup());
        final var transactions = this.transactionSpi.findByBankAccount(bankAccount);
        return BankAccountTransactions.builder().bankAccount(bankAccount).transactions(transactions).build();
    }
}
