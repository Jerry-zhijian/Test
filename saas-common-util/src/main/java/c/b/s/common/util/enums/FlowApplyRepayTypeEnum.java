package c.b.s.common.util.enums;

/**
 * Created by guiqingqing on 2018/9/8.
 */
public enum FlowApplyRepayTypeEnum {
    PART_REPAY(Byte.valueOf("1"), "部分逾期还款"),
    ALL_REPAY(Byte.valueOf("2"), "全额逾期还款"),
    ASSIST_SETTLE(Byte.valueOf("3"), "协商结清");

    FlowApplyRepayTypeEnum(byte type, String description) {
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

    public static boolean isValidType(byte type) {
        FlowApplyRepayTypeEnum[] enums = FlowApplyRepayTypeEnum.values();
        for (FlowApplyRepayTypeEnum e : enums) {
            if (e.getType() == type) {
                return true;
            }
        }
        return false;
    }

    public static String getRepaymentTxtByType(byte type) {
        FlowApplyRepayTypeEnum[] enums = FlowApplyRepayTypeEnum.values();
        if (PART_REPAY.getType() == type) {
            return "部分还款";
        } else if (ALL_REPAY.getType() == type) {
            return "全额还款";
        } else if (ASSIST_SETTLE.getType() == type) {
            return "协商结清";
        }
        return "";
    }
}