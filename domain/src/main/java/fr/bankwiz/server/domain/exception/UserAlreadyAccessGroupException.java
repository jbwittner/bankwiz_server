package fr.bankwiz.server.domain.exception;

import java.text.MessageFormat;

import fr.bankwiz.server.domain.model.data.GroupDomain;
import fr.bankwiz.server.domain.model.data.UserDomain;

public class UserAlreadyAccessGroupException extends FunctionalException {

    public UserAlreadyAccessGroupException(final UserDomain user, final GroupDomain group) {
        super(MessageFormat.format("The user {0} already has access to group {1}", user.getId(), group.getId()));
        this.attributes.put("userID", user.getId());
        this.attributes.put("groupID", group.getId());
    }
}
