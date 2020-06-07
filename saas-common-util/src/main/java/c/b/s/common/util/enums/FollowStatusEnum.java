package c.b.s.common.util.enums;

public enum FollowStatusEnum {

    ALL(1,"全部"),
    NONE_FOLLOW(2,"从未跟进"),
    TODAY_HAVE_FOLLOW(3,"今日已跟"),
    TODAY_NEW_CASE(4,"今日新案"),
    TODAY_MUST_FOLLOW(5,"今日必跟"),
    ONE_TO_THREE_FOLLOW(6,"1-3天未跟"),
    FOUR_TO_SEVEN_FOLLOW(7,"4-7天未跟"),
    EIGHT_TO_FIFTEEN_FOLLOW(8,"8-15天未跟"),
    OTHER_FOLLOW(9,"16-天及以上未跟");

    private Integer code ;
    private String name ;

    FollowStatusEnum(Integer code, String name) {
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
