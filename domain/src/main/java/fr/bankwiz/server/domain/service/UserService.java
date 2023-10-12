package fr.bankwiz.server.domain.service;

import ddd.DomainService;
import fr.bankwiz.server.domain.api.UserApi;
import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.domain.spi.UserSpi;

@DomainService
public class UserService implements UserApi {

    private final UserSpi userSpi;

    public UserService(UserSpi userSpi) {
        this.userSpi = userSpi;
    }

    @Override
    public User createUser(Long id) {
        this.userSpi.findById(id);
        return User.builder().userId(id).build();
    }
}
