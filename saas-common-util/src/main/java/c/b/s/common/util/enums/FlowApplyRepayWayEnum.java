package c.b.s.common.util.enums;

/**
 * Created by guiqingqing on 2019/6/21.
 */
public enum FlowApplyRepayWayEnum {
    OTHER(0, "其他"),
    APP(1, "App"),
    WECHAT(2, "微信"),
    ALIPAY(3, "支付宝"),
    PUBLIC_TRANSFER(4, "对公转账"),
    WEBSITE(5, "官网");

    FlowApplyRepayWayEnum(int type, String description) {
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
        FlowApplyRepayWayEnum[] enums = FlowApplyRepayWayEnum.values();
        for (FlowApplyRepayWayEnum e : enums) {
            if (e.getType() == type) {
                return e.getDescription();
            }
        }
        return "";
    }
}