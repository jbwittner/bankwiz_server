package fr.bankwiz.server;

import fr.bankwiz.server.model.User;

public class TestFactory {

    protected final PersonalFaker faker;

    public TestFactory(final PersonalFaker faker) {
        this.faker = faker;
    }

    protected User getUser() {
        final User user = User.builder()
                .email(this.faker.internet().emailAddress())
                .firstName(this.faker.name().firstName())
                .lastName(this.faker.name().lastName())
                .build();

        return user;
    }
}
