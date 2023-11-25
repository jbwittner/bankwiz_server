package fr.bankwiz.server.domain.model.data;

import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class User {

    private UUID id;
    private String authId;
    private String email;

    public User setEmail(String email) {
        this.email = email;
        return this;
    }
}
