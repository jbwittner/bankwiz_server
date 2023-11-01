package fr.bankwiz.server.domain.model.data;

import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class Group {

    private UUID groupUuid;
    private String groupName;
}
