package fr.bankwiz.server.service;

import fr.bankwiz.openapi.model.TransactionCreationRequest;
import fr.bankwiz.openapi.model.TransactionDTO;
import fr.bankwiz.openapi.model.TransactionUpdateRequest;
import fr.bankwiz.server.dto.TransactionDTOBuilder;
import fr.bankwiz.server.exception.BankAccountNotExistException;
import fr.bankwiz.server.exception.GroupNotExistException;
import fr.bankwiz.server.exception.TransactionNotExistException;
import fr.bankwiz.server.model.*;
import fr.bankwiz.server.repository.BankAccountRepository;
import fr.bankwiz.server.repository.GroupRepository;
import fr.bankwiz.server.repository.TransactionRepository;
import fr.bankwiz.server.security.AuthenticationFacade;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TransactionService {

    private static final TransactionDTOBuilder TRANSACTION_DTO_BUILDER = new TransactionDTOBuilder();

    private final AuthenticationFacade authenticationFacade;
    private final BankAccountRepository bankAccountRepository;
    private final GroupRepository groupRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(
            AuthenticationFacade authenticationFacade,
            BankAccountRepository bankAccountRepository,
            GroupRepository groupRepository,
            TransactionRepository transactionRepository
    ) {
        this.authenticationFacade = authenticationFacade;
        this.bankAccountRepository = bankAccountRepository;
        this.groupRepository = groupRepository;
        this.transactionRepository = transactionRepository;
    }

    public TransactionDTO addTransaction(TransactionCreationRequest transactionCreationRequest) {
        final Integer bankAccountId = transactionCreationRequest.getAccountId();

        final BankAccount bankAccount = this.bankAccountRepository
                .findById(bankAccountId)
                .orElseThrow(() -> new BankAccountNotExistException(bankAccountId));

        bankAccount.checkCanWriteGroup(this.authenticationFacade.getCurrentUser());

        Transaction transaction = Transaction.builder()
                .bankAccount(bankAccount)
                .amount(transactionCreationRequest.getAmountInCents())
                .date(transactionCreationRequest.getDate())
                .build();

        transaction = this.transactionRepository.save(transaction);

        return TRANSACTION_DTO_BUILDER.transform(transaction);
    }

    public void deleteTransaction(Integer transactionId) {
        final Transaction transaction = this.transactionRepository.findById(transactionId).orElseThrow(() -> new TransactionNotExistException(transactionId));

        final BankAccount bankAccount = transaction.getBankAccount();

        bankAccount.checkCanWriteGroup(this.authenticationFacade.getCurrentUser());

        this.transactionRepository.delete(transaction);
    }

    public TransactionDTO getTransaction(final Integer transactionId) {
        final Transaction transaction = this.transactionRepository.findById(transactionId).orElseThrow(() -> new TransactionNotExistException(transactionId));

        final BankAccount bankAccount = transaction.getBankAccount();

        bankAccount.checkCanReadGroup(this.authenticationFacade.getCurrentUser());

        return TRANSACTION_DTO_BUILDER.transform(transaction);
    }

    public TransactionDTO updateTransaction(final Integer transactionId, final TransactionUpdateRequest transactionUpdateRequest) {
        Transaction transaction = this.transactionRepository.findById(transactionId).orElseThrow(() -> new TransactionNotExistException(transactionId));

        final BankAccount bankAccount = transaction.getBankAccount();

        bankAccount.checkCanWriteGroup(this.authenticationFacade.getCurrentUser());

        if (transactionUpdateRequest.getAmountInCents() != null) {
            transaction.setAmount(transactionUpdateRequest.getAmountInCents());
        }
        if (transactionUpdateRequest.getDate() != null) {
            transaction.setDate(transactionUpdateRequest.getDate());
        }
        transaction = this.transactionRepository.save(transaction);
        return TRANSACTION_DTO_BUILDER.transform(transaction);
    }

    public List<TransactionDTO> getTransactionsByBankAccount(final Integer bankAccountId) {
        final User user = this.authenticationFacade.getCurrentUser();

        final BankAccount bankAccount = this.bankAccountRepository
                .findById(bankAccountId)
                .orElseThrow(() -> new BankAccountNotExistException(bankAccountId));

        bankAccount.checkCanReadGroup(user);

        final List<Transaction> transactions = this.transactionRepository.findAllByBankAccount(bankAccount);

        return TRANSACTION_DTO_BUILDER.transformAll(transactions);
    }

    public List<TransactionDTO> getTransactionsByGroup(final Integer groupId) {
        final User user = this.authenticationFacade.getCurrentUser();

        final Group group = this.groupRepository
                .findById(groupId)
                .orElseThrow(() -> new GroupNotExistException(groupId));

        group.checkCanRead(user);

        final List<Transaction> transactions = this.transactionRepository.findAllByBankAccountIn(group.getBankAccounts());

        return TRANSACTION_DTO_BUILDER.transformAll(transactions);
    }
}
