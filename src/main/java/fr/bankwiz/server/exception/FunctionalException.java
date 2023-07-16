package fr.bankwiz.server.exception;

public abstract class FunctionalException extends RuntimeException {

    protected FunctionalException(final String message) {
        super(message);
    }

    protected FunctionalException(final Throwable cause) {
        super(cause);
    }
}
