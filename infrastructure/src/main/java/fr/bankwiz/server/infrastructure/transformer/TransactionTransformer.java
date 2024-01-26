package fr.bankwiz.server.infrastructure.transformer;

import fr.bankwiz.openapi.model.TransactionDTO;
import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Transaction;
import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.TransactionEntity;

public final class TransactionTransformer {

    private TransactionTransformer() {}

    public static TransactionDTO toTransactionDTO(final Transaction transaction) {
        final TransactionDTO transactionDTO = new TransactionDTO(
                transaction.getId(), transaction.getBankAccount().getId(), transaction.getDecimalAmount());
        transactionDTO.setComment(transaction.getComment());
        return transactionDTO;
    }

    public static Transaction fromTransactionEntity(final TransactionEntity transactionEntity) {
        final BankAccount bankAccount =
                BankAccountTransformer.fromBankAccountEntity(transactionEntity.getBankAccountEntity());
        return Transaction.builder()
                .id(transactionEntity.getId())
                .decimalAmount(transactionEntity.getDecimalAmount())
                .bankAccount(bankAccount)
                .comment(transactionEntity.getComment())
                .build();
    }

    public static TransactionEntity toTransactionEntity(final Transaction transaction) {
        BankAccountEntity bankAccountEntity = BankAccountTransformer.toBankAccountEntity(transaction.getBankAccount());
        return TransactionEntity.builder()
                .id(transaction.getId())
                .decimalAmount(transaction.getDecimalAmount())
                .bankAccountEntity(bankAccountEntity)
                .comment(transaction.getComment())
                .build();
    }
}
