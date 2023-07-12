package fr.bankwiz.server.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import fr.bankwiz.openapi.api.UserApi;
import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.openapi.model.UserUpdateRequest;
import fr.bankwiz.server.service.UserService;

@RestController
public class UserController implements UserApi {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserDTO> checkRegistration() {
        final UserDTO userDTO = this.userService.checkRegistration();
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDTO> getCurrentUserInfo() {
        final UserDTO userDTO = this.userService.getCurrentUserInfo();
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDTO> getUser(Integer userId) {
        final UserDTO userDTO = this.userService.getUser(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<UserDTO>> getUsers() {
        final List<UserDTO> userDTOs = this.userService.getUsers();
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDTO> updateUser(UserUpdateRequest userUpdateRequest) {
        final UserDTO userDTO = this.userService.updateUser(userUpdateRequest);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
