package es.in2.issuer.domain.exception;

public class ErrorConstantsMessage {

    ErrorConstantsMessage() {
        throw new IllegalStateException("Utility class");
    }

    public static final String TENANT_NOT_FOUND_ERROR_MESSAGE = "Tenant not found";
    public static final String TENANT_ALREADY_EXISTS_ERROR_MESSAGE = "Tenant already exists";

}
