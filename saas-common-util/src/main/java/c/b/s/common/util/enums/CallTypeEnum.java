package c.b.s.common.util.enums;

/**
 * @author chengzhijian
 * @title: CallTypeEnum
 * @projectName saas-common
 * @date 2019/11/519:56
 */
public enum CallTypeEnum {

    CASE_DEEP_ROOT(1, "案件深跟"),
    CASE_ROUND_DIAL(2, "案件轮拨");

    CallTypeEnum(int code, String name){
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
        return CASE_DEEP_ROOT.getCode();
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
