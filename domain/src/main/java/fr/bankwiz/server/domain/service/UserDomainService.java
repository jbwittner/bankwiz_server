package fr.bankwiz.server.domain.service;

import java.util.UUID;

import fr.bankwiz.server.domain.api.UserApi;
import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.domain.model.UserAuthentication;
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
    public User checkRegistration() {
        final UserAuthentication userAuthentication = this.authenticationSpi.getUserAuthentication();

        final User user = this.userSpi
                .findByAuthId(userAuthentication.getSub())
                .orElse(User.builder()
                        .authId(userAuthentication.getSub())
                        .userUuid(UUID.randomUUID())
                        .build());
        user.setEmail(userAuthentication.getEmail());

        System.out.println(user);

        return this.userSpi.save(user);
    }

    @Override
    public User getCurrentUser() {
        return this.authenticationSpi.getCurrentUser();
    }
}
