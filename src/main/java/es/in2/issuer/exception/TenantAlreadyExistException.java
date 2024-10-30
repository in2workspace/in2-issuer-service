package es.in2.issuer.exception;

public class TenantAlreadyExistException extends RuntimeException {

    public TenantAlreadyExistException(String message) {
        super(message);
    }

}
