package c.b.s.common.util.enums;

/**
 * Created by guiqingqing on 2019/7/1.
 */
public enum CaseUpdateTypeEnum {
    EXIT_CASE(1),
    CHANGE_OWNER(2),
    UPDATE_AMOUNT(3);

    CaseUpdateTypeEnum(int type) {
        this.type = type;
    }

    private int type;

    public int getType() {
        return type;
    }
}