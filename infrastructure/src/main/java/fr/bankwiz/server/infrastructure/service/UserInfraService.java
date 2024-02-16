package fr.bankwiz.server.infrastructure.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.bankwiz.server.domain.api.UserApi;
import fr.bankwiz.server.domain.model.data.UserDomain;

@Service
public class UserInfraService {

    private final UserApi userApi;

    public UserInfraService(UserApi userApi) {
        this.userApi = userApi;
    }

    @Transactional
    public UserDomain checkRegistration() {
        return this.userApi.checkRegistration();
    }

    @Transactional(readOnly = true)
    public UserDomain getCurrentUser() {
        return this.userApi.getCurrentUser();
    }
}
