package fr.bankwiz.server.tool;

import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import fr.bankwiz.openapi.model.FunctionalExceptionDTO;
import fr.bankwiz.openapi.model.MethodArgumentNotValidExceptionDTO;
import fr.bankwiz.server.exception.FunctionalException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({FunctionalException.class, MethodArgumentNotValidException.class, Exception.class})
    public ResponseEntity<Object> globalExceptionHandler(final Exception ex, final WebRequest request)
            throws Exception {

        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        Object result;

        if (ex instanceof FunctionalException) {
            result = this.manageFunctionalException(ex, request);
        } else if (ex instanceof MethodArgumentNotValidException) {
            result = this.manageMethodArgumentNotValidException(ex, request);
        } else {
            log.error(
                    "HANDLING - UnknownException : {} / message : {}",
                    ex.getClass().getSimpleName(),
                    ex.getMessage());
            throw ex;
        }

        return ResponseEntity.status(httpStatus).body(result);
    }

    private FunctionalExceptionDTO manageFunctionalException(final Throwable throwable, final WebRequest request) {

        final String details = request.getDescription(false);
        final OffsetDateTime offsetDateTime = OffsetDateTime.now();

        log.error(
                "HANDLING - FunctionalException : {} / message : {}",
                throwable.getClass().getSimpleName(),
                throwable.getMessage());

        return new FunctionalExceptionDTO(
                HttpStatus.BAD_REQUEST.value(),
                details,
                throwable.getClass().getSimpleName(),
                throwable.getMessage(),
                offsetDateTime);
    }

    private MethodArgumentNotValidExceptionDTO manageMethodArgumentNotValidException(
            final Throwable throwable, final WebRequest request) {

        final MethodArgumentNotValidException methodArgumentNotValidException =
                (MethodArgumentNotValidException) throwable;

        final String details = request.getDescription(false);
        final OffsetDateTime offsetDateTime = OffsetDateTime.now();

        String message = null;
        String field = null;
        String objectName = null;

        final var fieldError = methodArgumentNotValidException.getFieldError();

        if (fieldError != null) {
            message = fieldError.getDefaultMessage();
            field = fieldError.getField();
            objectName = fieldError.getObjectName();
        }

        log.error(
                "HANDLING - MethodArgumentNotValidException : {} / objectName : {} / field : {} / message : {}",
                details,
                objectName,
                field,
                message);

        return new MethodArgumentNotValidExceptionDTO(
                HttpStatus.BAD_REQUEST.value(),
                details,
                throwable.getClass().getSimpleName(),
                message,
                offsetDateTime,
                field,
                objectName);
    }
}
