package fr.bankwiz.server.domain.service;

import java.util.List;
import java.util.UUID;

import fr.bankwiz.server.domain.api.BankAccountApi;
import fr.bankwiz.server.domain.exception.BankAccountNotExistException;
import fr.bankwiz.server.domain.exception.GroupNotExistException;
import fr.bankwiz.server.domain.model.data.BankAccountDomain;
import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain;
import fr.bankwiz.server.domain.model.data.UserDomain;
import fr.bankwiz.server.domain.model.input.BankAccountCreationInputDomain;
import fr.bankwiz.server.domain.model.input.BankAccountUpdateInputDomain;
import fr.bankwiz.server.domain.model.other.GroupBankAccountDomain;
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
    public BankAccountDomain createBankAccount(BankAccountCreationInputDomain bankAccountCreationInput) {

        final GroupDomain group = this.groupSpi
                .findById(bankAccountCreationInput.getGroupId())
                .orElseThrow(() -> new GroupNotExistException(bankAccountCreationInput.getGroupId()));
        final UserDomain user = this.authenticationSpi.getCurrentUser();
        this.checkRightTools.checkCanWrite(user, group);

        final BankAccountDomain bankAccount = BankAccountDomain.builder()
                .bankAccountName(bankAccountCreationInput.getBankAccountName())
                .decimalBaseAmount(bankAccountCreationInput.getDecimalBaseAmount())
                .group(group)
                .id(UUID.randomUUID())
                .currency(bankAccountCreationInput.getCurrency())
                .build();

        return this.bankAccountSpi.save(bankAccount);
    }

    @Override
    public List<GroupBankAccountDomain> getAllBankAccount() {

        final UserDomain user = this.authenticationSpi.getCurrentUser();
        final List<GroupRightDomain> groupRights = this.groupRightSpi.findByUser(user);

        return groupRights.stream()
                .map(GroupRightDomain::getGroup)
                .map(group -> {
                    final List<BankAccountDomain> bankAccounts = this.bankAccountSpi.findByGroup(group);
                    return GroupBankAccountDomain.builder()
                            .bankAccounts(bankAccounts)
                            .group(group)
                            .build();
                })
                .toList();
    }

    @Override
    public void deleteBankAccount(UUID bankAccountId) {
        final UserDomain user = this.authenticationSpi.getCurrentUser();

        final BankAccountDomain bankAccount = this.bankAccountSpi
                .findById(bankAccountId)
                .orElseThrow(() -> new BankAccountNotExistException(bankAccountId));

        final GroupDomain group = bankAccount.getGroup();
        this.checkRightTools.checkIsAdmin(user, group);

        this.bankAccountSpi.deleteById(bankAccountId);
    }

    @Override
    public BankAccountDomain updateBankAccount(UUID bankAccountId, BankAccountUpdateInputDomain bankAccountUpdateInput) {
        final UserDomain user = this.authenticationSpi.getCurrentUser();

        final BankAccountDomain bankAccount = this.bankAccountSpi
                .findById(bankAccountId)
                .orElseThrow(() -> new BankAccountNotExistException(bankAccountId));

        final GroupDomain group = bankAccount.getGroup();
        this.checkRightTools.checkIsAdmin(user, group);

        if (bankAccountUpdateInput.getBankAccountName() != null) {
            bankAccount.setBankAccountName(bankAccountUpdateInput.getBankAccountName());
        }

        if (bankAccountUpdateInput.getDecimalBaseAmount() != null) {
            bankAccount.setDecimalBaseAmount(bankAccountUpdateInput.getDecimalBaseAmount());
        }

        if (bankAccountUpdateInput.getGroupId() != null) {
            final UUID otherGroupId = bankAccountUpdateInput.getGroupId();
            final GroupDomain otherGroup =
                    this.groupSpi.findById(otherGroupId).orElseThrow(() -> new GroupNotExistException(otherGroupId));
            this.checkRightTools.checkCanWrite(user, otherGroup);
            bankAccount.setGroup(otherGroup);
        }

        return this.bankAccountSpi.save(bankAccount);
    }
}
