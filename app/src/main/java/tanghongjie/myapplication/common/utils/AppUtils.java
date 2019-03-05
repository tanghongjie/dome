package tanghongjie.myapplication.common.utils;

import android.app.Activity;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import tanghongjie.myapplication.beas.BaseApplication;
import tanghongjie.myapplication.constast.HttpUrls;
import tanghongjie.myapplication.httpManager.constant.HttpConstant;


/**
 * 创建时间: 2017/08/20 14:47
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述: app全局相关的工具类
 * 修改时间:
 * 修改描述:
 */
public class AppUtils {
    /**
     * activity显示的时候就添加,不可见的时候就移除,用于判断当前应用是否在前台显示
     * 只保存activity的tag
     */
    private static List<String> activityTagsList = new ArrayList<>();
    /***
     * 保存activity实例
     */
    private static List<Activity> activityList = new ArrayList<>();


    public static void addActivity(Activity activity) {
        if (activity != null){
            activityList.add(activity);
        }
    }

    public static void removeActivity(Activity activity) {
        if (activity != null){
            activityList.remove(activity);
        }
    }

    public static List<Activity> getActivityList() {
        if (activityList == null) {
            activityList = new ArrayList<>();
        }
        return activityList;
    }

    /***
     * 销毁所有activity实例
     */
    public static void destroyAllActivity() {
        if (activityList != null&&activityList.size()>0) {
            Activity activity;
            for (int i = 0; i < activityList.size(); i++) {
                activity = activityList.get(i);
                if (activity != null && !activity.isFinishing()) {
                    activity.finish();
                }
                activityList.remove(i);
                i--;
            }
        }
    }

    public static void addActivityTag(String tag){
        if (!TextUtils.isEmpty(tag)&&!activityTagsList.contains(tag)){
            activityTagsList.add(tag);
        }
    }

    public static void removeActivityTag(String tag){
        if (!TextUtils.isEmpty(tag)){
            activityTagsList.remove(tag);
        }
    }

    /**
     * 清除标签
     */
    public static void clearAllActivityTags() {
        activityTagsList.clear();
    }

    /**
     * 当前应用是否在前台
     */
    public static boolean isAppResume() {
        return activityTagsList.size() > 0;
    }

    /**
     * 是否是测试环境
     */
    public static boolean isTestServer() {
        return HttpUrls.HOST.equals(HttpUrls.HOST_HTTP_TEST);
    }

    /***
     * 获取http请求的签名字符串
     * @return
     */
    public static String getHttpAuthorizationValue(){
        return HttpConstant.HTTP_AUTHORIZATION+ BaseApplication.getInstance().getToken();
    }

}
