package fr.bankwiz.server.unittest.model.group;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight.GroupRightEnum;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

class CanReadTest extends UnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void userAdminRight() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRightEnum.ADMIN);

        final boolean result = group.canRead(user);

        Assertions.assertTrue(result);
    }

    @Test
    void userWriteRight() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRightEnum.WRITE);

        final boolean result = group.canRead(user);

        Assertions.assertTrue(result);
    }

    @Test
    void userReadRight() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRightEnum.READ);

        final boolean result = group.canRead(user);

        Assertions.assertTrue(result);
    }

    @Test
    void userNoRight() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroup();

        final boolean result = group.canRead(user);

        Assertions.assertFalse(result);
    }
}
