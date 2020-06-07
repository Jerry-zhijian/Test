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
public enum EducationEnum {
    JUNIOR_MIDDLE_SCHOOL(1, "初中及以下"),
    HIGH_SCHOOL(2, "高中/中专"),
    JUNIOR_COLLEGE_EDUCATION(3, "专科"),
    UNDERGRADUATE_COURSE(4, "本科"),
    MASTER(5, "硕士"),
    LEARNED_SCHOLAR(6, "博士");

    EducationEnum(int type, String description) {
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

        EducationEnum[] enums = EducationEnum.values();
        for (EducationEnum e : enums) {
            if (e.getDescription().equals(description)) {
                return e.getType();
            }
        }
        return 0;
    }

    public static String getName(int code) {
        EducationEnum[] enums = EducationEnum.values();
        for (EducationEnum e : enums) {
            if (e.getType() == code) {
                return e.getDescription();
            }
        }
        return "";
    }

    private static String mapping(String original) {
        String mappingSource = SpringContextUtil.getProperty("userdetail.education.mapping", String.class, "");
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