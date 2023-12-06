package fr.bankwiz.server.domain.model.other;

import java.util.List;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class GroupDetails {
    private final Group group;
    private final List<GroupRight> groupRights;
}
