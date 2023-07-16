package fr.bankwiz.server.unittest.service.userservice;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.dto.UserDTOBuilder;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.service.UserService;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

class GetUsersTest extends UnitTestBase {
    private UserService userService;

    private static final UserDTOBuilder USER_DTO_BUILDER = new UserDTOBuilder();

    @Override
    protected void initDataBeforeEach() {
        this.userService = new UserService(null, this.userRepositoryMockFactory.getRepository());
    }

    @Test
    void getUserOk() {
        final User user1 = this.unitTestFactory.getUser();
        final User user2 = this.unitTestFactory.getUser();
        final User user3 = this.unitTestFactory.getUser();
        final List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        this.userRepositoryMockFactory.mockFindAll(users);
        final List<UserDTO> userDTOs = this.userService.getUsers();

        Assertions.assertEquals(users.size(), userDTOs.size());

        final List<UserDTO> userDTOsExpected = USER_DTO_BUILDER.transformAll(users);

        Assertions.assertEquals(userDTOsExpected, userDTOs);
    }
}
