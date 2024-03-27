package fr.bankwiz.server.infrastructure.service;

import org.springframework.stereotype.Service;

import fr.bankwiz.server.domain.model.api.UserDomainApi;
import fr.bankwiz.server.domain.model.model.UserDomain;
import fr.bankwiz.server.domain.model.service.UserDomainService;
import fr.bankwiz.server.domain.model.spi.AuthenticationDomainSpi;
import fr.bankwiz.server.domain.model.spi.UserDomainSpi;

@Service
public class UserInfraService implements UserDomainApi {

    private final UserDomainApi userDomainApi;

    public UserInfraService(final AuthenticationDomainSpi authenticationDomainSpi, final UserDomainSpi userDomainSpi) {
        this.userDomainApi = new UserDomainService(authenticationDomainSpi, userDomainSpi);
    }

    @Override
    public UserDomain checkRegistration() {
        return this.userDomainApi.checkRegistration();
    }
}
