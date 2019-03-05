package tanghongjie.myapplication.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import tanghongjie.myapplication.constast.Constant;


/**
 * 创建时间:2018/02/02 11:59
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:sharePreferences存储数据的工具类
 * 修改时间:
 * 修改描述:
 */
public class SpUtils {
    /**
     * 通过全局的context去读写数据
     */
    public final static SharedPreferences sp= UIUtils.getContext().getSharedPreferences(
            Constant.CONFIG_FILE_NAME, Context.MODE_PRIVATE);


    /**
     * @param key      要查找的key
     * @param defValue 默认的 值,如果不存在就返回默认的
     * @return
     */
    public static boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    /**
     * 设置boolean值
     * @param key
     * @param value
     */
    public static void setBoolean(String key, boolean value) {
        boolean isSuccess=sp.edit().putBoolean(key, value).commit();
    }

    public static void setString(String key, String value) {
        boolean isSuccess=sp.edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return sp.getString(key, null);
    }

    public static void setLong(String key, long value) {
        boolean isSuccess=sp.edit().putLong(key, value).commit();
    }

    public static long getLong(String key) {
        return sp.getLong(key, Long.MIN_VALUE);
    }

    public static void setInt(String key, int value) {
        boolean isSuccess=sp.edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return sp.getInt(key, Integer.MIN_VALUE);
    }

    /**
     * 移除指定的key
     * @param key
     */
    public static void removeKey(String key){
        boolean isSuccess=sp.edit().remove(key).commit();
    }
}
