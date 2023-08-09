package fr.bankwiz.server.model.grouprelatedentity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.exception.IncompatibleGroupException;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRelatedEntity;
import fr.bankwiz.server.testhelper.UnitTestBase;

class CheckCanAssociateWithTest extends UnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void sameGroup() {
        final Group group = this.unitTestFactory.getGroup();
        final ImplGroupRelatedEntity entity1 = new ImplGroupRelatedEntity(group);
        final ImplGroupRelatedEntity entity2 = new ImplGroupRelatedEntity(group);

        Assertions.assertDoesNotThrow(() -> entity1.checkCanAssociateWith(entity2));
        Assertions.assertDoesNotThrow(() -> entity2.checkCanAssociateWith(entity1));
    }

    @Test
    void differentGroup() {
        final Group group1 = this.unitTestFactory.getGroup();
        final Group group2 = this.unitTestFactory.getGroup();
        final ImplGroupRelatedEntity entity1 = new ImplGroupRelatedEntity(group1);
        final ImplGroupRelatedEntity entity2 = new ImplGroupRelatedEntity(group2);

        Assertions.assertThrows(IncompatibleGroupException.class, () -> entity1.checkCanAssociateWith(entity2));
        Assertions.assertThrows(IncompatibleGroupException.class, () -> entity2.checkCanAssociateWith(entity1));
    }

    static class ImplGroupRelatedEntity extends GroupRelatedEntity {
        public ImplGroupRelatedEntity(Group group) {
            this.group = group;
        }
    }
}
