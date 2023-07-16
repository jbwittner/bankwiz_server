package fr.bankwiz.server.unittest.testhelper;

import fr.bankwiz.server.PersonalFaker;
import fr.bankwiz.server.TestFactory;
import fr.bankwiz.server.model.User;

public class UnitTestFactory extends TestFactory {
    public UnitTestFactory(PersonalFaker faker) {
        super.setFaker(faker);
    }

    public User getUser() {
        User user = super.getUser();
        user.setUserId(this.faker.random().nextInt(Integer.MAX_VALUE));
        return user;
    }
}
