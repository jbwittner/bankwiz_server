package fr.bankwiz.server.domain.testhelper.mock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import fr.bankwiz.server.domain.model.data.BankAccountDomain;
import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.spi.BankAccountSpi;

public class DomainMockBankAccountSpi extends DomainMockHelper<BankAccountSpi> {

    public DomainMockBankAccountSpi() {
        super(BankAccountSpi.class);
        Mockito.when(this.mock.getById(ArgumentMatchers.any())).thenCallRealMethod();
    }

    public DomainMockBankAccountSpi mockSave() {
        Mockito.when(this.mock.save(ArgumentMatchers.any())).thenAnswer(invocation -> {
            return invocation.<BankAccountDomain>getArgument(0);
        });
        return this;
    }

    public DomainMockBankAccountSpi mockExistsByGroup(final GroupDomain group, final Boolean exist) {
        Mockito.when(this.mock.existsByGroup(group)).thenReturn(exist);
        return this;
    }

    public DomainMockBankAccountSpi mockFindByGroup(
            final GroupDomain group, final List<BankAccountDomain> bankAccounts) {
        Mockito.when(this.mock.findByGroup(group)).thenReturn(bankAccounts);
        return this;
    }

    public DomainMockBankAccountSpi mockFindById(final UUID id, final Optional<BankAccountDomain> optionalBankAccount) {
        Mockito.when(this.mock.findById(id)).thenReturn(optionalBankAccount);
        return this;
    }
}
