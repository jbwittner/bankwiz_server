package fr.bankwiz.server.model.group;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.exception.UserNoWriteRightException;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight.GroupRightEnum;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.testhelper.UnitTestBase;

class CheckCanWriteTest extends UnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void userAdminRight() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRightEnum.ADMIN);

        Assertions.assertDoesNotThrow(() -> {
            group.checkCanWrite(user);
        });
    }

    @Test
    void userWriteRight() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRightEnum.WRITE);

        Assertions.assertDoesNotThrow(() -> {
            group.checkCanWrite(user);
        });
    }

    @Test
    void userReadRight() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRight(user, GroupRightEnum.READ);

        Assertions.assertThrows(UserNoWriteRightException.class, () -> {
            group.checkCanWrite(user);
        });
    }

    @Test
    void userNoRight() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroup();

        Assertions.assertThrows(UserNoWriteRightException.class, () -> {
            group.checkCanWrite(user);
        });
    }
}
