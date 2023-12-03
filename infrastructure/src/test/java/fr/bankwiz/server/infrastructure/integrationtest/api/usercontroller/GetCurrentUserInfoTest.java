package fr.bankwiz.server.infrastructure.integrationtest.api.usercontroller;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.oauth2.jwt.Jwt;

import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.model.data.UserAuthentication;
import fr.bankwiz.server.infrastructure.integrationtest.testhelper.InfrastructureIntegrationTestBase;

class GetCurrentUserInfoTest extends InfrastructureIntegrationTestBase {

    @Test
    void ok() throws Exception {
        User user = this.factory.getUser();
        Jwt jwt = this.mockAuthentification(user);

        Mockito.when(this.jwtDecoder.decode(anyString())).thenReturn(jwt);

        given().auth().oauth2(jwt.getTokenValue()).get("/user").then().statusCode(200);
        
        Assertions.assertTrue(true);
    }
    
}
