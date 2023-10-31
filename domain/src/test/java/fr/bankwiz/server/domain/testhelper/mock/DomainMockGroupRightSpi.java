package fr.bankwiz.server.domain.testhelper.mock;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.spi.GroupRightSpi;

public class DomainMockGroupRightSpi extends DomainMockHelper<GroupRightSpi> {

    public DomainMockGroupRightSpi() {
        super(GroupRightSpi.class);
    }

    public DomainMockGroupRightSpi mockSave() {
        Mockito.when(this.mock.save(ArgumentMatchers.any())).thenAnswer(invocation -> {
            return invocation.<GroupRight>getArgument(0);
        });
        return this;
    }
}
