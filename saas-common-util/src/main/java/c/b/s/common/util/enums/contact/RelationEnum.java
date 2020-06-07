package c.b.s.common.util.enums.contact;

import c.b.s.common.util.JsonUtil;
import c.b.s.common.util.SpringContextUtil;
import c.b.s.common.util.domain.TypeMapping;
import c.b.s.common.util.domain.TypeMappingMap;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 联系人关系枚举
 */
public enum RelationEnum {
    OTHER(0, "其他"),
    SPOUSE(1, "配偶"),
    CHILDREN(2, "子女"),
    PARENT(3, "父母"),
    BROTHERS_AND_SISTERS(4, "兄弟姐妹"),
    FRIEND(5, "朋友"),
    COLLEAGUE(6, "同事"),
    SELF(7, "本人"),
    COMPANY(8, "公司"),
    RELATIVES(9, "亲属"),
    CLASSMATE(10, "同学");

    RelationEnum(int code, String name){
        this.code = code;
        this.name = name;
    }

    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getCode(String name){
        name = mapping(name);

        for (RelationEnum e : RelationEnum.values()){
            if(e.getName().equals(name)){
                return e.getCode();
            }
        }
        return OTHER.getCode();
    }

    public static String getName(int code) {
        RelationEnum[] enums = RelationEnum.values();
        for (RelationEnum e : enums) {
            if (e.getCode() == code) {
                return e.getName();
            }
        }
        return "";
    }

    private static String mapping(String original) {
        String mappingSource = SpringContextUtil.getProperty("contact.relation.mapping", String.class, "");
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