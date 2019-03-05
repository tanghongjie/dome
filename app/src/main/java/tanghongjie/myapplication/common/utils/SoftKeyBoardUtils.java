package tanghongjie.myapplication.common.utils;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 创建时间:2018/03/07 16:13
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:软键盘相关的工具类
 * 修改时间:
 * 修改描述:
 */
public class SoftKeyBoardUtils {

    /**
     * 显示软键盘
     * @param context
     * @param editText
     */
    public static  void showKeyboard(final Context context, final EditText editText){
        try{
            Timer timer = new Timer();
            timer.schedule(new TimerTask()   {
                @Override
                public void run() {
                    InputMethodManager inputManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(editText, 0);
                }
            }, 350);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 隐藏软键盘
     * @param activity
     */
    public static void hideSoftKeyboard(Activity activity) {
        try{
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                if (activity.getCurrentFocus() != null){
                    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
