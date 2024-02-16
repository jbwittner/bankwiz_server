package fr.bankwiz.server.domain.spi;

import fr.bankwiz.server.domain.model.data.UserAuthenticationDomain;
import fr.bankwiz.server.domain.model.data.UserDomain;

public interface AuthenticationSpi {

    public UserAuthenticationDomain getUserAuthentication();

    public UserDomain getCurrentUser();
}
