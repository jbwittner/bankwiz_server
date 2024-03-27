package fr.bankwiz.server.infrastructure.unitest.spi.database.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.model.UserDomain;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.unitest.InfrastructureUnitTestBase;

class UserEntityTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void constructorTest() {
        final UserDomain userDomain = this.factory.getUserDomain();
        final UserEntity userEntity = new UserEntity(userDomain);

        Assertions.assertAll(
                () -> Assertions.assertEquals(userDomain.id(), userEntity.getId()),
                () -> Assertions.assertEquals(userDomain.authId(), userEntity.getAuthId()),
                () -> Assertions.assertEquals(userDomain.email(), userEntity.getEmail()));
    }

    @Test
    void toUserDomainTest() {
        final UserEntity userEntity = this.factory.getUserEntity();
        final UserDomain userDomain = userEntity.toUserDomain();

        Assertions.assertAll(
                () -> Assertions.assertEquals(userEntity.getId(), userDomain.id()),
                () -> Assertions.assertEquals(userEntity.getAuthId(), userDomain.authId()),
                () -> Assertions.assertEquals(userEntity.getEmail(), userDomain.email()));
    }
}
