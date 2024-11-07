package es.in2.issuer.shared.tenant.exception;

public class TenantAlreadyExistException extends RuntimeException {

    public TenantAlreadyExistException(String message) {
        super(message);
    }

}
