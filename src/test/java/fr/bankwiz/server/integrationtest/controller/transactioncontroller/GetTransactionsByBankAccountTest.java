package fr.bankwiz.server.integrationtest.controller.transactioncontroller;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.bankwiz.openapi.model.TransactionDTO;
import fr.bankwiz.server.exception.BankAccountNotExistException;
import fr.bankwiz.server.exception.UserNoReadRightException;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.BankAccount;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;

class GetTransactionsByBankAccountTest extends IntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void getTransactionByBankAccountOkAdmin() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.ADMIN);

        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);
        this.integrationTestFactory.getTransaction(bankAccount);

        final String uri = IntegrationMVCClient.UriEnum.TRANSACTION_FINDBYBANKACCOUNT.getUri();

        var result = this.client
                .doGet(uri + "?bankAccountId=" + bankAccount.getId(), user.getAuthId())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final List<TransactionDTO> transactionDTOs =
                IntegrationMVCClient.convertMvcResultToListOfResponseObjects(result, TransactionDTO.class);

        Assertions.assertAll(() -> Assertions.assertEquals(1, transactionDTOs.size()), () -> {
            for (TransactionDTO transactionDTO : transactionDTOs) {
                Assertions.assertEquals(
                        bankAccount.getId(), transactionDTO.getAccountIndexDTO().getAccountId());
            }
        });
    }

    @Test
    void getTransactionByBankAccountOkWrite() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);
        final Group group2 = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.WRITE);

        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);
        this.integrationTestFactory.getTransaction(bankAccount);
        final BankAccount bankAccount2 = this.integrationTestFactory.getBankAccount(group2);
        this.integrationTestFactory.getTransaction(bankAccount2);
        this.integrationTestFactory.getTransaction(bankAccount2);

        final String uri = IntegrationMVCClient.UriEnum.TRANSACTION_FINDBYBANKACCOUNT.getUri();

        var result = this.client
                .doGet(uri + "?bankAccountId=" + bankAccount2.getId(), user.getAuthId())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final List<TransactionDTO> transactionDTOs =
                IntegrationMVCClient.convertMvcResultToListOfResponseObjects(result, TransactionDTO.class);

        Assertions.assertAll(() -> Assertions.assertEquals(2, transactionDTOs.size()), () -> {
            for (TransactionDTO transactionDTO : transactionDTOs) {
                Assertions.assertEquals(
                        bankAccount2.getId(),
                        transactionDTO.getAccountIndexDTO().getAccountId());
            }
        });
    }

    @Test
    void getTransactionByBankAccountOkRead() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);

        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);
        this.integrationTestFactory.getTransaction(bankAccount);
        final BankAccount bankAccount2 = this.integrationTestFactory.getBankAccount(group);
        this.integrationTestFactory.getTransaction(bankAccount2);
        this.integrationTestFactory.getTransaction(bankAccount2);
        final Group group2 = this.integrationTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ);
        final BankAccount bankAccount3 = this.integrationTestFactory.getBankAccount(group2);

        final String uri = IntegrationMVCClient.UriEnum.TRANSACTION_FINDBYBANKACCOUNT.getUri();

        var result = this.client
                .doGet(uri + "?bankAccountId=" + bankAccount3.getId(), user.getAuthId())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final List<TransactionDTO> transactionDTOs =
                IntegrationMVCClient.convertMvcResultToListOfResponseObjects(result, TransactionDTO.class);

        Assertions.assertEquals(0, transactionDTOs.size());
    }

    @Test
    void userNoWritePermissionException() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroup();

        final BankAccount bankAccount = this.integrationTestFactory.getBankAccount(group);
        this.integrationTestFactory.getTransaction(bankAccount);

        final String uri = IntegrationMVCClient.UriEnum.TRANSACTION_FINDBYBANKACCOUNT.getUri();

        final var result = this.client.doGet(uri + "?bankAccountId=" + bankAccount.getId(), user.getAuthId());

        final UserNoReadRightException exception = new UserNoReadRightException(user, group);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }

    @Test
    void bankAccountNotExistException() throws Exception {
        final User user = this.integrationTestFactory.getUser();

        final Integer bankAccountId = this.faker.random().nextInt(Integer.MAX_VALUE);

        final String uri = IntegrationMVCClient.UriEnum.TRANSACTION_FINDBYBANKACCOUNT.getUri();

        final var result = this.client.doGet(uri + "?bankAccountId=" + bankAccountId, user.getAuthId());

        final BankAccountNotExistException exception = new BankAccountNotExistException(bankAccountId);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, exception);
    }
}
