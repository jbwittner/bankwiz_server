package fr.bankwiz.server.dto;

import fr.bankwiz.openapi.model.*;
import fr.bankwiz.server.model.BankAccount;

public class BankAccountDTOBuilder implements Transformer<BankAccount, BankAccountDTO> {

    private static final BankAccountIndexDTOBuilder BANK_ACCOUNT_INDEX_DTO = new BankAccountIndexDTOBuilder();
    private static final GroupIndexDTOBuilder GROUP_INDEX_DTO_BUILDER = new GroupIndexDTOBuilder();

    public BankAccountDTO transform(final BankAccount input) {
        final BankAccountIndexDTO bankAccountIndexDTO = BANK_ACCOUNT_INDEX_DTO.transform(input);
        final GroupIndexDTO groupIndexDTO = GROUP_INDEX_DTO_BUILDER.transform(input.getGroup());
        return new BankAccountDTO(groupIndexDTO, bankAccountIndexDTO);
    }
}
