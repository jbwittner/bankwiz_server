package fr.bankwiz.server.infrastructure.unittest.spi.userspiimpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.UserDomain;
import fr.bankwiz.server.domain.spi.UserSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.transformer.UserTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class SaveTest extends InfrastructureUnitTestBase {

    private UserSpi userSpi;

    @Override
    protected void initDataBeforeEach() {
        this.userSpi = this.buildUserSpiImpl();
    }

    @Test
    void save() {

        this.userEntityRepositoryMockFactory.mockSave();

        final UserDomain user = this.factory.getUser();

        final UserDomain userSaved = this.userSpi.save(user);

        final var argumentCaptor = this.userEntityRepositoryMockFactory.verifySaveCalled(UserEntity.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(UserTransformer.toUserEntity(user), argumentCaptor.getValue()),
                () -> Assertions.assertEquals(user.getAuthId(), userSaved.getAuthId()),
                () -> Assertions.assertEquals(user.getEmail(), userSaved.getEmail()),
                () -> Assertions.assertEquals(user.getId(), userSaved.getId()));
    }
}
