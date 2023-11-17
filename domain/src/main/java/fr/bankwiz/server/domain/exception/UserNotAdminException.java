package fr.bankwiz.server.domain.exception;

import java.text.MessageFormat;
import java.util.UUID;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.User;
import lombok.ToString;

@ToString
public class UserNotAdminException extends FunctionalException {

    private final UUID userID;
    private final UUID groupID;

    public UserNotAdminException(final User user, final Group group) {
        super(MessageFormat.format(
                "The user {0} is not an administrator of group {1}", user.getUserId(), group.getGroupId()));
        this.userID = user.getUserId();
        this.groupID = group.getGroupId();
    }
}
