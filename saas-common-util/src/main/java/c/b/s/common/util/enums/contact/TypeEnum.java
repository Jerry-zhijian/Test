package c.b.s.common.util.enums.contact;

public enum TypeEnum {

    NOTHING(1, "无"),
    MOBLIE(2, "手机"),
    COMPANY_PHONE(3, "公司电话"),
    RESIDENCE_PHONE(4, "住宅电话"),
    SECOND_PHONE(5, "二联电话"),
    THIRD_PHONE(6, "三联电话"),
    OTHER(0, "其他");

     TypeEnum(int code, String name){
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
        for (TypeEnum e : TypeEnum.values()){
            if(e.getName().equals(name)){
                return e.getCode();
            }
        }
        return OTHER.getCode();
    }

    public static String getName(int code) {
        TypeEnum[] enums = TypeEnum.values();
        for (TypeEnum e : enums) {
            if (e.getCode() == code) {
                return e.getName();
            }
        }
        return "";
    }
}
