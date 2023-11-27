package fr.bankwiz.server.infrastructure.spi.userspiimpl;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.spi.UserSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;

class FindByAuthIdTest extends InfrastructureUnitTestBase {

    private UserSpi userSpi;

    @Override
    protected void initDataBeforeEach() {
        this.userSpi = this.buildUserSpiImpl();
    }

    @Test
    void userExist() {

        final UserEntity userEntity = this.factory.getUserEntity();

        final String authId = userEntity.getAuthId();

        this.userEntityRepositoryMockFactory.mockFindByAuthId(authId, Optional.of(userEntity));

        final Optional<User> optionalUser = this.userSpi.findByAuthId(authId);

        Assertions.assertTrue(optionalUser.isPresent());

        final User userFind = optionalUser.get();

        Assertions.assertAll(
                () -> Assertions.assertEquals(userEntity.getAuthId(), userFind.getAuthId()),
                () -> Assertions.assertEquals(userEntity.getEmail(), userFind.getEmail()),
                () -> Assertions.assertEquals(userEntity.getId(), userFind.getId()));
    }

    @Test
    void userNotExist() {

        final Optional<User> optionalUser =
                this.userSpi.findByAuthId(this.faker.pokemon().name());

        Assertions.assertTrue(optionalUser.isEmpty());
    }
}
