package fr.bankwiz.server.domain.service.bankaccountservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.input.BankAccountUpdateInput;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.service.BankAccountService;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;
import fr.bankwiz.server.domain.tools.CheckRightTools;

class UpdateBankAccountTest extends DomainUnitTestBase {

    private BankAccountService bankAccountService;

    @Override
    protected void initDataBeforeEach() {
        final CheckRightTools checkRightTools = new CheckRightTools(this.mockGroupRightSpi.getMock());
        this.bankAccountService = new BankAccountService(
                this.mockBankAccountSpi.getMock(),
                this.mockGroupSpi.getMock(),
                this.mockGroupRightSpi.getMock(),
                this.mockAuthenticationSpi.getMock(),
                checkRightTools);
    }

    @Test
    void ok() {
        final User user = this.factory.getUser();
        this.mockAuthenticationSpi.mockGetCurrentUser(user);

        final BankAccount bankAccount = this.factory.getBankAccount();

        final UUID bankAccountId = bankAccount.getId();

        this.mockBankAccountSpi.mockFindById(bankAccountId, Optional.of(bankAccount)).mockSave();

        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(bankAccount.getGroup(), user, GroupRightEnum.ADMIN));

        this.mockGroupRightSpi.mockFindByGroup(bankAccount.getGroup(), groupRights);

        final String bankAccountNameBefore = bankAccount.getBankAccountName();

        BankAccountUpdateInput bankAccountUpdateInput = BankAccountUpdateInput.builder().bankAccountName(this.faker.starTrek().character())
        .build();

        final BankAccount bankAccountUpdated = this.bankAccountService.updateBankAccount(bankAccountId, bankAccountUpdateInput);

        Assertions.assertAll(
        () -> Assertions.assertNotEquals(bankAccountNameBefore, bankAccountUpdated.getBankAccountName()),
        () -> Assertions.assertEquals(bankAccountUpdateInput.getBankAccountName(), bankAccountUpdated.getBankAccountName()),
        () -> Assertions.assertEquals(bankAccount.getGroup(), bankAccountUpdated.getGroup()),
        () -> Assertions.assertEquals(bankAccount.getDecimalBaseAmount(), bankAccountUpdated.getDecimalBaseAmount())

        );


    }
    
}
