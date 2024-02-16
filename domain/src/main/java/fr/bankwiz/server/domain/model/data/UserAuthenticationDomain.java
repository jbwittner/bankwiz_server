package fr.bankwiz.server.domain.model.data;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class UserAuthenticationDomain {

    private String sub;
    private String email;
}
