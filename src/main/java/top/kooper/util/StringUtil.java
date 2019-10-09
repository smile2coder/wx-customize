package top.kooper.util;

import java.util.Random;
import java.util.RandomAccess;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ZxT
 * @date 2019-09-25
 * @desc
 */
public class StringUtil {

    public static final String EMPTY = "";

    /**
     * 根据正则表达式提取字符串
     * @param str
     * @param regex
     * @return
     */
    public static String extract(String str, String regex) {
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(str);
        while (matcher.find()) {
            return matcher.group(1);
        }
        return EMPTY;
    }

    public static String random4() {
        return String.valueOf((int)((Math.random()*9+1)*1000));
    }
}
