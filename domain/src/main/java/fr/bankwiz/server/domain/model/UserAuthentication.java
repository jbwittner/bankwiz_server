package fr.bankwiz.server.domain.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserAuthentication {

    private String sub;
    private String name;
    private String email;
}
