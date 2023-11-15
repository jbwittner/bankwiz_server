package fr.bankwiz.server.domain.model.data;

import java.util.List;

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
