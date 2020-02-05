package cc.ssnoodles.db;

import cc.ssnoodles.db.factory.DbFactoryImpl;
import cc.ssnoodles.db.util.FileUtil;

import java.sql.SQLException;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2020/1/30 16:21
 */
public class Application {

    public static void main(String[] args) throws SQLException {
        new DbFactoryImpl().create(FileUtil.PROPERTIES);
    }
}
