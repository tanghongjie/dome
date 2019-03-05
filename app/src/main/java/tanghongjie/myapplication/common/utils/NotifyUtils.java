package tanghongjie.myapplication.common.utils;

import android.app.NotificationManager;

import tanghongjie.myapplication.beas.BaseApplication;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 创建时间:2018/02/02 11:58
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:通知相关的工具类
 * 修改时间:
 * 修改描述:
 */
public class NotifyUtils {

    /**
     * 清除所有的通知
     */
    public static void clearAllNotify() {
        NotificationManager notifyManager = (NotificationManager) BaseApplication.getInstance()
                .getSystemService(NOTIFICATION_SERVICE);
        if (notifyManager!=null){
            notifyManager.cancelAll();
        }
    }

    /**
     * 清除指定的消息
     * @param notifyId
     */
    public static void clearNotify(int notifyId) {
        NotificationManager notifyManager = (NotificationManager) BaseApplication.getInstance()
                .getSystemService(NOTIFICATION_SERVICE);
        if (notifyManager!=null){
            notifyManager.cancel(notifyId);
        }
    }

}
