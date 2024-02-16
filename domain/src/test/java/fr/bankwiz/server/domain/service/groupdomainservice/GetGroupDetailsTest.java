package fr.bankwiz.server.domain.service.groupdomainservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.exception.GroupNotExistException;
import fr.bankwiz.server.domain.exception.UserNoReadRightException;
import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain;
import fr.bankwiz.server.domain.model.data.GroupRightDomain.GroupRightEnum;
import fr.bankwiz.server.domain.model.data.UserDomain;
import fr.bankwiz.server.domain.model.other.GroupDetailsDomain;
import fr.bankwiz.server.domain.service.GroupDomainService;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;
import fr.bankwiz.server.domain.tools.CheckRightTools;

class GetGroupDetailsTest extends DomainUnitTestBase {

    private GroupDomainService groupDomainService;

    @Override
    protected void initDataBeforeEach() {
        final CheckRightTools checkRightTools =
                new CheckRightTools(this.mockGroupRightSpi.getMock(), this.mockAuthenticationSpi.getMock());
        this.groupDomainService = new GroupDomainService(
                this.mockGroupSpi.getMock(),
                this.mockGroupRightSpi.getMock(),
                this.mockUserSpi.getMock(),
                this.mockBankAccountSpi.getMock(),
                this.mockAuthenticationSpi.getMock(),
                checkRightTools);
    }

    @Test
    void getGroupOk() {
        final UserDomain user = this.factory.getUser();

        this.mockAuthenticationSpi.mockGetCurrentUser(user);

        final GroupDomain group = this.factory.getGroup();

        final UUID groupUuid = group.getId();

        final List<GroupRightDomain> groupRights = new ArrayList<>();
        final GroupRightDomain groupRight1 = this.factory.getGroupRight(group, user, GroupRightEnum.READ);
        final GroupRightDomain groupRight2 = this.factory.getGroupRight(group, this.factory.getUser(), GroupRightEnum.READ);

        groupRights.add(groupRight1);
        groupRights.add(groupRight2);

        this.mockGroupSpi.mockFindById(groupUuid, Optional.of(group));
        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);

        final GroupDetailsDomain groupDetails = this.groupDomainService.getGroupDetails(groupUuid);

        Assertions.assertAll(
                () -> Assertions.assertNotNull(groupDetails.getGroup()),
                () -> Assertions.assertNotEquals(
                        0, groupDetails.getGroupRights().size()),
                () -> Assertions.assertEquals(group, groupDetails.getGroup()),
                () -> Assertions.assertEquals(
                        groupRight1, groupDetails.getGroupRights().get(0)),
                () -> Assertions.assertEquals(
                        groupRight2, groupDetails.getGroupRights().get(1)));
    }

    @Test
    void groupNotExist() {
        final UUID uuid = UUID.randomUUID();
        this.mockGroupSpi.mockFindById(uuid, Optional.empty());
        Assertions.assertThrows(GroupNotExistException.class, () -> this.groupDomainService.getGroupDetails(uuid));
    }

    @Test
    void noReadException() {
        final UserDomain user = this.factory.getUser();

        this.mockAuthenticationSpi.mockGetCurrentUser(user);

        final GroupDomain group = this.factory.getGroup();

        final UUID groupUuid = group.getId();

        final List<GroupRightDomain> groupRights = new ArrayList<>();

        this.mockGroupSpi.mockFindById(groupUuid, Optional.of(group));
        this.mockGroupRightSpi.mockFindByGroup(group, groupRights);

        Assertions.assertThrows(UserNoReadRightException.class, () -> {
            this.groupDomainService.getGroupDetails(groupUuid);
        });
    }
}
