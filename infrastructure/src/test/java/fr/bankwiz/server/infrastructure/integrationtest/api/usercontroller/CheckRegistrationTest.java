package fr.bankwiz.server.infrastructure.integrationtest.api.usercontroller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import io.restassured.http.ContentType;

import java.util.Base64;

import fr.bankwiz.server.domain.model.data.UserAuthentication;
import fr.bankwiz.server.domain.spi.AuthenticationSpi;
import fr.bankwiz.server.infrastructure.integrationtest.testhelper.InfrastructureIntegrationTestBase;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class CheckRegistrationTest extends InfrastructureIntegrationTestBase {

    public static String clientId = "some_client_id";
    public static String redirectUri = "some_redirect_uri";
    public static String scope = "some_scope";
    public static String username = "some_email";
    public static String password = "some_password";

    @MockBean
    private AuthenticationSpi authenticationSpi;

    public static String encode(String str1, String str2) {
        return new String(Base64.getEncoder().encode((str1 + ":" + str2).getBytes()));
    }
    
    @Test
    void ok() throws Exception {
        /*
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
        */
        Jwt jwt = Jwt.withTokenValue("token")
        .header("alg", "none")
        .claim("scope", "message:read")
        .build();

        Mockito.when(this.jwtDecoder.decode(anyString())).thenReturn(jwt);
        Mockito.when(this.authenticationSpi.getUserAuthentication()).thenReturn(UserAuthentication.builder().email("email@toto.com").sub("clientId").build());
        
        //this.webTestClient.mutateWith(SecurityMockServerConfigurers.mockJwt()).get().uri("/user/checkregistration").exchange().expectStatus().is2xxSuccessful();
        MvcResult mvcResult = this.mvc.perform(get("/user/checkregistration").header("Authorization", "Bearer " + jwt.getTokenValue())).andDo(print())
        .andExpect(status().isOk())
        .andReturn();

        assertNotNull(mvcResult.getResponse().getContentAsString());

        var users = this.userEntityRepository.findAll();
        users.stream().forEach(user -> System.out.println("user: " + user));
        Assertions.assertTrue(true);
    }
}
