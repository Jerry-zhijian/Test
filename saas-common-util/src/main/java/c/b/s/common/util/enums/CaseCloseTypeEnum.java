package c.b.s.common.util.enums;

/**
 * Created by guiqingqing on 2018/9/1.
 */
public enum CaseCloseTypeEnum {
    NO_CLOSE(Byte.valueOf("0"), "未关闭"),
    PAY_OFF(Byte.valueOf("1"), "还清"),
    BACK_CASE(Byte.valueOf("2"), "退案"),
    CONSULT_REPAY(Byte.valueOf("3"), "协商结清"),
    RECYCLE_CASE(Byte.valueOf("4"), "回收案件");

    CaseCloseTypeEnum(byte type, String description) {
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