package cc.ssnoodles.db.util;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2019-03-11 12:45
 */
public class TimeUtil {

    public static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static final DateTimeFormatter DATE_TIME_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String getTime() {
        return DATE_TIME.format(LocalDateTime.now());
    }

    public static String getTimeSS() {
        return DATE_TIME_SS.format(LocalDateTime.now());
    }

    public static LocalDateTime timestampToLocalDateTime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static long localDateTimeToTimestamp(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zoneId).toInstant();
        return instant.toEpochMilli();
    }
}
