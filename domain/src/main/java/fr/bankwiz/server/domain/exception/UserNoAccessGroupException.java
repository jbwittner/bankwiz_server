package fr.bankwiz.server.domain.exception;

import java.text.MessageFormat;
import java.util.UUID;

import fr.bankwiz.server.domain.model.data.Group;
import fr.bankwiz.server.domain.model.data.User;
import lombok.ToString;

@ToString
public class UserNoAccessGroupException extends FunctionalException {

    private final UUID userID;
    private final UUID groupID;

    public UserNoAccessGroupException(final User user, final Group group) {
        super(MessageFormat.format(
                "The user {0} does not have access to group {1}", user.getUserId(), group.getGroupId()));
        this.userID = user.getUserId();
        this.groupID = group.getGroupId();
    }
}
