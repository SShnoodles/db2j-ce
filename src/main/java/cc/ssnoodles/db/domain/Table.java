package cc.ssnoodles.db.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2018/7/12 23:13
 */
@Data
public class Table {
    private String name;
    private String remarks;
    private List<Column> columns;
    private List<Column> primaryKeys;

    private String className;
    private boolean primaryKey;
    private long timestamp;
}
