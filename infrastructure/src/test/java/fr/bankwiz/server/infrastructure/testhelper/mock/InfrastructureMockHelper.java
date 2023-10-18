package fr.bankwiz.server.infrastructure.testhelper.mock;

import org.mockito.Mockito;

public abstract class InfrastructureMockHelper<T> {

    protected final T mock;

    protected InfrastructureMockHelper(Class<T> mockClass) {
        this.mock = Mockito.mock(mockClass);
    }

    public T getMock() {
        return this.mock;
    }
}
