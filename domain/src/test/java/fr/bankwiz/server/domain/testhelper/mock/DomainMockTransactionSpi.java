package fr.bankwiz.server.domain.testhelper.mock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import fr.bankwiz.server.domain.model.data.BankAccountDomain;
import fr.bankwiz.server.domain.model.data.TransactionDomain;
import fr.bankwiz.server.domain.spi.TransactionSpi;

public class DomainMockTransactionSpi extends DomainMockHelper<TransactionSpi> {

    public DomainMockTransactionSpi() {
        super(TransactionSpi.class);
    }

    public DomainMockTransactionSpi verifySave(TransactionDomain transaction) {
        Mockito.verify(this.mock, Mockito.times(1)).save(transaction);
        return this;
    }

    public DomainMockTransactionSpi verifyDeleteById(UUID id) {
        Mockito.verify(this.mock, Mockito.times(1)).deleteById(id);
        return this;
    }

    public DomainMockTransactionSpi mockSave() {
        Mockito.when(this.mock.save(ArgumentMatchers.any())).thenAnswer(invocation -> {
            return invocation.<TransactionDomain>getArgument(0);
        });
        return this;
    }

    public DomainMockTransactionSpi mockFindByBankAccount(BankAccountDomain bankAccount, List<TransactionDomain> transactions) {
        Mockito.when(this.mock.findByBankAccount(bankAccount)).thenReturn(transactions);
        return this;
    }

    public DomainMockTransactionSpi mockFindById(UUID id, Optional<TransactionDomain> optionalTransaction) {
        Mockito.when(this.mock.findById(id)).thenReturn(optionalTransaction);
        return this;
    }
}
