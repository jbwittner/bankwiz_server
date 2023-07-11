package fr.bankwiz.server.integrationtest.testhelper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTestBase {

    @ServiceConnection
    private static MySQLContainer MY_SQL_CONTAINER;

    @BeforeAll
    public static void beforeAll() {
        MY_SQL_CONTAINER = new MySQLContainer(DockerImageName.parse("mysql:8.0.33"));
        //MY_SQL_CONTAINER.withInitScript("databases.sql");
        MY_SQL_CONTAINER.start();
    }

    @Autowired
    protected IntegrationMVCClient client;

    /** Method launch before each test */
    @BeforeEach
    public void beforeEach() {
        this.initDataBeforeEach();
    }

    /** Method used to prepare the data of tests */
    protected abstract void initDataBeforeEach();
}
