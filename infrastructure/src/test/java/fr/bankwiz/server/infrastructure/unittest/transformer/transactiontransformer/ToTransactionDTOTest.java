package fr.bankwiz.server.infrastructure.unittest.transformer.transactiontransformer;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.TransactionDTO;
import fr.bankwiz.server.domain.model.data.TransactionDomain;
import fr.bankwiz.server.infrastructure.transformer.TransactionTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class ToTransactionDTOTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    void compare(TransactionDomain transaction, TransactionDTO transactionDTO) {
        Assertions.assertAll(
                () -> Assertions.assertEquals(transaction.getDecimalAmount(), transactionDTO.getDecimalAmount()),
                () -> Assertions.assertEquals(transaction.getComment(), transactionDTO.getComment()),
                () -> Assertions.assertEquals(transaction.getId(), transactionDTO.getTransactionId()),
                () -> Assertions.assertEquals(transaction.getBankAccount().getId(), transactionDTO.getBankAccountId()));
    }

    @Test
    void single() {
        final TransactionDomain transaction = this.factory.getTransaction();
        final TransactionDTO transactionDTO = TransactionTransformer.toTransactionDTO(transaction);
        compare(transaction, transactionDTO);
    }

    @Test
    void list() {
        final List<TransactionDomain> transactions = new ArrayList<>();
        transactions.add(this.factory.getTransaction());
        transactions.add(this.factory.getTransaction());
        transactions.add(this.factory.getTransaction());
        transactions.add(this.factory.getTransaction());
        final List<TransactionDTO> transactionDTOs = TransactionTransformer.toTransactionDTO(transactions);

        Assertions.assertAll(() -> Assertions.assertEquals(transactions.size(), transactionDTOs.size()), () -> {
            transactionDTOs.forEach(transactionDTO -> {
                TransactionDomain transaction = transactions.stream()
                        .filter(transactionToFind ->
                                transactionToFind.getId().equals(transactionDTO.getTransactionId()))
                        .findFirst()
                        .orElseThrow();
                compare(transaction, transactionDTO);
            });
        });
    }
}
