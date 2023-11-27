package fr.bankwiz.server.domain.testhelper;

import org.junit.jupiter.api.BeforeEach;

import fr.bankwiz.server.domain.service.BankAccountService;
import fr.bankwiz.server.domain.service.GroupDomainService;
import fr.bankwiz.server.domain.service.UserDomainService;
import fr.bankwiz.server.domain.testhelper.mock.DomainMockAuthenticationSpi;
import fr.bankwiz.server.domain.testhelper.mock.DomainMockBankAccountSpi;
import fr.bankwiz.server.domain.testhelper.mock.DomainMockGroupRightSpi;
import fr.bankwiz.server.domain.testhelper.mock.DomainMockGroupSpi;
import fr.bankwiz.server.domain.testhelper.mock.DomainMockUserSpi;
import fr.bankwiz.server.domain.testhelper.tools.DomainFaker;
import fr.bankwiz.server.domain.testhelper.tools.DomainUnitTestFactory;
import fr.bankwiz.server.domain.tools.CheckRightTools;

public abstract class DomainUnitTestBase {

    protected DomainFaker faker;
    protected DomainUnitTestFactory factory;
    protected DomainMockAuthenticationSpi mockAuthenticationSpi;
    protected DomainMockUserSpi mockUserSpi;
    protected DomainMockGroupSpi mockGroupSpi;
    protected DomainMockGroupRightSpi mockGroupRightSpi;
    protected DomainMockBankAccountSpi mockBankAccountSpi;
    protected CheckRightTools checkRightTools;

    @BeforeEach
    public void beforeEach() {
        this.faker = new DomainFaker();
        this.factory = new DomainUnitTestFactory(faker);
        this.mockAuthenticationSpi = new DomainMockAuthenticationSpi();
        this.mockUserSpi = new DomainMockUserSpi();
        this.mockGroupSpi = new DomainMockGroupSpi();
        this.mockGroupRightSpi = new DomainMockGroupRightSpi();
        this.mockBankAccountSpi = new DomainMockBankAccountSpi();
        this.checkRightTools = new CheckRightTools(this.mockGroupRightSpi.getMock());
        this.initDataBeforeEach();
    }

    protected GroupDomainService buildGroupDomainService() {
        return new GroupDomainService(
                this.mockGroupSpi.getMock(),
                this.mockGroupRightSpi.getMock(),
                this.mockUserSpi.getMock(),
                this.mockBankAccountSpi.getMock(),
                this.mockAuthenticationSpi.getMock(),
                checkRightTools);
    }

    protected UserDomainService buildUserDomainService() {
        return new UserDomainService(this.mockAuthenticationSpi.getMock(), this.mockUserSpi.getMock());
    }

    protected BankAccountService buildBankAccountService() {
        return new BankAccountService(
                this.mockBankAccountSpi.getMock(),
                this.mockGroupSpi.getMock(),
                this.mockAuthenticationSpi.getMock(),
                this.checkRightTools);
    }

    /** Method used to prepare the data of tests */
    protected abstract void initDataBeforeEach();
}
