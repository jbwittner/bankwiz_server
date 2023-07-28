package fr.bankwiz.server.unittest.model.group;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.exception.UserNotAdminException;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight.GroupRightEnum;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

class CheckIsAdminTest extends UnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void userAdminRight() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRightEnum.ADMIN);

        Assertions.assertDoesNotThrow(() -> {
            group.checkIsAdmin(user);
        });
    }

    @Test
    void userWriteRight() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRightEnum.WRITE);

        Assertions.assertThrows(UserNotAdminException.class, () -> {
            group.checkIsAdmin(user);
        });
    }

    @Test
    void userReadRight() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRightEnum.READ);

        Assertions.assertThrows(UserNotAdminException.class, () -> {
            group.checkIsAdmin(user);
        });
    }

    @Test
    void userNoRight() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroup();

        Assertions.assertThrows(UserNotAdminException.class, () -> {
            group.checkIsAdmin(user);
        });
    }
}
