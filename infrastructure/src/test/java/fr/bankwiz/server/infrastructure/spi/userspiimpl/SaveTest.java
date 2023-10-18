package fr.bankwiz.server.infrastructure.spi.userspiimpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.infrastructure.spi.UserSpiImpl;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.testhelper.mock.repository.UserEntityRepositoryMockFactory;

class SaveTest extends InfrastructureUnitTestBase {

    private UserSpiImpl userSpiImpl;
    private UserEntityRepositoryMockFactory userEntityRepositoryMockFactory;

    @Override
    protected void initDataBeforeEach() {
        this.userEntityRepositoryMockFactory = new UserEntityRepositoryMockFactory();
        this.userSpiImpl = new UserSpiImpl(userEntityRepositoryMockFactory.getRepository());
    }

    @Test
    void save() {

        this.userEntityRepositoryMockFactory.mockSave();

        final User user = this.factory.getUser();

        this.userSpiImpl.save(user);

        final User userSaved = this.userSpiImpl.save(user);

        Assertions.assertAll(
                () -> Assertions.assertEquals(user.getAuthId(), userSaved.getAuthId()),
                () -> Assertions.assertEquals(user.getEmail(), userSaved.getEmail()),
                () -> Assertions.assertEquals(user.getUserUuid(), userSaved.getUserUuid()));
    }
}
