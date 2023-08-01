package fr.bankwiz.server.exception;

import fr.bankwiz.server.model.Group;

public class IncompatibleGroupException extends FunctionalException {

    public IncompatibleGroupException(Group associatedGroup, Group targetGroup) {
        super("Cannot associate with an entity from a different group. Associated group: "
                + associatedGroup.getName()
                + ", Target group: "
                + targetGroup.getName());
    }
}