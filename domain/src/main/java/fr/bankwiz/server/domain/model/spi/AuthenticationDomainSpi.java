package fr.bankwiz.server.domain.model.spi;

import fr.bankwiz.server.domain.model.model.UserAuthenticationDomain;

public interface AuthenticationDomainSpi {
    public UserAuthenticationDomain getUserAuthentication();
}
