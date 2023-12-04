package fr.bankwiz.server.infrastructure.integrationtest.api;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.Jwt;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.model.data.UserAuthentication;
import fr.bankwiz.server.domain.spi.AuthenticationSpi;
import fr.bankwiz.server.infrastructure.integrationtest.testhelper.InfrastructureIntegrationTestBase;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.UserEntityRepository;

import static io.restassured.RestAssured.given;

class UserControllerTest extends InfrastructureIntegrationTestBase {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @MockBean
    private AuthenticationSpi authenticationSpi;

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void getCurrentUserInfo() throws Exception {
        final User user = this.factory.getUser();
        final Jwt jwt = this.mockAuthentification(user);

        final UserDTO response =
                given().auth().oauth2(jwt.getTokenValue()).get("/user").as(UserDTO.class);

        Assertions.assertEquals(user.getEmail(), response.getEmail());

        final Optional<UserEntity> optional = this.userEntityRepository.findById(response.getId());

        Assertions.assertAll(
                () -> Assertions.assertTrue(optional.isPresent()),
                () -> Assertions.assertEquals(user.getEmail(), optional.get().getEmail()),
                () -> Assertions.assertEquals(user.getAuthId(), optional.get().getAuthId()));
    }

    @Test
    void checkregistration() throws Exception {
        final Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("scope", "message:read")
                .build();

        Mockito.when(this.jwtDecoder.decode(jwt.getTokenValue())).thenReturn(jwt);

        final String email = faker.internet().emailAddress();
        final String sub = this.factory.getAuthId();

        Mockito.when(this.authenticationSpi.getUserAuthentication())
                .thenReturn(UserAuthentication.builder().email(email).sub(sub).build());

        UserDTO response = given().auth()
                .oauth2(jwt.getTokenValue())
                .get("/user/checkregistration")
                .as(UserDTO.class);

        Assertions.assertEquals(email, response.getEmail());

        Optional<UserEntity> optional = this.userEntityRepository.findById(response.getId());

        Assertions.assertAll(
                () -> Assertions.assertTrue(optional.isPresent()),
                () -> Assertions.assertEquals(email, optional.get().getEmail()),
                () -> Assertions.assertEquals(sub, optional.get().getAuthId()));
    }
}
