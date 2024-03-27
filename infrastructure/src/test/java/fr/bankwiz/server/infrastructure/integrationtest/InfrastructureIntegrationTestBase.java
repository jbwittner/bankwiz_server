package fr.bankwiz.server.infrastructure.integrationtest;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import fr.bankwiz.server.infrastructure.testtools.InfrastructureFaker;
import fr.bankwiz.server.infrastructure.testtools.InfrastructureIntegrationTestFactory;
import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
public class InfrastructureIntegrationTestBase {

    protected InfrastructureIntegrationTestFactory factory;
    protected InfrastructureFaker faker;

    @LocalServerPort
    private Integer port;

    @MockBean
    // mock the JwtDecoder so that the jwks is not resolved since no AuthZ Server Setup
    protected JwtDecoder jwtDecoder;

    static PostgreSQLContainer<?> pgContainer;

    static {
        pgContainer = new PostgreSQLContainer<>("pgvector/pgvector:pg16");
        pgContainer.start();
    }

    @DynamicPropertySource
    static void configureProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", pgContainer::getJdbcUrl);
        registry.add("spring.datasource.username", pgContainer::getUsername);
        registry.add("spring.datasource.password", pgContainer::getPassword);
    }

    @BeforeEach
    void setUp() {
        this.faker = new InfrastructureFaker();
        this.factory = new InfrastructureIntegrationTestFactory(faker);
        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
