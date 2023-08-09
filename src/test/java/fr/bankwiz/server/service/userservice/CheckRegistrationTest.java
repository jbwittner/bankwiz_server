package fr.bankwiz.server.service.userservice;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.security.AuthenticationFacade;
import fr.bankwiz.server.service.UserService;
import fr.bankwiz.server.testhelper.UnitTestBase;

class CheckRegistrationTest extends UnitTestBase {
    private UserService userService;

    private AuthenticationFacade.IdData idData;

    @Override
    protected void initDataBeforeEach() {
        this.userService = new UserService(
                this.authenticationFacadeMockFactory.getAuthenticationFacade(),
                this.userRepositoryMockFactory.getRepository());

        this.idData = new AuthenticationFacade.IdData();
        idData.setEmail(this.faker.internet().emailAddress());
        idData.setSub(this.faker.pokemon().name());
        idData.setName(this.faker.name().username());
        idData.setFamilyName(this.faker.name().lastName());
        idData.setGivenName(this.faker.name().firstName());
    }

    @Test
    void userAlreadyExist() {
        this.authenticationFacadeMockFactory.mockGetIdData(idData);
        final User userBefore = this.unitTestFactory.getUser();
        this.userRepositoryMockFactory
                .mockFindByAuthId(idData.getSub(), Optional.of(userBefore))
                .mockSave();
        final UserDTO userDTO = this.userService.checkRegistration();

        var argumentCaptor = this.userRepositoryMockFactory.verifySaveCalled(User.class);
        final User userSaved = argumentCaptor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(this.idData.getSub(), userSaved.getAuthId()),
                () -> Assertions.assertEquals(this.idData.getGivenName(), userSaved.getFirstName()),
                () -> Assertions.assertEquals(this.idData.getFamilyName(), userSaved.getLastName()),
                () -> Assertions.assertEquals(this.idData.getEmail(), userSaved.getEmail()));

        Assertions.assertAll(
                () -> Assertions.assertEquals(this.idData.getGivenName(), userDTO.getFirstName()),
                () -> Assertions.assertEquals(this.idData.getFamilyName(), userDTO.getLastName()),
                () -> Assertions.assertEquals(this.idData.getEmail(), userDTO.getEmail()));
    }

    @Test
    void userNotRegistered() {
        this.authenticationFacadeMockFactory.mockGetIdData(idData);
        this.userRepositoryMockFactory
                .mockFindByAuthId(idData.getSub(), Optional.empty())
                .mockSave();
        final UserDTO userDTO = this.userService.checkRegistration();

        var argumentCaptor = this.userRepositoryMockFactory.verifySaveCalled(User.class);
        final User userSaved = argumentCaptor.getValue();

        Assertions.assertAll(
                () -> Assertions.assertEquals(this.idData.getSub(), userSaved.getAuthId()),
                () -> Assertions.assertEquals(this.idData.getGivenName(), userSaved.getFirstName()),
                () -> Assertions.assertEquals(this.idData.getFamilyName(), userSaved.getLastName()),
                () -> Assertions.assertEquals(this.idData.getEmail(), userSaved.getEmail()));

        Assertions.assertAll(
                () -> Assertions.assertEquals(this.idData.getGivenName(), userDTO.getFirstName()),
                () -> Assertions.assertEquals(this.idData.getFamilyName(), userDTO.getLastName()),
                () -> Assertions.assertEquals(this.idData.getEmail(), userDTO.getEmail()));
    }
}
