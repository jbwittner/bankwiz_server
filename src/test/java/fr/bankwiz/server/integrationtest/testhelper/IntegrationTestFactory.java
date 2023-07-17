package fr.bankwiz.server.integrationtest.testhelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.bankwiz.server.TestFactory;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.repository.UserRepository;

@Component
public class IntegrationTestFactory extends TestFactory {

    @Autowired
    private UserRepository userRepository;

    public User getUser() {
        final User user = super.getUser();
        return this.userRepository.save(user);
    }
}
