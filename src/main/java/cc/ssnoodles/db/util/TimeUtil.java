package cc.ssnoodles.db.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2019-03-11 12:45
 */
public class TimeUtil {

    private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String getTime() {
        return DATE_TIME.format(LocalDateTime.now());
    }

}
