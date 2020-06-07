package c.b.s.common.util.enums;

/**
 * Created by guiqingqing on 2018/8/30.
 */
public enum MessageTypeEnum {
    BATCH_UPLOAD(Byte.valueOf("1"), "批量处理"),
    FLOW_APPROVAL(Byte.valueOf("2"), "流程审批"),
    BATCH_ALLOT(Byte.valueOf("3"), "批量分配"),
    CASE_CLEAR(Byte.valueOf("4"), "清空案件"),
    CHANGE_OWNER(Byte.valueOf("5"), "更换人员"),
    REPORT_EXPORT(Byte.valueOf("6"), "报表导出"),
    BATCH_DOWNLOAD(Byte.valueOf("7"), "批量下载"),
    REDAIL_NUMBER(Byte.valueOf("8"), "重新呼叫"),
    PROMPT_MESSAGE(Byte.valueOf("9"), "提示消息"),
    APPROVAL_RESULT(Byte.valueOf("10"), "审批结果"),
    RE_CHECK_RESULT(Byte.valueOf("11"), "录音复检"),
    CASE_EXPORT(Byte.valueOf("12"), "案件导出"),
    MISSED_CALL(Byte.valueOf("13"), "未接来电"),
    BATCH_DOWNLOAD_SOUND_RECORD(Byte.valueOf("14"), "批量下载录音");

    MessageTypeEnum(byte type, String description) {
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
}