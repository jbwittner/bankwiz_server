package fr.bankwiz.server.infrastructure.spi.userspiimpl;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.infrastructure.spi.UserSpiImpl;
import fr.bankwiz.server.infrastructure.spi.database.entity.UserEntity;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.testhelper.mock.repository.UserEntityRepositoryMockFactory;

class FindByAuthIdTest extends InfrastructureUnitTestBase {

    private UserSpiImpl userSpiImpl;
    private UserEntityRepositoryMockFactory userEntityRepositoryMockFactory;

    @Override
    protected void initDataBeforeEach() {
        this.userEntityRepositoryMockFactory = new UserEntityRepositoryMockFactory();
        this.userSpiImpl = new UserSpiImpl(userEntityRepositoryMockFactory.getRepository());
    }

    @Test
    void userExist() {

        final UserEntity userEntity = this.factory.getUserEntity();

        final String authId = userEntity.getAuthId();

        this.userEntityRepositoryMockFactory.mockFindByAuthId(authId, Optional.of(userEntity));

        final Optional<User> optionalUser = this.userSpiImpl.findByAuthId(authId);

        Assertions.assertTrue(optionalUser.isPresent());

        final User userFind = optionalUser.get();

        Assertions.assertAll(
                () -> Assertions.assertEquals(userEntity.getAuthId(), userFind.getAuthId()),
                () -> Assertions.assertEquals(userEntity.getEmail(), userFind.getEmail()),
                () -> Assertions.assertEquals(userEntity.getUserId(), userFind.getUserUuid()));
    }

    @Test
    void userNotExist() {

        final Optional<User> optionalUser =
                this.userSpiImpl.findByAuthId(this.faker.pokemon().name());

        Assertions.assertTrue(optionalUser.isEmpty());
    }
}
