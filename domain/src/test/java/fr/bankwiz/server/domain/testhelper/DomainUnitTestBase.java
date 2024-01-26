package fr.bankwiz.server.domain.testhelper;

import org.junit.jupiter.api.BeforeEach;

import fr.bankwiz.server.domain.testhelper.mock.DomainMockAuthenticationSpi;
import fr.bankwiz.server.domain.testhelper.mock.DomainMockBankAccountSpi;
import fr.bankwiz.server.domain.testhelper.mock.DomainMockCheckRightTool;
import fr.bankwiz.server.domain.testhelper.mock.DomainMockGroupRightSpi;
import fr.bankwiz.server.domain.testhelper.mock.DomainMockGroupSpi;
import fr.bankwiz.server.domain.testhelper.mock.DomainMockTransactionSpi;
import fr.bankwiz.server.domain.testhelper.mock.DomainMockUserSpi;
import fr.bankwiz.server.domain.testhelper.tools.DomainFaker;
import fr.bankwiz.server.domain.testhelper.tools.DomainUnitTestFactory;

public abstract class DomainUnitTestBase {

    protected DomainFaker faker;
    protected DomainUnitTestFactory factory;
    protected DomainMockAuthenticationSpi mockAuthenticationSpi;
    protected DomainMockUserSpi mockUserSpi;
    protected DomainMockGroupSpi mockGroupSpi;
    protected DomainMockGroupRightSpi mockGroupRightSpi;
    protected DomainMockBankAccountSpi mockBankAccountSpi;
    protected DomainMockTransactionSpi mockTransactionSpi;
    protected DomainMockCheckRightTool mockCheckRightTool;

    @BeforeEach
    public void beforeEach() {
        this.faker = new DomainFaker();
        this.factory = new DomainUnitTestFactory(faker);
        this.mockCheckRightTool = new DomainMockCheckRightTool();
        this.mockAuthenticationSpi = new DomainMockAuthenticationSpi();
        this.mockUserSpi = new DomainMockUserSpi();
        this.mockGroupSpi = new DomainMockGroupSpi();
        this.mockGroupRightSpi = new DomainMockGroupRightSpi();
        this.mockBankAccountSpi = new DomainMockBankAccountSpi();
        this.mockTransactionSpi = new DomainMockTransactionSpi();
        this.initDataBeforeEach();
    }

    /** Method used to prepare the data of tests */
    protected abstract void initDataBeforeEach();
}
