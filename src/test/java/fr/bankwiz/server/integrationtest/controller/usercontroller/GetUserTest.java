package fr.bankwiz.server.integrationtest.controller.usercontroller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.exception.UserNotExistException;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.User;

class GetUserTest extends IntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void getUserOk() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final User user2 = this.integrationTestFactory.getUser();

        final var result = this.client
                .doGet(IntegrationMVCClient.UriEnum.USER_ID.getUri(user2.getUserId()), user.getAuthId())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final UserDTO userDTO = IntegrationMVCClient.convertMvcResultToResponseObject(result, UserDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(user2.getUserId(), userDTO.getUserId()),
                () -> Assertions.assertEquals(user2.getFirstName(), userDTO.getFirstName()),
                () -> Assertions.assertEquals(user2.getLastName(), userDTO.getLastName()),
                () -> Assertions.assertEquals(user2.getEmail(), userDTO.getEmail()));
    }

    @Test
    void userNotExist() throws Exception {
        final User user = this.integrationTestFactory.getUser();
        final Integer id = user.getUserId() + 1;
        final String uri = IntegrationMVCClient.UriEnum.USER_ID.getUri(id);
        final var result = this.client.doGet(uri, user.getAuthId());

        final UserNotExistException userNotExistException = new UserNotExistException(id);

        IntegrationMVCClient.checkResponseFunctionalException(result, uri, userNotExistException);
    }
}
