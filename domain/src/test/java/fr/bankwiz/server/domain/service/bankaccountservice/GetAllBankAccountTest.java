package fr.bankwiz.server.domain.service.bankaccountservice;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.model.data.GroupRight;
import fr.bankwiz.server.domain.model.data.User;
import fr.bankwiz.server.domain.model.data.GroupRight.GroupRightEnum;
import fr.bankwiz.server.domain.service.BankAccountService;
import fr.bankwiz.server.domain.testhelper.DomainUnitTestBase;
import fr.bankwiz.server.domain.tools.CheckRightTools;

class GetAllBankAccountTest extends DomainUnitTestBase {

    private BankAccountService bankAccountService;

    @Override
    protected void initDataBeforeEach() {
        final CheckRightTools checkRightTools = new CheckRightTools(this.mockGroupRightSpi.getMock());
        this.bankAccountService = new BankAccountService(
                this.mockBankAccountSpi.getMock(),
                this.mockGroupSpi.getMock(),
                this.mockGroupRightSpi.getMock(),
                this.mockAuthenticationSpi.getMock(),
                checkRightTools);
    }

    @Test
    void ok() {
        final User user = this.factory.getUser();

        this.mockAuthenticationSpi.mockGetCurrentUser(user);

        List<GroupRight> groupRights = new ArrayList<>();
        groupRights.add(this.factory.getGroupRight(user, GroupRightEnum.READ));
        groupRights.add(this.factory.getGroupRight(user, GroupRightEnum.READ));
        groupRights.add(this.factory.getGroupRight(user, GroupRightEnum.READ));
        
        this.mockGroupRightSpi.mockFindByUser(user, groupRights);


    }
    
}
