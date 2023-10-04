package fr.bankwiz.server.testhelper;

import java.util.UUID;

import fr.bankwiz.server.model.*;

public class UnitTestFactory {

    private PersonalFaker faker;

    public UnitTestFactory(PersonalFaker faker) {
        this.faker = faker;
    }

    protected UUID getRandomUUID() {
        return UUID.randomUUID();
    }

    public User getUser() {
        final String authId = "auth|" + this.faker.random().nextInt(Integer.MAX_VALUE);
        return User.builder()
                .email(this.faker.internet().emailAddress())
                .authId(authId)
                .userAccountId(this.getRandomUUID())
                .build();
    }

}
