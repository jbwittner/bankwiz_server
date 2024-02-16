package fr.bankwiz.server.infrastructure.transformer;

import java.util.Currency;
import java.util.List;

import fr.bankwiz.openapi.model.BankAccountIndexDTO;
import fr.bankwiz.openapi.model.CurrencyIndexDTO;
import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;

public class BankAccountTransformer {

    private BankAccountTransformer() {}

    public static BankAccountEntity toBankAccountEntity(final BankAccount bankAccount) {
        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(bankAccount.getGroup());
        final BankAccountEntity.CurrencyEntityEnum currencyEntityEnum = BankAccountEntity.CurrencyEntityEnum.valueOf(bankAccount.getCurrency().toString());
        return BankAccountEntity.builder()
                .baseAmountDecimal(bankAccount.getDecimalBaseAmount())
                .id(bankAccount.getId())
                .bankAccountName(bankAccount.getBankAccountName())
                .groupEntity(groupEntity)
                .currencyEntityEnum(currencyEntityEnum)
                .build();
    }

    public static BankAccount fromBankAccountEntity(final BankAccountEntity bankAccountEntity) {
        final Group group = GroupTransformer.fromGroupEntity(bankAccountEntity.getGroupEntity());
        final BankAccount.CurrencyEnumDomain currencyEnumDomain = BankAccount.CurrencyEnumDomain.valueOf(bankAccountEntity.getCurrencyEntityEnum().toString());
        return BankAccount.builder()
                .bankAccountName(bankAccountEntity.getBankAccountName())
                .decimalBaseAmount(bankAccountEntity.getBaseAmountDecimal())
                .id(bankAccountEntity.getId())
                .group(group)
                .currency(currencyEnumDomain)
                .build();
    }

    public static List<BankAccount> fromBankAccountEntity(final List<BankAccountEntity> bankAccountEntities) {
        return bankAccountEntities.stream()
                .map(BankAccountTransformer::fromBankAccountEntity)
                .toList();
    }

    public static BankAccountIndexDTO toBankAccountIndexDTO(final BankAccount bankAccount) {
        final Currency currency = Currency.getInstance(bankAccount.getCurrency().toString());
        final CurrencyIndexDTO currencyIndexDTO = new CurrencyIndexDTO(currency.getCurrencyCode(), currency.getDisplayName(), currency.getSymbol());
        return new BankAccountIndexDTO(
                bankAccount.getBankAccountName(), bankAccount.getId(), bankAccount.getDecimalBaseAmount(), currencyIndexDTO);
    }

    public static List<BankAccountIndexDTO> toBankAccountIndexDTO(final List<BankAccount> bankAccounts) {
        return bankAccounts.stream()
                .map(BankAccountTransformer::toBankAccountIndexDTO)
                .toList();
    }
}
