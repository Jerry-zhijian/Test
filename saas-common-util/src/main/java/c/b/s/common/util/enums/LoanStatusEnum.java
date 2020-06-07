package c.b.s.common.util.enums;

/**
 * Created by guiqingqing on 2018/11/26.
 */
public enum LoanStatusEnum {
    NOT_REPAY(Byte.valueOf("0"), "未还款"),
    PART_REPAY(Byte.valueOf("1"), "部分还款"),
    ALREADY_REPAY(Byte.valueOf("2"), "已还清"),
    CONSULT_REPAY(Byte.valueOf("3"), "协议结清"),
    BACK_CASE(Byte.valueOf("4"), "退案");

    LoanStatusEnum(byte type, String description) {
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