package fr.bankwiz.server.infrastructure.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import fr.bankwiz.openapi.api.UserApi;
import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.infrastructure.service.UserInfraService;

@Controller
public class UserController implements UserApi {

    private UserInfraService userInfraService;

    public UserController(UserInfraService userInfraService) {
        this.userInfraService = userInfraService;
    }

    @Override
    public ResponseEntity<UserDTO> checkRegistration() {
        UserDTO userDTO = userInfraService.createUser(1L);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDTO> getCurrentUserInfo() {
        return UserApi.super.getCurrentUserInfo();
    }
}
