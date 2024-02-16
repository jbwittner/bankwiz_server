package fr.bankwiz.server.domain.model.data;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class GroupRight {

    private UUID id;
    private GroupDomain group;
    private User user;
    private GroupRightEnum groupRightEnum;

    public enum GroupRightEnum {
        READ,
        WRITE,
        ADMIN
    }
}
