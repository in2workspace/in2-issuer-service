package es.in2.issuer.backoffice.exception;

public class TenantAlreadyExistException extends RuntimeException {

    public TenantAlreadyExistException(String message) {
        super(message);
    }

}
