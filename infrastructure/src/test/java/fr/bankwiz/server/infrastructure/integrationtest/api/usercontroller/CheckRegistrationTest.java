package fr.bankwiz.server.infrastructure.integrationtest.api.usercontroller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

import fr.bankwiz.server.infrastructure.integrationtest.testhelper.InfrastructureIntegrationTestBase;
import io.restassured.RestAssured;

class CheckRegistrationTest extends InfrastructureIntegrationTestBase {
    
    @Test
    void ok() {
        var users = this.userEntityRepository.findAll();
        users.stream().forEach(user -> System.out.println("user: " + user));
        Assertions.assertTrue(true);
    }
}
