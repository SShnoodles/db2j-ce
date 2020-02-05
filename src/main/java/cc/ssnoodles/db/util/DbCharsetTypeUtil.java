package cc.ssnoodles.db.util;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2018/7/13 08:35
 */
public class DbCharsetTypeUtil {

    public static String convertDatabaseCharsetType(String in, String type) {
        String dbUser;
        if (type.equals("oracle")) {
            dbUser = in.toUpperCase();
        } else if (type.equals("postgresql")) {
            dbUser = "public";
        } else if (type.equals("mysql")) {
            dbUser = null;
        } else {
            dbUser = in;
        }
        return dbUser;
    }
}
