package fr.bankwiz.server.infrastructure.transformer;

import java.util.List;

import fr.bankwiz.openapi.model.BankAccountIndexDTO;
import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;

public class BankAccountTransformer {

    private BankAccountTransformer() {}

    public static BankAccountEntity toBankAccountEntity(final BankAccount bankAccount) {
        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(bankAccount.getGroup());
        return BankAccountEntity.builder()
                .baseAmountDecimal(bankAccount.getDecimalBaseAmount())
                .id(bankAccount.getId())
                .bankAccountName(bankAccount.getBankAccountName())
                .groupEntity(groupEntity)
                .build();
    }

    public static BankAccount fromBankAccountEntity(final BankAccountEntity bankAccountEntity) {
        final Group group = GroupTransformer.fromGroupEntity(bankAccountEntity.getGroupEntity());
        return BankAccount.builder()
                .bankAccountName(bankAccountEntity.getBankAccountName())
                .decimalBaseAmount(bankAccountEntity.getBaseAmountDecimal())
                .id(bankAccountEntity.getId())
                .group(group)
                .build();
    }

    public static List<BankAccount> fromBankAccountEntity(final List<BankAccountEntity> bankAccountEntities) {
        return bankAccountEntities.stream()
                .map(BankAccountTransformer::fromBankAccountEntity)
                .toList();
    }

    public static BankAccountIndexDTO toBankAccountIndexDTO(final BankAccount bankAccount) {
        return new BankAccountIndexDTO(bankAccount.getBankAccountName(), bankAccount.getId(), bankAccount.getDecimalBaseAmount());
    }

    public static List<BankAccountIndexDTO> toBankAccountIndexDTO(final List<BankAccount> bankAccounts) {
        return bankAccounts.stream()
                .map(BankAccountTransformer::toBankAccountIndexDTO)
                .toList();
    }
}
