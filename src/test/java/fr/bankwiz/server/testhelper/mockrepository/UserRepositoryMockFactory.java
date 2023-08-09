package fr.bankwiz.server.testhelper.mockrepository;

import java.util.Optional;

import org.mockito.Mockito;

import fr.bankwiz.server.model.User;
import fr.bankwiz.server.repository.UserRepository;

public class UserRepositoryMockFactory extends AbstractRepositoryMockFactory<User, UserRepository, Integer> {
    public UserRepositoryMockFactory() {
        super(UserRepository.class);
    }

    public UserRepositoryMockFactory mockFindByAuthId(String authId, Optional<User> optionalUser) {
        Mockito.when(repository.findByAuthId(authId)).thenReturn(optionalUser);
        return this;
    }
}
