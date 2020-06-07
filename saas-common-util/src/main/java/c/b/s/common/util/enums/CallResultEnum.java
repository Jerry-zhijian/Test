package c.b.s.common.util.enums;

public enum CallResultEnum {

    ANSWER(1, "已接听"),NO_ANSWER(2, "未接听");

    private Integer code;
    private String name;

    CallResultEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
