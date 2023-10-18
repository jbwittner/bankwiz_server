package fr.bankwiz.server.domain.model;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class User {

    private UUID userUuid;
    private String authId;
    private String email;

    public User setEmail(String email) {
        this.email = email;
        return this;
    }
}
