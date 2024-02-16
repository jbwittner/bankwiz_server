package fr.bankwiz.server.domain.service.bankaccountservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import fr.bankwiz.server.domain.exception.BankAccountNotExistException;
import fr.bankwiz.server.domain.exception.UserNotAdminException;
import fr.bankwiz.server.domain.model.data.BankAccountDomain;
import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.service.BankAccountService;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;
import fr.bankwiz.server.domain.tools.CheckRightTools;

class DeleteBankAccountTest extends DomainUnitTestBase {

    private BankAccountService bankAccountService;

    @Override
    protected void initDataBeforeEach() {
        final CheckRightTools checkRightTools =
                new CheckRightTools(this.mockGroupRightSpi.getMock(), this.mockAuthenticationSpi.getMock());
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

        final BankAccountDomain bankAccount = this.factory.getBankAccount();
        final UUID bankAccountId = bankAccount.getId();

        this.mockBankAccountSpi.mockFindById(bankAccountId, Optional.of(bankAccount));

        final GroupDomain group = bankAccount.getGroup();
        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, user, GroupRightEnum.ADMIN));
        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);

        this.bankAccountService.deleteBankAccount(bankAccountId);

        Mockito.verify(this.mockBankAccountSpi.getMock(), Mockito.times(1)).deleteById(bankAccountId);
    }

    @Test
    void isNotAdmin() {
        final User user = this.factory.getUser();
        this.mockAuthenticationSpi.mockGetCurrentUser(user);

        final BankAccountDomain bankAccount = this.factory.getBankAccount();
        final UUID bankAccountId = bankAccount.getId();

        this.mockBankAccountSpi.mockFindById(bankAccountId, Optional.of(bankAccount));

        final GroupDomain group = bankAccount.getGroup();
        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, user, GroupRightEnum.WRITE));
        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);

        Assertions.assertThrows(
                UserNotAdminException.class, () -> this.bankAccountService.deleteBankAccount(bankAccountId));
    }

    @Test
    void bankAccountNotExist() {
        final User user = this.factory.getUser();
        this.mockAuthenticationSpi.mockGetCurrentUser(user);

        final UUID bankAccountId = UUID.randomUUID();

        this.mockBankAccountSpi.mockFindById(bankAccountId, Optional.empty());

        Assertions.assertThrows(
                BankAccountNotExistException.class, () -> this.bankAccountService.deleteBankAccount(bankAccountId));
    }
}
