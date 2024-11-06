package es.in2.issuer.backoffice.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ApplicationUtilsTest {

    @Test
    void testConstructorThrowsException() {
        try {
            Constructor<ApplicationUtils> constructor = ApplicationUtils.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            Exception exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
            assertInstanceOf(IllegalStateException.class, exception.getCause());
            assertEquals("Utility class", exception.getCause().getMessage());
        } catch (NoSuchMethodException e) {
            fail("Constructor not found");
        }
    }

    @Test
    void testParseIsoZonedDateTimeToUnixTimestamp() {
        // Given an ISO Zoned Date Time string
        String isoZonedDateTime = "2024-11-05T00:00:00Z";

        // When parsed to Unix timestamp
        long expectedTimestamp = 1730764800L; // Corresponds to 2024-11-05T12:30:00Z in Unix time
        long actualTimestamp = ApplicationUtils.parseIsoZonedDataTimeToUnixTimestamp(isoZonedDateTime);

        // Then the parsed Unix timestamp should match the expected value
        assertEquals(expectedTimestamp, actualTimestamp, "The Unix timestamp should match the expected value");
    }

    @Test
    void testParseIsoZonedDateTimeToUnixTimestampWithDifferentTimezone() {
        // Given an ISO Zoned Date Time string with timezone offset
        String isoZonedDateTimeWithOffset = "2024-11-05T00:00:00+01:00";

        // When parsed to Unix timestamp
        long expectedTimestamp = 1730761200L; // The same as 2024-11-05T12:30:00Z in Unix time
        long actualTimestamp = ApplicationUtils.parseIsoZonedDataTimeToUnixTimestamp(isoZonedDateTimeWithOffset);

        // Then the parsed Unix timestamp should match the expected value
        assertEquals(expectedTimestamp, actualTimestamp, "The Unix timestamp should match the expected value even with timezone offset");
    }

    @Test
    void testInvalidDateFormat() {
        // Given an invalid date format
        String invalidDate = "2024-11-05 12:30:00";

        // Expecting an exception when parsed
        assertThrows(
                java.time.format.DateTimeParseException.class,
                () -> ApplicationUtils.parseIsoZonedDataTimeToUnixTimestamp(invalidDate),
                "Expected DateTimeParseException to be thrown for invalid date format"
        );
    }
}