package fr.bankwiz.server.unittest.testhelper;

import org.junit.jupiter.api.BeforeEach;

public abstract class UnitTestBase {

    /** Method launch before each test */
    @BeforeEach
    public void beforeEach() {
        this.initDataBeforeEach();
    }

    /** Method used to prepare the data of tests */
    protected abstract void initDataBeforeEach();
}
