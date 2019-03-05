package tanghongjie.myapplication.common.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import tanghongjie.myapplication.constast.Constant;

/**
 * 创建时间:2018/02/02 11:59
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:获取系统参数工具
 * 修改时间:
 * 修改描述:
 */
public class SystemUtils {

    /**
     * 获取当前手机系统语言。
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 判断当前应用是否是debug状态
     */
    public static boolean isApkInDebug() {
    /*    try {
            return BuildConfig.IS_DEBUG;
        } catch (Exception e) {*/
            return false;
     //   }
    }

    /**
     * 获取手机IMEI
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI() {
        try {
            TelephonyManager tm = (TelephonyManager) UIUtils.getContext().getSystemService(Context
                    .TELEPHONY_SERVICE);
            if (TextUtils.isEmpty(tm.getDeviceId())){
                return "";
            }else{
                return tm.getDeviceId();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取手机UUID
     * @return 手机IMEI
     */
    @SuppressLint("MissingPermission")
    private static String getUUID() {
        try{
            TelephonyManager tm = (TelephonyManager) UIUtils.getContext()
                    .getSystemService(Context.TELEPHONY_SERVICE);

            String tmDevice, tmSerial, androidId;

            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
            androidId = ""
                    + android.provider.Settings.Secure.getString(
                    UIUtils.getContext().getContentResolver(),
                    android.provider.Settings.Secure.ANDROID_ID);

            UUID deviceUuid = new UUID(androidId.hashCode(),
                    ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
            return deviceUuid.toString();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 获取APP-VersionCode
     */
    public static int getAppVersionCode() {
        String pName = UIUtils.getContext().getPackageName();
        int versionCode = 0;

        try {
            PackageInfo pInfo = UIUtils.getContext().getPackageManager().getPackageInfo(
                    pName, PackageManager.GET_CONFIGURATIONS);
            versionCode = pInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取APP-VersionName
     */
    public static String getAppVersionName() {
        String pName = UIUtils.getContext().getPackageName();
        String versionName = "";

        try {
            PackageInfo pInfo = UIUtils.getContext().getPackageManager().getPackageInfo(
                    pName, PackageManager.GET_CONFIGURATIONS);
            versionName = pInfo.versionName;
        } catch (Exception e) {
        }
        return versionName;
    }

    /**
     * 判断当前是否有网络
     */
    public static boolean isNetAvailable() {
        boolean flag = false;
        ConnectivityManager cm = (ConnectivityManager) UIUtils.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                flag = ni.isAvailable();
            }
        }
        return flag;
    }

    /**
     * 判断当前网络是否是wifi
     *
     * @return
     */
    public static boolean isWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) UIUtils.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 获取当前网络类型
     * wifi/手机网络/无网络
     *
     * @return
     */
    public static String getCurrentNetType() {
        if (!isNetAvailable()) {
            return Constant.NO_NET;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) UIUtils.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null) {
            if (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return Constant.NET_TYPE_WIFI;
            }
            if (activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return Constant.NET_TYPE_MOBILE;
            }
        }
        return null;
    }

    // 获取手机cpu的个数
    public static int getCpuCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent;
        //判断手机系统的版本  即API大于10 就是3.0或以上版本
        if (Build.VERSION.SDK_INT > 10) {
            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        } else {
            intent = new Intent();
            ComponentName component = new ComponentName("com.android.settings", "com.android" +
                    ".settings.WirelessSettings");
            intent.setComponent(component);
            intent.setAction("android.intent.action.VIEW");
        }
        activity.startActivity(intent);
    }

    /**
     * 获取手机型号
     */
    public static String getPhoneModel() {
        return Build.MODEL;
    }

    /**
     * 获取当前应用程序的包名
     * @return 返回包名
     */
    public static String getAppProcessName() {
        try{
            //当前应用pid
            int pid = android.os.Process.myPid();
            //任务管理类
            ActivityManager manager = (ActivityManager) UIUtils.getContext().getSystemService(Context
                    .ACTIVITY_SERVICE);
            //遍历所有应用
            List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo info : infos) {
                if (info.pid == pid){
                    //得到当前应用
                    return info.processName;//返回包名
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName() {
        try {
            Context context = UIUtils.getContext();
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取手机厂商
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取当前手机系统版本号
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * 20180403必须开启了GPS才算
     * @param context
     * @return true 表示开启
     */
    public static boolean isGpsOpen(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager==null){
            return false;
        }else{
            // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
            boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
            boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            return gps;
        }
    }

    /***
     * 跳转到系统拨号界面,传递电话号码
     * @param phoneNum
     */
    public static void callPhone(Context context, String phoneNum){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 获取当前进程名称
     * @param context
     * @return
     */
    public static String getProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = null;
        if (am != null) {
            runningApps = am.getRunningAppProcesses();
        }
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo proInfo : runningApps) {
            if (proInfo.pid == android.os.Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName;
                }
            }
        }
        return null;
    }

}
