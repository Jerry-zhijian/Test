package c.b.s.common.util.enums;

/**
 * Created by guiqingqing on 2018/9/8.
 */
public enum FlowApplyStatusEnum {
    PENDING(0, "待审批"),
    APPROVE(1, "审批通过"),
    REJECT(2, "审批拒绝");

    FlowApplyStatusEnum(int type, String description) {
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