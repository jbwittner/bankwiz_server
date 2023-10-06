package fr.bankwiz.server.domain.service;

import ddd.DomainService;
import fr.bankwiz.server.domain.api.UserService;
import fr.bankwiz.server.domain.model.User;

@DomainService
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
