package fr.bankwiz.server.infrastructure.integrationtest.testhelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import fr.bankwiz.server.infrastructure.spi.database.repository.UserEntityRepository;

@SpringBootTest
@ActiveProfiles(value = "test")
public abstract class InfrastructureIntegrationTestBase {

    @Autowired
    protected UserEntityRepository userEntityRepository;
    
}
