package c.b.s.common.util.enums;

/**
 * @author chengzhijian
 * @title: TenantTypeEnum
 * @projectName saas-common
 * @date 2019/11/2317:38
 */
public enum TenantTypeEnum {

    COLLECT_COMPANY(0, "催收公司"),
    OUTSOURCE_COMPANY(1, "委外公司");

    TenantTypeEnum(int code, String name){
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
        return COLLECT_COMPANY.getCode();
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
