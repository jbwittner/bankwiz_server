package fr.bankwiz.server.domain.model.service;

import fr.bankwiz.server.domain.model.api.UserDomainApi;
import fr.bankwiz.server.domain.model.model.UserDomain;
import fr.bankwiz.server.domain.model.spi.AuthenticationDomainSpi;
import fr.bankwiz.server.domain.model.spi.UserDomainSpi;

public class UserDomainService implements UserDomainApi {

    private final AuthenticationDomainSpi authenticationDomainSpi;
    private final UserDomainSpi userDomainSpi;

    public UserDomainService(final AuthenticationDomainSpi authenticationDomainSpi, final UserDomainSpi userDomainSpi) {
        this.authenticationDomainSpi = authenticationDomainSpi;
        this.userDomainSpi = userDomainSpi;
    }

    @Override
    public UserDomain checkRegistration() {
        return null;
    }
}
