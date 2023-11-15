package fr.bankwiz.server.domain.model.input;

import java.util.UUID;

import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddUserGroupInput {
    private UUID userId;
   private GroupRightEnum right;
}
