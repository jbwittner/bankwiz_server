package fr.bankwiz.server.integrationtest.controller.bankaccountcontroller;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.bankwiz.server.exception.BankAccountNotExistException;
import fr.bankwiz.server.exception.UserNotAdminException;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.BankAccount;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;

class DeleteAccountTest extends IntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void deleteAccountOk() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);
        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);

        final Integer bankAccountId = bankAccount.getId();

        final String uri = IntegrationMVCClient.UriEnum.BANK_ACCOUNT_ID_ACCOUNT.getUri(bankAccountId);

        this.client
                .doDelete(uri, user.getAuthId())
                .andExpect(MockMvcResultMatchers.status().isOk());

        final Optional<BankAccount> optionalBankAccount = this.bankAccountRepository.findById(bankAccountId);
        final Integer groupId = group.getUserGroupId();
        final Group groupUpdated = this.groupRepository.findById(groupId).orElseThrow();

        Assertions.assertAll(
                () -> Assertions.assertFalse(optionalBankAccount.isPresent()),
                () -> Assertions.assertEquals(0, groupUpdated.getBankAccounts().size()));
    }

    @Test
    void userNotAdminException() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);
        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);

        final Integer bankAccountId = bankAccount.getId();

        final String uri = IntegrationMVCClient.UriEnum.BANK_ACCOUNT_ID_ACCOUNT.getUri(bankAccountId);

        final var result = this.client.doDelete(uri, user.getAuthId());

        final UserNotAdminException exception = new UserNotAdminException(user, group);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }

    @Test
    void groupNotExistException() throws Exception {
        final User user = this.integrationTestFactory.getUser();

        final Integer bankAccountId = this.faker.random().nextInt(Integer.MAX_VALUE);

        final String uri = IntegrationMVCClient.UriEnum.BANK_ACCOUNT_ID_ACCOUNT.getUri(bankAccountId);

        final var result = this.client.doDelete(uri, user.getAuthId());

        final BankAccountNotExistException exception = new BankAccountNotExistException(bankAccountId);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }
}
