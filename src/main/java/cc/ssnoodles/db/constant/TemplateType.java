package cc.ssnoodles.db.constant;

import cc.ssnoodles.db.util.StringUtil;
import lombok.*;

/**
 * @author ssnoodles
 * @version 1.0
 * Create at 2019-01-30 13:52
 */
@AllArgsConstructor
@Getter
public enum TemplateType {
    JPA("jpa"),
    DTO("dto"),
    POJO("pojo"),
    REPOSITORY("repository"),
    CONTROLLER("controller"),
    CRITERIA("criteria"),
    REF("ref");

    private String type;

    public static TemplateType get(String type) {
        if (StringUtil.isEmpty(type)) {
            return TemplateType.POJO;
        }
        for (TemplateType value : values()) {
            if (value.type.equals(type)) {
                return value;
            }
        }
        return TemplateType.POJO;
    }
}
