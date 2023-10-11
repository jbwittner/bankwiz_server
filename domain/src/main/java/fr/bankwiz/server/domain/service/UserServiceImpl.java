package fr.bankwiz.server.domain.service;

import fr.bankwiz.server.domain.api.UserService;
import fr.bankwiz.server.domain.model.User;

public class UserServiceImpl implements UserService {

    public UserServiceImpl() {
        /* */
    }

    @Override
    public User createUser(Long id) {
        return User.builder().userId(id).build();
    }
}
