package fr.bankwiz.server.infrastructure.service;

import org.springframework.stereotype.Service;

import fr.bankwiz.server.domain.api.UserService;
import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.domain.service.UserServiceImpl;

@Service
public class UserServiceInfra implements UserService {

    private final UserService userService;

    public UserServiceInfra() {
        this.userService = new UserServiceImpl();
    }

    @Override
    public User createUser(Long id) {
        return this.userService.createUser(id);
    }
}
