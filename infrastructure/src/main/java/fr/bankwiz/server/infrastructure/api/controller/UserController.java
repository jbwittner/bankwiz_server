package fr.bankwiz.server.infrastructure.api.controller;

import fr.bankwiz.openapi.api.UserApi;
import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.domain.model.api.UserDomainApi;
import fr.bankwiz.server.domain.model.model.UserDomain;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserApi {

    private final UserDomainApi userDomainApi;

    public  UserController(final UserDomainApi userDomainApi){
        this.userDomainApi = userDomainApi;
    }

    @Override
    public ResponseEntity<UserDTO> checkRegistration() {
        final UserDomain userDomain = userDomainApi.checkRegistration();
        System.out.println(userDomain);
        return UserApi.super.checkRegistration();
    }
}
