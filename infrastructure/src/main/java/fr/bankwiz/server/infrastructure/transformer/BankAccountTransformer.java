package fr.bankwiz.server.infrastructure.transformer;

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
}
