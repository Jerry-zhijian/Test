package c.b.s.common.util.enums.qc;

/**
 * Created by guiqingqing on 2019/5/9.
 */
public enum QualityCheckStatusEnum {
    UN_ALLOT(0, "未分配"),
    WAITING_FOR_CHECK(100, "待质检"),
    ALREADY_CHECK(200, "已质检");

    QualityCheckStatusEnum(int status, String description) {
        this.status = status;
        this.description = description;
    }

    private int status;
    private String description;

    public int getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}