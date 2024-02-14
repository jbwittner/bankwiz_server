package fr.bankwiz.server.domain.service;

import java.util.UUID;

import fr.bankwiz.server.domain.api.TransactionApi;
import fr.bankwiz.server.domain.exception.TransactionNotExistException;
import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Transaction;
import fr.bankwiz.server.domain.model.input.TransactionCreationInput;
import fr.bankwiz.server.domain.model.input.UpdateTransactionInput;
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
        return BankAccountTransactions.builder()
                .bankAccount(bankAccount)
                .transactions(transactions)
                .build();
    }

    @Override
    public Transaction updateTransaction(UUID bankaccountId, UpdateTransactionInput updateTransactionInput) {

        final Transaction transaction = this.transactionSpi
                .findById(bankaccountId)
                .orElseThrow(() -> new TransactionNotExistException(bankaccountId));

        this.checkRightTools.checkCurrentUserCanWrite(
                transaction.getBankAccount().getGroup());

        var transactionBuilder = Transaction.builder();

        if (updateTransactionInput.getComment() != null) {
            transactionBuilder.comment(updateTransactionInput.getComment());
        } else {
            transactionBuilder.comment(transaction.getComment());
        }

        if (updateTransactionInput.getDecimalAmount() != null) {
            transactionBuilder.decimalAmount(updateTransactionInput.getDecimalAmount());
        } else {
            transactionBuilder.decimalAmount(transaction.getDecimalAmount());
        }

        transactionBuilder.id(transaction.getId()).bankAccount(transaction.getBankAccount());

        final Transaction transactionUpdated = transactionBuilder.build();

        return this.transactionSpi.save(transactionUpdated);
    }

    @Override
    public void deleteTransaction(UUID bankaccountId) {
        final Transaction transaction = this.transactionSpi
                .findById(bankaccountId)
                .orElseThrow(() -> new TransactionNotExistException(bankaccountId));
        this.checkRightTools.checkCurrentUserCanWrite(
                transaction.getBankAccount().getGroup());
        this.transactionSpi.deleteById(bankaccountId);
    }
}
