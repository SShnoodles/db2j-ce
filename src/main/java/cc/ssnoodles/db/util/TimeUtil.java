package cc.ssnoodles.db.util;

import java.time.LocalDateTime;
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

}
