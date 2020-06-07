package c.b.s.common.util.enums;

public enum CallStatusEnum {


    NORMAL(1, "正常通话"),
    TURNOFF(2, "关机"),
    DOWN(3, "停机"),
    EMPTY(4, "空号"),
    HANGUP(5, "挂断"),
    UNCONNECTED(6, "无应答"),
    IVR(7, "IVR播报"),
    OTHER(0, "其他");


    CallStatusEnum(int code, String name){
        this.code = code;
        this.name = name;
    }

    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getCode(String name){
        for (CallStatusEnum e : CallStatusEnum.values()){
            if(e.getName().equals(name)){
                return e.getCode();
            }
        }
        return OTHER.getCode();
    }

    public static String getName(int code) {
        CallStatusEnum[] enums = CallStatusEnum.values();
        for (CallStatusEnum e : enums) {
            if (e.getCode() == code) {
                return e.getName();
            }
        }
        return "";
    }
}
