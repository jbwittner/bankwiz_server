package fr.bankwiz.server.infrastructure.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import fr.bankwiz.openapi.api.UserApi;
import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.domain.api.UserDomainApi;
import fr.bankwiz.server.domain.model.UserDomain;
import fr.bankwiz.server.infrastructure.tools.DTOMapper;

@RestController
public class UserController implements UserApi {

    private final UserDomainApi userDomainApi;

    public UserController(final UserDomainApi userDomainApi) {
        this.userDomainApi = userDomainApi;
    }

    @Override
    public ResponseEntity<UserDTO> checkRegistration() {
        final UserDomain userDomain = userDomainApi.checkRegistration();
        final UserDTO userDTO = DTOMapper.toUserDTO(userDomain);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDTO> getCurrentUserInfo() {
        final UserDomain userDomain = userDomainApi.getCurrentUser();
        final UserDTO userDTO = DTOMapper.toUserDTO(userDomain);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
