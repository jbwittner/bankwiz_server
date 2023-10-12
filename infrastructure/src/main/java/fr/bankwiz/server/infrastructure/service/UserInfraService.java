package fr.bankwiz.server.infrastructure.service;

import org.springframework.stereotype.Service;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.domain.api.UserApi;
import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.infrastructure.transformer.UserTransformer;

@Service
public class UserInfraService {

    private static final UserTransformer USER_TRANSFORMER = new UserTransformer();

    private final UserApi userApi;

    public UserInfraService(UserApi userApi) {
        this.userApi = userApi;
    }

    public UserDTO createUser(Long id) {
        final User user = this.userApi.createUser(id);
        return USER_TRANSFORMER.transformToUserDTO(user);
    }
}
