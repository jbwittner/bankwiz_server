package fr.bankwiz.server.domain.model.data;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserAuthentication {

    private String sub;
    private String email;
}
