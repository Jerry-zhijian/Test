package c.b.s.common.util.enums.billing;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by guiqingqing on 2018/11/5.
 */
public enum BillingPeriodEnum {
    YEAR(Byte.valueOf("1"), "年"),
    MONTH(Byte.valueOf("2"), "月"),
    WEEK(Byte.valueOf("3"), "周"),
    DAY(Byte.valueOf("4"), "日");

    BillingPeriodEnum(byte type, String description) {
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

    public static Map<Byte, String> allBillingPeriods() {
        return Arrays.asList(BillingPeriodEnum.values()).stream().collect(Collectors.toMap(BillingPeriodEnum::getType, e -> e.getDescription()));
    }

    public static BillingPeriodEnum enumOf(byte type) {
        BillingPeriodEnum[] enums = BillingPeriodEnum.values();
        for (BillingPeriodEnum e : enums) {
            if (e.getType() == type) {
                return e;
            }
        }
        return null;
    }
}