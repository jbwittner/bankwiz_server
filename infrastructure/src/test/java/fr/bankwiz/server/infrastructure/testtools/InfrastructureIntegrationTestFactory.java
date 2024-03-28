package fr.bankwiz.server.infrastructure.testtools;

import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.spi.database.repository.UserEntityRepository;

public class InfrastructureIntegrationTestFactory extends InfrastructureUnitTestFactory {

    private final UserEntityRepository userEntityRepository;

    public InfrastructureIntegrationTestFactory(
            final InfrastructureFaker infrastructureFaker, final UserEntityRepository userEntityRepository) {
        super(infrastructureFaker);
        this.userEntityRepository = userEntityRepository;
    }

    public UserEntity getUserEntity() {
        final UserEntity userEntity = super.getUserEntity();
        return this.userEntityRepository.save(userEntity);
    }
}
