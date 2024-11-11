package es.in2.issuer.domain.exception;

public class TenantAlreadyExistException extends RuntimeException {

    public TenantAlreadyExistException(String message) {
        super(message);
    }

}
