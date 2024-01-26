package fr.bankwiz.server.domain.service.transactionservice;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.exception.UserNoWriteRightException;
import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Transaction;
import fr.bankwiz.server.domain.model.input.TransactionCreationInput;
import fr.bankwiz.server.domain.service.TransactionDomainService;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;

class CreateTransactionTest extends DomainUnitTestBase {

    private TransactionDomainService transactionDomainService;

    @Override
    protected void initDataBeforeEach() {
        this.transactionDomainService = new TransactionDomainService(
                this.mockTransactionSpi.getMock(),
                this.mockBankAccountSpi.getMock(),
                this.mockCheckRightTool.getMock());
    }

    @Test
    void creationOk() {
        final BankAccount bankAccount = this.factory.getBankAccount();
        final TransactionCreationInput transactionCreationInput = TransactionCreationInput.builder()
                .bankAccountId(bankAccount.getId())
                .comment(this.faker.yoda().quote())
                .decimalAmount(this.faker.random().nextInt(Integer.MAX_VALUE))
                .build();

        this.mockBankAccountSpi.mockFindById(bankAccount.getId(), Optional.of(bankAccount));
        this.mockCheckRightTool.mockCheckCurrentUserCanWrite(bankAccount.getGroup(), true);
        this.mockTransactionSpi.mockSave();

        final Transaction transaction = this.transactionDomainService.createTransaction(transactionCreationInput);

        Assertions.assertAll(
                () -> Assertions.assertEquals(transactionCreationInput.getComment(), transaction.getComment()),
                () -> Assertions.assertEquals(
                        transactionCreationInput.getBankAccountId(),
                        transaction.getBankAccount().getId()),
                () -> Assertions.assertEquals(
                        transactionCreationInput.getDecimalAmount(), transaction.getDecimalAmount()));
    }

    @Test
    void userCantWrite() {
        final BankAccount bankAccount = this.factory.getBankAccount();
        final TransactionCreationInput transactionCreationInput = TransactionCreationInput.builder()
                .bankAccountId(bankAccount.getId())
                .comment(this.faker.yoda().quote())
                .decimalAmount(this.faker.random().nextInt(Integer.MAX_VALUE))
                .build();

        this.mockBankAccountSpi.mockFindById(bankAccount.getId(), Optional.of(bankAccount));
        this.mockCheckRightTool.mockCheckCurrentUserCanWrite(bankAccount.getGroup(), false);

        Assertions.assertThrows(
                UserNoWriteRightException.class,
                () -> this.transactionDomainService.createTransaction(transactionCreationInput));
    }
}
