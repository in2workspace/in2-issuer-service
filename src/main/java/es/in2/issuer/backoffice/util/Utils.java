package es.in2.issuer.backoffice.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static long parseIsoZonedDataTimeToUnixTimestamp(String date) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME);
        return zonedDateTime.toInstant().getEpochSecond();
    }
}
