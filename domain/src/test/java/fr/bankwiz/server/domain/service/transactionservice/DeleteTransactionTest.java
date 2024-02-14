package fr.bankwiz.server.domain.service.transactionservice;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.exception.TransactionNotExistException;
import fr.bankwiz.server.domain.exception.UserNoWriteRightException;
import fr.bankwiz.server.domain.model.data.Transaction;
import fr.bankwiz.server.domain.service.TransactionDomainService;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;

class DeleteTransactionTest extends DomainUnitTestBase {

    private TransactionDomainService transactionDomainService;

    @Override
    protected void initDataBeforeEach() {
        this.transactionDomainService = new TransactionDomainService(
                this.mockTransactionSpi.getMock(),
                this.mockBankAccountSpi.getMock(),
                this.mockCheckRightTool.getMock());
    }

    @Test
    void ok() {
        final Transaction transaction = this.factory.getTransaction();
        final UUID transactionId = transaction.getId();
        this.mockTransactionSpi.mockFindById(transactionId, Optional.of(transaction));

        this.mockCheckRightTool.mockCheckCurrentUserCanWrite(
                transaction.getBankAccount().getGroup(), true);

        this.transactionDomainService.deleteTransaction(transactionId);

        this.mockTransactionSpi.verifyDeleteById(transactionId);
    }

    @Test
    void noWriteRight() {
        final Transaction transaction = this.factory.getTransaction();
        final UUID transactionId = transaction.getId();
        this.mockTransactionSpi.mockFindById(transactionId, Optional.of(transaction));

        this.mockCheckRightTool.mockCheckCurrentUserCanWrite(
                transaction.getBankAccount().getGroup(), false);

        Assertions.assertThrows(
                UserNoWriteRightException.class, () -> this.transactionDomainService.deleteTransaction(transactionId));
    }

    @Test
    void TransactionNotExist() {
        final UUID transactionId = UUID.randomUUID();
        this.mockTransactionSpi.mockFindById(transactionId, Optional.empty());

        Assertions.assertThrows(
                TransactionNotExistException.class,
                () -> this.transactionDomainService.deleteTransaction(transactionId));
    }
}
