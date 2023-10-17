package fr.bankwiz.server.infrastructure.spi.userspiimpl;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.infrastructure.spi.UserSpiImpl;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;

class FindByAuthIdTest extends InfrastructureUnitTestBase {

    private UserSpiImpl userSpiImpl;

    @Override
    protected void initDataBeforeEach() {
        this.userSpiImpl = new UserSpiImpl();
    }

    @Test
    void userExist() {

        final User user = this.factory.getUser();

        this.userSpiImpl.save(user);

        final Optional<User> optionalUser = this.userSpiImpl.findByAuthId(user.getAuthId());

        Assertions.assertTrue(optionalUser.isPresent());

        final User userFind = optionalUser.get();

        Assertions.assertAll(
                () -> Assertions.assertEquals(user.getAuthId(), userFind.getAuthId()),
                () -> Assertions.assertEquals(user.getEmail(), userFind.getEmail()),
                () -> Assertions.assertEquals(user.getUserUuid(), userFind.getUserUuid()));
    }

    @Test
    void userNotExist() {

        final Optional<User> optionalUser =
                this.userSpiImpl.findByAuthId(this.faker.pokemon().name());

        Assertions.assertTrue(optionalUser.isEmpty());
    }
}
