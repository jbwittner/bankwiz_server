package fr.bankwiz.server.domain.tools.checkrighttools;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;
import fr.bankwiz.server.domain.tools.CheckRightTools;

class CanWriteTest extends DomainUnitTestBase {

    private CheckRightTools checkRightTools;

    @Override
    protected void initDataBeforeEach() {
        this.checkRightTools = new CheckRightTools(this.mockGroupRightSpi.getMock());
    }

    @Test
    void noRight() {
        final User user = this.factory.getUser();
        final Group group = this.factory.getGroup();

        final List<GroupRight> groupRights = new ArrayList<>();

        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);

        final Boolean result = this.checkRightTools.canRead(user, group);
        Assertions.assertFalse(result);
    }

    @Test
    void canNotWrite() {
        final User user = this.factory.getUser();
        final Group group = this.factory.getGroup();

        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, user, GroupRightEnum.READ));

        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);

        final Boolean result = this.checkRightTools.canWrite(user, group);
        Assertions.assertFalse(result);
    }

    @ParameterizedTest
    @EnumSource(
            value = GroupRightEnum.class,
            names = {"ADMIN", "WRITE"})
    void canWrite(final GroupRightEnum right) {
        final User user = this.factory.getUser();
        final Group group = this.factory.getGroup();

        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, user, right));

        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);

        final Boolean result = this.checkRightTools.canWrite(user, group);
        Assertions.assertTrue(result);
    }
}
