package fr.bankwiz.server.infrastructure.integrationtest.api;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.domain.model.model.UserAuthenticationDomain;
import fr.bankwiz.server.infrastructure.integrationtest.InfrastructureIntegrationTestBase;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.UserEntityRepository;

import static io.restassured.RestAssured.given;

class UserControllerTest extends InfrastructureIntegrationTestBase {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Test
    void checkregistration() throws Exception {
        final Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("scope", "message:read")
                .build();

        Mockito.when(this.jwtDecoder.decode(jwt.getTokenValue())).thenReturn(jwt);

        final String email = faker.internet().emailAddress();
        final String sub = this.factory.getAuthId();

        final UserAuthenticationDomain userAuthenticationDomain = new UserAuthenticationDomain(sub, email);

        Mockito.when(this.authenticationSpi.getUserAuthentication()).thenReturn(userAuthenticationDomain);

        final UserDTO response = given().auth()
                .oauth2(jwt.getTokenValue())
                .get("/user/checkregistration")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(UserDTO.class);

        Assertions.assertEquals(email, response.getEmail());

        final Optional<UserEntity> optional = this.userEntityRepository.findById(response.getId());

        Assertions.assertAll(
                () -> Assertions.assertTrue(optional.isPresent()),
                () -> Assertions.assertEquals(email, optional.get().getEmail()),
                () -> Assertions.assertEquals(sub, optional.get().getAuthId()));
    }
}
