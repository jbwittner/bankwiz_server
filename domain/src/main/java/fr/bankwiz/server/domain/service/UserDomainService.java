package fr.bankwiz.server.domain.service;

import ddd.DomainService;
import fr.bankwiz.server.domain.api.UserApi;
import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.domain.model.UserAuthentication;
import fr.bankwiz.server.domain.spi.AuthenticationFacadeSpi;
import fr.bankwiz.server.domain.spi.UserSpi;

@DomainService
public class UserDomainService implements UserApi {

    private final UserSpi userSpi;
    private final AuthenticationFacadeSpi authenticationFacadeSpi;

    public UserDomainService(AuthenticationFacadeSpi authenticationFacadeSpi, UserSpi userSpi) {
        this.authenticationFacadeSpi = authenticationFacadeSpi;
        this.userSpi = userSpi;
    }

    @Override
    public User checkRegistration() {
        final UserAuthentication userAuthentication = this.authenticationFacadeSpi.getUserAuthentication();

        final User user = this.userSpi.findByAuthId(userAuthentication.getSub()).orElse(User.builder().authId(userAuthentication.getSub()).build());
        user.setEmail(userAuthentication.getEmail());

        return this.userSpi.save(user);
    }
}
