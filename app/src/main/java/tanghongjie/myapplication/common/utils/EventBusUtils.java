package tanghongjie.myapplication.common.utils;

import org.greenrobot.eventbus.EventBus;

/**
 * 创建时间: 2017/08/09 14:39
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:eventbus二次封装
 * 修改时间:
 * 修改描述:
 */
public class EventBusUtils {

    public static void register(Object object) {
        if (object != null) {
            if (!EventBus.getDefault().isRegistered(object)) {
                EventBus.getDefault().register(object);
            }
        }
    }

    public static void unregister(Object object) {
        if (object != null) {
            EventBus.getDefault().unregister(object);
        }
    }

    public static void post(Object object) {
        if (object != null) {
            EventBus.getDefault().post(object);
        }
    }

}
