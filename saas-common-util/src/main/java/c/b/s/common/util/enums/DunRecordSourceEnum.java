package c.b.s.common.util.enums;

import c.b.s.common.util.enums.contact.RelationEnum;

public enum DunRecordSourceEnum {


    PAGE_ADD(1, "页面新增"),
    IMPORT(2, "导入"),
    OTHER(0, "其他");

    DunRecordSourceEnum(int code, String name){
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
        for (RelationEnum e : RelationEnum.values()){
            if(e.getName().equals(name)){
                return e.getCode();
            }
        }
        return OTHER.getCode();
    }

    public static String getName(int code) {
        RelationEnum[] enums = RelationEnum.values();
        for (RelationEnum e : enums) {
            if (e.getCode() == code) {
                return e.getName();
            }
        }
        return "";
    }
}
