package c.b.s.common.util.enums;

/**
 * Created by guiqingqing on 2018/9/4.
 */
public enum ModuleEnum {
    ALL(0, 1, 9999, "全部"),
    M1(1, 1, 30, "M1模块"),
    M2(2, 31, 60, "M2模块"),
    M3(3, 61, 90, "M3模块"),
    M4(4, 91, 120, "M4模块"),
    M5(5, 121, 150, "M5模块"),
    M6(6, 151, 180, "M6模块"),
    M7(7, 181, 210, "M7模块"),
    M8(8, 211, 240, "M8模块"),
    M9(9, 241, 270, "M9模块"),
    M10(10, 271, 300, "M10模块"),
    M11(11, 301, 330, "M11模块"),
    M12(12, 331, 360, "M12模块"),
    M12_PLUS(13, 361, 9999, "M12+模块"),
    NOT_OVERDUE(14, -9999, 0, "未逾期");

    ModuleEnum(long id, int minOverdueDays, int maxOverdueDays, String description) {
        this.id = id;
        this.minOverdueDays = minOverdueDays;
        this.maxOverdueDays = maxOverdueDays;
        this.description = description;
    }

    private long id;
    private int minOverdueDays;
    private int maxOverdueDays;
    private String description;

    public long getId() {
        return id;
    }

    public int getMinOverdueDays() {
        return minOverdueDays;
    }

    public int getMaxOverdueDays() {
        return maxOverdueDays;
    }

    public String getDescription() {
        return description;
    }

    public static ModuleEnum getByOverdueDays(int overdueDays) {
        ModuleEnum[] enums = ModuleEnum.values();
        for (ModuleEnum e : enums) {
            if (e != ALL && e.getMinOverdueDays() <= overdueDays && e.getMaxOverdueDays() >= overdueDays) {
                return e;
            }
        }
        return null;
    }

    public static ModuleEnum getById(int id) {
        ModuleEnum[] enums = ModuleEnum.values();
        for (ModuleEnum e : enums) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }
}