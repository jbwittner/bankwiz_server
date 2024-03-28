package fr.bankwiz.server.domain.model;

import java.util.UUID;

public record UserDomain(UUID id, String authId, String email) {}
