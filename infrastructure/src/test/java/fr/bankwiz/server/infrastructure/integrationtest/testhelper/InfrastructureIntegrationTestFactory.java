package fr.bankwiz.server.infrastructure.integrationtest.testhelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.testhelper.tools.DomainUnitTestFactory;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.UserEntityRepository;
import fr.bankwiz.server.infrastructure.transformer.UserTransformer;


@Component
public class InfrastructureIntegrationTestFactory extends DomainUnitTestFactory {

    @Autowired
    private UserEntityRepository userEntityRepository;

    public InfrastructureIntegrationTestFactory() {}

    public User getUser() {
        final User user = super.getUser();
        final UserEntity userEntity = UserTransformer.toUserEntity(user);
        this.userEntityRepository.save(userEntity);
        return user;
    }
    
}
