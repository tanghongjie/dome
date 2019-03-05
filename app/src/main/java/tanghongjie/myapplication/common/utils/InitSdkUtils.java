package tanghongjie.myapplication.common.utils;


/**
 * 创建时间: 2017/06/07 21:04
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:BaseApp中初始化所有第三方sdk
 * 修改时间:
 * 修改描述:
 */
public class InitSdkUtils {
  /*  private static String TAG = "--InitSdkUtils--";
    *//**
     * 主进程
     *//*
    private static final String PROCESS_NAME_MAIN = "com.zswl.xxlvip";
    *//**
     * 百度地图进程
     *//*
    private static final String PROCESS_NAME_MAP = "com.zswl.xxlvip:remote";
    *//**
     * 阿里推送进程
     *//*
    private static final String PROCESS_NAME_PUSH = "com.zswl.xxlvip:channel";

    *//**
     * 协力出行appid
     *//*
    private static String BUGLY_APPID = "cb3b9c106c";
    *//**
     * 友盟统计appkey
     *//*
    private static String UMENG_APP_KEY = "5b8c923a8f4a9d52e1000020";
    *//**
     * 阿里推送
     *//*
    private static CloudPushService pushService;
    *//**
     * 微信支付使用的对象
     *//*
    private static IWXAPI sIWXAPI;
    *//**
     * 是否适配多进程
     *//*
    private static boolean isMultiProcess = true;

    *//**
     * 获取bugly的appid
     *
     * @return
     *//*
    private static String getBuglyAppId() {
        return BUGLY_APPID;
    }

    *//**
     * 初始化sdk
     * ps：当前应用有多个进程，会导致BaseApplication.onCreate（）方法执行多次
     *//*
    public static void init() {
        BaseApplication application = BaseApplication.getInstance();
        String processName = SystemUtils.getProcessName(application);

        if (isMultiProcess) {
            if (processName != null) {
                if (PROCESS_NAME_MAIN.equals(processName)) {
                    initMainProcessSdk(application);
                } else if (PROCESS_NAME_MAP.equals(processName)) {
                    initMapProcessSdk(application);
                } else if (PROCESS_NAME_PUSH.equals(processName)) {
                    initPushProcessSdk(application);
                } else {
                    initMainProcessSdk(application);
                }
            } else {
                initMainProcessSdk(application);
            }
        } else {
            initMainProcessSdk(application);
        }

        Log.e(TAG, "init()-进程名称:" + processName);

    }

    *//**
     * 初始化主进程sdk
     *
     * @param application
     *//*
    private static void initMainProcessSdk(BaseApplication application) {
        Ts.clearTs();

        //初始化日志打印
        LogUtils.init(SystemUtils.isApkInDebug(), application.getString(R.string.app_name));

        //初始化全局对象
        GlobalUtils.init(application);

        //创建应用使用的文件夹
        FileUtils.initDir();

        //初始化bugly
        initBugly();

        //okhttp请求日志拦截打印,数据库和sp数据查看
        Stetho.initializeWithDefaults(application);

        //初始化xUtils
        x.Ext.init(application);
        x.Ext.setDebug(Constant.IS_DEBUG);

        //初始化x5浏览器
        initX5Web();

        //初始化shareSdk
        MobSDK.init(application);

        //初始化支付sdk
        initPaySdk();

        //初始化友盟
        initUmeng(application);

        //初始化百度地图
        initBaiduMap();

        //初始化阿里推送
        initCloudChannel();

        //初始化检测内存泄漏sdk
        application.setRefWatcher(LeakCanary.install(application));
    }

    *//**
     * 初始化友盟统计
     *//*
    private static void initUmeng(Application application) {
        *//**
         * 注意: 即使您已经在AndroidManifest.xml中配置过appkey和channel值，也需要在App代码中调用初始化接口
         （如需要使用AndroidManifest.xml中配置好的appkey和channel值，UMConfigure.init调用中appkey和channel参数请置为null）。
         *//*
        UMConfigure.init(application, UMENG_APP_KEY, "UMENG_APPKEY", UMConfigure.DEVICE_TYPE_PHONE,"");
        //普通统计场景
        MobclickAgent.setScenarioType(application, EScenarioType.E_UM_NORMAL);
        //关闭统计错误日志
        MobclickAgent.setCatchUncaughtExceptions(false);
    }

    *//**
     * 初始化百度地图进程sdk
     *
     * @param application
     *//*
    private static void initMapProcessSdk(BaseApplication application) {
        //初始化百度地图
        initBaiduMap();
    }

    *//**
     * 初始化阿里推送进程sdk
     *
     * @param application
     *//*
    private static void initPushProcessSdk(BaseApplication application) {
        //初始化阿里推送
        initCloudChannel();
    }

    *//***
     * 初始化支付sdk
     *//*
    private static void initPaySdk() {
        // 微信sdk初始化
        sIWXAPI = WXAPIFactory.createWXAPI(BaseApplication.getInstance(), WalletConstant
                .WECHAT_APP_ID, true);
        sIWXAPI.registerApp(WalletConstant.WECHAT_APP_ID);
    }

    *//**
     * 获取IWXAPI,用于微信第三方功能
     *//*
    public static IWXAPI getIWXAPI() {
        return sIWXAPI;
    }

    *//**
     * 初始化阿里云推送通道
     *//*
    private static void initCloudChannel() {
        PushServiceFactory.init(BaseApplication.getInstance());
        pushService = PushServiceFactory.getCloudPushService();

        pushService.register(BaseApplication.getInstance(), new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                LogUtils.i(TAG + "阿里init cloud_channel success");
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                LogUtils.e(TAG + "阿里init cloud_channel failed -- errorCode:" + errorCode + " -- " +
                        "errorMessage:" + errorMessage);
            }
        });
    }

    *//***
     * 清除阿里推送显示的通知
     *//*
    public static void clearAliNotifications() {
        if (pushService != null) {
            pushService.clearNotifications();
        }
    }

    *//**
     * 在清除账号前调用
     * 账号退出登录解绑阿里推送
     *//*
    public static void removePushId() {
        UserInfo userInfo = BaseApplication.getInstance().getUserInfo();
        if (userInfo != null) {
            String pushId = userInfo.getPushId();
            if (pushService != null && !TextUtils.isEmpty(pushId)) {
                pushService.removeAlias(pushId, new CommonCallback() {
                    @Override
                    public void onSuccess(String s) {
                        LogUtils.d(TAG + "解绑推送成功");
                    }

                    @Override
                    public void onFailed(String s, String s1) {
                        LogUtils.d(TAG + "解绑推送失败");
                    }
                });
            }
        }
    }

    *//**
     * 初始化百度相关
     *//*
    private static void initBaiduMap() {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(BaseApplication.getInstance());
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        LogUtils.d("百度地图版本:" + VersionInfo.getApiVersion());
    }

    private static void initX5Web() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.d(TAG + "initX5Web--onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                LogUtils.d(TAG + "initX5Web--onCoreInitFinished");
            }
        };

        //初始化tbs x5 webview
        QbSdk.initX5Environment(BaseApplication.getInstance(), cb);
    }

    *//**
     * 初始化bugly sdk
     *//*
    private static void initBugly() {

        *//**
         * true表示app启动自动初始化升级模块; false不会自动初始化;
         * 开发者如果担心sdk初始化影响app启动速度，可以设置为false，
         * 在后面某个时刻手动调用Beta.init(getApplicationContext(),false);
         *//*
        Beta.autoInit = true;

        *//**
         * true表示初始化时自动检查升级; false表示不会自动检查升级,需要手动调用Beta.checkUpgrade()方法;
         *//*
        Beta.autoCheckUpgrade = true;

        *//**
         * 设置升级检查周期为60s(默认检查周期为0s)，60s内SDK不重复向后台请求策略);
         *//*
        //Beta.upgradeCheckPeriod = 60 * 1000;

        *//**
         * 设置启动延时为1s（默认延时3s），APP启动1s后初始化SDK，避免影响APP启动速度;
         *//*
        Beta.initDelay = 3 * 1000;

        *//**
         * 设置通知栏大图标，largeIconId为项目中的图片资源;
         *//*
        Beta.largeIconId = R.mipmap.ic_launcher;

        *//**
         * 设置状态栏小图标，smallIconId为项目中的图片资源Id;
         *//*
        Beta.smallIconId = R.mipmap.ic_launcher;

        *//**
         * 设置更新弹窗默认展示的banner，defaultBannerId为项目中的图片资源Id;
         * 当后台配置的banner拉取失败时显示此banner，默认不设置则展示“loading“;
         *//*
        //Beta.defaultBannerId = R.drawable.ic_launcher;

        *//**
         * 设置sd卡的Download为更新资源保存目录;
         * 后续更新资源会保存在此目录，需要在manifest中添加WRITE_EXTERNAL_STORAGE权限;
         *//*
        //Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment
        // .DIRECTORY_DOWNLOADS);

        *//**
         * 已经确认过的弹窗在APP下次启动自动检查更新时会再次显示;
         *//*
        Beta.showInterruptedStrategy = true;

        *//**
         * 只允许在MainActivity上显示更新弹窗，其他activity上不显示弹窗; 不设置会默认所有activity都可以显示弹窗;
         *//*
        Beta.canShowUpgradeActs.add(MainActivity.class);

        *//***
         * 升级SDK默认是开启热更新能力的，如果你不需要使用热更新，可以将这个接口设置为false。
         *//*
        Beta.enableHotfix = false;

        Bugly.init(BaseApplication.getInstance(), getBuglyAppId(), Constant.IS_DEBUG);
    }
*/
}
