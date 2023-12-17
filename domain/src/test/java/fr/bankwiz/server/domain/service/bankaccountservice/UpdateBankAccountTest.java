package fr.bankwiz.server.domain.service.bankaccountservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.exception.BankAccountNotExistException;
import fr.bankwiz.server.domain.exception.GroupNotExistException;
import fr.bankwiz.server.domain.exception.UserNoWriteRightException;
import fr.bankwiz.server.domain.exception.UserNotAdminException;
import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.model.input.BankAccountUpdateInput;
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
    void bankAccountName() {
        final User user = this.factory.getUser();
        this.mockAuthenticationSpi.mockGetCurrentUser(user);

        final BankAccount bankAccount = this.factory.getBankAccount();

        final UUID bankAccountId = bankAccount.getId();

        this.mockBankAccountSpi
                .mockFindById(bankAccountId, Optional.of(bankAccount))
                .mockSave();

        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(bankAccount.getGroup(), user, GroupRightEnum.ADMIN));

        this.mockGroupRightSpi.mockFindByGroup(bankAccount.getGroup(), groupRights);

        final String bankAccountNameBefore = bankAccount.getBankAccountName();

        BankAccountUpdateInput bankAccountUpdateInput = BankAccountUpdateInput.builder()
                .bankAccountName(this.faker.starTrek().character())
                .build();

        final BankAccount bankAccountUpdated =
                this.bankAccountService.updateBankAccount(bankAccountId, bankAccountUpdateInput);

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(bankAccountNameBefore, bankAccountUpdated.getBankAccountName()),
                () -> Assertions.assertEquals(
                        bankAccountUpdateInput.getBankAccountName(), bankAccountUpdated.getBankAccountName()),
                () -> Assertions.assertEquals(bankAccount.getGroup(), bankAccountUpdated.getGroup()),
                () -> Assertions.assertEquals(
                        bankAccount.getDecimalBaseAmount(), bankAccountUpdated.getDecimalBaseAmount()));
    }

    @Test
    void decimalBaseAmount() {
        final User user = this.factory.getUser();
        this.mockAuthenticationSpi.mockGetCurrentUser(user);

        final BankAccount bankAccount = this.factory.getBankAccount();

        final UUID bankAccountId = bankAccount.getId();

        this.mockBankAccountSpi
                .mockFindById(bankAccountId, Optional.of(bankAccount))
                .mockSave();

        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(bankAccount.getGroup(), user, GroupRightEnum.ADMIN));

        this.mockGroupRightSpi.mockFindByGroup(bankAccount.getGroup(), groupRights);

        final Integer decimalBaseAmountBefore = bankAccount.getDecimalBaseAmount();

        BankAccountUpdateInput bankAccountUpdateInput = BankAccountUpdateInput.builder()
                .decimalBaseAmount(this.faker.random().nextInt(Integer.MAX_VALUE))
                .build();

        final BankAccount bankAccountUpdated =
                this.bankAccountService.updateBankAccount(bankAccountId, bankAccountUpdateInput);

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(decimalBaseAmountBefore, bankAccountUpdated.getDecimalBaseAmount()),
                () -> Assertions.assertEquals(
                        bankAccount.getBankAccountName(), bankAccountUpdated.getBankAccountName()),
                () -> Assertions.assertEquals(bankAccount.getGroup(), bankAccountUpdated.getGroup()),
                () -> Assertions.assertEquals(
                        bankAccountUpdateInput.getDecimalBaseAmount(), bankAccountUpdated.getDecimalBaseAmount()));
    }

    @Test
    void group() {
        final User user = this.factory.getUser();
        this.mockAuthenticationSpi.mockGetCurrentUser(user);

        final BankAccount bankAccount = this.factory.getBankAccount();

        final UUID bankAccountId = bankAccount.getId();

        this.mockBankAccountSpi
                .mockFindById(bankAccountId, Optional.of(bankAccount))
                .mockSave();

        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(bankAccount.getGroup(), user, GroupRightEnum.ADMIN));

        final Group newGroup = this.factory.getGroup();

        this.mockGroupSpi.mockFindById(newGroup.getId(), Optional.of(newGroup));

        groupRights.add(this.factory.getGroupRight(newGroup, user, GroupRightEnum.ADMIN));

        this.mockGroupRightSpi.mockFindByGroup(bankAccount.getGroup(), groupRights);

        final Group groupBefore = bankAccount.getGroup();

        BankAccountUpdateInput bankAccountUpdateInput =
                BankAccountUpdateInput.builder().groupId(newGroup.getId()).build();

        final BankAccount bankAccountUpdated =
                this.bankAccountService.updateBankAccount(bankAccountId, bankAccountUpdateInput);

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(groupBefore, bankAccountUpdated.getGroup()),
                () -> Assertions.assertEquals(
                        bankAccount.getBankAccountName(), bankAccountUpdated.getBankAccountName()),
                () -> Assertions.assertEquals(
                        bankAccountUpdateInput.getGroupId(),
                        bankAccountUpdated.getGroup().getId()),
                () -> Assertions.assertEquals(
                        bankAccount.getDecimalBaseAmount(), bankAccountUpdated.getDecimalBaseAmount()));
    }

    @Test
    void newGroupNotAdminException() {
        final User user = this.factory.getUser();
        this.mockAuthenticationSpi.mockGetCurrentUser(user);

        final BankAccount bankAccount = this.factory.getBankAccount();

        final UUID bankAccountId = bankAccount.getId();

        this.mockBankAccountSpi
                .mockFindById(bankAccountId, Optional.of(bankAccount))
                .mockSave();

        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(bankAccount.getGroup(), user, GroupRightEnum.ADMIN));

        final Group newGroup = this.factory.getGroup();

        this.mockGroupSpi.mockFindById(newGroup.getId(), Optional.of(newGroup));

        groupRights.add(this.factory.getGroupRight(newGroup, user, GroupRightEnum.READ));

        this.mockGroupRightSpi.mockFindByGroup(bankAccount.getGroup(), groupRights);

        BankAccountUpdateInput bankAccountUpdateInput =
                BankAccountUpdateInput.builder().groupId(newGroup.getId()).build();

        Assertions.assertThrows(UserNoWriteRightException.class, () -> {
            this.bankAccountService.updateBankAccount(bankAccountId, bankAccountUpdateInput);
        });
    }

    @Test
    void newGroupNotExistException() {
        final User user = this.factory.getUser();
        this.mockAuthenticationSpi.mockGetCurrentUser(user);

        final BankAccount bankAccount = this.factory.getBankAccount();

        final UUID bankAccountId = bankAccount.getId();

        this.mockBankAccountSpi
                .mockFindById(bankAccountId, Optional.of(bankAccount))
                .mockSave();

        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(bankAccount.getGroup(), user, GroupRightEnum.ADMIN));

        final Group newGroup = this.factory.getGroup();

        this.mockGroupSpi.mockFindById(newGroup.getId(), Optional.empty());

        groupRights.add(this.factory.getGroupRight(newGroup, user, GroupRightEnum.ADMIN));

        this.mockGroupRightSpi.mockFindByGroup(bankAccount.getGroup(), groupRights);

        BankAccountUpdateInput bankAccountUpdateInput =
                BankAccountUpdateInput.builder().groupId(newGroup.getId()).build();

        Assertions.assertThrows(GroupNotExistException.class, () -> {
            this.bankAccountService.updateBankAccount(bankAccountId, bankAccountUpdateInput);
        });
    }

    @Test
    void userNotAdminException() {
        final User user = this.factory.getUser();
        this.mockAuthenticationSpi.mockGetCurrentUser(user);

        final BankAccount bankAccount = this.factory.getBankAccount();

        final UUID bankAccountId = bankAccount.getId();

        this.mockBankAccountSpi
                .mockFindById(bankAccountId, Optional.of(bankAccount))
                .mockSave();

        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(bankAccount.getGroup(), user, GroupRightEnum.WRITE));

        this.mockGroupRightSpi.mockFindByGroup(bankAccount.getGroup(), groupRights);

        BankAccountUpdateInput bankAccountUpdateInput =
                BankAccountUpdateInput.builder().build();

        Assertions.assertThrows(UserNotAdminException.class, () -> {
            this.bankAccountService.updateBankAccount(bankAccountId, bankAccountUpdateInput);
        });
    }

    @Test
    void bankAccountNotExistException() {
        final User user = this.factory.getUser();
        this.mockAuthenticationSpi.mockGetCurrentUser(user);

        final UUID bankAccountId = UUID.randomUUID();

        this.mockBankAccountSpi.mockFindById(bankAccountId, Optional.empty()).mockSave();

        BankAccountUpdateInput bankAccountUpdateInput =
                BankAccountUpdateInput.builder().build();

        Assertions.assertThrows(BankAccountNotExistException.class, () -> {
            this.bankAccountService.updateBankAccount(bankAccountId, bankAccountUpdateInput);
        });
    }
}
