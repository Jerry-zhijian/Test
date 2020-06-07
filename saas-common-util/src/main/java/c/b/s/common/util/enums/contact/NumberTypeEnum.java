package c.b.s.common.util.enums.contact;

/**
 * 导入号码，信修号码枚举
 */
public enum NumberTypeEnum {
    IMPORT_NUM(2, "导入号码"),
    RESTORATION_NUM(1, "信修号码");

    NumberTypeEnum(int code, String name){
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


    public static String getName(int code){
        for(NumberTypeEnum typeEnum:NumberTypeEnum.values()){
            if(typeEnum.getCode() == code){
                return typeEnum.getName();
            }
        }
        return "" ;
    }


    public static int getCode(int name){
        for(NumberTypeEnum typeEnum:NumberTypeEnum.values()){
            if(typeEnum.getName().equals(name)){
                return typeEnum.getCode();
            }
        }
        return 0 ;
    }


}