package cc.ssnoodles.db.handler;

import cc.ssnoodles.db.constant.*;
import cc.ssnoodles.db.domain.*;
import cc.ssnoodles.db.util.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2018/8/27 08:41
 */
public interface Template {

    String SYSTEM_NAME = System.getProperty("user.name");

    List<Schema> SCHEMAS = new ArrayList<>();

    String render(Config config, Table table) throws SQLException;

    void write(Config config) throws SQLException;

    String className(String name, String rename);

    default ClassContext getContent(Config config, Table table, String...importString) {
        ClassContext classContext = new ClassContext();
        classContext.setAuthor(StringUtil.isEmpty(config.getAuthor()) ? SYSTEM_NAME : config.getAuthor());
        classContext.setDateTime(TimeUtil.getTime());
        // imports
        List<String> imports = new ArrayList<>();
        imports.add("lombok.*");
        // class
        table.setClassName(className(table.getName(), config.getSingleTableRename()));
        table.setPrimaryKey(table.getPrimaryKeys().size() > 0);
        table.getColumns().forEach(column -> {
            column.setFieldName(StringUtil.underlineToHump(column.getName()));
            if (0 == column.getDecimalDigits() && "NUMBER".equals(column.getType())) {
                column.setFieldType(ColumnType.INTEGER.getJavaType());
            } else {
                column.setFieldType(ColumnType.get(column.getType().toUpperCase()));
            }
            table.getPrimaryKeys().forEach(key -> {
                if (key.getName().equals(column.getName())) {
                    key.setFieldType(column.getFieldType());
                    key.setFieldName(column.getFieldName());
                }
            });

        });
        imports.addAll(Arrays.asList(importString));
        classContext.setImports(imports);
        classContext.setTable(table);
        return classContext;
    }

    default void init(Config config) throws SQLException {
        SCHEMAS.clear();
        SCHEMAS.addAll(getTables(config));
    }

    default void refresh(List<Schema> schemas) {
        SCHEMAS.clear();
        SCHEMAS.addAll(schemas);
    }

    default String render(Config config, Table table, String template) {
        return VelocityUtil.generate(template, getContent(config, table));
    }

    default List<Schema> getTables(Config config) throws SQLException {
        Connection conn = ConnUtil.getConn(config);
        DatabaseMetaData dbMetData = conn.getMetaData();
        ResultSet schemas = dbMetData.getSchemas();

        List<Schema> schemaList = new ArrayList<>();
        while (schemas.next()) {
            Schema schema = new Schema();
            String schemaName = schemas.getString("TABLE_SCHEM");
            if (config.getDbType().isDefaultSchema(schemaName)) continue;
            ResultSet rs = dbMetData.getTables(
                    null,
                    schemaName,
                    StringUtil.isEmpty(config.getSingleTableName()) ? null : config.getSingleTableName(),
                    new String[]{"TABLE"});
            long timestamp = System.currentTimeMillis();
            List<Table> tableList = new ArrayList<>();
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                String tableRemarks = rs.getString("REMARKS");
                Table table = new Table();
                List<Column> columns = new ArrayList<>();
                ResultSet colRet;
                if (DbType.ORACLE.getType().equals(config.getDbType().getType())) {
                    colRet = dbMetData.getColumns(null, config.getUsername().toUpperCase(), tableName, "%");
                } else {
                    colRet = dbMetData.getColumns(config.getUsername(), schemaName, tableName, "%");
                }

                while (colRet.next()) {
                    String columnName = colRet.getString("COLUMN_NAME");
                    String columnType = colRet.getString("TYPE_NAME");
                    String columnRemarks = colRet.getString("REMARKS");
                    int columnSize = colRet.getInt("COLUMN_SIZE");
                    int nullable = colRet.getInt("NULLABLE");
                    int decimalDigits = colRet.getInt("DECIMAL_DIGITS");
                    Column column = new Column();
                    column.setName(columnName);
                    column.setType(columnType);
                    column.setRemarks(columnRemarks);
                    column.setDecimalDigits(decimalDigits);
                    column.setSize(columnSize);
                    column.setNullable(nullable == 1);
                    columns.add(column);
                }
                colRet.close();

                table.setName(tableName);
                table.setRemarks(tableRemarks);
                table.setColumns(columns);

                ResultSet primaryKeysRet;
                if (DbType.ORACLE.getType().equals(config.getDbType().getType())) {
                    primaryKeysRet = conn.getMetaData().getPrimaryKeys(null, config.getUsername().toUpperCase(), tableName);
                } else {
                    primaryKeysRet = conn.getMetaData().getPrimaryKeys(config.getUsername(), null, tableName);
                }
                List<Column> primaryKeys = new ArrayList<>();
                while (primaryKeysRet.next()) {
                    String columnName = primaryKeysRet.getString("COLUMN_NAME");
                    for (Column column : columns) {
                        if (column.getName().equals(columnName)) {
                            column.setPrimaryKey(true);
                            primaryKeys.add(column);
                        }
                    }
                }
                primaryKeysRet.close();

                table.setPrimaryKeys(primaryKeys);
                table.setTimestamp(timestamp);
                tableList.add(table);
                System.out.println("Db2j-ce: " + tableList.size() + ". " + schemaName + "." + tableName);
            }
            rs.close();
            schema.setName(schemaName);
            schema.setTables(tableList);
            schema.setTimestamp(timestamp);
            schemaList.add(schema);
        }
        schemas.close();
        conn.close();
        return schemaList;
    }
}
