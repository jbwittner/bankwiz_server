package fr.bankwiz.server.infrastructure.integrationtest.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;

import fr.bankwiz.openapi.model.FunctionalExceptionDTO;
import fr.bankwiz.server.domain.exception.FunctionalException;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.spi.AuthenticationSpi;
import fr.bankwiz.server.infrastructure.integrationtest.testhelper.InfrastructureIntegrationTestBase;

import static io.restassured.RestAssured.given;

class GlobalExceptionHandlerTest extends InfrastructureIntegrationTestBase {

    @MockBean
    private AuthenticationSpi authenticationSpi;

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void exception() {
        final User user = this.factory.getUser();
        final Jwt jwt = this.mockAuthentification(user);

        final String attribute_1 = this.faker.superhero().name();
        final Integer attribute_2 = this.faker.random().nextInt(Integer.MAX_VALUE);
        final User attribute_3 = this.factory.getUser();
        final List<String> attribute_4 = new ArrayList<>();
        attribute_4.add(this.faker.space().constellation());
        attribute_4.add(this.faker.space().constellation());
        attribute_4.add(this.faker.space().constellation());

        final FunctionalException exception =
                new TestFunctionalException(attribute_1, attribute_2, attribute_3, attribute_4);

        Mockito.when(this.authenticationSpi.getUserAuthentication()).thenThrow(exception);

        final FunctionalExceptionDTO response = given().auth()
                .oauth2(jwt.getTokenValue())
                .get("/user/checkregistration")
                .then()
                .assertThat()
                .statusCode(400)
                .extract()
                .as(FunctionalExceptionDTO.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        Assertions.assertEquals("uri=/user/checkregistration", response.getDetails());
        Assertions.assertEquals(TestFunctionalException.class.getSimpleName(), response.getException());

        @SuppressWarnings("unchecked")
        final Map<String, Object> map = (Map<String, Object>) response.getAttributes();

        Assertions.assertEquals(attribute_1, map.get("attribute_1"));
        
        Assertions.assertEquals(attribute_2, map.get("attribute_2"));

        String atribute_3_string = String.format(
                "{id=%s, authId=%s, email=%s}", attribute_3.getId(), attribute_3.getAuthId(), attribute_3.getEmail());
                
        Assertions.assertEquals(atribute_3_string, map.get("attribute_3").toString());

        Assertions.assertEquals(attribute_4, map.get("attribute_4"));

        Assertions.assertEquals(exception.getMessage(), response.getMessage());
    }

    class TestFunctionalException extends FunctionalException {

        public TestFunctionalException(
                String attribute_1, Integer attribute_2, Object attribute_3, List<String> attribute_4) {
            super("TestFunctionalException : "
                    + attribute_1
                    + " // "
                    + attribute_2
                    + " // "
                    + attribute_3
                    + " // "
                    + attribute_4);
            this.attributes.put("attribute_1", attribute_1);
            this.attributes.put("attribute_2", attribute_2);
            this.attributes.put("attribute_3", attribute_3);
            this.attributes.put("attribute_4", attribute_4);
        }
    }
}
