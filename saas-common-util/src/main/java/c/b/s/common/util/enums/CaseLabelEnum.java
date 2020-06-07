package c.b.s.common.util.enums;

/**
 * @author chengzhijian
 * @title: CaseLabelEnum
 * @projectName saas-common
 * @date 2020/1/617:02
 */
public enum CaseLabelEnum {
	NORMAL(0 ,"普通") ,
    GUIDE(1 ,"指导") ,
    WARN(2 ,"警告"),
    STOP_DUN(3,"停催");

    CaseLabelEnum(int type, String description) {
        this.type = type;
        this.description = description;
    }

    private int type;
    private String description;

    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
