package fr.bankwiz.server.infrastructure.integrationtest.api.statuscontroller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.oauth2.jwt.Jwt;

import fr.bankwiz.server.infrastructure.integrationtest.testhelper.InfrastructureIntegrationTestBase;

import static io.restassured.RestAssured.given;

class GetPrivateStatusTest extends InfrastructureIntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void withoutAuthentification() {
        given().log().all().get("/status/private").then().statusCode(401);
    }

    @Test
    void withAuthentification() {
        final Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("permissions", new ArrayList<>())
                .build();

        Mockito.when(this.jwtDecoder.decode(jwt.getTokenValue())).thenReturn(jwt);

        given().log()
                .all()
                .auth()
                .oauth2(jwt.getTokenValue())
                .get("/status/private")
                .then()
                .statusCode(200);
    }

    @Test
    void withAdminScope() {
        List<String> permissions = new ArrayList<>();
        permissions.add("admin:configuration");

        final Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("permissions", permissions)
                .build();

        Mockito.when(this.jwtDecoder.decode(jwt.getTokenValue())).thenReturn(jwt);

        given().log()
                .all()
                .auth()
                .oauth2(jwt.getTokenValue())
                .get("/status/private")
                .then()
                .statusCode(200);
    }
}
