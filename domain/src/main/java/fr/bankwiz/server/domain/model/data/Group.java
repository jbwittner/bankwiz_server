package fr.bankwiz.server.domain.model.data;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Group {

    private UUID groupUuid;
    private String groupName;
}
