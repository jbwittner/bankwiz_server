package fr.bankwiz.server.domain.testhelper;

import java.util.UUID;

import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.domain.model.UserAuthentication;

public class DomainUnitTestFactory {

    private DomainFaker domainFaker;

    public DomainUnitTestFactory(DomainFaker domainFaker) {
        this.domainFaker = domainFaker;
    }

    public String getAuthId() {
        return "auth|" + this.domainFaker.random().nextInt(Integer.MAX_VALUE);
    }

    public UserAuthentication getUserAuthentication() {
        return UserAuthentication.builder()
                .email(this.domainFaker.internet().emailAddress())
                .sub(this.getAuthId())
                .build();
    }

    public User getUser() {
        return User.builder()
                .authId(this.getAuthId())
                .email(this.domainFaker.internet().emailAddress())
                .userUuid(UUID.randomUUID())
                .build();
    }
}
