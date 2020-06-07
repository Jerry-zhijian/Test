package c.b.s.common.util.enums;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guiqingqing on 2018/9/3.
 */
public enum RepayTypeEnum {
    CONSULT_REPAY(Byte.valueOf("1"), "协商结清"),
    NORMAL_REPAY(Byte.valueOf("2"), "正常还款");

    RepayTypeEnum(byte type, String description) {
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
        RepayTypeEnum[] enums = RepayTypeEnum.values();
        for (RepayTypeEnum e : enums) {
            if (e.getDescription().equals(description)) {
                return e.getType();
            }
        }
        return 0;
    }

    public static String allDescription() {
        List<String> result = new ArrayList();
        RepayTypeEnum[] enums = RepayTypeEnum.values();
        for (RepayTypeEnum e : enums) {
            result.add(e.getDescription());
        }
        return StringUtils.collectionToCommaDelimitedString(result);
    }
}