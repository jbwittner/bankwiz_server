package fr.bankwiz.server.domain.service;

import fr.bankwiz.server.domain.model.User;

import java.util.UUID;

public class HelperDomain {
    public HelperDomain() {
        final User user = new User(UUID.randomUUID(), "", "");
    }
}
