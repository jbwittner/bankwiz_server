package fr.bankwiz.server.domain.testhelper.mock;

import org.mockito.Mockito;

public abstract class DomainMockHelper<T> {

    protected final T mock;

    protected DomainMockHelper(Class<T> mockClass) {
        this.mock = Mockito.mock(mockClass);
    }

    public T getMock() {
        return this.mock;
    }
}
