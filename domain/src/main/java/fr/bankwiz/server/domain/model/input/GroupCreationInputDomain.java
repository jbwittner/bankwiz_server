package fr.bankwiz.server.domain.model.input;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GroupCreationInputDomain {

    private String groupName;
}
