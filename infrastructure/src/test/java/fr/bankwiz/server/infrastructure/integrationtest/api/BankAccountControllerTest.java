package fr.bankwiz.server.infrastructure.integrationtest.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;

import fr.bankwiz.openapi.model.BankAccountCreationRequest;
import fr.bankwiz.openapi.model.BankAccountIndexDTO;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.infrastructure.integrationtest.testhelper.InfrastructureIntegrationTestBase;
import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.BankAccountEntityRepository;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;

import static io.restassured.RestAssured.given;

class BankAccountControllerTest extends InfrastructureIntegrationTestBase {

    @Autowired
    private BankAccountEntityRepository bankAccountEntityRepository;

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void createBankAccount() {
        final User user = this.factory.getUser();
        final Jwt jwt = this.mockAuthentification(user);

        final Group group = this.factory.getGroup();
        this.factory.getGroupRight(group, user, GroupRightEnum.ADMIN);

        final BankAccountCreationRequest bankAccountCreationRequest = new BankAccountCreationRequest(
                this.faker.superhero().name(),
                group.getId(),
                this.faker.random().nextInt(Integer.MAX_VALUE));

        final BankAccountIndexDTO response = given().auth()
                .oauth2(jwt.getTokenValue())
                .header("Content-type", "application/json")
                .body(bankAccountCreationRequest)
                .post("/bankaccount")
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .as(BankAccountIndexDTO.class);

        Assertions.assertEquals(bankAccountCreationRequest.getBankAccountName(), response.getBankAccountName());

        final GroupEntity groupEntity = GroupTransformer.toGroupEntity(group);

        final var bankAccounts = this.bankAccountEntityRepository.findByGroupEntity(groupEntity);

        Assertions.assertEquals(1, bankAccounts.size());

        final BankAccountEntity bankAccountEntity = bankAccounts.get(0);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        bankAccountCreationRequest.getGroupId(),
                        bankAccountEntity.getGroupEntity().getId()),
                () -> Assertions.assertEquals(
                        bankAccountCreationRequest.getBankAccountName(), bankAccountEntity.getBankAccountName()),
                () -> Assertions.assertEquals(
                        bankAccountCreationRequest.getDecimalBaseAmount(), bankAccountEntity.getBaseAmountDecimal()));
    }
}
