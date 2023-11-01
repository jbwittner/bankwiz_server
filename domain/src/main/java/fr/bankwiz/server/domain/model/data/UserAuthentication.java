package fr.bankwiz.server.domain.model.data;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class UserAuthentication {

    private String sub;
    private String email;
}
