package fr.bankwiz.server.domain.model;

import java.util.UUID;

public record User(UUID id, String authId, String email) {}
