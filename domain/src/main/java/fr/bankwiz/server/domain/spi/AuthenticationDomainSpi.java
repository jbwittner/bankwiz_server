package fr.bankwiz.server.domain.spi;

import fr.bankwiz.server.domain.model.UserAuthenticationDomain;

public interface AuthenticationDomainSpi {
    UserAuthenticationDomain getUserAuthentication();

    String getCurrentUserAuthId();
}
