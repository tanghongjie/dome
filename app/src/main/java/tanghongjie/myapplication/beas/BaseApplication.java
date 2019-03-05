package tanghongjie.myapplication.beas;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import tanghongjie.myapplication.common.utils.AppUtils;
import tanghongjie.myapplication.common.utils.GlobalUtils;
import tanghongjie.myapplication.common.utils.NotifyUtils;
import tanghongjie.myapplication.common.utils.Ts;
import tanghongjie.myapplication.common.utils.UserInfoSaveUtils;
import tanghongjie.myapplication.httpManager.OkHttpClientManager;


/**
 * 创建时间: 2017/08/02 21:45
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:全局application
 * 修改时间:
 * 修改描述:
 */
public class BaseApplication extends Application {
    private final String TAG = "--BaseApplication--";
    private static BaseApplication instance;
    /***
     * 检测内存泄漏对象
     */
 //   private RefWatcher mRefWatcher;
    /**
     * 登录成功后保存的用户部分信息
     */

//    private UserInfo userInfo;
    /**
     * 登录成功后返回的session,每次请求都需要添加的字段
     */
    private String token;
    /***
     * 用户id
     */
    private String uid;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //初始化所有的sdk
       // InitSdkUtils.init();
    GlobalUtils.init(instance);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //应用方法数64k,主动加载
      //  MultiDex.install(this);
    }

    /**
     * 设置内存监控对象
     *
     * @param mRefWatcher
     */
  /*  public void setRefWatcher(RefWatcher mRefWatcher) {
        this.mRefWatcher = mRefWatcher;
    }*/

    /**
     * 获取内存监控对象
     *
     * @return
     */
 /*   public static RefWatcher getRefWatcher(Context context) {
        BaseApplication application = (BaseApplication) context.getApplicationContext();
        if (application.mRefWatcher == null) {
            application.mRefWatcher = LeakCanary.install(BaseApplication.getInstance());
        }
        return application.mRefWatcher;
    }*/

    public static BaseApplication getInstance() {
        return instance;
    }

    /**
     * 获取登录成功后返回的SessionId
     *
     * @return
     */
    public String getToken() {
        if (TextUtils.isEmpty(token)) {
            token= UserInfoSaveUtils.getToken();
        }
        return token;
    }

    /**
     * 保存登录成功的信息(仅仅在登录成功后保存,主要是保存推送相关的信息)
     * 保存前已经做非空校验了
     *
     * @param userInfo
     * @param token
     */
/*    public void setUserInfo(@NonNull UserInfo userInfo, String token) {
        this.userInfo = userInfo;
        this.token=token;
        this.uid=userInfo.getId();
    }*/

    /**
     * 更新用户信息
     *
     * @param userInfo
     */
    /*public void setUserInfo(@NonNull UserInfo userInfo) {
        this.userInfo = userInfo;
    }*/

    /**
     * 获取保存的用户信息
     *
     * @return
     */
 /*   public UserInfo getUserInfo() {
        if (userInfo == null || !userInfo.isUserInfoFull()) {
            userInfo = UserInfoSaveUtils.getUserInfo();
            if (userInfo == null || !userInfo.isUserInfoFull()) {
                //登录信息已经失效了
                userInfo = new UserInfo();
            }
        }
        return userInfo;
    }*/

    public String getUid() {
        return this.uid;
    }

    /***
     * 清除本地缓存的用户信息
     * 1.手动退出登录
     * 2.登录失效后调用
     */
    public void logout() {
        //清除本地保存的密码
        UserInfoSaveUtils.clearPassword();
        //清除推送信息
      //  InitSdkUtils.removePushId();
        //清除本地保存的登录信息
        UserInfoSaveUtils.clearUserInfo();

        //清除内存中的临时数据
        clearInfo();

        //销毁所有的activity
        AppUtils.destroyAllActivity();
    }

    /**
     * 清除内存中相关信息
     */
    public void clearInfo() {
        try {
            //关闭应用的时候,清除通知栏
            NotifyUtils.clearAllNotify();

            OkHttpClientManager.getInstance().cancelAll();

          //  UploadFileUtils.cancelAll();

            //销毁线程池
           // ThreadPoolManager.destroyPool();

          //  userInfo = null;
            token = null;

            //清除x5webview的cookie(主要是商城的cookie)
         //   X5WebViewUtils.removeX5WebviewCookies(false);

            //防止x5没有加载,使用的原生的webview
          //  X5WebViewUtils.removeSystemWebviewCookies(false);

            //清除toast
            Ts.clearTs();

          //  LogUtils.e(TAG + "--clearInfo-执行到最后,无异常");
        } catch (Exception e) {
            e.printStackTrace();
         //   LogUtils.e(TAG + "--clearInfo-异常");
        }
    }

    /***
     * 应用崩溃后被调用
     */
    public void crashHandler() {
      //  LogUtils.d(TAG + "--crashHandler");
        NotifyUtils.clearAllNotify();
        OkHttpClientManager.getInstance().cancelAll();
        //销毁线程池
       // ThreadPoolManager.destroyPool();

        AppUtils.clearAllActivityTags();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
