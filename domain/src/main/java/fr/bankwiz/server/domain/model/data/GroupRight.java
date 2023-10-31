package fr.bankwiz.server.domain.model.data;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GroupRight {

    private UUID groupRightUuid;
    private Group group;
    private User user;
    private GroupRightEnum groupRightEnum;

    public enum GroupRightEnum {
        READ,
        WRITE,
        ADMIN
    }
}
