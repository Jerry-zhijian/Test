package c.b.s.common.util.enums;

public enum FollowTypeEnum {

    NO_FOLLOW(0,"未跟进"),FOLLOW(1,"跟进中");

    private Integer code ;
    private String name ;

    FollowTypeEnum(Integer code, String name) {
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
