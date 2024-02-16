package fr.bankwiz.server.infrastructure.unittest.transformer.groupdetailstransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.GroupDetailsDTO;
import fr.bankwiz.openapi.model.UserDTO;
import fr.bankwiz.server.domain.model.data.GroupRightDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain.GroupRightEnum;
import fr.bankwiz.server.domain.model.other.GroupDetails;
import fr.bankwiz.server.infrastructure.transformer.GroupDetailsTransformer;
import fr.bankwiz.server.infrastructure.transformer.UserTransformer;
import fr.bankwiz.server.infrastructure.unittest.testhelper.InfrastructureUnitTestBase;

class ToGroupDetailsDTOTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void ok() {
        final List<GroupRightDomain> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(GroupRightEnum.ADMIN));
        groupRights.add(this.factory.getGroupRight(GroupRightEnum.READ));
        groupRights.add(this.factory.getGroupRight(GroupRightEnum.WRITE));

        final GroupDetails groupDetails = GroupDetails.builder()
                .group(this.factory.getGroup())
                .groupRights(groupRights)
                .build();

        final GroupDetailsDTO groupDetailsDTO = GroupDetailsTransformer.toGroupDetailsDTO(groupDetails);

        Assertions.assertAll(
                () -> Assertions.assertEquals(groupDetails.getGroup().getId(), groupDetailsDTO.getGroupId()),
                () -> Assertions.assertEquals(groupDetails.getGroup().getGroupName(), groupDetailsDTO.getGroupName()),
                () -> Assertions.assertEquals(
                        groupDetails.getGroupRights().size(),
                        groupDetailsDTO.getUsersRights().size()),
                () -> {
                    groupDetailsDTO.getUsersRights().stream().forEach(userGroupRightDTO -> {
                        final Optional<GroupRightDomain> optional = groupRights.stream()
                                .filter(groupRight -> groupRight.getId().equals(userGroupRightDTO.getGroupRightId()))
                                .findAny();
                        if (optional.isEmpty()) {
                            Assertions.fail();
                        }

                        final GroupRightDomain groupRight = optional.get();
                        Assertions.assertAll(
                                () -> Assertions.assertEquals(groupRight.getId(), userGroupRightDTO.getGroupRightId()),
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
