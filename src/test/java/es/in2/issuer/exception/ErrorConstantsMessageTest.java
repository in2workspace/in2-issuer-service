package es.in2.issuer.exception;

import es.in2.issuer.domain.exception.ErrorConstantsMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ErrorConstantsMessageTest {

    @Test
    void testConstructorThrowsException() {
        Exception exception = assertThrows(IllegalStateException.class, ErrorConstantsMessage::new);
        assertEquals("Utility class", exception.getMessage());
    }

    @Test
    void testConstantsValues() {
        assertEquals("Tenant not found", ErrorConstantsMessage.TENANT_NOT_FOUND_ERROR_MESSAGE);
        assertEquals("Tenant already exists", ErrorConstantsMessage.TENANT_ALREADY_EXISTS_ERROR_MESSAGE);
    }

}
