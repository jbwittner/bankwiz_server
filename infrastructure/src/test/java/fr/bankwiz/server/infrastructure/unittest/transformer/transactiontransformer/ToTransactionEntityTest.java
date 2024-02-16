package fr.bankwiz.server.infrastructure.unittest.transformer.transactiontransformer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.TransactionDomain;
import fr.bankwiz.server.infrastructure.spi.database.entity.TransactionEntity;
import fr.bankwiz.server.infrastructure.transformer.BankAccountTransformer;
import fr.bankwiz.server.infrastructure.transformer.TransactionTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class ToTransactionEntityTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    void compare(TransactionDomain transaction, TransactionEntity transactionEntity) {
        Assertions.assertAll(
                () -> Assertions.assertEquals(transaction.getDecimalAmount(), transactionEntity.getDecimalAmount()),
                () -> Assertions.assertEquals(transaction.getComment(), transactionEntity.getComment()),
                () -> Assertions.assertEquals(transaction.getId(), transactionEntity.getId()),
                () -> Assertions.assertEquals(
                        transaction.getBankAccount(),
                        BankAccountTransformer.fromBankAccountEntity(transactionEntity.getBankAccountEntity())));
    }

    @Test
    void single() {
        final TransactionDomain transaction = this.factory.getTransaction();
        final TransactionEntity transactionEntity = TransactionTransformer.toTransactionEntity(transaction);
        compare(transaction, transactionEntity);
    }
}
