package fr.bankwiz.server.service.groupservice;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.GroupDTO;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.GroupRight;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.service.GroupService;
import fr.bankwiz.server.testhelper.UnitTestBase;

class GetGroupsTest extends UnitTestBase {

    private GroupService groupService;

    @Override
    protected void initDataBeforeEach() {
        this.groupService = new GroupService(
                this.authenticationFacadeMockFactory.getAuthenticationFacade(),
                this.groupRepositoryMockFactory.getRepository(),
                this.userRepositoryMockFactory.getRepository(),
                this.groupRightRepositoryMockFactory.getRepository());
    }

    @Test
    void getGroupsOk() {
        final User user = this.unitTestFactory.getUser();
        final List<Group> groups = new ArrayList<>();
        groups.add(this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ));
        groups.add(this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ));
        groups.add(this.unitTestFactory.getGroupWithRight(user, GroupRight.GroupRightEnum.READ));

        this.authenticationFacadeMockFactory.mockGetCurrentUser(user);

        final List<GroupDTO> groupDTOs = this.groupService.getGroups();

        Assertions.assertEquals(3, groupDTOs.size());

        groups.forEach(g -> {
            GroupDTO groupDTO = groupDTOs.stream()
                    .filter(gDto -> gDto.getGroupId().equals(g.getUserGroupId()))
                    .findFirst()
                    .orElseThrow();
            Assertions.assertEquals(g.getName(), groupDTO.getGroupName());
        });
    }
}
