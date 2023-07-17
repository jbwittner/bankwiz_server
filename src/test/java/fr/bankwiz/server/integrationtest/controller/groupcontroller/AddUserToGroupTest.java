package fr.bankwiz.server.integrationtest.controller.groupcontroller;

import org.junit.Test;

import fr.bankwiz.openapi.model.AddUserGroupRequest;
import fr.bankwiz.openapi.model.GroupAuthorizationEnum;
import fr.bankwiz.server.integrationtest.testhelper.IntegrationTestBase;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.model.GroupRight.GroupRightEnum;

public class AddUserToGroupTest extends IntegrationTestBase {

    @Override
    protected void initDataBeforeEach() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initDataBeforeEach'");
    }

    @Test
    void addUserOk() {
        final User admin = this.integrationTestFactory.getUser();
        final Group group = this.integrationTestFactory.getGroupWithRigh(admin, GroupRightEnum.ADMIN);

        final User userToAdd = this.integrationTestFactory.getUser();
        final Integer userToAddId = userToAdd.getUserId();

        final AddUserGroupRequest addUserGroupRequest =
                new AddUserGroupRequest(userToAddId, GroupAuthorizationEnum.READ);

    }
    
}
