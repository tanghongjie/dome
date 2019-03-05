package tanghongjie.myapplication.common.utils;

import android.content.ClipboardManager;
import android.content.Context;

/**
 * 创建时间:2018/05/15 17:31
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:剪切板使用的工具类
 * 修改时间:
 * 修改描述:
 */
public class ClipboardUtils {

    /***
     * 将文本复制到剪切板
     * @param context
     * @param text
     * @return
     */
    public static boolean putTextIntoClip(Context context, String text) {
        try {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context
                    .CLIPBOARD_SERVICE);
            clipboardManager.setText(text);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
