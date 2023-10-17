package fr.bankwiz.server.infrastructure.testhelper;

import java.util.UUID;

import fr.bankwiz.server.domain.model.User;

public class InfrastructureUnitTestFactory {

    private InfrastructureFaker infrastructureFaker;

    public InfrastructureUnitTestFactory(InfrastructureFaker infrastructureFaker) {
        this.infrastructureFaker = infrastructureFaker;
    }

    public String getAuthId() {
        return "auth|" + this.infrastructureFaker.random().nextInt(Integer.MAX_VALUE);
    }

    public User getUser() {
        return User.builder()
                .authId(this.getAuthId())
                .email(this.infrastructureFaker.internet().emailAddress())
                .userUuid(UUID.randomUUID())
                .build();
    }
}
