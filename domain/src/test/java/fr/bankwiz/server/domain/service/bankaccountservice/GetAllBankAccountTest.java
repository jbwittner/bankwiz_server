package fr.bankwiz.server.domain.service.bankaccountservice;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.BankAccountDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.UserDomain;
import fr.bankwiz.server.domain.model.other.GroupBankAccountDomain;
import fr.bankwiz.server.domain.service.BankAccountService;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;
import fr.bankwiz.server.domain.tools.CheckRightTools;

class GetAllBankAccountTest extends DomainUnitTestBase {

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
        final UserDomain user = this.factory.getUser();

        this.mockAuthenticationSpi.mockGetCurrentUser(user);

        final List<GroupRightDomain> groupRights = new ArrayList<>();
        final GroupRightDomain groupRight1 = this.factory.getGroupRight(user, GroupRightEnum.READ);
        final GroupRightDomain groupRight2 = this.factory.getGroupRight(user, GroupRightEnum.READ);
        final GroupRightDomain groupRight3 = this.factory.getGroupRight(user, GroupRightEnum.READ);

        groupRights.add(groupRight1);
        groupRights.add(groupRight2);
        groupRights.add(groupRight3);

        this.mockGroupRightSpi.mockFindByUser(user, groupRights);

        final List<BankAccountDomain> bankAccounts1 = new ArrayList<>();
        final List<BankAccountDomain> bankAccounts2 = new ArrayList<>();
        final List<BankAccountDomain> bankAccounts3 = new ArrayList<>();

        bankAccounts1.add(this.factory.getBankAccount(groupRight1.getGroup()));
        bankAccounts1.add(this.factory.getBankAccount(groupRight1.getGroup()));
        bankAccounts1.add(this.factory.getBankAccount(groupRight1.getGroup()));

        bankAccounts2.add(this.factory.getBankAccount(groupRight2.getGroup()));
        bankAccounts2.add(this.factory.getBankAccount(groupRight2.getGroup()));

        bankAccounts3.add(this.factory.getBankAccount(groupRight3.getGroup()));

        this.mockBankAccountSpi.mockFindByGroup(groupRight1.getGroup(), bankAccounts1);
        this.mockBankAccountSpi.mockFindByGroup(groupRight2.getGroup(), bankAccounts2);
        this.mockBankAccountSpi.mockFindByGroup(groupRight3.getGroup(), bankAccounts3);

        final var result = this.bankAccountService.getAllBankAccount();

        Assertions.assertEquals(3, result.size());

        final GroupBankAccountDomain groupBankAccount1 = result.stream()
                .filter(groupBankAccount -> groupBankAccount
                        .getGroup()
                        .getId()
                        .equals(groupRight1.getGroup().getId()))
                .findFirst()
                .orElseThrow();

        Assertions.assertEquals(groupRight1.getGroup(), groupBankAccount1.getGroup());
        Assertions.assertEquals(bankAccounts1, groupBankAccount1.getBankAccounts());

        final GroupBankAccountDomain groupBankAccount2 = result.stream()
                .filter(groupBankAccount -> groupBankAccount
                        .getGroup()
                        .getId()
                        .equals(groupRight2.getGroup().getId()))
                .findFirst()
                .orElseThrow();

        Assertions.assertEquals(groupRight2.getGroup(), groupBankAccount2.getGroup());
        Assertions.assertEquals(bankAccounts2, groupBankAccount2.getBankAccounts());

        final GroupBankAccountDomain groupBankAccount3 = result.stream()
                .filter(groupBankAccount -> groupBankAccount
                        .getGroup()
                        .getId()
                        .equals(groupRight3.getGroup().getId()))
                .findFirst()
                .orElseThrow();

        Assertions.assertEquals(groupRight3.getGroup(), groupBankAccount3.getGroup());
        Assertions.assertEquals(bankAccounts3, groupBankAccount3.getBankAccounts());
    }
}
