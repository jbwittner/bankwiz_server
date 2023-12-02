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
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MySQLContainer;

import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.infrastructure.spi.database.repository.UserEntityRepository;
import io.restassured.RestAssured;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
@AutoConfigureWebTestClient
public abstract class InfrastructureIntegrationTestBase {

    @Autowired
	WebApplicationContext wac;

    static MySQLContainer<?> mySQLContainer;

    protected MockMvc mvc;

    @LocalServerPort
    private Integer port;

    @Autowired
    protected UserEntityRepository userEntityRepository;

    @MockBean
	// mock the JwtDecoder so that the jwks is not resolved since no AuthZ Server Setup
	protected JwtDecoder jwtDecoder;

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
        RestAssured.baseURI = "http://localhost:" + port;
        mvc = MockMvcBuilders
				.webAppContextSetup(this.wac)
				.apply(springSecurity()) 
				.build();
    }

    protected Jwt mockJwt(User user){
        Jwt jwt = Jwt.withTokenValue("token")
        .header("alg", "none")
        .claim("scope", "message:read")
        .build();

        Mockito.when(this.jwtDecoder.decode(anyString())).thenReturn(jwt);
        
        return jwt;
    }
    
    
}
