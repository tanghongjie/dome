package tanghongjie.myapplication.constast;


import java.io.File;

import tanghongjie.myapplication.common.utils.SDUtils;
import tanghongjie.myapplication.common.utils.SystemUtils;
import tanghongjie.myapplication.common.utils.UIUtils;

/**
 * 创建时间: 2017/08/02 21:30
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:全局常量
 * 修改时间:
 * 修改描述:
 */
public interface Constant {
    //当前状态是否是debug模式
    boolean IS_DEBUG = SystemUtils.isApkInDebug();

    //性别 0-未知；1—男；2—女
    String GENDER_NONE="0";
    String GENDER_MAN="1";
    String GENDER_WOMAN="2";

    String APP_FILE_PROVIDER ="com.zswl.xxlvip.fileprovider";

    //身份认证状态
    //未认证
    String AUTHCSTATUS_NO="1";
    //认证成功
    String AUTHCSTATUS_PASS="2";
    //认证失败
    String AUTHCSTATUS_FAIL="3";

    //网络状态
    String NO_NET = "NO_NET";
    String NET_TYPE_WIFI = "NET_TYPE_WIFI";
    String NET_TYPE_MOBILE = "NET_TYPE_MOBILE";

    //应用内部的文件目录
    String APP_INTERNAL_PATH = UIUtils.getContext().getFilesDir().getAbsolutePath() + File.separator;

    //应用内部缓存目录
    String APP_INTERNAL_CACHE_PATH = UIUtils.getContext().getCacheDir().getAbsolutePath() + File.separator;

    //glide缓存目录
    String GLIDE_CACHE_PATH = APP_INTERNAL_PATH + "glideCachePath/";

    // sharePreferences文件名
    String CONFIG_FILE_NAME ="xxlvip_config" ;


    //应用在SD卡中创建的文件夹名称
    String APP_PATH= "xxlvip";

    //应用在SD卡中创建的目录
    String SD_ROOT_PATH= SDUtils.getSDCardPath() + APP_PATH+ File.separator;


    //登录信息失效
    String TOKEN_ERROR_ACTION = "TOKEN_ERROR_ACTION";

    //登录使用的标记,当前使用的手机类型:1是安卓，2是苹果
    String APP_TYPE = "1";

    //是否记住登录状态:0：否；1：是。app端固定传1
    String IS_REMEMBER_LOGIN_STATUS = "1";

    //登录类型
    //用户名+密码
    String LOGIN_TYPE_PASS="100";
    //手机号+短信验证码
    String LOGIN_TYPE_SMS_CODE ="200";

    //注册类型:0 会员发起注册，1 APP自动注册（当手机+短信登录提示账户不存在时）
    String REGISTER_TYPE_AUTO="1";

    //短信验证码发送类型：1-登录，2-注册，3-重置登录密码，4-重置支付密码
    //校验手机号码,类型(针对验证手机号及短信验证码)：2-注册，3-重置登录密码，4-重置支付密码
    String SEND_SMS_TYPE_LOGIN="1";
    String SEND_SMS_TYPE_REGISTER="2";
    String SEND_SMS_TYPE_RESET_LOGIN_PASS ="3";
    String SEND_SMS_TYPE_RESET_PAY_PASS ="4";

    //客服电话
    String SERVICE_PHONE_NUM ="4008630788";

}
