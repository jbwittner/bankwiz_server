package fr.bankwiz.server.domain.exception;

import java.util.HashMap;
import java.util.Map;

public abstract class FunctionalException extends RuntimeException {

    protected final Map<String, Object> attributes = new HashMap<>();

    protected FunctionalException(final String message) {
        super(message);
    }
}
