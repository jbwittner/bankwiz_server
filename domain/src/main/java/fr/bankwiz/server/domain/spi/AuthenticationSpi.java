package fr.bankwiz.server.domain.spi;

import fr.bankwiz.server.domain.model.User;
import fr.bankwiz.server.domain.model.UserAuthentication;

public interface AuthenticationSpi {

    public UserAuthentication getUserAuthentication();

    public User getCurrentUser();
}
