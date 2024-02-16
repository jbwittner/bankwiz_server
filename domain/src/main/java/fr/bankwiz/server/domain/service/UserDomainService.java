package fr.bankwiz.server.domain.service;

import java.util.UUID;

import fr.bankwiz.server.domain.api.UserApi;
import fr.bankwiz.server.domain.model.data.UserAuthenticationDomain;
import fr.bankwiz.server.domain.model.data.UserDomain;
import fr.bankwiz.server.domain.spi.AuthenticationSpi;
import fr.bankwiz.server.domain.spi.UserSpi;

public class UserDomainService implements UserApi {

    private final UserSpi userSpi;
    private final AuthenticationSpi authenticationSpi;

    public UserDomainService(AuthenticationSpi authenticationSpi, UserSpi userSpi) {
        this.authenticationSpi = authenticationSpi;
        this.userSpi = userSpi;
    }

    @Override
    public UserDomain checkRegistration() {
        final UserAuthenticationDomain userAuthentication = this.authenticationSpi.getUserAuthentication();

        final UserDomain user = this.userSpi
                .findByAuthId(userAuthentication.getSub())
                .orElse(UserDomain.builder()
                        .authId(userAuthentication.getSub())
                        .id(UUID.randomUUID())
                        .build());
        user.setEmail(userAuthentication.getEmail());

        return this.userSpi.save(user);
    }

    @Override
    public UserDomain getCurrentUser() {
        return this.authenticationSpi.getCurrentUser();
    }
}
