package fr.bankwiz.server.integrationtest.controller.usercontroller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.dto.UserDTOBuilder;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.repository.UserRepository;

public class GetUsersTest extends IntegrationTestBase {

    private static final UserDTOBuilder USER_DTO_BUILDER = new UserDTOBuilder();

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void getUsersOk() throws Exception {
        final User user1 = this.integrationTestFactory.getUser();
        final User user2 = this.integrationTestFactory.getUser();
        final User user3 = this.integrationTestFactory.getUser();

        final List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        final List<UserDTO> userDTOs = USER_DTO_BUILDER.transformAll(users);

        final var result = this.client
                .doGet(IntegrationMVCClient.UriEnum.USERS.getUri(), "")
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final var dataResult = IntegrationMVCClient.convertMvcResultToListOfResponseObjects(result, UserDTO.class);

        Assertions.assertEquals(userDTOs, dataResult);
    }
}
