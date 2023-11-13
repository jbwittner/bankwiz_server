package fr.bankwiz.server.domain.testhelper.mock;

import java.util.Optional;
import java.util.UUID;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.spi.GroupSpi;

public class DomainMockGroupSpi extends DomainMockHelper<GroupSpi> {

    public DomainMockGroupSpi() {
        super(GroupSpi.class);
    }

    public DomainMockGroupSpi mockSave() {
        Mockito.when(this.mock.save(ArgumentMatchers.any())).thenAnswer(invocation -> {
            return invocation.<Group>getArgument(0);
        });
        return this;
    }

    public DomainMockGroupSpi mockFindById(UUID id, Optional<Group> optionalGroup) {
        Mockito.when(this.mock.findById(id)).thenReturn(optionalGroup);
        return this;
    }
}
