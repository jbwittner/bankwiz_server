package fr.bankwiz.server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.bankwiz.openapi.model.BankAccountCreationRequest;
import fr.bankwiz.openapi.model.BankAccountDTO;
import fr.bankwiz.openapi.model.BankAccountGroupDTO;
import fr.bankwiz.openapi.model.BankAccountUpdateRequest;
import fr.bankwiz.server.dto.BankAccountDTOBuilder;
import fr.bankwiz.server.exception.GroupNotExistException;
import fr.bankwiz.server.model.BankAccount;
import fr.bankwiz.server.model.Group;
import fr.bankwiz.server.model.User;
import fr.bankwiz.server.repository.BankAccountRepository;
import fr.bankwiz.server.repository.GroupRepository;
import fr.bankwiz.server.security.AuthenticationFacade;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class BankAccountService {

    private static final BankAccountDTOBuilder BANK_ACCOUNT_DTO_BUILDER = new BankAccountDTOBuilder();

    private final AuthenticationFacade authenticationFacade;
    private final GroupRepository groupRepository;

    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(
            AuthenticationFacade authenticationFacade,
            GroupRepository groupRepository,
            BankAccountRepository bankAccountRepository) {
        this.authenticationFacade = authenticationFacade;
        this.groupRepository = groupRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    public BankAccountDTO addAccount(BankAccountCreationRequest bankAccountCreationRequest) {
        final Integer groupId = bankAccountCreationRequest.getGroupId();
        final Group group =
                this.groupRepository.findById(groupId).orElseThrow(() -> new GroupNotExistException(groupId));

        final User user = this.authenticationFacade.getCurrentUser();

        group.checkIsAdmin(user);

        BankAccount bankAccount = BankAccount.builder()
                .name(bankAccountCreationRequest.getAccountName())
                .baseAmountDecimal(bankAccountCreationRequest.getBaseAmountDecimal())
                .build();

        bankAccount = this.bankAccountRepository.save(bankAccount);

        group.addBankAccount(bankAccount);

        return BANK_ACCOUNT_DTO_BUILDER.transform(bankAccount);
    }

    public void deleteAccount(Integer bankAccountId) {}

    public BankAccountDTO getAccount(Integer bankAccountId) {
        return null;
    }

    public List<BankAccountGroupDTO> getBankAccounts() {
        return null;
    }

    public BankAccountDTO updateAccount(Integer bankAccountId, BankAccountUpdateRequest bankAccountUpdateRequest) {
        return null;
    }
}
