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
public class GroupRight {

    private UUID groupRightId;
    private Group group;
    private User user;
    private GroupRightEnum groupRightEnum;

    public enum GroupRightEnum {
        READ,
        WRITE,
        ADMIN
    }
}
