package cc.ssnoodles.db.constant;

import cc.ssnoodles.db.util.StringUtil;
import lombok.*;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2018/7/13 08:42
 */
@AllArgsConstructor
@Getter
public enum DbType {
    ORACLE("oracle", "oracle.jdbc.OracleDriver"),
    POSTGRESQL("postgresql", "org.postgresql.Driver"),
    MYSQL("mysql", "com.mysql.cj.jdbc.Driver");

    private String type;

    private String driver;

    public static String get(String type) {
        for (DbType value : values()) {
            if (value.getType().equals(type)) {
                return value.getDriver();
            }
        }
        throw new RuntimeException("No database was found to be supported");
    }

    public static DbType getFromUrl(String url) {
        if (StringUtil.isEmpty(url)) {
            throw new RuntimeException("Load configuration file url property failed");
        }
        if (url.contains(DbType.ORACLE.getType())) {
            return DbType.ORACLE;
        } else if (url.contains(DbType.POSTGRESQL.getType())) {
            return DbType.POSTGRESQL;
        } else if (url.contains(DbType.MYSQL.getType())) {
            return DbType.MYSQL;
        } else {
            throw new RuntimeException("No database was found to be supported");
        }
    }
}
