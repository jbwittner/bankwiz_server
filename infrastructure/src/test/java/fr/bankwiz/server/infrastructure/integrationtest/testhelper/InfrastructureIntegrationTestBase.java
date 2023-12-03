package fr.bankwiz.server.infrastructure.integrationtest.testhelper;

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
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MySQLContainer;

import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.testhelper.tools.DomainFaker;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import io.restassured.RestAssured;

import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
@AutoConfigureWebTestClient
public abstract class InfrastructureIntegrationTestBase {

    @Autowired
	private WebApplicationContext wac;

    @Autowired
    protected InfrastructureIntegrationTestFactory factory;

    static MySQLContainer<?> mySQLContainer;

    @LocalServerPort
    private Integer port;

    @MockBean
	// mock the JwtDecoder so that the jwks is not resolved since no AuthZ Server Setup
	protected JwtDecoder jwtDecoder;

    protected DomainFaker faker;

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
    void setUp() {
        this.faker = new DomainFaker();
        this.factory.setFaker(faker);
        RestAssured.baseURI = "http://localhost:" + port;
    }

    protected Jwt mockAuthentification(User user){
        Jwt jwt = Jwt.withTokenValue("token")
        .header("alg", "none")
        .claim("scope", "message:read")
        .subject(user.getAuthId())
        .build();
        Mockito.when(this.jwtDecoder.decode(anyString())).thenReturn(jwt);
        return jwt;
    }

    protected Jwt mockAuthentification(UserEntity userEntity){
        Jwt jwt = Jwt.withTokenValue("token")
        .header("alg", "none")
        .claim("scope", "message:read")
        .subject(userEntity.getAuthId())
        .build();
        Mockito.when(this.jwtDecoder.decode(anyString())).thenReturn(jwt);
        return jwt;
    }
    
    
}
