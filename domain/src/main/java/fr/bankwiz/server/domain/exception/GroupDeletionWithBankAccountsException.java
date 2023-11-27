package fr.bankwiz.server.domain.exception;

import java.util.UUID;

public class GroupDeletionWithBankAccountsException extends FunctionalException {

    public GroupDeletionWithBankAccountsException(final UUID groupId) {
        super("Cannot delete group with ID: " + groupId + " because it still has associated bank accounts.");
        this.attributes.put("groupId", groupId);
    }
}
