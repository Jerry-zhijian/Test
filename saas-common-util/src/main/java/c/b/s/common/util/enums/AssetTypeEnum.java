package c.b.s.common.util.enums;

/**
 * Created by guiqingqing on 2018/9/10.
 */
public enum AssetTypeEnum {
    OTHER(Byte.valueOf("0"), "其他"),
    LOAN_COMPANY(Byte.valueOf("1"), "贷款公司"),
    INSURANCE_COMPANY(Byte.valueOf("2"), "保险公司"),
    BANK(Byte.valueOf("3"), "银行"),
    COLLECTION_ORGANIZATION(Byte.valueOf("4"), "催收机构"),
    OWN_FUNDS(Byte.valueOf("5"), "自有资金");

    AssetTypeEnum(byte type, String description) {
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