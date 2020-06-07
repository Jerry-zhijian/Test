package c.b.s.common.util.enums;

/**
 * Created by guiqingqing on 2019/6/21.
 */
public enum FlowApplyRepayFeatureEnum {
    SELF(0, "本人还款"),
    THIRD_PARTY(1, "第三方代偿");

    FlowApplyRepayFeatureEnum(int type, String description) {
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

    public static String getRepaymentTxtByType(int type) {
        FlowApplyRepayFeatureEnum[] enums = FlowApplyRepayFeatureEnum.values();
        for (FlowApplyRepayFeatureEnum e : enums) {
            if (e.getType() == type) {
                return e.getDescription();
            }
        }
        return "";
    }
}