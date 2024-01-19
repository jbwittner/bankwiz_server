package fr.bankwiz.server.domain.tools.checkrighttools;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import fr.bankwiz.server.domain.exception.UserNotAdminException;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;
import fr.bankwiz.server.domain.tools.CheckRightTools;

class CheckIsAdminTest extends DomainUnitTestBase {

    private CheckRightTools checkRightTools;

    @Override
    protected void initDataBeforeEach() {
        this.checkRightTools =
                new CheckRightTools(this.mockGroupRightSpi.getMock(), this.mockAuthenticationSpi.getMock());
    }

    @Test
    void noRight() {
        final User user = this.factory.getUser();
        final Group group = this.factory.getGroup();

        final List<GroupRight> groupRights = new ArrayList<>();

        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);

        Assertions.assertThrows(UserNotAdminException.class, () -> this.checkRightTools.checkIsAdmin(user, group));
    }

    @Test
    void isAdmin() {
        final User user = this.factory.getUser();
        final Group group = this.factory.getGroup();

        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, user, GroupRightEnum.ADMIN));

        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);

        Assertions.assertDoesNotThrow(() -> this.checkRightTools.checkIsAdmin(user, group));
    }

    @ParameterizedTest
    @EnumSource(
            value = GroupRightEnum.class,
            names = {"READ", "WRITE"})
    void isNotAdmin(final GroupRightEnum right) {
        final User user = this.factory.getUser();
        final Group group = this.factory.getGroup();

        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, user, right));

        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);

        Assertions.assertThrows(UserNotAdminException.class, () -> this.checkRightTools.checkIsAdmin(user, group));
    }
}
