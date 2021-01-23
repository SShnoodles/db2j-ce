package cc.ssnoodles.db.handler;

import cc.ssnoodles.db.constant.TemplateType;
import cc.ssnoodles.db.domain.Config;
import cc.ssnoodles.db.domain.Table;
import cc.ssnoodles.db.util.*;

import java.io.File;
import java.sql.SQLException;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2020/2/5 17:03
 */
public class RepositoryTemplate implements Template {

    @Override
    public String render(Config config, Table table) {
        return VelocityUtil.generate(TemplateType.REPOSITORY, getContent(config, table));
    }

    @Override
    public void write(Config config) throws SQLException {
        init(config);
        SCHEMAS.forEach(schema ->
                schema.getTables().forEach(table ->
                    FileUtil.write2JavaFiles(
                            config.getOutPath() + File.separator + className(table.getName(), config.getSingleTableRename()),
                            render(config, table),
                            config.isOverwriteFiles()
                    )
                )
        );
    }

    @Override
    public String className(String name, String rename) {
        return StringUtil.isEmpty(rename) ? StringUtil.underlineToHumpTopUpperCase(name) + "Repository" : StringUtil.underlineToHumpTopUpperCase(rename);
    }
}
