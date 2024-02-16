package fr.bankwiz.server.domain.spi;

import fr.bankwiz.server.domain.model.data.UserDomain;
import fr.bankwiz.server.domain.model.data.UserAuthenticationDomain;

public interface AuthenticationSpi {

    public UserAuthenticationDomain getUserAuthentication();

    public UserDomain getCurrentUser();
}
