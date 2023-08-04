package fr.bankwiz.server.dto;

import java.util.List;

import fr.bankwiz.openapi.model.BankAccountGroupDTO;
import fr.bankwiz.openapi.model.BankAccountIndexDTO;
import fr.bankwiz.openapi.model.GroupIndexDTO;
import fr.bankwiz.server.model.Group;

public class BankAccountGroupDTOBuilder implements Transformer<Group, BankAccountGroupDTO> {

    private static final BankAccountIndexDTOBuilder BANK_ACCOUNT_INDEX_DTO = new BankAccountIndexDTOBuilder();
    private static final GroupIndexDTOBuilder GROUP_INDEX_DTO_BUILDER = new GroupIndexDTOBuilder();

    public BankAccountGroupDTO transform(final Group input) {
        final List<BankAccountIndexDTO> bankAccountIndexDTOs =
                BANK_ACCOUNT_INDEX_DTO.transformAll(input.getBankAccounts());
        final GroupIndexDTO groupIndexDTO = GROUP_INDEX_DTO_BUILDER.transform(input);
        return new BankAccountGroupDTO(groupIndexDTO, bankAccountIndexDTOs);
    }
}
