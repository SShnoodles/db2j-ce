package cc.ssnoodles.db.util;

import cc.ssnoodles.db.domain.Config;
import cc.ssnoodles.db.constant.DbType;

import java.sql.*;
import java.util.Properties;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2018/7/13 08:36
 */
public class ConnUtil {

    public static Connection getConn(Config config) {
        Properties props = new Properties();
        props.put("user", config.getUsername());
        props.put("password", config.getPassword());
        props.put("remarksReporting", "true");

        Connection conn = null;
        try {
            Class.forName(DbType.get(config.getDbType().getType()));
            conn = DriverManager.getConnection(config.getUrl(), props);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Connection failed");
        }
        return conn;
    }
}
