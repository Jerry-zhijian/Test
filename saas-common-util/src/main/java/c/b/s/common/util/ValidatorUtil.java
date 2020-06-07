package c.b.s.common.util;

import java.util.regex.Pattern;

/**
 * Created by guiqingqing on 2018/8/15.
 */
public class ValidatorUtil {
    private static final String PASSWORD_PATTERN = "^(?![a-zA-Z]+$)(?![0-9]+$)(?![!@#\\$%\\^&\\*\\()_\\+\\-,\\.]+$)[a-zA-Z0-9!@#\\$%\\^&\\*\\()_\\+\\-,\\.]{6,12}$";

    public static boolean validPassword(String password) {
        return Pattern.compile(PASSWORD_PATTERN).matcher(password).matches();
    }

    public static void main(String[] args) {
        System.out.println(validPassword("123456789")); // false
        System.out.println(validPassword("abcdefg")); // false
        System.out.println(validPassword("!@#$%^&")); // false
        System.out.println(validPassword("12u34f")); // true
        System.out.println(validPassword("zhiniu1234")); // true
        System.out.println(validPassword("sd21@$")); // true
        System.out.println(validPassword("1q@")); // false
        System.out.println(validPassword("1!@#$%^")); // true
        System.out.println(validPassword("1&*()_+")); // true
        System.out.println(validPassword("1&*()_+")); // true
        System.out.println(validPassword("zhiniu1234-")); // true
    }
}