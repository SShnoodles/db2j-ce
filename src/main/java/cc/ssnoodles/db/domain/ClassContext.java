package cc.ssnoodles.db.domain;

import lombok.*;

import java.util.List;

/**
 * java entity class template context
 * @author ssnoodles
 * @version 1.0
 * Create at 2020/2/1 21:06
 */
@Data
public class ClassContext {
    private Table table;
    private List<String> imports;
    private String author;
    private String dateTime;
}
