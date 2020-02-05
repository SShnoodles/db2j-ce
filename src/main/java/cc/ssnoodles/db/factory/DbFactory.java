package cc.ssnoodles.db.factory;

import cc.ssnoodles.db.domain.Config;

import java.sql.SQLException;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2020/2/3 10:18
 */
public interface DbFactory {
    void create(Config config) throws SQLException;
}
