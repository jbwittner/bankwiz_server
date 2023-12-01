package fr.bankwiz.server.infrastructure.integrationtest.api.usercontroller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import io.restassured.http.ContentType;

import java.util.Base64;

import fr.bankwiz.server.infrastructure.integrationtest.testhelper.InfrastructureIntegrationTestBase;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

class CheckRegistrationTest extends InfrastructureIntegrationTestBase {

    public static String clientId = "some_client_id";
    public static String redirectUri = "some_redirect_uri";
    public static String scope = "some_scope";
    public static String username = "some_email";
    public static String password = "some_password";

    public static String encode(String str1, String str2) {
        return new String(Base64.getEncoder().encode((str1 + ":" + str2).getBytes()));
    }
    
    @Test
    void ok() {
        String authorization = encode(username, password);
        var test = given().header("authorization", "Basic " + authorization)
        .contentType(ContentType.URLENC)
        .formParam("response_type", "code")
        .queryParam("client_id", clientId)
        .queryParam("redirect_uri", redirectUri)
        .queryParam("scope", scope)
        .post("/oauth2/authorize")
        .then()
        .statusCode(200)
        .extract()
        .response();

        System.out.println(test);
        //this.webTestClient.mutateWith(SecurityMockServerConfigurers.mockJwt()).get().uri("/user/checkregistration").exchange().expectStatus().is2xxSuccessful();
        //this.webTestClient.get().uri("/user/checkregistration").exchange().expectStatus().is2xxSuccessful();
        var users = this.userEntityRepository.findAll();
        users.stream().forEach(user -> System.out.println("user: " + user));
        Assertions.assertTrue(true);
    }
}
