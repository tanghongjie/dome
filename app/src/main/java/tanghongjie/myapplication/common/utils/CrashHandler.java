package tanghongjie.myapplication.common.utils;


import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

import java.lang.Thread.UncaughtExceptionHandler;

import tanghongjie.myapplication.R;
import tanghongjie.myapplication.beas.BaseApplication;

/**
 * 创建时间:2018/02/02 11:59
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:仅用于闪退后重启应用
 * 修改时间:
 * 修改描述:
 */
public class CrashHandler implements UncaughtExceptionHandler {
    private static CrashHandler INSTANCE = new CrashHandler();
    private UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;
    private Class clazz;
    //调试模式下不重启应用
    private boolean isRestartApp;

    private CrashHandler() {}

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    public void init(Context context, Class clazz, boolean isDebug) {
        this.mContext=context;
        this.isRestartApp=!isDebug;
        this.clazz=clazz;
        //保存一份系统默认的CrashHandler
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //使用我们自定义的异常处理器替换程序默认的
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (isRestartApp&&clazz!=null){
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(BaseApplication.getInstance(), R.string.app_exception_restart, Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }.start();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            BaseApplication.getInstance().crashHandler();

            //修改为启动页面Activity
            Intent intent = new Intent(mContext, clazz);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }else{
            if (ex!=null&&mDefaultHandler!=null){
                mDefaultHandler.uncaughtException(thread, ex);
            }
        }
    }

}