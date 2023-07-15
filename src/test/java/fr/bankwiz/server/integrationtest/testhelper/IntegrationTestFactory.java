package fr.bankwiz.server.integrationtest.testhelper;

import fr.bankwiz.server.PersonalFaker;
import fr.bankwiz.server.TestFactory;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IntegrationTestFactory extends TestFactory {

    @Autowired
    private UserRepository userRepository;

    protected User getUser() {
        final User user = super.getUser();
        return this.userRepository.save(user);
    }

}
