package fr.bankwiz.server.unittest.model.group;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.model.GroupRight.GroupRightEnum;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

class IsAdminTest extends UnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void userNotAdmin() {
        final User userNotAdmin = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroup();

        final boolean result = group.isAdmin(userNotAdmin);

        Assertions.assertFalse(result);
    }

    @Test
    void userAdmin() {
        final User adminUser = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRigh(adminUser, GroupRightEnum.ADMIN);

        final boolean result = group.isAdmin(adminUser);

        Assertions.assertTrue(result);
    }
}