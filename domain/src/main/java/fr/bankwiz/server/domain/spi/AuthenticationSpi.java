package fr.bankwiz.server.domain.spi;

import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.model.data.UserAuthentication;

public interface AuthenticationSpi {

    public UserAuthentication getUserAuthentication();

    public User getCurrentUser();
}
