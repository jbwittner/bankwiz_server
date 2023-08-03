package fr.bankwiz.server.dto;

import fr.bankwiz.openapi.model.BankAccountIndexDTO;
import fr.bankwiz.server.model.BankAccount;

public class BankAccountIndexDTOBuilder implements Transformer<BankAccount, BankAccountIndexDTO> {

    public BankAccountIndexDTO transform(final BankAccount input) {
        return new BankAccountIndexDTO(input.getId(), input.getName(), input.getBaseAmountDecimal());
    }
}
