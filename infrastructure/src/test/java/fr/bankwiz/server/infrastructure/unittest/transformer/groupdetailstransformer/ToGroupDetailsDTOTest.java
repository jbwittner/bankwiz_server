package fr.bankwiz.server.infrastructure.unittest.transformer.groupdetailstransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.GroupDetailsDTO;
import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.domain.model.data.GroupDetails;
import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.infrastructure.transformer.GroupDetailsTransformer;
import fr.bankwiz.server.infrastructure.transformer.UserTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class ToGroupDetailsDTOTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void ok() {
        final List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(GroupRightEnum.ADMIN));
        groupRights.add(this.factory.getGroupRight(GroupRightEnum.READ));
        groupRights.add(this.factory.getGroupRight(GroupRightEnum.WRITE));

        final GroupDetails groupDetails = GroupDetails.builder()
                .group(this.factory.getGroup())
                .groupRights(groupRights)
                .build();

        final GroupDetailsDTO groupDetailsDTO = GroupDetailsTransformer.toGroupDetailsDTO(groupDetails);

        Assertions.assertAll(
                () -> Assertions.assertEquals(groupDetails.getGroup().getId(), groupDetailsDTO.getId()),
                () -> Assertions.assertEquals(groupDetails.getGroup().getGroupName(), groupDetailsDTO.getGroupName()),
                () -> Assertions.assertEquals(
                        groupDetails.getGroupRights().size(),
                        groupDetailsDTO.getUsersRights().size()),
                () -> {
                    groupDetailsDTO.getUsersRights().stream().forEach(userGroupRightDTO -> {
                        final Optional<GroupRight> optional = groupRights.stream()
                                .filter(groupRight -> groupRight.getId().equals(userGroupRightDTO.getId()))
                                .findAny();
                        if (optional.isEmpty()) {
                            Assertions.fail();
                        }

                        final GroupRight groupRight = optional.get();
                        Assertions.assertAll(
                                () -> Assertions.assertEquals(groupRight.getId(), userGroupRightDTO.getId()),
                                () -> Assertions.assertEquals(
                                        groupRight.getGroupRightEnum().name(),
                                        userGroupRightDTO.getRight().name()),
                                () -> {
                                    final UserDTO userDTO = UserTransformer.toUserDTO(groupRight.getUser());
                                    Assertions.assertEquals(userDTO, userGroupRightDTO.getUser());
                                });
                    });
                });
    }
}
