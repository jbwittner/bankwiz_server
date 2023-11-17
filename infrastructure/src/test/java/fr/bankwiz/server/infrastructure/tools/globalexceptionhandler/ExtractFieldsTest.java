package fr.bankwiz.server.infrastructure.tools.globalexceptionhandler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.server.domain.exception.FunctionalException;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.tools.GlobalExceptionHandler;
import lombok.EqualsAndHashCode;

class ExtractFieldsTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void ok() {
        final String attributOne = this.faker.space().galaxy();
        final String attributeTwo = this.faker.space().galaxy();

        final TestFunctionalException testFunctionalException = new TestFunctionalException(attributOne, attributeTwo);

        final var fields = GlobalExceptionHandler.extractFields(testFunctionalException);

        Assertions.assertAll(
                () -> Assertions.assertEquals(3, fields.size()),
                () -> Assertions.assertEquals(attributOne, fields.get("attributeOne")),
                () -> Assertions.assertEquals(attributeTwo, fields.get("attributeTwo")),
                () -> Assertions.assertEquals(new TestObject(), fields.get("testObject")));
    }

    @EqualsAndHashCode
    class TestObject {
        private String testObjectAtributOne = "one";
        private String testObjectAtributTwo = "two";

        public String getTestObjectAtributOne() {
            return testObjectAtributOne;
        }

        public String getTestObjectAtributTwo() {
            return testObjectAtributTwo;
        }
    }

    class TestFunctionalException extends FunctionalException {

        private String attributeOne;
        private String attributeTwo;
        private TestObject testObject;

        protected TestFunctionalException(String attributeOne, String attributeTwo) {
            super(attributeOne + attributeTwo);
            this.attributeOne = attributeOne;
            this.attributeTwo = attributeTwo;
            this.testObject = new TestObject();
        }

        public String getAttributeOne() {
            return attributeOne;
        }

        public String getAttributeTwo() {
            return attributeTwo;
        }

        public TestObject getTestObject() {
            return testObject;
        }
    }
}
