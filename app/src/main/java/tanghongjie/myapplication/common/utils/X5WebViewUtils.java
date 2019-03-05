package tanghongjie.myapplication.common.utils;

import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;


/**
 * 创建时间:2018/05/25 10:55
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述: x5webview相关的工具类
 * 修改时间:
 * 修改描述:
 */
public class X5WebViewUtils {

    /**
     * 这个两个在 API level 21 被抛弃
     * CookieManager.getInstance().removeSessionCookie();
     * CookieManager.getInstance().removeAllCookie();
     *
     * 推荐使用这两个， level 21 新加的
     * CookieManager.getInstance().removeSessionCookies();
     * CookieManager.getInstance().removeAllCookies();
     *
     * 清除x5webview的cookie(退出登录的时候被清除)
     **/
    public static void removeX5WebviewCookies(boolean isOldWay) {
        boolean flag=true;
        try{
            if (flag){
             CookieSyncManager.createInstance(UIUtils.getContext());
               CookieSyncManager.getInstance().startSync();
             CookieManager.getInstance().removeAllCookie();
              CookieSyncManager.getInstance().sync();
            }else {
                //新的方式清除cookie
               CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.removeAllCookie();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    cookieManager.flush();
                } else {
                  CookieSyncManager.createInstance(UIUtils.getContext());
                    CookieSyncManager.getInstance().sync();
                }
            }
            LogUtils.e("removeX5WebviewCookies--清除x5的cookie");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 这个两个在 API level 21 被抛弃
     * CookieManager.getInstance().removeSessionCookie();
     * CookieManager.getInstance().removeAllCookie();
     *
     * 推荐使用这两个， level 21 新加的
     * CookieManager.getInstance().removeSessionCookies();
     * CookieManager.getInstance().removeAllCookies();
     *
     * 清除x5webview的cookie(退出登录的时候被清除)
     **/
    public static void removeSystemWebviewCookies(boolean isOldWay) {
        boolean flag=true;
        try{
            if (flag){
                android.webkit.CookieSyncManager.createInstance(UIUtils.getContext());
                android.webkit.CookieSyncManager.getInstance().startSync();
                android.webkit.CookieManager.getInstance().removeAllCookie();
                android.webkit.CookieSyncManager.getInstance().sync();
            }else {
                //新的方式清除cookie
                android.webkit.CookieManager cookieManager = android.webkit.CookieManager.getInstance();
                cookieManager.removeAllCookie();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    cookieManager.flush();
                } else {
                    android.webkit.CookieSyncManager.createInstance(UIUtils.getContext());
                    android.webkit.CookieSyncManager.getInstance().sync();
                }
            }
            LogUtils.e("removeSystemWebviewCookies--清除cookie");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
