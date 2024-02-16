package fr.bankwiz.server.infrastructure.integrationtest.api.usercontroller;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.domain.model.data.UserDomain;
import fr.bankwiz.server.infrastructure.integrationtest.testhelper.InfrastructureIntegrationTestBase;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.UserEntityRepository;

import static io.restassured.RestAssured.given;

class GetCurrentUserInfoTest extends InfrastructureIntegrationTestBase {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void getCurrentUserInfo() throws Exception {
        final UserDomain user = this.factory.getUser();
        final Jwt jwt = this.mockAuthentification(user);

        final UserDTO response = given().auth()
                .oauth2(jwt.getTokenValue())
                .get("/user")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(UserDTO.class);

        Assertions.assertEquals(user.getEmail(), response.getEmail());

        final Optional<UserEntity> optional = this.userEntityRepository.findById(response.getId());

        Assertions.assertAll(
                () -> Assertions.assertTrue(optional.isPresent()),
                () -> Assertions.assertEquals(user.getEmail(), optional.get().getEmail()),
                () -> Assertions.assertEquals(user.getAuthId(), optional.get().getAuthId()));
    }
}
