package fr.bankwiz.server.domain.model.input;

import java.util.UUID;

import fr.bankwiz.server.domain.model.data.GroupRightDomain.GroupRightEnum;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddUserGroupInputDomain {
    private UUID userId;
    private GroupRightEnum right;
}
