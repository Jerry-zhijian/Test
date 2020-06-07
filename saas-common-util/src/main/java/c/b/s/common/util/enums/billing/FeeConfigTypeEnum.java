package c.b.s.common.util.enums.billing;

/**
 * Created by guiqingqing on 2018/11/6.
 */
public enum FeeConfigTypeEnum {
    BILLING_TYPE(Byte.valueOf("1"), "计费方式"),
    BILLING_PERIOD(Byte.valueOf("2"), "计费周期"),
    DEDUCTION_TYPE(Byte.valueOf("3"), "扣费策略");

    FeeConfigTypeEnum(byte type, String description) {
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
}