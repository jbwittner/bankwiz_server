package fr.bankwiz.server.domain.testhelper.mock;

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
}
