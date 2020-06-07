package c.b.s.common.util.enums;

public enum ColourLabelEnum {

    RED(1,"红色") ,
    YELLOW(2,"黄色") ,
    GREEN(3,"绿色") ,
    BLUE(4,"蓝色") ;

    private Integer code ;
    private String name ;

    ColourLabelEnum(Integer code, String name) {
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
