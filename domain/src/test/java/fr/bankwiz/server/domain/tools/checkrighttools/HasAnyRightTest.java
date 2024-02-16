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
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;
import fr.bankwiz.server.domain.tools.CheckRightTools;

class HasAnyRightTest extends DomainUnitTestBase {

    private CheckRightTools checkRightTools;

    @Override
    protected void initDataBeforeEach() {
        this.checkRightTools =
                new CheckRightTools(this.mockGroupRightSpi.getMock(), this.mockAuthenticationSpi.getMock());
    }

    @ParameterizedTest
    @EnumSource(value = GroupRightEnum.class)
    void hasRight(final GroupRightEnum right) {
        final User user = this.factory.getUser();
        final GroupDomain group = this.factory.getGroup();

        final List<GroupRightDomain> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(group, user, right));

        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);

        final Boolean result = this.checkRightTools.hasAnyRight(user, group);
        Assertions.assertTrue(result);
    }

    @Test
    void hasNotRight() {
        final User user = this.factory.getUser();
        final GroupDomain group = this.factory.getGroup();

        final List<GroupRightDomain> groupRights = new ArrayList<>();

        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);

        final Boolean result = this.checkRightTools.hasAnyRight(user, group);
        Assertions.assertFalse(result);
    }
}
