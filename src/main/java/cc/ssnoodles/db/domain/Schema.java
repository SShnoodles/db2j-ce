package cc.ssnoodles.db.domain;

import lombok.Data;

import java.util.List;

@Data
public class Schema {
    private String name;

    private String remarks;

    private List<Table> tables;

    private long timestamp;
}
