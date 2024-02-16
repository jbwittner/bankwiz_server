package fr.bankwiz.server.domain.service.bankaccountservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.exception.GroupNotExistException;
import fr.bankwiz.server.domain.exception.UserNoWriteRightException;
import fr.bankwiz.server.domain.model.data.BankAccountDomain;
import fr.bankwiz.server.domain.model.data.BankAccountDomain.CurrencyEnumDomain;
import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.UserDomain;
import fr.bankwiz.server.domain.model.input.BankAccountCreationInput;
import fr.bankwiz.server.domain.service.BankAccountService;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;
import fr.bankwiz.server.domain.tools.CheckRightTools;

class CreateBankAccountTest extends DomainUnitTestBase {

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
    void createBankAccountOk() {
        final UserDomain user = this.factory.getUser();
        final GroupDomain group = this.factory.getGroup();
        final List<GroupRightDomain> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(user, GroupRightEnum.WRITE));

        this.mockGroupSpi.mockFindById(group.getId(), Optional.of(group));
        this.mockAuthenticationSpi.mockGetCurrentUser(user);
        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);
        this.mockBankAccountSpi.mockSave();

        final String bankAccountName = this.faker.superhero().name();
        final UUID groupId = group.getId();
        final Integer decimalBaseAmount = this.faker.random().nextInt(Integer.MAX_VALUE);

        final BankAccountCreationInput bankAccountCreationInput = BankAccountCreationInput.builder()
                .bankAccountName(bankAccountName)
                .groupId(groupId)
                .decimalBaseAmount(decimalBaseAmount)
                .build();

        final BankAccountDomain bankAccount = this.bankAccountService.createBankAccount(bankAccountCreationInput);

        Assertions.assertAll(
                () -> Assertions.assertEquals(bankAccountName, bankAccount.getBankAccountName()),
                () -> Assertions.assertEquals(group, bankAccount.getGroup()),
                () -> Assertions.assertEquals(decimalBaseAmount, bankAccount.getDecimalBaseAmount()));
    }

    @Test
    void userCantWrite() {
        final UserDomain user = this.factory.getUser();
        final GroupDomain group = this.factory.getGroup();
        final List<GroupRightDomain> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(user, GroupRightEnum.READ));

        this.mockGroupSpi.mockFindById(group.getId(), Optional.of(group));
        this.mockAuthenticationSpi.mockGetCurrentUser(user);
        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);

        final UUID groupId = group.getId();

        final BankAccountCreationInput bankAccountCreationInput = BankAccountCreationInput.builder()
                .groupId(groupId)
                .currency(CurrencyEnumDomain.EUR)
                .build();

        Assertions.assertThrows(
                UserNoWriteRightException.class,
                () -> this.bankAccountService.createBankAccount(bankAccountCreationInput));
    }

    @Test
    void groupNotExist() {
        final BankAccountCreationInput bankAccountCreationInput =
                BankAccountCreationInput.builder().groupId(UUID.randomUUID()).build();

        Assertions.assertThrows(
                GroupNotExistException.class,
                () -> this.bankAccountService.createBankAccount(bankAccountCreationInput));
    }
}
