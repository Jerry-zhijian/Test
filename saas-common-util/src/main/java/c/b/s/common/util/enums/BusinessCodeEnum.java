package c.b.s.common.util.enums;

public enum BusinessCodeEnum {
    AUTH_TOKEN_EXPIRE(80010, "token过期或不存在");

     BusinessCodeEnum(int code, String name){
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
}
