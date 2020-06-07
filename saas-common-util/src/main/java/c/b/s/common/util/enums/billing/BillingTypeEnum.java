package c.b.s.common.util.enums.billing;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by guiqingqing on 2018/11/5.
 */
public enum BillingTypeEnum {
    FIXED_UNIT_PRICE(Byte.valueOf("1"), "固定单价"),
    ACCUMULATIVE_BILLING(Byte.valueOf("2"), "累计计费"),
    ARRIVAL_BILLING(Byte.valueOf("3"), "到达计费"),
    RESOURCE_PACKAGE(Byte.valueOf("4"), "资源包");

    BillingTypeEnum(byte type, String description) {
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

    public static Map<Byte, String> allBillingTypes() {
        return Arrays.asList(BillingTypeEnum.values()).stream().collect(Collectors.toMap(BillingTypeEnum::getType, e -> e.getDescription()));
    }

    public static BillingTypeEnum enumOf(byte type) {
        BillingTypeEnum[] enums = BillingTypeEnum.values();
        for (BillingTypeEnum e : enums) {
            if (e.getType() == type) {
                return e;
            }
        }
        return null;
    }
}