package fr.bankwiz.server.domain.testhelper.mock;

import java.util.List;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.spi.BankAccountSpi;

public class DomainMockBankAccountSpi extends DomainMockHelper<BankAccountSpi> {

    public DomainMockBankAccountSpi() {
        super(BankAccountSpi.class);
    }

    public DomainMockBankAccountSpi mockSave() {
        Mockito.when(this.mock.save(ArgumentMatchers.any())).thenAnswer(invocation -> {
            return invocation.<BankAccount>getArgument(0);
        });
        return this;
    }

    public DomainMockBankAccountSpi mockExistsByGroup(final Group group, final Boolean exist) {
        Mockito.when(this.mock.existsByGroup(group)).thenReturn(exist);
        return this;
    }

    public DomainMockBankAccountSpi mockFindByGroup(final Group group, final List<BankAccount> bankAccounts) {
        Mockito.when(this.mock.findByGroup(group)).thenReturn(bankAccounts);
        return this;
    }
}
