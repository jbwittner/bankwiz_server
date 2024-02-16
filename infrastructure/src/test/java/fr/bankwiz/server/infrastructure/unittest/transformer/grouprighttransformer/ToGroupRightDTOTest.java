package fr.bankwiz.server.infrastructure.unittest.transformer.grouprighttransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.openapi.model.UserGroupRightDTO;
import fr.bankwiz.server.domain.model.data.GroupRightDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain.GroupRightEnum;
import fr.bankwiz.server.infrastructure.transformer.GroupRightTransformer;
import fr.bankwiz.server.infrastructure.transformer.UserTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class ToGroupRightDTOTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    void checkData(GroupRightDomain groupRight, UserGroupRightDTO userGroupDetailsDTO) {
        Assertions.assertAll(
                () -> Assertions.assertEquals(groupRight.getId(), userGroupDetailsDTO.getGroupRightId()),
                () -> Assertions.assertEquals(
                        groupRight.getGroupRightEnum().name(),
                        userGroupDetailsDTO.getRight().name()),
                () -> {
                    final UserDTO userDTO = UserTransformer.toUserDTO(groupRight.getUser());
                    Assertions.assertEquals(userDTO, userGroupDetailsDTO.getUser());
                });
    }

    @Test
    void ok() {
        final GroupRightDomain groupRight = this.factory.getGroupRight(GroupRightEnum.WRITE);
        final UserGroupRightDTO userGroupDetailsDTO = GroupRightTransformer.toGroupRightDTO(groupRight);
        this.checkData(groupRight, userGroupDetailsDTO);
    }

    @Test
    void okList() {
        final List<GroupRightDomain> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(GroupRightEnum.WRITE));
        groupRights.add(this.factory.getGroupRight(GroupRightEnum.ADMIN));
        groupRights.add(this.factory.getGroupRight(GroupRightEnum.READ));

        final List<UserGroupRightDTO> userGroupDetailsDTOs = GroupRightTransformer.toGroupRightDTO(groupRights);

        Assertions.assertEquals(groupRights.size(), userGroupDetailsDTOs.size());

        userGroupDetailsDTOs.stream().forEach(detailDTO -> {
            final Optional<GroupRightDomain> optional = groupRights.stream()
                    .filter(groupRight -> groupRight.getId().equals(detailDTO.getGroupRightId()))
                    .findAny();
            if (optional.isEmpty()) {
                Assertions.fail();
            }

            final GroupRightDomain groupRight = optional.get();
            checkData(groupRight, detailDTO);
        });
    }
}
