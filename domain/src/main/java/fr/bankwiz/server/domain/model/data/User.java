package fr.bankwiz.server.domain.model.data;

import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class User {

    private UUID userUuid;
    private String authId;
    private String email;

    public User setEmail(String email) {
        this.email = email;
        return this;
    }
}
