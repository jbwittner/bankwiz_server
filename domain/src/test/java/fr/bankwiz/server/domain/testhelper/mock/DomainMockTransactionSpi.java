package fr.bankwiz.server.domain.testhelper.mock;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import fr.bankwiz.server.domain.model.data.Transaction;
import fr.bankwiz.server.domain.spi.TransactionSpi;

public class DomainMockTransactionSpi extends DomainMockHelper<TransactionSpi> {

    public DomainMockTransactionSpi() {
        super(TransactionSpi.class);
    }

    public DomainMockTransactionSpi mockSave() {
        Mockito.when(this.mock.save(ArgumentMatchers.any())).thenAnswer(invocation -> {
            return invocation.<Transaction>getArgument(0);
        });
        return this;
    }
}
