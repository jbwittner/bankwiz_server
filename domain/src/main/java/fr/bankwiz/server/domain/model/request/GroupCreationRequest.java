package fr.bankwiz.server.domain.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class GroupCreationRequest {

    private String groupName;
    
}
