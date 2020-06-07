package c.b.s.common.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by guiqingqing on 2018/8/29.
 */
public class CommonUtil {
    private static final Pattern NUM_PATTERN = Pattern.compile("-?[0-9]+");
    // ¥##,###.##
    private static final Pattern RMB_PATTERN = Pattern.compile("^¥\\d{1,3}(,\\d{3})*(\\.\\d{1,9})?$");
    private static final Pattern BIGDECIMAL_PATTERN = Pattern.compile("^\\d+(\\.\\d{1,9})?$");
    private static final Pattern MOBILE_PHONE_PATTERN = Pattern.compile("^\\d{11}$");
    private static final Pattern FIXED_PHONE_PATTERN = Pattern.compile("^(0\\d{2,3}\\-?)?\\d{7,8}$");

    public static boolean isNum(String target) {
        if (Objects.isNull(target)) {
            return false;
        }
        return NUM_PATTERN.matcher(target).matches();
    }

    public static Integer getInteger(String target) {
        return Integer.parseInt(target);
    }

    public static BigDecimal getBigDecimal(String target) {
        return new BigDecimal(target);
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return StringUtils.isBlank((String) obj);
        } else if (obj instanceof Collection) {
            return ((Collection) obj).size() == 0 ? true : false;
        } else if (obj instanceof Map) {
            return ((Map) obj).size() == 0 ? true : false;
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0 ? true : false;
        }
        return false;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static String nullToEmpty(String value){
        if(isEmpty(value)){
            return "";
        }
        return value;
    }

    /**
     * 脱敏处理
     * @param source 原字符串
     * @param start 脱敏保留前位数
     * @param end 脱敏保留后位数
     * @return
     */
    public static String desensitization(String source, int start, int end) {
        return source.replaceAll(String.format("(?<=.{%d}).(?=.{%d})", start, end), "*");
    }

    //号码脱码
    public static String phoneFilter(String content) {
        if (StringUtils.isNotEmpty(content)) {
            return desensitization(content, 3, 4); //脱敏 前3后4
        }
        return "";
    }

    //身份证号脱敏
    public static String cardNoFilter(String content) {
        if (StringUtils.isNotEmpty(content)) {
            return desensitization(content, 6, 4); //脱敏 前6后4
        }
        return "";
    }

    //银行卡号脱敏
    public static String bankNoFilter(String content) {
        if (StringUtils.isNotEmpty(content)) {
            return desensitization(content, 6, 4); //脱敏 前6后4
        }
        return "";
    }

    //催记内容
    public static String bodyFilter(String content) {
        String replaceContent = "*";
        if (StringUtils.isNotEmpty(content)) {
            String fixedPhoneRegex = "(0[0-9]{2,3}\\-?)?(\\d{2})\\d{3,4}(\\d{2})";

            return content.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1" + replaceContent + "$2")
                    .replaceAll(fixedPhoneRegex, "$1$2" + replaceContent + "$3");
        }
        return "";
    }

    public static boolean isRMB(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return RMB_PATTERN.matcher(source).matches();
    }

    public static boolean isBigDecimal(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return BIGDECIMAL_PATTERN.matcher(source).matches();
    }

    // 验证是否是手机号或固话
    public static boolean isPhoneNumber(String phoneNumber) {
        return isMobilePhone(phoneNumber) || isFixedPhone(phoneNumber);
    }

    // 验证是否是手机号
    public static boolean isMobilePhone(String phoneNumber) {
        return MOBILE_PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    // 验证是否是固话
    public static boolean isFixedPhone(String phoneNumber) {
        return FIXED_PHONE_PATTERN.matcher(phoneNumber).matches();
    }
    
    /**
     * oc_1-10-4755537-1061-13504445103
     * @param taskId
     * @return assetId
     */
    public static String subAssetIdFromTaskId(String taskId) {
		String[] str = taskId.split("-");
		return str[1];
    }
    
}