package fr.bankwiz.server.domain.testhelper;

import java.util.UUID;

import fr.bankwiz.server.domain.model.model.UserAuthenticationDomain;
import fr.bankwiz.server.domain.model.model.UserDomain;

public class DomainUnitTestFactory {
    private final DomainFaker domainFaker;

    public DomainUnitTestFactory(final DomainFaker domainFaker) {
        this.domainFaker = domainFaker;
    }

    public String getAuthId() {
        return "auth|" + this.domainFaker.random().nextInt(Integer.MAX_VALUE);
    }

    public UserAuthenticationDomain getUserAuthentication() {
        return new UserAuthenticationDomain(
                this.getAuthId(), this.domainFaker.internet().emailAddress());
    }

    public UserDomain getUserDomain() {
        return new UserDomain(
                UUID.randomUUID(), this.getAuthId(), this.domainFaker.internet().emailAddress());
    }
}
