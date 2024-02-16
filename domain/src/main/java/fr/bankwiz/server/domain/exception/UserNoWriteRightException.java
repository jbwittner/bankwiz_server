package fr.bankwiz.server.domain.exception;

import java.text.MessageFormat;

import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.User;

public class UserNoWriteRightException extends FunctionalException {

    public UserNoWriteRightException(final User user, final GroupDomain group) {
        super(MessageFormat.format("The user {0} have no write right of group {1}", user.getId(), group.getId()));
        this.attributes.put("userID", user.getId());
        this.attributes.put("groupID", group.getId());
    }
}
