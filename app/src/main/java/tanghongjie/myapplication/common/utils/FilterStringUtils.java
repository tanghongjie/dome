package tanghongjie.myapplication.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 创建时间: 2017/08/20 14:47
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:过滤字符串
 * 修改时间:
 * 修改描述:
 */
public class FilterStringUtils {
    //中文和英文
    public static final String STR_NUM= "[^a-zA-Z\u4E00-\u9FA5]";
    //中文和英文+数字
    public static final String CHINESE_STR_NUM= "[^a-zA-Z0-9\u4E00-\u9FA5]";

    public static String filterString(String oldString, String regEx){
        Pattern p   =   Pattern.compile(regEx);
        Matcher m   =   p.matcher(oldString);
        return   m.replaceAll("").trim();
    }
}
