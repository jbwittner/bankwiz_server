package fr.bankwiz.server.infrastructure.integrationtest.testhelper;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.testhelper.tools.DomainFaker;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
@AutoConfigureWebTestClient
public abstract class InfrastructureIntegrationTestBase {

    @Autowired
    protected InfrastructureIntegrationTestFactory factory;

    @LocalServerPort
    private Integer port;

    @MockBean
    // mock the JwtDecoder so that the jwks is not resolved since no AuthZ Server Setup
    protected JwtDecoder jwtDecoder;

    protected DomainFaker faker;
    static MySQLContainer<?> mySQLContainer;

    static {
        mySQLContainer = new MySQLContainer<>("mysql:8.0.33");
        mySQLContainer.withInitScript("database.sql");
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @BeforeAll
    static void beforeAll() {
        mySQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        mySQLContainer.stop();
    }

    @BeforeEach
    void setUp() {
        this.faker = new DomainFaker();
        this.factory.setFaker(this.faker);
        RestAssured.baseURI = "http://localhost:" + port;
    }

    protected Jwt mockAuthentification(User user) {
        List<String> permissions = new ArrayList<>();

        final Jwt jwt = Jwt.withTokenValue(this.faker.superhero().descriptor())
                .header("alg", "none")
                .claim("scope", "openid profile email")
                .claim("permissions", permissions)
                .subject(user.getAuthId())
                .build();

        Mockito.when(this.jwtDecoder.decode(jwt.getTokenValue())).thenReturn(jwt);
        return jwt;
    }

    protected Jwt mockAuthentification(UserEntity userEntity) {
        List<String> permissions = new ArrayList<>();

        final Jwt jwt = Jwt.withTokenValue(this.faker.superhero().descriptor())
                .header("alg", "none")
                .claim("scope", "openid profile email")
                .claim("permissions", permissions)
                .subject(userEntity.getAuthId())
                .build();

        Mockito.when(this.jwtDecoder.decode(jwt.getTokenValue())).thenReturn(jwt);
        return jwt;
    }
}
