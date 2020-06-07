package c.b.s.common.util.enums;

/**
 * Created by guiqingqing on 2018/9/3.
 */
public enum AssistStatusEnum {
    WAITING_FOR_ASSIST(Byte.valueOf("10"), "待协催"),
    ASSISTING(Byte.valueOf("20"), "协催中"),
    STOP_ASSIST(Byte.valueOf("30"), "停止协催");

    AssistStatusEnum(byte type, String description) {
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