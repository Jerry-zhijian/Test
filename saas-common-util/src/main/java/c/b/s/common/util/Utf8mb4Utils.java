package c.b.s.common.util;

import java.io.UnsupportedEncodingException;

public class Utf8mb4Utils {

    public static String filterUtf8mb4(String str) {
        final int LAST_BMP = 0xFFFF;
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            int codePoint = str.codePointAt(i);
            if (codePoint < LAST_BMP) {
                sb.appendCodePoint(codePoint);
            } else {
                i++;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "a中\uD83D\uDD11a中";
        System.out.println(filterUtf8mb4(s));
    }

}
