package c.b.s.common.util.enums;

public enum ContactStatusEnum {
    OUGT(1, "OUGT_无法沟通"),
    LMS(2, "LMS_留言转告"),
    PTP(3, "PTP_承诺3日内还款"),
    NOST(4, "NOST_无法达成还款协议"),
    FOLLOWP(5, "FollowP_跟进下P客户"),
    OPTP(6, "OPTP_第三人3日内代偿"),
    HPTP(7, "HPTP_承诺还款，但不确定，极大可能不还"),
    NLMS(8, "NLMS_无法转告"),
    YJJD(9, "YJJD_一接就断"),
    NOCH(10, "NOCH_无此人"),
    EYQK(11, "EYQK_恶意欠款"),
    CDLT(12, "CDLT_资金困难"),
    ZMH(13, "ZMH_还款方式"),
    KFDH(14, "KFDH_客服电话"),
    YZM(15, "YZM_外部原因导致还不了款，需要协助"),
    OTHER(0, "其他");

    ContactStatusEnum(int code, String name){
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
        for (ContactStatusEnum e : ContactStatusEnum.values()){
            if(e.getName().equals(name)){
                return e.getCode();
            }
        }
        return OTHER.getCode();
    }

    public static String getName(int code) {
        ContactStatusEnum[] enums = ContactStatusEnum.values();
        for (ContactStatusEnum e : enums) {
            if (e.getCode() == code) {
                return e.getName();
            }
        }
        return "";
    }
}
