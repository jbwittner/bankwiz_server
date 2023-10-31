package fr.bankwiz.server.domain.service;

import fr.bankwiz.server.domain.api.GroupApi;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.request.GroupCreationRequest;
import fr.bankwiz.server.domain.spi.AuthenticationSpi;
import fr.bankwiz.server.domain.spi.GroupSpi;
import fr.bankwiz.server.domain.spi.UserSpi;

public class GroupDomainService implements GroupApi {

    private final GroupSpi groupSpi;
    private final AuthenticationSpi authenticationSpi;

    public GroupDomainService(GroupSpi groupSpi, AuthenticationSpi authenticationSpi){
        this.groupSpi = groupSpi;
        this.authenticationSpi = authenticationSpi;
    }

    @Override
    public Group groupCreation(GroupCreationRequest groupCreationRequest) {
        final Group group = Group.builder().groupName(groupCreationRequest.getGroupName()).build();
        return group;
    }
    
}
