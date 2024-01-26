package fr.bankwiz.server.infrastructure.integrationtest.api;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;

import fr.bankwiz.openapi.model.CreateTransactionRequest;
import fr.bankwiz.openapi.model.TransactionDTO;
import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.infrastructure.integrationtest.testhelper.InfrastructureIntegrationTestBase;
import fr.bankwiz.server.infrastructure.spi.database.entity.TransactionEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.TransactionEntityRepository;

import static io.restassured.RestAssured.given;

class TransactionControllerTest extends InfrastructureIntegrationTestBase {

    @Autowired
    private TransactionEntityRepository transactionEntityRepository;

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void createGroup() throws Exception {
        final User user = this.factory.getUser();
        final Jwt jwt = this.mockAuthentification(user);

        final Group group = this.factory.getGroup();
        this.factory.getGroupRight(group, user, GroupRightEnum.WRITE);
        final BankAccount bankAccount = this.factory.getBankAccount(group);

        final CreateTransactionRequest createTransactionRequest = new CreateTransactionRequest(
                bankAccount.getId(), this.faker.random().nextInt(Integer.MAX_VALUE));
        createTransactionRequest.setComment(this.faker.yoda().quote());

        final TransactionDTO response = given().auth()
                .oauth2(jwt.getTokenValue())
                .header("Content-type", "application/json")
                .body(createTransactionRequest)
                .put("/transactions")
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
}
