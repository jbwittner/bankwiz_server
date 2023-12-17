package fr.bankwiz.server.domain.model.data;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class GroupRelated {
    private UUID id;
    protected Group group;
}
