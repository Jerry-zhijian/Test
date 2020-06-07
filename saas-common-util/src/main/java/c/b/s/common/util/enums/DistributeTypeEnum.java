package c.b.s.common.util.enums;

/**
 * Created by guiqingqing on 2018/9/4.
 */
public enum DistributeTypeEnum {
    AVERAGE_ALLOT(1, "平均分配"),
    AFFUSION_ALLOT(2, "注水分配");

    DistributeTypeEnum(int type, String description) {
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

    public static DistributeTypeEnum getDistributeTypeByType(int type) {
        DistributeTypeEnum[] enums = DistributeTypeEnum.values();
        for (DistributeTypeEnum e : enums) {
            if (e.getType() == type) {
                return e;
            }
        }
        return null;
    }
}