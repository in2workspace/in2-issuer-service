package es.in2.issuer.exception;

public class ErrorConstantsMessage {

    ErrorConstantsMessage() {
        throw new IllegalStateException("Utility class");
    }

    public static final String TENANT_NOT_FOUND_ERROR_MESSAGE = "Tenant not found";
    public static final String TENANT_ALREADY_EXISTS_ERROR_MESSAGE = "Tenant already exists";
    public static final String JWT_PARSE_ERROR_MESSAGE = "Error parsing JWT";
    public static final String AUTHORIZATION_ERROR_MESSAGE = "Unauthorized";

}
