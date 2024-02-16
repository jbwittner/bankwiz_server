package fr.bankwiz.server.domain.service;

import java.util.List;
import java.util.UUID;

import fr.bankwiz.server.domain.api.BankAccountApi;
import fr.bankwiz.server.domain.exception.BankAccountNotExistException;
import fr.bankwiz.server.domain.exception.GroupNotExistException;
import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.model.input.BankAccountCreationInput;
import fr.bankwiz.server.domain.model.input.BankAccountUpdateInput;
import fr.bankwiz.server.domain.model.other.GroupBankAccount;
import fr.bankwiz.server.domain.spi.AuthenticationSpi;
import fr.bankwiz.server.domain.spi.BankAccountSpi;
import fr.bankwiz.server.domain.spi.GroupRightSpi;
import fr.bankwiz.server.domain.spi.GroupSpi;
import fr.bankwiz.server.domain.tools.CheckRightTools;

public class BankAccountService implements BankAccountApi {

    private final BankAccountSpi bankAccountSpi;
    private final GroupSpi groupSpi;
    private final GroupRightSpi groupRightSpi;
    private final AuthenticationSpi authenticationSpi;
    private final CheckRightTools checkRightTools;

    public BankAccountService(
            final BankAccountSpi bankAccountSpi,
            final GroupSpi groupSpi,
            final GroupRightSpi groupRightSpi,
            final AuthenticationSpi authenticationSpi,
            final CheckRightTools checkRightTools) {
        this.bankAccountSpi = bankAccountSpi;
        this.groupSpi = groupSpi;
        this.groupRightSpi = groupRightSpi;
        this.authenticationSpi = authenticationSpi;
        this.checkRightTools = checkRightTools;
    }

    @Override
    public BankAccount createBankAccount(BankAccountCreationInput bankAccountCreationInput) {

        final BankAccount.CurrencyEnumDomain currencyEnumDomain = BankAccount.CurrencyEnumDomain.valueOf(bankAccountCreationInput.getCurrency());

        final Group group = this.groupSpi
                .findById(bankAccountCreationInput.getGroupId())
                .orElseThrow(() -> new GroupNotExistException(bankAccountCreationInput.getGroupId()));
        final User user = this.authenticationSpi.getCurrentUser();
        this.checkRightTools.checkCanWrite(user, group);

        final BankAccount bankAccount = BankAccount.builder()
                .bankAccountName(bankAccountCreationInput.getBankAccountName())
                .decimalBaseAmount(bankAccountCreationInput.getDecimalBaseAmount())
                .group(group)
                .id(UUID.randomUUID())
                .currency(currencyEnumDomain)
                .build();

        return this.bankAccountSpi.save(bankAccount);
    }

    @Override
    public List<GroupBankAccount> getAllBankAccount() {

        final User user = this.authenticationSpi.getCurrentUser();
        final List<GroupRight> groupRights = this.groupRightSpi.findByUser(user);

        return groupRights.stream()
                .map(GroupRight::getGroup)
                .map(group -> {
                    final List<BankAccount> bankAccounts = this.bankAccountSpi.findByGroup(group);
                    return GroupBankAccount.builder()
                            .bankAccounts(bankAccounts)
                            .group(group)
                            .build();
                })
                .toList();
    }

    @Override
    public void deleteBankAccount(UUID bankAccountId) {
        final User user = this.authenticationSpi.getCurrentUser();

        final BankAccount bankAccount = this.bankAccountSpi
                .findById(bankAccountId)
                .orElseThrow(() -> new BankAccountNotExistException(bankAccountId));

        final Group group = bankAccount.getGroup();
        this.checkRightTools.checkIsAdmin(user, group);

        this.bankAccountSpi.deleteById(bankAccountId);
    }

    @Override
    public BankAccount updateBankAccount(UUID bankAccountId, BankAccountUpdateInput bankAccountUpdateInput) {
        final User user = this.authenticationSpi.getCurrentUser();

        final BankAccount bankAccount = this.bankAccountSpi
                .findById(bankAccountId)
                .orElseThrow(() -> new BankAccountNotExistException(bankAccountId));

        final Group group = bankAccount.getGroup();
        this.checkRightTools.checkIsAdmin(user, group);

        if (bankAccountUpdateInput.getBankAccountName() != null) {
            bankAccount.setBankAccountName(bankAccountUpdateInput.getBankAccountName());
        }

        if (bankAccountUpdateInput.getDecimalBaseAmount() != null) {
            bankAccount.setDecimalBaseAmount(bankAccountUpdateInput.getDecimalBaseAmount());
        }

        if (bankAccountUpdateInput.getGroupId() != null) {
            final UUID otherGroupId = bankAccountUpdateInput.getGroupId();
            final Group otherGroup =
                    this.groupSpi.findById(otherGroupId).orElseThrow(() -> new GroupNotExistException(otherGroupId));
            this.checkRightTools.checkCanWrite(user, otherGroup);
            bankAccount.setGroup(otherGroup);
        }

        return this.bankAccountSpi.save(bankAccount);
    }
}
