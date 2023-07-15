package fr.bankwiz.server.integrationtest.controller.usercontroller;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationMVCClient;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.repository.UserRepository;
import fr.bankwiz.server.security.AuthenticationFacade;
import fr.bankwiz.server.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class CheckRegistrationTest extends IntegrationTestBase {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void initDataBeforeEach() {
    }

    @Test
    void userNotRegistered() throws Exception {
        final String subject = "auth0|13546354";

        final AuthenticationFacade.IdData idData = new AuthenticationFacade.IdData();
        idData.setEmail(this.faker.internet().emailAddress());
        idData.setSub(subject);
        idData.setName(this.faker.name().username());
        idData.setFamilyName(this.faker.name().lastName());
        idData.setGivenName(this.faker.name().firstName());

        Mockito.when(this.authenticationFacade.getIdData()).thenReturn(idData);

        final var result = this.client.doGet(IntegrationMVCClient.UriEnum.USER_CHECKREGISTRATION.getUri(), subject).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        final UserDTO userDTO = IntegrationMVCClient.convertMvcResultToResponseObject(result, UserDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(idData.getGivenName(), userDTO.getFirstName()),
                () -> Assertions.assertEquals(idData.getFamilyName(), userDTO.getLastName()),
                () -> Assertions.assertEquals(idData.getEmail(), userDTO.getEmail()));

        final User userSaved = this.userRepository.findById(userDTO.getUserId()).orElseThrow();

        Assertions.assertAll(
                () -> Assertions.assertEquals(idData.getSub(), userSaved.getAuthId()),
                () -> Assertions.assertEquals(idData.getGivenName(), userSaved.getFirstName()),
                () -> Assertions.assertEquals(idData.getFamilyName(), userSaved.getLastName()),
                () -> Assertions.assertEquals(idData.getEmail(), userSaved.getEmail()));

    }
}
