package fr.bankwiz.server.domain.service.transactionservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.exception.UserNoReadRightException;
import fr.bankwiz.server.domain.model.data.BankAccountDomain;
import fr.bankwiz.server.domain.model.data.Transaction;
import fr.bankwiz.server.domain.model.other.BankAccountTransactions;
import fr.bankwiz.server.domain.service.TransactionDomainService;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;

class GetAllTransactionOfBankAccountTest extends DomainUnitTestBase {

    private TransactionDomainService transactionDomainService;

    @Override
    protected void initDataBeforeEach() {
        this.transactionDomainService = new TransactionDomainService(
                this.mockTransactionSpi.getMock(),
                this.mockBankAccountSpi.getMock(),
                this.mockCheckRightTool.getMock());
    }

    @Test
    void getAll() {
        final BankAccountDomain bankAccount = this.factory.getBankAccount();
        final List<Transaction> transactions = new ArrayList<>();
        transactions.add(this.factory.getTransaction(bankAccount));
        transactions.add(this.factory.getTransaction(bankAccount));
        transactions.add(this.factory.getTransaction(bankAccount));
        transactions.add(this.factory.getTransaction(bankAccount));

        this.mockBankAccountSpi.mockFindById(bankAccount.getId(), Optional.of(bankAccount));
        this.mockCheckRightTool.mockCheckCurrentUserCanWrite(bankAccount.getGroup(), true);
        this.mockTransactionSpi.mockFindByBankAccount(bankAccount, transactions);

        final BankAccountTransactions bankAccountTransactions =
                this.transactionDomainService.getAllTransactionOfBankAccount(bankAccount.getId());

        Assertions.assertAll(
                () -> Assertions.assertEquals(bankAccount, bankAccountTransactions.getBankAccount()),
                () -> Assertions.assertEquals(
                        transactions.size(),
                        bankAccountTransactions.getTransactions().size()),
                () -> {
                    bankAccountTransactions.getTransactions().forEach(transactionFinded -> {
                        Transaction transactionInput = transactions.stream()
                                .filter(transaction -> transaction.getId().equals(transactionFinded.getId()))
                                .findAny()
                                .orElseThrow();
                        Assertions.assertEquals(transactionInput, transactionFinded);
                    });
                });
    }

    @Test
    void userCantRead() {
        final BankAccountDomain bankAccount = this.factory.getBankAccount();
        final UUID bankAccountId = bankAccount.getId();
        this.mockBankAccountSpi.mockFindById(bankAccount.getId(), Optional.of(bankAccount));
        this.mockCheckRightTool.mockCheckCurrentUserCanRead(bankAccount.getGroup(), false);

        Assertions.assertThrows(
                UserNoReadRightException.class,
                () -> this.transactionDomainService.getAllTransactionOfBankAccount(bankAccountId));
    }
}
