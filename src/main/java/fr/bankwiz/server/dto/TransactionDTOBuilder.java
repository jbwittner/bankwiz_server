package fr.bankwiz.server.dto;

import fr.bankwiz.openapi.model.BankAccountIndexDTO;
import fr.bankwiz.openapi.model.TransactionDTO;
import fr.bankwiz.openapi.model.TransactionIndexDTO;
import fr.bankwiz.server.model.Transaction;

public class TransactionDTOBuilder implements Transformer<Transaction, TransactionDTO> {

    private static final BankAccountIndexDTOBuilder BANK_ACCOUNT_INDEX_DTO = new BankAccountIndexDTOBuilder();
    private static final TransactionIndexDTOBuilder TRANSACTION_INDEX_DTO_BUILDER = new TransactionIndexDTOBuilder();

    public TransactionDTO transform(final Transaction input) {
        final BankAccountIndexDTO bankAccountIndexDTO = BANK_ACCOUNT_INDEX_DTO.transform(input.getBankAccount());
        final TransactionIndexDTO transactionIndexDTO = TRANSACTION_INDEX_DTO_BUILDER.transform(input);
        return new TransactionDTO(bankAccountIndexDTO, transactionIndexDTO);
    }
}
