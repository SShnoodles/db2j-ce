package cc.ssnoodles.db.domain;

import cc.ssnoodles.db.constant.DbType;
import cc.ssnoodles.db.constant.TemplateType;
import lombok.*;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2019-01-30 12:27
 */
@Data
public class Config {
    private DbType dbType;

    private String url;

    private String username;

    private String password;

    private String outPath;

    private TemplateType[] templates;

    private String author;

    private boolean overwriteFiles;

    private String singleTableName;

    private String singleTableRename;

    private String customTemplate;
}
