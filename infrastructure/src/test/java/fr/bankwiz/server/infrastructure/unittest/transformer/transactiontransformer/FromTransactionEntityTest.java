package fr.bankwiz.server.infrastructure.unittest.transformer.transactiontransformer;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.TransactionDomain;
import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.TransactionEntity;
import fr.bankwiz.server.infrastructure.transformer.BankAccountTransformer;
import fr.bankwiz.server.infrastructure.transformer.TransactionTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class FromTransactionEntityTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    void compare(TransactionEntity transactionEntity, TransactionDomain transaction) {
        Assertions.assertAll(
                () -> Assertions.assertEquals(transactionEntity.getDecimalAmount(), transaction.getDecimalAmount()),
                () -> Assertions.assertEquals(transactionEntity.getComment(), transaction.getComment()),
                () -> Assertions.assertEquals(transactionEntity.getId(), transaction.getId()),
                () -> Assertions.assertEquals(
                        transactionEntity.getBankAccountEntity(),
                        BankAccountTransformer.toBankAccountEntity(transaction.getBankAccount())));
    }

    @Test
    void single() {
        final TransactionEntity transactionEntity = this.factory.getTransactionEntity();
        final TransactionDomain transaction = TransactionTransformer.fromTransactionEntity(transactionEntity);
        compare(transactionEntity, transaction);
    }

    @Test
    void list() {
        final List<TransactionEntity> transactionEntities = new ArrayList<>();
        final BankAccountEntity bankAccountEntity = this.factory.getBankAccountEntity();
        transactionEntities.add(this.factory.getTransactionEntity(bankAccountEntity));
        transactionEntities.add(this.factory.getTransactionEntity(bankAccountEntity));
        transactionEntities.add(this.factory.getTransactionEntity(bankAccountEntity));
        transactionEntities.add(this.factory.getTransactionEntity(bankAccountEntity));
        final List<TransactionDomain> transactions = TransactionTransformer.fromTransactionEntity(transactionEntities);

        Assertions.assertAll(() -> Assertions.assertEquals(transactionEntities.size(), transactions.size()), () -> {
            transactions.forEach(transaction -> {
                TransactionEntity transactionEntity = transactionEntities.stream()
                        .filter(transactionToFind -> transactionToFind.getId().equals(transaction.getId()))
                        .findFirst()
                        .orElseThrow();
                compare(transactionEntity, transaction);
            });
        });
    }
}
