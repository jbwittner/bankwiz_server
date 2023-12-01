package fr.bankwiz.server.infrastructure.integrationtest.testhelper;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;

import fr.bankwiz.server.infrastructure.spi.database.repository.UserEntityRepository;

@SpringBootTest
@ActiveProfiles(value = "test")
public abstract class InfrastructureIntegrationTestBase {

    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.33");

    @BeforeAll
    static void beforeAll() {
        mySQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        mySQLContainer.stop();
    }

    @Autowired
    protected UserEntityRepository userEntityRepository;
    
}
