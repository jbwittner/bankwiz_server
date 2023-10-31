package fr.bankwiz.server.domain.model.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GroupCreationRequest {

    private String groupName;
}
