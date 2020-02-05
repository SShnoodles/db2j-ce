package cc.ssnoodles.db.util;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2018/7/13 08:49
 */
public class StringUtil {

    public static String underlineToHump(String str){
        StringBuilder result = new StringBuilder();
        String a[] = str.split("_");
        for(String s : a){
            if(result.length() == 0){
                result.append(s.toLowerCase());
            }else{
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    public static String underlineToHumpTopUpperCase(String str) {
        String s = underlineToHump(str);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static String underlineUrl(String str) {
        StringBuilder result = new StringBuilder();
        String[] a = str.split("_");
        for (String s : a) {
            result.append(s.toLowerCase());
            result.append("/");
        }
        String s = result.toString();
        return s.substring(0, s.length() - 1);
    }

    public static boolean isEmpty(String str) {
        return (str == null || "".equals(str));
    }

    public static String topLowerCase(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    public static String toUrl(String str) {
        StringBuilder result = new StringBuilder();
        String[] strs = str.split("(?<=[a-z])(?=[A-Z])");
        for (String s : strs) {
            result.append(s.toLowerCase());
            result.append("/");
        }
        String s = result.toString();
        return s.substring(0, s.length() - 1);
    }
}
