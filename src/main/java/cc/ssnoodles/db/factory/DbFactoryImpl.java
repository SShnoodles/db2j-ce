package cc.ssnoodles.db.factory;

import cc.ssnoodles.db.constant.TemplateType;
import cc.ssnoodles.db.domain.Config;
import cc.ssnoodles.db.handler.*;
import cc.ssnoodles.db.util.StringUtil;

import java.sql.SQLException;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2020/2/3 10:19
 */
public class DbFactoryImpl implements DbFactory{

    @Override
    public void create(Config config) throws SQLException {
        if (StringUtil.isEmpty(config.getCustomTemplate())) {
            for (TemplateType template : config.getTemplates()) {
                if (TemplateType.POJO.equals(template)) {
                    new PojoTemplate().write(config);
                }
                if (TemplateType.DTO.equals(template)) {
                    new DtoTemplate().write(config);
                }
                if (TemplateType.JPA.equals(template)) {
                    new JpaTemplate().write(config);
                }
                if (TemplateType.REPOSITORY.equals(template)) {
                    new RepositoryTemplate().write(config);
                }
            }
        } else {
            new CustomTemplate().write(config);
        }
    }
}
