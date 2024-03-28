package fr.bankwiz.server.infrastructure.service;

import org.springframework.stereotype.Service;

import fr.bankwiz.server.domain.api.UserDomainApi;
import fr.bankwiz.server.domain.model.UserDomain;
import fr.bankwiz.server.domain.service.UserDomainService;
import fr.bankwiz.server.domain.spi.AuthenticationDomainSpi;
import fr.bankwiz.server.domain.spi.UserDomainSpi;

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

    @Override
    public UserDomain getCurrentUser() {
        return this.userDomainApi.getCurrentUser();
    }
}
