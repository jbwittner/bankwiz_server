package fr.bankwiz.server.infrastructure.integrationtest.api.usercontroller;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.Jwt;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.domain.model.data.UserAuthentication;
import fr.bankwiz.server.domain.spi.AuthenticationSpi;
import fr.bankwiz.server.infrastructure.integrationtest.testhelper.InfrastructureIntegrationTestBase;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.UserEntityRepository;

import static io.restassured.RestAssured.given;

class CheckregistrationTest extends InfrastructureIntegrationTestBase {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @MockBean
    private AuthenticationSpi authenticationSpi;

    @Override
    protected void initDataBeforeEach() {}

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

        UserDTO response = given().log().all().auth()
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
