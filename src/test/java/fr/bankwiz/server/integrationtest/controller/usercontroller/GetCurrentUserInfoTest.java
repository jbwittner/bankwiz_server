package fr.bankwiz.server.integrationtest.controller.usercontroller;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class GetCurrentUserInfoTest extends IntegrationTestBase {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void getCurrentUserInfoOk() throws Exception {
        final User user = this.integrationTestFactory.getUser();

        final var result = this.client
                .doGet(IntegrationMVCClient.UriEnum.USER.getUri(), user.getAuthId())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final UserDTO userDTO = IntegrationMVCClient.convertMvcResultToResponseObject(result, UserDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(user.getUserId(), userDTO.getUserId()),
                () -> Assertions.assertEquals(user.getEmail(), userDTO.getEmail()),
                () -> Assertions.assertEquals(user.getFirstName(), userDTO.getFirstName()),
                () -> Assertions.assertEquals(user.getLastName(), userDTO.getLastName()));

    }

    @Test
    void userNotExist() throws Exception {
        /*
        final var result = this.client
                .doGet(IntegrationMVCClient.UriEnum.USER.getUri(), "user.getAuthId()")
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

         */
    }


}
