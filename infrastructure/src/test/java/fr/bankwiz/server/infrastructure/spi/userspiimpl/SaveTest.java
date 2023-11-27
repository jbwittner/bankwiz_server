package fr.bankwiz.server.infrastructure.spi.userspiimpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.spi.UserSpi;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.transformer.UserTransformer;

class SaveTest extends InfrastructureUnitTestBase {

    private UserSpi userSpi;

    @Override
    protected void initDataBeforeEach() {
        this.userSpi = this.buildUserSpiImpl();
    }

    @Test
    void save() {

        this.userEntityRepositoryMockFactory.mockSave();

        final User user = this.factory.getUser();

        final User userSaved = this.userSpi.save(user);

        final var argumentCaptor = this.userEntityRepositoryMockFactory.verifySaveCalled(UserEntity.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(UserTransformer.toUserEntity(user), argumentCaptor.getValue()),
                () -> Assertions.assertEquals(user.getAuthId(), userSaved.getAuthId()),
                () -> Assertions.assertEquals(user.getEmail(), userSaved.getEmail()),
                () -> Assertions.assertEquals(user.getId(), userSaved.getId()));
    }
}
