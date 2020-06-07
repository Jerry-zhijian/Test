package c.b.s.common.util.enums;

public enum TaskStatusEnum {

    TASK_CREATE_FAIL(260, "任务创建失败"),
    TASK_CREATE_INIT(215, "任务初始化中"),
    TASK_CREATE_SUCCESS(220, "任务创建成功"),
    TASK_RUNNING(210, "任务执行中"),
    TASK_PAUSE(230, "任务暂停"),
    TASK_DELETE(240, "任务已删除"),
    TASK_INVALID(270, "任务已失效"),
    TASK_END(300, "任务已结束"),
    TASK_FINISH(400 ,"任务完成");

    TaskStatusEnum(Integer status, String description) {
        this.status = status;
        this.description = description;

    }

    private Integer status ;
    private String description ;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
