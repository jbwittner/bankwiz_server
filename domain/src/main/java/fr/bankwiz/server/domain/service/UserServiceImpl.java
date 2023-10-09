package fr.bankwiz.server.domain.service;

import fr.bankwiz.server.domain.api.UserService;
import fr.bankwiz.server.domain.model.User;

public class UserServiceImpl implements UserService {

    public UserServiceImpl() {
        System.out.println("USerserviceImpl");
    }

    @Override
    public User createUser(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createUser'");
    }
}
