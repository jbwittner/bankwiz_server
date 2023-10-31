package fr.bankwiz.server.domain.api;

import fr.bankwiz.server.domain.model.data.User;

public interface UserApi {
    User checkRegistration();

    User getCurrentUser();
}
