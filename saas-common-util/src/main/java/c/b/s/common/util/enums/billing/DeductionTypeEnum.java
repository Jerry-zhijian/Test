package c.b.s.common.util.enums.billing;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by guiqingqing on 2018/11/6.
 */
public enum DeductionTypeEnum {
    PERIOD_START(Byte.valueOf("1"), "周期起始"),
    PERIOD_END(Byte.valueOf("2"), "周期结束");

    DeductionTypeEnum(byte type, String description) {
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

    public static Map<Byte, String> allDeductionTypes() {
        return Arrays.asList(DeductionTypeEnum.values()).stream().collect(Collectors.toMap(DeductionTypeEnum::getType, e -> e.getDescription()));
    }
}