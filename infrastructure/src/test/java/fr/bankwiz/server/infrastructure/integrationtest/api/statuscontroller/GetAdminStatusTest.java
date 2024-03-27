package fr.bankwiz.server.infrastructure.integrationtest.api.statuscontroller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.oauth2.jwt.Jwt;

import fr.bankwiz.server.infrastructure.integrationtest.InfrastructureIntegrationTestBase;

import static io.restassured.RestAssured.given;

public class GetAdminStatusTest extends InfrastructureIntegrationTestBase {

    @Test
    void withoutAuthentification() {
        given().get("/status/admin").then().statusCode(401);
    }

    @Test
    void withAuthentification() {
        final Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("permissions", new ArrayList<>())
                .build();

        Mockito.when(this.jwtDecoder.decode(jwt.getTokenValue())).thenReturn(jwt);

        given().auth().oauth2(jwt.getTokenValue()).get("/status/admin").then().statusCode(403);
    }

    @Test
    void withAdminScope() {
        final List<String> permissions = new ArrayList<>();
        permissions.add("admin:configuration");

        final Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("permissions", permissions)
                .build();

        Mockito.when(this.jwtDecoder.decode(jwt.getTokenValue())).thenReturn(jwt);

        given().auth().oauth2(jwt.getTokenValue()).get("/status/admin").then().statusCode(200);
    }
}
