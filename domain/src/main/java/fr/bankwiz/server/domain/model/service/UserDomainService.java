package fr.bankwiz.server.domain.model.service;

import java.util.Optional;
import java.util.UUID;

import fr.bankwiz.server.domain.model.api.UserDomainApi;
import fr.bankwiz.server.domain.model.model.UserAuthenticationDomain;
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
        final UserAuthenticationDomain userAuthenticationDomain = this.authenticationDomainSpi.getUserAuthentication();

        final Optional<UserDomain> optionalUserDomain = this.userDomainSpi.findByAuthId(userAuthenticationDomain.sub());

        final UserDomain userDomain;

        if (optionalUserDomain.isPresent()) {
            final UserDomain userDomainFinded = optionalUserDomain.get();
            userDomain = new UserDomain(
                    userDomainFinded.id(), userAuthenticationDomain.sub(), userAuthenticationDomain.email());
        } else {
            userDomain =
                    new UserDomain(UUID.randomUUID(), userAuthenticationDomain.sub(), userAuthenticationDomain.email());
        }

        return this.userDomainSpi.save(userDomain);
    }
}
