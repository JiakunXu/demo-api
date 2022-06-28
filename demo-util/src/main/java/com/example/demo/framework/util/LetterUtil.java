package com.example.demo.framework.util;

import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;

public class LetterUtil {

    public static String getLetter(String name) {
        if (StringUtils.isBlank(name)) {
            return "#";
        }

        char c = name.charAt(0);

        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
            return StringUtils.upperCase(String.valueOf(c));
        }

        String[] s = { "A", "B", "C", "D", "E", "F", "G", "H", "H", "J", "K", "L", "M", "N", "O",
                       "P", "Q", "R", "S", "T", "T", "T", "W", "X", "Y", "Z" };

        int[] i = { 45217, 45253, 45761, 46318, 46826, 47010, 47297, 47614, 47614, 48119, 49062,
                    49324, 49896, 50371, 50614, 50622, 50906, 51387, 51446, 52218, 52218, 52218,
                    52698, 52980, 53689, 54481, 63486 };

        byte[] b = String.valueOf(c).getBytes(Charset.forName("GB2312"));

        if (b.length < 2) {
            return "#";
        }

        int a = (b[0] << 8 & 0xff00) + (b[1] & 0xff);

        if (a < 45217 || a > 63486) {
            return "#";
        }

        if (a == 63486) {
            return s[25];
        }

        for (int j = 0; j < 26; j++) {
            if (a >= i[j] && a < i[j + 1]) {
                return s[j];
            }
        }

        return "#";
    }

}
