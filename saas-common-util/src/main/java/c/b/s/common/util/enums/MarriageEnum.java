package c.b.s.common.util.enums;

import c.b.s.common.util.JsonUtil;
import c.b.s.common.util.SpringContextUtil;
import c.b.s.common.util.domain.TypeMapping;
import c.b.s.common.util.domain.TypeMappingMap;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by guiqingqing on 2018/9/1.
 */
public enum MarriageEnum {
    NEVER_MARRIED(1, "未婚"),
    MARRIED(2, "已婚"),
    WIDOWED(3, "丧偶"),
    DIVORCED(4, "离婚");

    MarriageEnum(int type, String description) {
        this.type = type;
        this.description = description;
    }

    private int type;
    private String description;

    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public static int getType(String description) {
        description = mapping(description);

        MarriageEnum[] enums = MarriageEnum.values();
        for (MarriageEnum e : enums) {
            if (e.getDescription().equals(description)) {
                return e.getType();
            }
        }
        return 0;
    }

    public static String getName(int code) {
        MarriageEnum[] enums = MarriageEnum.values();
        for (MarriageEnum e : enums) {
            if (e.getType() == code) {
                return e.getDescription();
            }
        }
        return "";
    }

    private static String mapping(String original) {
        String mappingSource = SpringContextUtil.getProperty("userdetail.marriage.mapping", String.class, "");
        if (StringUtils.isEmpty(mappingSource)) {
            return original;
        }
        TypeMappingMap typeMappingMap = JsonUtil.convertToObject(mappingSource, TypeMappingMap.class);

        List<TypeMapping> mappings = typeMappingMap.getMappings();
        for (TypeMapping mapping : mappings) {
            if (mapping.getOriginal().equals(original)) {
                return mapping.getMapping();
            }
        }

        return original;
    }
}