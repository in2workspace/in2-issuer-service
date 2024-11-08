package es.in2.issuer.backoffice.util;

import java.security.SecureRandom;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class ApplicationUtils {
    private static final SecureRandom secureRandom = new SecureRandom();
    
    private ApplicationUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static long parseIsoZonedDataTimeToUnixTimestamp(String date) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME);
        return zonedDateTime.toInstant().getEpochSecond();
    }

    public static String generateNonce() {
        byte[] nonceBytes = new byte[16]; // using 16-byte will result in 22 characters when Base64-encoded
        secureRandom.nextBytes(nonceBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(nonceBytes);
    }
    
}
