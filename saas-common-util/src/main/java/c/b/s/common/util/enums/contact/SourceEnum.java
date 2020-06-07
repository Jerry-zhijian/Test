package c.b.s.common.util.enums.contact;

public enum SourceEnum {
     CASE_IMPORT(1, "分案导入"),
     PAGE_ADD(2, "页面添加"),
     INFO_REPAIR(3, "推荐"),
     API(4, "外部接口"),
     OTHER(0, "其他");

     SourceEnum(int code, String name){
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

    public static int getEnumByName(String name){
        for (SourceEnum e : SourceEnum.values()){
            if(e.getName().equals(name)){
                return e.getCode();
            }
        }
        return OTHER.getCode();
    }

    public static String getName(int code) {
        SourceEnum[] enums = SourceEnum.values();
        for (SourceEnum e : enums) {
            if (e.getCode() == code) {
                return e.getName();
            }
        }
        return "";
    }
}
