package c.b.s.common.util.enums;

public enum VendorTypeEnum {

    ONE_HAND(1 ,"一手") ,
    TWO_HAND(2 ,"二手") ,
    THREE_HAND(3 ,"三手") ,
    FOUR_HAND(4 ,"四手") ,
    FIVE_HAND(5 ,"五手") ;

    private Integer type ;
    private String description ;

    VendorTypeEnum(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
