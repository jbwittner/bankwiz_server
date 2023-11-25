package fr.bankwiz.server.infrastructure.transformer.bankaccounttransformer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.bankwiz.openapi.model.BankAccountIndexDTO;
import fr.bankwiz.server.domain.model.data.BankAccount;
import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.infrastructure.testhelper.InfrastructureUnitTestBase;
import fr.bankwiz.server.infrastructure.transformer.BankAccountTransformer;

class ToBankAccountIndexDTOTest extends InfrastructureUnitTestBase {

    @Override
    protected void initDataBeforeEach() {}

    @Test
    void ok() {
        final Group group = this.factory.getGroup();
        final BankAccount bankAccount = this.factory.getBankAccount(group);
        final BankAccountIndexDTO bankAccountIndexDTO = BankAccountTransformer.toBankAccountIndexDTO(bankAccount);

        Assertions.assertAll(
                () -> Assertions.assertEquals(
                        bankAccount.getBankAccountName(), bankAccountIndexDTO.getBankAccountName()),
                () -> Assertions.assertEquals(bankAccount.getId(), bankAccountIndexDTO.getBankAccountId()));
    }
}
