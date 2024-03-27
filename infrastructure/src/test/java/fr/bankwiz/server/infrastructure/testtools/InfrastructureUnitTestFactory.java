package fr.bankwiz.server.infrastructure.testtools;

import java.util.UUID;

import fr.bankwiz.server.domain.testhelper.DomainUnitTestFactory;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;

public class InfrastructureUnitTestFactory extends DomainUnitTestFactory {

    protected final InfrastructureFaker faker;

    public InfrastructureUnitTestFactory(final InfrastructureFaker infrastructureFaker) {
        super(infrastructureFaker);
        this.faker = infrastructureFaker;
    }

    public UserEntity getUserEntity() {
        return new UserEntity(
                UUID.randomUUID(), this.getAuthId(), this.faker.internet().emailAddress());
    }
}
