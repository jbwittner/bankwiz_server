package fr.bankwiz.server.infrastructure.service;

import org.springframework.stereotype.Service;

import fr.bankwiz.server.domain.api.UserApi;
import fr.bankwiz.server.domain.model.data.User;

@Service
public class UserInfraService {

    private final UserApi userApi;

    public UserInfraService(UserApi userApi) {
        this.userApi = userApi;
    }

    public User checkRegistration() {
        return this.userApi.checkRegistration();
    }

    public User getCurrentUser() {
        return this.userApi.getCurrentUser();
    }
}
