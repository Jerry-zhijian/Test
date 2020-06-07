package c.b.s.common.util;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class IDCardUtil {

    /**
     * (粗略)检查身份证号是否合法
     * @param idCardNo
     * @return
     */
    public static boolean isIdCardNo(String idCardNo) {
        String pattern = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)";
        return Pattern.matches(pattern, idCardNo);
    }

    /**
     * 根据身份证提取性别
     * -1:未知
     * 0:女
     * 1:男
     * @param cardNo
     * @return
     */
    public static int extractGenderFromCardNo(String cardNo) {
        if (!StringUtils.hasText(cardNo) || cardNo.trim().length() < 15) {
            return -1;
        }
        int gender = 0;
        try {
            if (cardNo.trim().length() == 15) {
                gender = Integer.parseInt(cardNo.trim().substring(14));
            } else if (cardNo.trim().length() == 18) {
                gender = Integer.parseInt(cardNo.trim().substring(16, 17));
            }
        } catch (NumberFormatException e) {
            return -1;
        }
        return gender % 2;
    }

    /**
     * 根据身份证提取年龄
     * @param idCardNo
     * @return
     */
    public static int extractAgeFromIdCardNo(String idCardNo) {
        int age = 0;
        if (!StringUtils.hasText(idCardNo) || idCardNo.trim().length() < 15) {
            return age;
        }
        try {
            Date ageDate;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            if (idCardNo.trim().length() > 15) {
                ageDate = simpleDateFormat.parse(idCardNo.trim().substring(6, 14));
            } else {
                ageDate = simpleDateFormat.parse("19" + idCardNo.trim().substring(6, 12));
            }
            Date currentDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            age = currentDate.getYear() - ageDate.getYear();
            currentDate.setYear(currentDate.getYear() - age);
            if (currentDate.getTime() < ageDate.getTime()) {
                age--;
            }
        } catch (ParseException e) {
        }
        return age;
    }
}