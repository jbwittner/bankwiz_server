package fr.bankwiz.server.infrastructure.unittest.transformer.transactiontransformer;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.TransactionIndexDTO;
import fr.bankwiz.server.domain.model.data.Transaction;
import fr.bankwiz.server.infrastructure.transformer.TransactionTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class ToTransactionIndexDTOTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    void compare(Transaction transaction, TransactionIndexDTO transactionIndexDTO) {
        Assertions.assertAll(
                () -> Assertions.assertEquals(transaction.getDecimalAmount(), transactionIndexDTO.getDecimalAmount()),
                () -> Assertions.assertEquals(transaction.getComment(), transactionIndexDTO.getComment()),
                () -> Assertions.assertEquals(transaction.getId(), transactionIndexDTO.getTransactionId()));
    }

    @Test
    void single() {
        final Transaction transaction = this.factory.getTransaction();
        final TransactionIndexDTO transactionIndexDTO = TransactionTransformer.toTransactionIndexDTO(transaction);
        compare(transaction, transactionIndexDTO);
    }

    @Test
    void list() {
        final List<Transaction> transactions = new ArrayList<>();
        transactions.add(this.factory.getTransaction());
        transactions.add(this.factory.getTransaction());
        transactions.add(this.factory.getTransaction());
        transactions.add(this.factory.getTransaction());
        final List<TransactionIndexDTO> transactionIndexDTOs =
                TransactionTransformer.toTransactionIndexDTO(transactions);

        Assertions.assertAll(() -> Assertions.assertEquals(transactions.size(), transactionIndexDTOs.size()), () -> {
            transactionIndexDTOs.forEach(transactionIndexDTO -> {
                Transaction transaction = transactions.stream()
                        .filter(transactionToFind ->
                                transactionToFind.getId().equals(transactionIndexDTO.getTransactionId()))
                        .findFirst()
                        .orElseThrow();
                compare(transaction, transactionIndexDTO);
            });
        });
    }
}
