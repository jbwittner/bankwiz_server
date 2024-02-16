package fr.bankwiz.server.domain.model.other;

import java.util.List;

import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GroupDetailsDomain {
    private final GroupDomain group;
    private final List<GroupRightDomain> groupRights;
}
