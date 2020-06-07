package c.b.s.common.util.enums;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guiqingqing on 2018/9/7.
 */
public enum AssistTypeEnum {
    ALLOT_ASSIST(Byte.valueOf("1"), "分配协催"),
    STOP_ASSIST(Byte.valueOf("2"), "结束协催");

    AssistTypeEnum(byte type, String description) {
        this.type = type;
        this.description = description;
    }

    private byte type;
    private String description;

    public byte getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public static byte getTypeByDescription(String description) {
        AssistTypeEnum[] enums = AssistTypeEnum.values();
        for (AssistTypeEnum e : enums) {
            if (e.getDescription().equals(description)) {
                return e.getType();
            }
        }
        return 0;
    }

    public static String allDescription() {
        List<String> result = new ArrayList();
        AssistTypeEnum[] enums = AssistTypeEnum.values();
        for (AssistTypeEnum e : enums) {
            result.add(e.getDescription());
        }
        return StringUtils.collectionToCommaDelimitedString(result);
    }
}