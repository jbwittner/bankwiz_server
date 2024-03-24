package fr.bankwiz.server.domain.testhelper.mock;

import org.mockito.Mockito;

import lombok.Getter;

@Getter
public abstract class MockHelper<T> {

    protected final T mock;

    protected MockHelper(final Class<T> mockClass) {
        this.mock = Mockito.mock(mockClass, invocation -> {
            throw new RuntimeException(
                    "Unstubbed method called: " + invocation.getMethod().getName());
        });
    }
}
