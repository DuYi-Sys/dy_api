package com.duyi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExUtil {

    public static boolean match(String regEx, String value) {

        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);

        Matcher matcher = pattern.matcher(value);

        return matcher.matches();
    }
}
