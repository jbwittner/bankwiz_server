package fr.bankwiz.server;

import fr.bankwiz.server.model.User;

public class TestFactory {

    protected PersonalFaker faker;

    public void setFaker(final PersonalFaker faker){
        this.faker = faker;
    }

    protected User getUser() {

        return User.builder()
                .email(this.faker.internet().emailAddress())
                .firstName(this.faker.name().firstName())
                .lastName(this.faker.name().lastName())
                .build();
    }
}
