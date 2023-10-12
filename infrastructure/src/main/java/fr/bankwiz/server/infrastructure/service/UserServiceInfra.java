package fr.bankwiz.server.infrastructure.service;

import org.springframework.stereotype.Service;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.domain.api.UserApi;
import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.infrastructure.transformer.UserTransformer;

@Service
public class UserServiceInfra {

    private static final UserTransformer USER_TRANSFORMER = new UserTransformer();

    private final UserApi userService;

    public UserServiceInfra(UserApi userService) {
        this.userService = userService;
    }

    public UserDTO createUser(Long id) {
        final User user = this.userService.createUser(id);
        return USER_TRANSFORMER.transformToUserDTO(user);
    }
}
