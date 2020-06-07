package c.b.s.common.util.enums;

/**
 * Created by guiqingqing on 2018/9/8.
 */
public enum FlowApplyTypeEnum {
    REPAY(Byte.valueOf("1"), "还款申请"),
    ASSIST(Byte.valueOf("2"), "协催申请");

    FlowApplyTypeEnum(byte type, String description) {
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

    public static String getDescription(byte type) {
        FlowApplyTypeEnum[] enums = FlowApplyTypeEnum.values();
        for (FlowApplyTypeEnum e : enums) {
            if (e.getType() == type) {
                return e.getDescription();
            }
        }
        return "";
    }
}