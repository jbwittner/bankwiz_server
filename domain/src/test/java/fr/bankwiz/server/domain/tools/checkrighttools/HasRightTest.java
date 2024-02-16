package fr.bankwiz.server.domain.tools.checkrighttools;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.UserDomain;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;
import fr.bankwiz.server.domain.tools.CheckRightTools;

class HasRightTest extends DomainUnitTestBase {

    private CheckRightTools checkRightTools;

    @Override
    protected void initDataBeforeEach() {
        this.checkRightTools =
                new CheckRightTools(this.mockGroupRightSpi.getMock(), this.mockAuthenticationSpi.getMock());
    }

    @Test
    void hasAdminRight() {
        final UserDomain user = this.factory.getUser();
        final GroupDomain group = this.factory.getGroup();

        final List<GroupRightDomain> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, user, GroupRightEnum.ADMIN));

        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);

        final Boolean result = this.checkRightTools.hasRight(user, group, GroupRightEnum.ADMIN);
        Assertions.assertTrue(result);
    }

    @ParameterizedTest
    @EnumSource(
            value = GroupRightEnum.class,
            names = {"READ", "WRITE"})
    void hasNotAdminRight(final GroupRightEnum right) {
        final UserDomain user = this.factory.getUser();
        final GroupDomain group = this.factory.getGroup();

        final List<GroupRightDomain> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, user, right));

        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);

        final Boolean result = this.checkRightTools.hasRight(user, group, GroupRightEnum.ADMIN);
        Assertions.assertFalse(result);
    }

    @Test
    void hasWriteRight() {
        final UserDomain user = this.factory.getUser();
        final GroupDomain group = this.factory.getGroup();

        final List<GroupRightDomain> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, user, GroupRightEnum.WRITE));

        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);

        final Boolean result = this.checkRightTools.hasRight(user, group, GroupRightEnum.WRITE);
        Assertions.assertTrue(result);
    }

    @ParameterizedTest
    @EnumSource(
            value = GroupRightEnum.class,
            names = {"ADMIN", "READ"})
    void hasNotWriteRight(final GroupRightEnum right) {
        final UserDomain user = this.factory.getUser();
        final GroupDomain group = this.factory.getGroup();

        final List<GroupRightDomain> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, user, right));

        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);

        final Boolean result = this.checkRightTools.hasRight(user, group, GroupRightEnum.WRITE);
        Assertions.assertFalse(result);
    }

    @Test
    void hasReadRight() {
        final UserDomain user = this.factory.getUser();
        final GroupDomain group = this.factory.getGroup();

        final List<GroupRightDomain> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, user, GroupRightEnum.READ));

        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);

        final Boolean result = this.checkRightTools.hasRight(user, group, GroupRightEnum.READ);
        Assertions.assertTrue(result);
    }

    @ParameterizedTest
    @EnumSource(
            value = GroupRightEnum.class,
            names = {"ADMIN", "WRITE"})
    void hasNotReadRight(final GroupRightEnum right) {
        final UserDomain user = this.factory.getUser();
        final GroupDomain group = this.factory.getGroup();

        final List<GroupRightDomain> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, user, right));

        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);

        final Boolean result = this.checkRightTools.hasRight(user, group, GroupRightEnum.READ);
        Assertions.assertFalse(result);
    }
}
