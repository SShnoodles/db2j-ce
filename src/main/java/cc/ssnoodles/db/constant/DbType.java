package cc.ssnoodles.db.constant;

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
}
