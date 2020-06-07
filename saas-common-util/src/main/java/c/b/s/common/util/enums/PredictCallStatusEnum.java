package c.b.s.common.util.enums;

/**
 * @author chengzhijian
 * @title: PredictCallStatusEnum
 * @projectName saas-common
 * @date 2019/11/614:00
 */
public enum PredictCallStatusEnum {

    NOT_TO_CALL(0, "未拨打"),
    BE_BEING_CALL(1, "正在拨打"),
    HAVE_TO_CALL(2, "已拨打");

    PredictCallStatusEnum(int code, String name){
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
        return NOT_TO_CALL.getCode();
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
