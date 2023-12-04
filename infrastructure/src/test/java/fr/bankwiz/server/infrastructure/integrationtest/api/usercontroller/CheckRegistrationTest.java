package fr.bankwiz.server.infrastructure.integrationtest.api.usercontroller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.Jwt;

import fr.bankwiz.server.domain.model.data.UserAuthentication;
import fr.bankwiz.server.domain.spi.AuthenticationSpi;
import fr.bankwiz.server.infrastructure.integrationtest.testhelper.InfrastructureIntegrationTestBase;

import static io.restassured.RestAssured.given;

class CheckRegistrationTest extends InfrastructureIntegrationTestBase {

    @MockBean
    private AuthenticationSpi authenticationSpi;

    @Test
    void ok() throws Exception {
        final Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("scope", "message:read")
                .build();

        Mockito.when(this.jwtDecoder.decode(jwt.getTokenValue())).thenReturn(jwt);

        final String email = this.faker.internet().emailAddress();
        final String sub = this.factory.getAuthId();

        Mockito.when(this.authenticationSpi.getUserAuthentication())
                .thenReturn(UserAuthentication.builder().email(email).sub(sub).build());

        given().auth()
                .oauth2(jwt.getTokenValue())
                .get("/user/checkregistration")
                .then()
                .statusCode(200);

        Assertions.assertTrue(true);
    }
}
