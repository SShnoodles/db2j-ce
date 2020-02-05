package cc.ssnoodles.db.constant;

import lombok.*;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2018/7/12 23:00
 */
@AllArgsConstructor
@Getter
public enum ColumnType {
    UUID("UUID"),

    CHAR("String"),
    VARCHAR("String"),
    VARCHAR2("String"),
    NVARCHAR2("String"),
    BPCHAR("String"),
    CHARACTER("String"),
    TEXT("String"),
    LONGTEXT("String"),

    DATE("OffsetDateTime"),
    DATETIME("OffsetDateTime"),
    TIMESTAMP("OffsetDateTime"),

    INTEGER("Integer"),
    SMALLINT("Integer"),
    INT("Integer"),
    INT2("Integer"),
    INT4("Integer"),
    BIT("Integer"),
    TINYINT("Integer"),

    INT8("Long"),
    BIGINT("Long"),

    NUMBER("BigDecimal"),
    NUMERIC("BigDecimal"),
    DECIMAL("BigDecimal"),
    DOUBLE("BigDecimal"),

    BOOLEAN("Boolean"),
    BOOL("Boolean");

    private String javaType;

    public static String get(String type) {
        for (ColumnType value : values()) {
            if (value.name().equalsIgnoreCase(type)) {
                return value.getJavaType();
            }
        }
        return "Object";
    }
}
