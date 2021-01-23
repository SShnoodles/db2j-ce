package cc.ssnoodles.db.constant;

import cc.ssnoodles.db.util.StringUtil;
import lombok.*;

import java.util.Arrays;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2018/7/13 08:42
 */
@AllArgsConstructor
@Getter
public enum DbType {
    ORACLE("oracle", "oracle.jdbc.OracleDriver",
            new String[] {"ANONYMOUS", "APEX_030200", "APEX_PUBLIC_USER", "APPQOSSYS", "BI", "CTXSYS", "DBSNMP", "DIP",
                    "EXFSYS", "FLOWS_FILES", "HR", "IX", "MDDATA", "MDSYS", "MGMT_VIEW", "OE", "OLAPSYS", "ORACLE_OCM",
                    "ORDDATA", "ORDPLUGINS", "ORDSYS", "OUTLN", "OWBSYS", "OWBSYS_AUDIT", "PM", "SCOTT", "SH",
                    "SI_INFORMTN_SCHEMA", "SPATIAL_CSW_ADMIN_USR", "SPATIAL_WFS_ADMIN_USR", "SYS", "SYSMAN", "SYSTEM",
                    "WMSYS", "XDB", "XS$NULL"}),
    POSTGRESQL("postgresql", "org.postgresql.Driver", new String[] {"information_schema", "pg_catalog", "pg_toast", "pg_temp_1", "pg_toast_temp_1"}),
    MYSQL("mysql", "com.mysql.cj.jdbc.Driver", new String[] {"mysql", "information_schema", "performance_schema", "sys"});

    private final String type;

    private final String driver;

    private final String[] defaultSchemas;

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

    public boolean isDefaultSchema(String schema) {
        return Arrays.asList(defaultSchemas).contains(schema);
    }
}
