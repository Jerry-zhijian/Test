package c.b.s.common.util.enums;

/**
 * Created by guiqingqing on 2018/10/15.
 */
public enum CaseStatusEnum {
    INFORMATION_RESTORATION(1, "推荐"),
    ONESELF_PTP(100, "本人承诺还款"),
    ONESELF_NOT_PTP(101, "本人可联"),
    CONTACT_CONNECT(102, "联系人可联"),
    STOP_DUN(2,"停催");

    CaseStatusEnum(int type, String description) {
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