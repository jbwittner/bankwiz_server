package fr.bankwiz.server.domain.testhelper.mock;

import java.util.ArrayList;
import java.util.List;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.spi.GroupRightSpi;

public class DomainMockGroupRightSpi extends DomainMockHelper<GroupRightSpi> {

    private List<GroupRight> groupRightsSaved;

    public DomainMockGroupRightSpi() {
        super(GroupRightSpi.class);
        this.groupRightsSaved = new ArrayList<>();
    }

    public List<GroupRight> getGroupRightsSaved() {
        return this.groupRightsSaved;
    }

    public DomainMockGroupRightSpi mockSave() {
        Mockito.when(this.mock.save(ArgumentMatchers.any())).thenAnswer(invocation -> {
            final GroupRight groupRight = invocation.<GroupRight>getArgument(0);
            this.groupRightsSaved.add(groupRight);
            return groupRight;
        });
        return this;
    }

}
