package fr.bankwiz.server.exception;

public class OneToManyElementException extends FunctionalException {

    public OneToManyElementException() {
        super("Entity have one or multiple elements (one to many)");
    }
}
