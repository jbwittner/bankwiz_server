package fr.bankwiz.server.infrastructure.tools.globalexceptionhandler;

import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.exception.FunctionalException;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;

class ExtractFieldsTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void ok() {
        final String attributOne = this.faker.space().galaxy();
                final String attributeTwo = this.faker.space().galaxy();

        final TestFunctionalException testFunctionalException = new TestFunctionalException(attributOne, attributeTwo);
    }

    class TestFunctionalException extends FunctionalException {

        private String attributeOne;
        private String attributeTwo;

        protected TestFunctionalException(String attributeOne, String attributeTwo) {
            super(attributeOne + attributeTwo);
            this.attributeOne = attributeOne;
            this.attributeTwo = attributeTwo;
        }

        public String getAttributeOne() {
            return attributeOne;
        }

        public String getAttributeTwo() {
            return attributeTwo;
        }

    }
}