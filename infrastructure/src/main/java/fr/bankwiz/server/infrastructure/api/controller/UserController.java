package fr.bankwiz.server.infrastructure.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import fr.bankwiz.openapi.api.UserApi;
import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.infrastructure.service.UserInfraService;
import fr.bankwiz.server.infrastructure.transformer.UserTransformer;

@Controller
public class UserController implements UserApi {

    private UserInfraService userInfraService;

    public UserController(UserInfraService userInfraService) {
        this.userInfraService = userInfraService;
    }

    @Override
    public ResponseEntity<UserDTO> checkRegistration() {
        final User user = userInfraService.checkRegistration();
        final UserDTO userDTO = UserTransformer.toUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDTO> getCurrentUserInfo() {
        final User user = userInfraService.getCurrentUser();
        final UserDTO userDTO = UserTransformer.toUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
