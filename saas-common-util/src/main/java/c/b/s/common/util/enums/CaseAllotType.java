package c.b.s.common.util.enums;

/**
 * Created by guiqingqing on 2018/9/4.
 */
public enum CaseAllotType {
    PAGE_ONE_CASE_ALLOT(Byte.valueOf("1"), "案件分配"),
    PAGE_BATCH_CASE_ALLOT(Byte.valueOf("2"), "批量分配"),
    IMPORT_CASE_ALLOT(Byte.valueOf("3"), "导入分配"),
    CASE_CLEAR(Byte.valueOf("4"), "清空案件"),
    CHANGE_OWNER(Byte.valueOf("5"), "更换人员"),
    API_ONE_CASE_ALLOT(Byte.valueOf("6"), "案件分配"),
    API_CASE_CLEAR(Byte.valueOf("7"), "清空案件");

    CaseAllotType(byte type, String description) {
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

    public static String getDescriptionByType(byte type) {
        CaseAllotType[] enums = CaseAllotType.values();
        for (CaseAllotType e : enums) {
            if (e.getType() == type) {
                return e.getDescription();
            }
        }
        return "";
    }
}