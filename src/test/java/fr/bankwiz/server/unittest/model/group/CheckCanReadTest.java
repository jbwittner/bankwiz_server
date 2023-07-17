package fr.bankwiz.server.unittest.model.group;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.exception.UserNoReadRightException;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight.GroupRightEnum;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.unittest.testhelper.UnitTestBase;

class CheckCanReadTest extends UnitTestBase {

    @Override
    protected void initDataBeforeEach() {}
    @Test
    void userAdminRight() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRigh(user, GroupRightEnum.ADMIN);

Assertions.assertDoesNotThrow(() -> {
            group.checkCanRead(user);
        });    }

    @Test
    void userWriteRight() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRigh(user, GroupRightEnum.WRITE);

        Assertions.assertDoesNotThrow(() -> {
            group.checkCanRead(user);
        });

    }


    @Test
    void userReadRight() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroupWithRigh(user, GroupRightEnum.READ);

Assertions.assertDoesNotThrow(() -> {
            group.checkCanRead(user);
        });    }

    @Test
    void userNoRight() {
        final User user = this.unitTestFactory.getUser();
        final Group group = this.unitTestFactory.getGroup();

Assertions.assertThrows(UserNoReadRightException.class, () -> {
            group.checkCanRead(user);
        });    }
}
