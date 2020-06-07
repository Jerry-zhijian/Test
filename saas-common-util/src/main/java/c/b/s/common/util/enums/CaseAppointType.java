package c.b.s.common.util.enums;

/**
 * @author chengzhijian
 * @title: CaseAppointType
 * @projectName saas-common
 * @date 2019/12/420:28
 */
public enum CaseAppointType {

    PAGE_ONE_CASE_APPOINT(Byte.valueOf("1"), "委案"),
    PAGE_BATCH_CASE_APPOINT(Byte.valueOf("2"), "批量委案"),
    IMPORT_CASE_APPOINT(Byte.valueOf("3"), "导入委案");

    CaseAppointType(byte type, String description) {
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
