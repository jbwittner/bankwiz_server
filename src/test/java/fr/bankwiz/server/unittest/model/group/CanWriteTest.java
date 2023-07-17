package fr.bankwiz.server.unittest.model.group;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.model.GroupRight.GroupRightEnum;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

class CanWriteTest extends UnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void userWriteRight() {
        final User anotherUser = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRigh(anotherUser, GroupRightEnum.WRITE);

        final boolean result = group.canWrite(anotherUser);

        Assertions.assertTrue(result);
    }

    @Test
    void userAdminRight() {
        final User anotherUser = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRigh(anotherUser, GroupRightEnum.ADMIN);

        final boolean result = group.canWrite(anotherUser);

        Assertions.assertTrue(result);
    }

    @Test
    void userReadRight() {
        final User anotherUser = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRigh(anotherUser, GroupRightEnum.READ);

        final boolean result = group.canWrite(anotherUser);

        Assertions.assertFalse(result);
    }

    @Test
    void userNoRight() {
        final User anotherUser = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroup();

        final boolean result = group.canWrite(anotherUser);

        Assertions.assertFalse(result);
    }
}