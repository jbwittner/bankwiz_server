package fr.bankwiz.server.infrastructure.integrationtest.testhelper;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MySQLContainer;

import fr.bankwiz.server.infrastructure.spi.database.repository.UserEntityRepository;
import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
@AutoConfigureWebTestClient
public abstract class InfrastructureIntegrationTestBase {

    static MySQLContainer<?> mySQLContainer;

    protected WebTestClient webTestClient;

    @LocalServerPort
    private Integer port;

    @Autowired
    protected UserEntityRepository userEntityRepository;

    static {
        mySQLContainer = new MySQLContainer<>("mysql:8.0.33");
        mySQLContainer.withInitScript("database.sql");
    }

    @BeforeAll
    static void beforeAll() {
        mySQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        mySQLContainer.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @BeforeEach
    void setUp(ApplicationContext context) {
        RestAssured.baseURI = "http://localhost:" + port;
        webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }
    
    
}
