package fr.bankwiz.server.domain.service;

import java.util.UUID;

import fr.bankwiz.server.domain.api.BankAccountApi;
import fr.bankwiz.server.domain.exception.GroupNotExistException;
import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.model.input.BankAccountCreationInput;
import fr.bankwiz.server.domain.spi.AuthenticationSpi;
import fr.bankwiz.server.domain.spi.BankAccountSpi;
import fr.bankwiz.server.domain.spi.GroupSpi;
import fr.bankwiz.server.domain.tools.CheckRightTools;

public class BankAccountService implements BankAccountApi {

    private final BankAccountSpi bankAccountSpi;
    private final GroupSpi groupSpi;
    private final AuthenticationSpi authenticationSpi;
    private final CheckRightTools checkRightTools;

    public BankAccountService(
            final BankAccountSpi bankAccountSpi,
            final GroupSpi groupSpi,
            final AuthenticationSpi authenticationSpi,
            final CheckRightTools checkRightTools) {
        this.bankAccountSpi = bankAccountSpi;
        this.groupSpi = groupSpi;
        this.authenticationSpi = authenticationSpi;
        this.checkRightTools = checkRightTools;
    }

    @Override
    public BankAccount createBankAccount(BankAccountCreationInput bankAccountCreationInput) {
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
                .build();

        return this.bankAccountSpi.save(bankAccount);
    }
}
