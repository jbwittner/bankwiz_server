package fr.bankwiz.server.domain.model.data;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class UserAuthentication {

    private String sub;
    private String email;
}
