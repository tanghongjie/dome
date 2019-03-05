package tanghongjie.myapplication.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.WindowManager;



/**
 * 创建时间: 2017/08/20 14:47
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述: 全局公共类,BaseApp中初始化
 * 修改时间:
 * 修改描述:
 */
public class GlobalUtils {
    /**
     * 全局上下文
     */
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    /**
     * 线程的通信工具
     */
    private static Handler handler;
    /**
     * 主线程
     */
    private static Thread mainThread;
    /**
     * 主线程的id
     */
    private static int mainThreadId;
    /**
     * 屏幕的宽度
     */
    private static int screenWidth;
    /**
     * 屏幕的高度
     */
    private static int screenHeight;

    public static void init(@NonNull Context context) {
        mContext = context;
        handler = new Handler();
        mainThreadId = android.os.Process.myTid();
        mainThread = handler.getLooper().getThread();

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display= wm != null ? wm.getDefaultDisplay() : null;
        if (display!=null){
            screenWidth = display.getWidth();
            screenHeight = display.getHeight();
          //  LogUtils.e("获取屏幕宽高："+screenWidth+"*"+screenHeight);
        }
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static Context getContext() {
        return mContext;
    }

    public static Thread getMainThread() {
        return mainThread;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }

    public static Handler getHandler() {
        return handler;
    }

}
