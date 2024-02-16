package fr.bankwiz.server.infrastructure.integrationtest.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;

import fr.bankwiz.openapi.model.BankAccountIndexDTO;
import fr.bankwiz.openapi.model.BankAccountTransactionsDTO;
import fr.bankwiz.openapi.model.CreateTransactionRequest;
import fr.bankwiz.openapi.model.TransactionDTO;
import fr.bankwiz.openapi.model.TransactionIndexDTO;
import fr.bankwiz.openapi.model.UpdateTransactionRequest;
import fr.bankwiz.server.domain.model.data.BankAccountDomain;
import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.Transaction;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.infrastructure.integrationtest.testhelper.InfrastructureIntegrationTestBase;
import fr.bankwiz.server.infrastructure.spi.database.entity.TransactionEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.TransactionEntityRepository;
import fr.bankwiz.server.infrastructure.transformer.BankAccountTransformer;
import fr.bankwiz.server.infrastructure.transformer.TransactionTransformer;

import static io.restassured.RestAssured.given;

class TransactionControllerTest extends InfrastructureIntegrationTestBase {

    @Autowired
    private TransactionEntityRepository transactionEntityRepository;

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void createTransaction() throws Exception {
        final User user = this.factory.getUser();
        final Jwt jwt = this.mockAuthentification(user);

        final GroupDomain group = this.factory.getGroup();
        this.factory.getGroupRight(group, user, GroupRightEnum.WRITE);
        final BankAccountDomain bankAccount = this.factory.getBankAccount(group);

        final CreateTransactionRequest createTransactionRequest = new CreateTransactionRequest(
                bankAccount.getId(), this.faker.random().nextInt(Integer.MAX_VALUE));
        createTransactionRequest.setComment(this.faker.superhero().name());

        final TransactionDTO response = given().auth()
                .oauth2(jwt.getTokenValue())
                .header("Content-type", "application/json")
                .body(createTransactionRequest)
                .post("/transaction")
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .as(TransactionDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(createTransactionRequest.getDecimalAmount(), response.getDecimalAmount()),
                () -> Assertions.assertEquals(createTransactionRequest.getComment(), response.getComment()),
                () -> Assertions.assertEquals(
                        createTransactionRequest.getBankAccountId(), response.getBankAccountId()));

        Optional<TransactionEntity> optionalTransactionEntity =
                this.transactionEntityRepository.findById(response.getTransactionId());

        Assertions.assertAll(() -> Assertions.assertTrue(optionalTransactionEntity.isPresent()), () -> {
            final TransactionEntity transactionEntity = optionalTransactionEntity.get();
            Assertions.assertAll(
                    () -> Assertions.assertEquals(
                            createTransactionRequest.getDecimalAmount(), transactionEntity.getDecimalAmount()),
                    () -> Assertions.assertEquals(
                            createTransactionRequest.getComment(), transactionEntity.getComment()),
                    () -> Assertions.assertEquals(
                            createTransactionRequest.getBankAccountId(),
                            transactionEntity.getBankAccountEntity().getId()));
        });
    }

    @Test
    void getAllTransactionOfBankAccount() {
        final User user = this.factory.getUser();
        final Jwt jwt = this.mockAuthentification(user);

        final GroupDomain group = this.factory.getGroup();
        this.factory.getGroupRight(group, user, GroupRightEnum.READ);
        final BankAccountDomain bankAccount = this.factory.getBankAccount(group);
        final List<Transaction> transactions = new ArrayList<>();
        transactions.add(this.factory.getTransaction(bankAccount));
        transactions.add(this.factory.getTransaction(bankAccount));
        transactions.add(this.factory.getTransaction(bankAccount));

        final BankAccountTransactionsDTO response = given().auth()
                .oauth2(jwt.getTokenValue())
                .header("Content-type", "application/json")
                .get("/transaction/bankaccount/" + bankAccount.getId())
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(BankAccountTransactionsDTO.class);

        BankAccountIndexDTO bankAccountIndexDTOExpected = BankAccountTransformer.toBankAccountIndexDTO(bankAccount);

        Assertions.assertEquals(bankAccountIndexDTOExpected, response.getBankAccountIndex());

        final var transactionIndexDTOs = response.getTransactions();

        transactionIndexDTOs.forEach(transactionIndexDTO -> {
            final Transaction transactionFinded = transactions.stream()
                    .filter(transaction -> transaction.getId().equals(transactionIndexDTO.getTransactionId()))
                    .findFirst()
                    .orElseThrow();
            final TransactionIndexDTO transactionIndexDTOFinded =
                    TransactionTransformer.toTransactionIndexDTO(transactionFinded);
            Assertions.assertEquals(transactionIndexDTO, transactionIndexDTOFinded);
        });
    }

    @Test
    void updateTransaction() {
        final User user = this.factory.getUser();
        final Jwt jwt = this.mockAuthentification(user);

        final GroupDomain group = this.factory.getGroup();
        this.factory.getGroupRight(group, user, GroupRightEnum.WRITE);
        final BankAccountDomain bankAccount = this.factory.getBankAccount(group);

        final Transaction transaction = this.factory.getTransaction(bankAccount);
        final UUID transactionId = transaction.getId();

        final UpdateTransactionRequest updateTransactionRequest = new UpdateTransactionRequest();
        updateTransactionRequest.setComment(this.faker.rickAndMorty().character());
        updateTransactionRequest.setDecimalAmount(this.faker.random().nextInt(Integer.MAX_VALUE));

        final TransactionDTO response = given().auth()
                .oauth2(jwt.getTokenValue())
                .header("Content-type", "application/json")
                .body(updateTransactionRequest)
                .put("/transaction/" + transactionId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(TransactionDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(transactionId, response.getTransactionId()),
                () -> Assertions.assertNotEquals(transaction.getComment(), response.getComment()),
                () -> Assertions.assertNotEquals(transaction.getDecimalAmount(), response.getDecimalAmount()),
                () -> Assertions.assertEquals(updateTransactionRequest.getComment(), response.getComment()),
                () -> Assertions.assertEquals(
                        updateTransactionRequest.getDecimalAmount(), response.getDecimalAmount()));

        final TransactionEntity transactionEntity =
                this.transactionEntityRepository.findById(transactionId).orElseThrow();

        Assertions.assertAll(
                () -> Assertions.assertEquals(updateTransactionRequest.getComment(), transactionEntity.getComment()),
                () -> Assertions.assertEquals(
                        updateTransactionRequest.getDecimalAmount(), transactionEntity.getDecimalAmount()));
    }

    @Test
    void deleteTransaction() {
        final User user = this.factory.getUser();
        final Jwt jwt = this.mockAuthentification(user);

        final GroupDomain group = this.factory.getGroup();
        this.factory.getGroupRight(group, user, GroupRightEnum.WRITE);
        final BankAccountDomain bankAccount = this.factory.getBankAccount(group);

        final Transaction transaction = this.factory.getTransaction(bankAccount);
        final UUID transactionId = transaction.getId();

        given().auth()
                .oauth2(jwt.getTokenValue())
                .header("Content-type", "application/json")
                .delete("/transaction/" + transactionId)
                .then()
                .assertThat()
                .statusCode(200);

        final Optional<TransactionEntity> optional = this.transactionEntityRepository.findById(transactionId);

        Assertions.assertTrue(optional.isEmpty());
    }
}
