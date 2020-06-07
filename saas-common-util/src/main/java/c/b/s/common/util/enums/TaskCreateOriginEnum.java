package c.b.s.common.util.enums;

public enum TaskCreateOriginEnum {

    FRONT_CREATE(0,"前端创建"),INTERFACE_CREATE(1,"接口创建");

    private Integer code ;
    private String desc ;

    TaskCreateOriginEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
