package fr.bankwiz.server.domain.model.data;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public abstract class GroupRelated {
    private UUID id;
    protected GroupDomain group;
}
