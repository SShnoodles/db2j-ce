package cc.ssnoodles.db.domain;

import lombok.*;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2018/7/12 23:13
 */
@Data
public class Column {
    private String name;
    private String type;
    private String remarks;
    private boolean primaryKey;
    private boolean nullable;
    private int size;
    private int decimalDigits;

    private String fieldName;
    private String fieldType;
}
