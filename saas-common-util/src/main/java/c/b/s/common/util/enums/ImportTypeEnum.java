package c.b.s.common.util.enums;

/**
 * Created by guiqingqing on 2018/8/30.
 */
public enum ImportTypeEnum {
    ORGANIZATION(Byte.valueOf("1"), "批量新增组织"),
    USER(Byte.valueOf("2"), "批量新增用户"),
    EXTNO(Byte.valueOf("3"), "批量新增分机号"),
    CASE_IMPORT(Byte.valueOf("4"), "案件导入"),
    BACK_CASE(Byte.valueOf("5"), "退案"),
    BATCH_REPAY(Byte.valueOf("6"), "批量还款"),
    ALLOT_IMPORT(Byte.valueOf("7"), "导入分配"),
    ASSIST_ADJUST(Byte.valueOf("13"), "协催调整"),
    REPORT_EXPORT(Byte.valueOf("8"), "报表导出"),
    DUN_RECORD_IMPORT(Byte.valueOf("9"), "催记导入"),
    WHITE_IMPORT(Byte.valueOf("10"), "白名单导入"),
    CONTACT_IMPORT(Byte.valueOf("11"), "号码导入"),
    UPDATE_USER_GROUP(Byte.valueOf("12"), "批量调整用户组织");

    ImportTypeEnum(byte type, String description) {
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