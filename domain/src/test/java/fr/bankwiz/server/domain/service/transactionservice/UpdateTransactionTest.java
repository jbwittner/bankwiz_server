package fr.bankwiz.server.domain.service.transactionservice;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.exception.TransactionNotExistException;
import fr.bankwiz.server.domain.exception.UserNoWriteRightException;
import fr.bankwiz.server.domain.model.data.TransactionDomain;
import fr.bankwiz.server.domain.model.input.UpdateTransactionInput;
import fr.bankwiz.server.domain.service.TransactionDomainService;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;

class UpdateTransactionTest extends DomainUnitTestBase {

    private TransactionDomainService transactionDomainService;

    @Override
    protected void initDataBeforeEach() {
        this.transactionDomainService = new TransactionDomainService(
                this.mockTransactionSpi.getMock(),
                this.mockBankAccountSpi.getMock(),
                this.mockCheckRightTool.getMock());
    }

    @Test
    void updateAll() {
        final TransactionDomain transaction = this.factory.getTransaction();
        final UUID transactionId = transaction.getId();
        this.mockTransactionSpi
                .mockFindById(transactionId, Optional.of(transaction))
                .mockSave();

        this.mockCheckRightTool.mockCheckCurrentUserCanWrite(
                transaction.getBankAccount().getGroup(), true);

        final UpdateTransactionInput updateTransactionInput = UpdateTransactionInput.builder()
                .comment(this.faker.superhero().name())
                .decimalAmount(this.faker.random().nextInt(Integer.MAX_VALUE))
                .build();

        final TransactionDomain transactionUpdated =
                this.transactionDomainService.updateTransaction(transactionId, updateTransactionInput);

        this.mockTransactionSpi.verifySave(transactionUpdated);

        Assertions.assertAll(
                () -> Assertions.assertEquals(transaction.getId(), transactionUpdated.getId()),
                () -> Assertions.assertNotEquals(transaction.getComment(), transactionUpdated.getComment()),
                () -> Assertions.assertNotEquals(transaction.getDecimalAmount(), transactionUpdated.getDecimalAmount()),
                () -> Assertions.assertEquals(updateTransactionInput.getComment(), transactionUpdated.getComment()),
                () -> Assertions.assertEquals(
                        updateTransactionInput.getDecimalAmount(), transactionUpdated.getDecimalAmount()));
    }

    @Test
    void updateDecimalAmount() {
        final TransactionDomain transaction = this.factory.getTransaction();
        final UUID transactionId = transaction.getId();
        this.mockTransactionSpi
                .mockFindById(transactionId, Optional.of(transaction))
                .mockSave();

        this.mockCheckRightTool.mockCheckCurrentUserCanWrite(
                transaction.getBankAccount().getGroup(), true);

        final UpdateTransactionInput updateTransactionInput = UpdateTransactionInput.builder()
                .decimalAmount(this.faker.random().nextInt(Integer.MAX_VALUE))
                .build();

        final TransactionDomain transactionUpdated =
                this.transactionDomainService.updateTransaction(transactionId, updateTransactionInput);

        this.mockTransactionSpi.verifySave(transactionUpdated);

        Assertions.assertAll(
                () -> Assertions.assertEquals(transaction.getId(), transactionUpdated.getId()),
                () -> Assertions.assertEquals(transaction.getComment(), transactionUpdated.getComment()),
                () -> Assertions.assertNotEquals(transaction.getDecimalAmount(), transactionUpdated.getDecimalAmount()),
                () -> Assertions.assertEquals(
                        updateTransactionInput.getDecimalAmount(), transactionUpdated.getDecimalAmount()));
    }

    @Test
    void updateComment() {
        final TransactionDomain transaction = this.factory.getTransaction();
        final UUID transactionId = transaction.getId();
        this.mockTransactionSpi
                .mockFindById(transactionId, Optional.of(transaction))
                .mockSave();

        this.mockCheckRightTool.mockCheckCurrentUserCanWrite(
                transaction.getBankAccount().getGroup(), true);

        final UpdateTransactionInput updateTransactionInput = UpdateTransactionInput.builder()
                .comment(this.faker.rickAndMorty().quote())
                .build();

        final TransactionDomain transactionUpdated =
                this.transactionDomainService.updateTransaction(transactionId, updateTransactionInput);

        this.mockTransactionSpi.verifySave(transactionUpdated);

        Assertions.assertAll(
                () -> Assertions.assertEquals(transaction.getId(), transactionUpdated.getId()),
                () -> Assertions.assertNotEquals(transaction.getComment(), transactionUpdated.getComment()),
                () -> Assertions.assertEquals(transaction.getDecimalAmount(), transactionUpdated.getDecimalAmount()),
                () -> Assertions.assertEquals(updateTransactionInput.getComment(), transactionUpdated.getComment()));
    }

    @Test
    void noWriteRight() {
        final TransactionDomain transaction = this.factory.getTransaction();
        final UUID transactionId = transaction.getId();
        this.mockTransactionSpi.mockFindById(transactionId, Optional.of(transaction));

        this.mockCheckRightTool.mockCheckCurrentUserCanWrite(
                transaction.getBankAccount().getGroup(), false);

        Assertions.assertThrows(
                UserNoWriteRightException.class,
                () -> this.transactionDomainService.updateTransaction(transactionId, null));
    }

    @Test
    void TransactionNotExist() {
        final UUID transactionId = UUID.randomUUID();
        this.mockTransactionSpi.mockFindById(transactionId, Optional.empty());

        Assertions.assertThrows(
                TransactionNotExistException.class,
                () -> this.transactionDomainService.updateTransaction(transactionId, null));
    }
}
