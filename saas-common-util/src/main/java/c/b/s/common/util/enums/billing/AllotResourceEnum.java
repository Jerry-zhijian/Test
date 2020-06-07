package c.b.s.common.util.enums.billing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guiqingqing on 2019/3/27.
 */
public enum AllotResourceEnum {
    AMOUNT_100(16, 100, "100分案资源"),
    AMOUNT_200(17, 200, "200分案资源"),
    AMOUNT_300(18, 300, "300分案资源"),
    AMOUNT_400(19, 400, "400分案资源"),
    AMOUNT_500(20, 500, "500分案资源"),
    AMOUNT_600(21, 600, "600分案资源"),
    AMOUNT_700(22, 700, "700分案资源"),
    AMOUNT_800(23, 800, "800分案资源"),
    AMOUNT_900(24, 900, "900分案资源"),
    AMOUNT_1000(25, 1000, "1000分案资源"),
    AMOUNT_1100(26, 1100, "1100分案资源"),
    AMOUNT_1200(27, 1200, "1200分案资源"),
    AMOUNT_1300(28, 1300, "1300分案资源"),
    AMOUNT_1400(29, 1400, "1400分案资源"),
    AMOUNT_1500(30, 1500, "1500分案资源"),
    AMOUNT_1600(31, 1600, "1600分案资源"),
    AMOUNT_1700(32, 1700, "1700分案资源"),
    AMOUNT_1800(33, 1800, "1800分案资源"),
    AMOUNT_1900(34, 1900, "1900分案资源"),
    AMOUNT_2000(35, 2000, "2000分案资源"),
    AMOUNT_2100(36, 2100, "2100分案资源"),
    AMOUNT_2200(37, 2200, "2200分案资源"),
    AMOUNT_2300(38, 2300, "2300分案资源"),
    AMOUNT_2400(39, 2400, "2400分案资源"),
    AMOUNT_2500(40, 2500, "2500分案资源"),
    AMOUNT_2600(41, 2600, "2600分案资源"),
    AMOUNT_2700(42, 2700, "2700分案资源"),
    AMOUNT_2800(43, 2800, "2800分案资源"),
    AMOUNT_2900(44, 2900, "2900分案资源"),
    AMOUNT_3000(45, 3000, "3000分案资源"),
    AMOUNT_3100(46, 3100, "3100分案资源"),
    AMOUNT_3200(47, 3200, "3200分案资源"),
    AMOUNT_3300(48, 3300, "3300分案资源"),
    AMOUNT_3400(49, 3400, "3400分案资源"),
    AMOUNT_3500(50, 3500, "3500分案资源"),
    AMOUNT_3600(51, 3600, "3600分案资源"),
    AMOUNT_3700(52, 3700, "3700分案资源"),
    AMOUNT_3800(53, 3800, "3800分案资源"),
    AMOUNT_3900(54, 3900, "3900分案资源"),
    AMOUNT_4000(55, 4000, "4000分案资源"),
    AMOUNT_4100(56, 4100, "4100分案资源"),
    AMOUNT_4200(57, 4200, "4200分案资源"),
    AMOUNT_4300(58, 4300, "4300分案资源"),
    AMOUNT_4400(59, 4400, "4400分案资源"),
    AMOUNT_4500(60, 4500, "4500分案资源"),
    AMOUNT_4600(61, 4600, "4600分案资源"),
    AMOUNT_4700(62, 4700, "4700分案资源"),
    AMOUNT_4800(63, 4800, "4800分案资源"),
    AMOUNT_4900(64, 4900, "4900分案资源"),
    AMOUNT_5000(65, 5000, "5000分案资源"),
    AMOUNT_5100(66, 5100, "5100分案资源"),
    AMOUNT_5200(67, 5200, "5200分案资源"),
    AMOUNT_5300(68, 5300, "5300分案资源"),
    AMOUNT_5400(69, 5400, "5400分案资源"),
    AMOUNT_5500(70, 5500, "5500分案资源"),
    AMOUNT_5600(71, 5600, "5600分案资源"),
    AMOUNT_5700(72, 5700, "5700分案资源"),
    AMOUNT_5800(73, 5800, "5800分案资源"),
    AMOUNT_5900(74, 5900, "5900分案资源"),
    AMOUNT_6000(75, 6000, "6000分案资源"),
    AMOUNT_6100(76, 6100, "6100分案资源"),
    AMOUNT_6200(77, 6200, "6200分案资源"),
    AMOUNT_6300(78, 6300, "6300分案资源"),
    AMOUNT_6400(79, 6400, "6400分案资源"),
    AMOUNT_6500(80, 6500, "6500分案资源"),
    AMOUNT_6600(81, 6600, "6600分案资源"),
    AMOUNT_6700(82, 6700, "6700分案资源"),
    AMOUNT_6800(83, 6800, "6800分案资源"),
    AMOUNT_6900(84, 6900, "6900分案资源"),
    AMOUNT_7000(85, 7000, "7000分案资源"),
    AMOUNT_7100(86, 7100, "7100分案资源"),
    AMOUNT_7200(87, 7200, "7200分案资源"),
    AMOUNT_7300(88, 7300, "7300分案资源"),
    AMOUNT_7400(89, 7400, "7400分案资源"),
    AMOUNT_7500(90, 7500, "7500分案资源"),
    AMOUNT_7600(91, 7600, "7600分案资源"),
    AMOUNT_7700(92, 7700, "7700分案资源"),
    AMOUNT_7800(93, 7800, "7800分案资源"),
    AMOUNT_7900(94, 7900, "7900分案资源"),
    AMOUNT_8000(95, 8000, "8000分案资源"),
    AMOUNT_8100(96, 8100, "8100分案资源"),
    AMOUNT_8200(97, 8200, "8200分案资源"),
    AMOUNT_8300(98, 8300, "8300分案资源"),
    AMOUNT_8400(99, 8400, "8400分案资源"),
    AMOUNT_8500(100, 8500, "8500分案资源"),
    AMOUNT_8600(101, 8600, "8600分案资源"),
    AMOUNT_8700(102, 8700, "8700分案资源"),
    AMOUNT_8800(103, 8800, "8800分案资源"),
    AMOUNT_8900(104, 8900, "8900分案资源"),
    AMOUNT_9000(105, 9000, "9000分案资源"),
    AMOUNT_9100(106, 9100, "9100分案资源"),
    AMOUNT_9200(107, 9200, "9200分案资源"),
    AMOUNT_9300(108, 9300, "9300分案资源"),
    AMOUNT_9400(109, 9400, "9400分案资源"),
    AMOUNT_9500(110, 9500, "9500分案资源"),
    AMOUNT_9600(111, 9600, "9600分案资源"),
    AMOUNT_9700(112, 9700, "9700分案资源"),
    AMOUNT_9800(113, 9800, "9800分案资源"),
    AMOUNT_9900(114, 9900, "9900分案资源"),
    AMOUNT_100000(115, 100000, "100000分案资源");

    AllotResourceEnum(long type, int amount, String description) {
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    private long type;
    private int amount;
    private String description;

    public long getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public static List<Long> allTypes() {
        List<Long> types = new ArrayList();
        AllotResourceEnum[] enums = AllotResourceEnum.values();
        for (AllotResourceEnum e : enums) {
            types.add(e.getType());
        }
        return types;
    }

    public static Map<Long, AllotResourceEnum> allResourceMap() {
        Map<Long, AllotResourceEnum> map = new HashMap();
        AllotResourceEnum[] enums = AllotResourceEnum.values();
        for (AllotResourceEnum e : enums) {
            map.put(e.getType(), e);
        }
        return map;
    }

    public static List<AllotResourceEnum> allResource() {
        List<AllotResourceEnum> list = new ArrayList();
        AllotResourceEnum[] enums = AllotResourceEnum.values();
        for (AllotResourceEnum e : enums) {
            list.add(e);
        }
        return list;
    }

    public static AllotResourceEnum getByType(int type) {
        AllotResourceEnum[] enums = AllotResourceEnum.values();
        for (AllotResourceEnum e : enums) {
            if (e.getType() == type) {
                return e;
            }
        }
        return null;
    }
}