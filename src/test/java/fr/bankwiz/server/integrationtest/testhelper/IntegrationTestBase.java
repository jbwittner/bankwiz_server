package fr.bankwiz.server.integrationtest.testhelper;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import fr.bankwiz.server.PersonalFaker;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public abstract class IntegrationTestBase {

    protected PersonalFaker faker;

    @Autowired
    protected IntegrationTestFactory integrationTestFactory;

    @LocalServerPort
    protected int port;

    @ServiceConnection
    private static final MySQLContainer<?> MY_SQL_CONTAINER;

    private static final String MYSQL_IMAGE_NAME = "mysql:8.0.33";

    static {
        MY_SQL_CONTAINER = new MySQLContainer<>(DockerImageName.parse(MYSQL_IMAGE_NAME));
        MY_SQL_CONTAINER.start();
    }

    @Autowired
    protected IntegrationMVCClient client;

    /** Method launch before each test */
    @BeforeEach
    public void beforeEach() {
        this.faker = new PersonalFaker();
        this.integrationTestFactory.setFaker(this.faker);
        this.initDataBeforeEach();
    }

    /** Method used to prepare the data of tests */
    protected abstract void initDataBeforeEach();
}
