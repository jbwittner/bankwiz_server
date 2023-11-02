package fr.bankwiz.server.domain.model.data;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class UserAuthentication {

    private String sub;
    private String email;
}
