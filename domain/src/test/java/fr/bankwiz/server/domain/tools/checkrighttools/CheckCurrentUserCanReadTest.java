package fr.bankwiz.server.domain.tools.checkrighttools;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import fr.bankwiz.server.domain.exception.UserNoWriteRightException;
import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.UserDomain;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;
import fr.bankwiz.server.domain.tools.CheckRightTools;

class CheckCurrentUserCanReadTest extends DomainUnitTestBase {

    private CheckRightTools checkRightTools;

    @Override
    protected void initDataBeforeEach() {
        this.checkRightTools =
                new CheckRightTools(this.mockGroupRightSpi.getMock(), this.mockAuthenticationSpi.getMock());
    }

    @Test
    void noRight() {
        final UserDomain user = this.factory.getUser();
        final GroupDomain group = this.factory.getGroup();

        final List<GroupRightDomain> groupRights = new ArrayList<>();

        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);
        this.mockAuthenticationSpi.mockGetCurrentUser(user);

        Assertions.assertThrows(
                UserNoWriteRightException.class, () -> this.checkRightTools.checkCurrentUserCanWrite(group));
    }

    @ParameterizedTest
    @EnumSource(
            value = GroupRightEnum.class,
            names = {"ADMIN", "WRITE", "READ"})
    void canWrite(final GroupRightEnum right) {
        final UserDomain user = this.factory.getUser();
        final GroupDomain group = this.factory.getGroup();

        final List<GroupRightDomain> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, user, right));

        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);
        this.mockAuthenticationSpi.mockGetCurrentUser(user);

        Assertions.assertDoesNotThrow(() -> this.checkRightTools.checkCurrentUserCanRead(group));
    }
}
