package tanghongjie.myapplication.common.utils;

import android.support.annotation.NonNull;
import android.util.Base64;

/**
 * 创建时间: 2018/08/13 11:09
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:Base64加密解密工具类
 * 修改时间:
 * 修改描述:
 */
public class Base64Utils {

    private Base64Utils() {}

    /***
     * base64加密
     * @param text
     * @return
     */
    public static String encode(@NonNull String text){
        //NO_WRAP 这个参数意思是略去所有的换行符
        String string= Base64.encodeToString(text.getBytes(), Base64.NO_WRAP);
        //用字符串对象的replaceAll方法替换掉\r和\n，代码如下：
        return string.replaceAll("[\\s*\t\n\r]", "");
    }

}
