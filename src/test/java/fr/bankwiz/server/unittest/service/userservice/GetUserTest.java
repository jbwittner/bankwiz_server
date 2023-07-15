package fr.bankwiz.server.unittest.service.userservice;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.exception.UserNotExistException;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.service.UserService;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

class GetUserTest extends UnitTestBase {
    private UserService userService;

    @Override
    protected void initDataBeforeEach() {
        this.userService = new UserService(null, this.userRepositoryMockFactory.getRepository());
    }

    @Test
    void getUserOk() {
        final User user = this.unitTestFactory.getUser();
        this.userRepositoryMockFactory.mockFindById(user.getUserId(), Optional.of(user));
        final UserDTO userDto = this.userService.getUser(user.getUserId());

        Assertions.assertAll(
                () -> Assertions.assertEquals(user.getUserId(), userDto.getUserId()),
                () -> Assertions.assertEquals(user.getFirstName(), userDto.getFirstName()),
                () -> Assertions.assertEquals(user.getLastName(), userDto.getLastName()),
                () -> Assertions.assertEquals(user.getEmail(), userDto.getEmail()));
    }

    @Test
    void userNotExist() {
        final Integer id = this.faker.random().nextInt(Integer.MAX_VALUE);
        this.userRepositoryMockFactory.mockFindById(id, Optional.empty());
        Assertions.assertThrows(UserNotExistException.class, () -> {
            this.userService.getUser(id);
        });
    }
}
