package fr.bankwiz.server.infrastructure.integrationtest.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;

import fr.bankwiz.openapi.model.BankAccountCreationRequest;
import fr.bankwiz.openapi.model.BankAccountIndexDTO;
import fr.bankwiz.openapi.model.BankAccountUpdateRequest;
import fr.bankwiz.openapi.model.GroupBankAccountIndexDTO;
import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.infrastructure.integrationtest.testhelper.InfrastructureIntegrationTestBase;
import fr.bankwiz.server.infrastructure.spi.database.entity.BankAccountEntity;
import fr.bankwiz.server.infrastructure.spi.database.entity.GroupEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.BankAccountEntityRepository;
import fr.bankwiz.server.infrastructure.transformer.GroupTransformer;
import io.restassured.common.mapper.TypeRef;

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
                this.faker.random().nextInt(Integer.MAX_VALUE),
                "EUR");

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

    @Test
    void getAllBankAccount() {
        final User user = this.factory.getUser();
        final Jwt jwt = this.mockAuthentification(user);

        final List<Group> groups = new ArrayList<>();
        final Group group1 = this.factory.getGroup();
        groups.add(group1);
        this.factory.getGroupRight(group1, user, GroupRightEnum.READ);

        final Group group2 = this.factory.getGroup();
        groups.add(group2);
        this.factory.getGroupRight(group2, user, GroupRightEnum.READ);

        final List<BankAccount> bankAccountsGroup1 = new ArrayList<>();
        final List<BankAccount> bankAccountsGroup2 = new ArrayList<>();

        bankAccountsGroup1.add(this.factory.getBankAccount(group1));
        bankAccountsGroup1.add(this.factory.getBankAccount(group1));

        bankAccountsGroup2.add(this.factory.getBankAccount(group2));
        bankAccountsGroup2.add(this.factory.getBankAccount(group2));
        bankAccountsGroup2.add(this.factory.getBankAccount(group2));

        final var response = given().auth()
                .oauth2(jwt.getTokenValue())
                .header("Content-type", "application/json")
                .get("/bankaccount/bankaccounts")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<GroupBankAccountIndexDTO>>() {});

        Assertions.assertEquals(2, response.size());

        final GroupBankAccountIndexDTO groupBankAccountIndexDTO1 = response.stream()
                .filter(groupBankAccountIndexDTO ->
                        groupBankAccountIndexDTO.getGroupeIndex().getGroupId().equals(group1.getId()))
                .findFirst()
                .orElseThrow();
        Assertions.assertEquals(
                bankAccountsGroup1.size(),
                groupBankAccountIndexDTO1.getBankAccountIndexList().size());
        groupBankAccountIndexDTO1.getBankAccountIndexList().forEach(bankAccountIndexDTO -> {
            final BankAccount bankAccount = bankAccountsGroup1.stream()
                    .filter(bankAccountToFind ->
                            bankAccountToFind.getId().equals(bankAccountIndexDTO.getBankAccountId()))
                    .findFirst()
                    .orElseThrow();
            Assertions.assertAll(
                    () -> Assertions.assertEquals(
                            bankAccount.getBankAccountName(), bankAccountIndexDTO.getBankAccountName()),
                    () -> Assertions.assertEquals(bankAccount.getId(), bankAccountIndexDTO.getBankAccountId()));
        });

        final GroupBankAccountIndexDTO groupBankAccountIndexDTO2 = response.stream()
                .filter(groupBankAccountIndexDTO ->
                        groupBankAccountIndexDTO.getGroupeIndex().getGroupId().equals(group2.getId()))
                .findFirst()
                .orElseThrow();
        Assertions.assertEquals(
                bankAccountsGroup2.size(),
                groupBankAccountIndexDTO2.getBankAccountIndexList().size());
        groupBankAccountIndexDTO2.getBankAccountIndexList().forEach(bankAccountIndexDTO -> {
            final BankAccount bankAccount = bankAccountsGroup2.stream()
                    .filter(bankAccountToFind ->
                            bankAccountToFind.getId().equals(bankAccountIndexDTO.getBankAccountId()))
                    .findFirst()
                    .orElseThrow();
            Assertions.assertAll(
                    () -> Assertions.assertEquals(
                            bankAccount.getBankAccountName(), bankAccountIndexDTO.getBankAccountName()),
                    () -> Assertions.assertEquals(bankAccount.getId(), bankAccountIndexDTO.getBankAccountId()));
        });
    }

    @Test
    void deleteBankAccount() {
        final User user = this.factory.getUser();
        final Jwt jwt = this.mockAuthentification(user);

        final BankAccount bankAccount = this.factory.getBankAccount();
        final UUID id = bankAccount.getId();

        this.factory.getGroupRight(bankAccount.getGroup(), user, GroupRightEnum.ADMIN);

        final String uri = "/bankaccount/" + id.toString();

        given().auth()
                .oauth2(jwt.getTokenValue())
                .header("Content-type", "application/json")
                .delete(uri)
                .then()
                .assertThat()
                .statusCode(200);

        final Boolean bankAccountExist = this.bankAccountEntityRepository.existsById(id);

        Assertions.assertFalse(bankAccountExist);
    }

    @Test
    void updateBankAccount() {
        final User user = this.factory.getUser();
        final Jwt jwt = this.mockAuthentification(user);

        final BankAccount bankAccountBefore = this.factory.getBankAccount();
        this.factory.getGroupRight(bankAccountBefore.getGroup(), user, GroupRightEnum.ADMIN);

        final BankAccountUpdateRequest bankAccountCreationRequest = new BankAccountUpdateRequest();
        bankAccountCreationRequest.bankAccountName(this.faker.witcher().character());
        bankAccountCreationRequest.decimalBaseAmount(this.faker.random().nextInt(Integer.MAX_VALUE));

        final String uri = "/bankaccount/" + bankAccountBefore.getId().toString();

        final var result = given().auth()
                .oauth2(jwt.getTokenValue())
                .header("Content-type", "application/json")
                .body(bankAccountCreationRequest)
                .put(uri)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(BankAccountIndexDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(bankAccountBefore.getBankAccountName(), result.getBankAccountName()),
                () -> Assertions.assertNotEquals(
                        bankAccountBefore.getDecimalBaseAmount(), result.getDecimalBaseAmount()),
                () -> Assertions.assertEquals(
                        bankAccountCreationRequest.getBankAccountName(), result.getBankAccountName()),
                () -> Assertions.assertEquals(
                        bankAccountCreationRequest.getDecimalBaseAmount(), result.getDecimalBaseAmount()));

        final BankAccountEntity entity = this.bankAccountEntityRepository
                .findById(bankAccountBefore.getId())
                .orElseThrow();

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(bankAccountBefore.getBankAccountName(), entity.getBankAccountName()),
                () -> Assertions.assertNotEquals(
                        bankAccountBefore.getDecimalBaseAmount(), entity.getBaseAmountDecimal()),
                () -> Assertions.assertEquals(
                        bankAccountCreationRequest.getBankAccountName(), entity.getBankAccountName()),
                () -> Assertions.assertEquals(
                        bankAccountCreationRequest.getDecimalBaseAmount(), entity.getBaseAmountDecimal()));
    }
}
