package fr.bankwiz.server.infrastructure.transformer;

import java.util.List;

import fr.bankwiz.openapi.model.TransactionDTO;
import fr.bankwiz.openapi.model.TransactionIndexDTO;
import fr.bankwiz.server.domain.model.data.BankAccountDomain;
import fr.bankwiz.server.domain.model.data.TransactionDomain;
import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.TransactionEntity;

public final class TransactionTransformer {

    private TransactionTransformer() {}

    public static TransactionDTO toTransactionDTO(final TransactionDomain transaction) {
        final TransactionDTO transactionDTO = new TransactionDTO(
                transaction.getId(), transaction.getBankAccount().getId(), transaction.getDecimalAmount());
        transactionDTO.setComment(transaction.getComment());
        return transactionDTO;
    }

    public static TransactionIndexDTO toTransactionIndexDTO(final TransactionDomain transaction) {
        final TransactionIndexDTO transactionIndexDTO =
                new TransactionIndexDTO(transaction.getId(), transaction.getDecimalAmount());
        transactionIndexDTO.setComment(transaction.getComment());
        return transactionIndexDTO;
    }

    public static TransactionDomain fromTransactionEntity(final TransactionEntity transactionEntity) {
        final BankAccountDomain bankAccount =
                BankAccountTransformer.fromBankAccountEntity(transactionEntity.getBankAccountEntity());
        return TransactionDomain.builder()
                .id(transactionEntity.getId())
                .decimalAmount(transactionEntity.getDecimalAmount())
                .bankAccount(bankAccount)
                .comment(transactionEntity.getComment())
                .build();
    }

    public static TransactionEntity toTransactionEntity(final TransactionDomain transaction) {
        BankAccountEntity bankAccountEntity = BankAccountTransformer.toBankAccountEntity(transaction.getBankAccount());
        return TransactionEntity.builder()
                .id(transaction.getId())
                .decimalAmount(transaction.getDecimalAmount())
                .bankAccountEntity(bankAccountEntity)
                .comment(transaction.getComment())
                .build();
    }

    public static List<TransactionDomain> fromTransactionEntity(final List<TransactionEntity> transactionEntities) {
        return transactionEntities.stream()
                .map(TransactionTransformer::fromTransactionEntity)
                .toList();
    }

    public static List<TransactionDTO> toTransactionDTO(final List<TransactionDomain> transactions) {
        return transactions.stream()
                .map(TransactionTransformer::toTransactionDTO)
                .toList();
    }

    public static List<TransactionIndexDTO> toTransactionIndexDTO(final List<TransactionDomain> transactions) {
        return transactions.stream()
                .map(TransactionTransformer::toTransactionIndexDTO)
                .toList();
    }
}
