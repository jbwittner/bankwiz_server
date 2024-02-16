package fr.bankwiz.server.domain.testhelper.mock;

import java.util.UUID;

import org.mockito.Mockito;

import fr.bankwiz.server.domain.exception.UserNoReadRightException;
import fr.bankwiz.server.domain.exception.UserNoWriteRightException;
import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.tools.CheckRightTools;

public class DomainMockCheckRightTool extends DomainMockHelper<CheckRightTools> {

    public DomainMockCheckRightTool() {
        super(CheckRightTools.class);
    }

    public void mockCheckCurrentUserCanWrite(GroupDomain group, Boolean canWrite) {
        if (canWrite == false) {
            final User user = User.builder().id(UUID.randomUUID()).build();
            Mockito.doThrow(new UserNoWriteRightException(user, group))
                    .when(this.mock)
                    .checkCurrentUserCanWrite(group);
        }
    }

    public void mockCheckCurrentUserCanRead(GroupDomain group, Boolean canRead) {
        if (canRead == false) {
            final User user = User.builder().id(UUID.randomUUID()).build();
            Mockito.doThrow(new UserNoReadRightException(user, group))
                    .when(this.mock)
                    .checkCurrentUserCanRead(group);
        }
    }
}
