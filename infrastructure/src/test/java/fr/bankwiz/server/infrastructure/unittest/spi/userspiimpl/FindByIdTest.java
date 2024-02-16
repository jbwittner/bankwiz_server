package fr.bankwiz.server.infrastructure.unittest.spi.userspiimpl;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.UserDomain;
import fr.bankwiz.server.domain.spi.UserSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class FindByIdTest extends InfrastructureUnitTestBase {

    private UserSpi userSpi;

    @Override
    protected void initDataBeforeEach() {
        this.userSpi = this.buildUserSpiImpl();
    }

    @Test
    void userExist() {

        final UserEntity userEntity = this.factory.getUserEntity();

        final UUID userId = userEntity.getId();

        this.userEntityRepositoryMockFactory.mockFindById(userId, Optional.of(userEntity));

        final Optional<UserDomain> optionalUser = this.userSpi.findById(userId);

        Assertions.assertTrue(optionalUser.isPresent());

        final UserDomain userFind = optionalUser.get();

        Assertions.assertAll(
                () -> Assertions.assertEquals(userEntity.getAuthId(), userFind.getAuthId()),
                () -> Assertions.assertEquals(userEntity.getEmail(), userFind.getEmail()),
                () -> Assertions.assertEquals(userEntity.getId(), userFind.getId()));
    }

    @Test
    void userNotExist() {

        final Optional<UserDomain> optionalUser = this.userSpi.findById(UUID.randomUUID());

        Assertions.assertTrue(optionalUser.isEmpty());
    }
}
