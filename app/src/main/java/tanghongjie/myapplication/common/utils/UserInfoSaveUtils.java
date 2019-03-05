package tanghongjie.myapplication.common.utils;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import tanghongjie.myapplication.common.bean.UserInfo;
import tanghongjie.myapplication.constast.Constant;
import tanghongjie.myapplication.httpManager.ThreadPoolManager;


/**
 * 创建时间:2018/03/15 14:27
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:用户信息保存的工具类,用于登录成功后会返回的用户信息保存和读取,账号密码保存
 *          ps:目前只有用账号密码(手机号码)登录,应用中使用手机号码的地方直接使用了getAccount()方法
 * 修改时间:
 * 修改描述:
 */
public class UserInfoSaveUtils {
    /**
     * 登录成功后,用户信息保存到本地的key,保存的json
     * 用户信息key
     */
    private static final String USERINFO_KEY = "USERINFO_KEY";
    /**
     * 登录成功后token保存的key
     */
    private static final String TOKEN_KEY = "TOKEN_KEY";
    /**
     * 保存密码
     */
    private static final String PASSWORD_KEY = "PASSWORD_KEY";
    /**
     * 账号保存的key
     */
    private static final String ACCOUNT_KEY = "ACCOUNT_KEY";
    /**
     * 登录类型保存的key
     */
    private static final String LOGIN_TYPE_KEY = "LOGIN_TYPE_KEY";

    //登录的类型
    //账号密码登录
    public static final String LOGIN_TYPE_PASS = "LOGIN_TYPE_PASS";
    //验证码登录
    public static final String LOGIN_TYPE_SMSCODE = "LOGIN_TYPE_SMS_CODE";
    //注册免登录
    public static final String LOGIN_TYPE_REGISTER = "LOGIN_TYPE_REGISTER";

    /**
     * 保存前必须对返回的信息和关键字段做数据校验
     * 13ms
     * @param userInfo 用户信息
     * @param token 登录凭证
     * @param loginType 当前登录类型,验证码还是密码登录
     */
    public static void saveUserInfo(@NonNull UserInfo userInfo, @NonNull String token, @NonNull String loginType) {
        //用户信息
        SpUtils.setString(USERINFO_KEY,JSON.toJSONString(userInfo));
        //保存token
        SpUtils.setString(TOKEN_KEY,token);
        //保存当前登录类型
        SpUtils.setString(LOGIN_TYPE_KEY,loginType);
    }

    /**
     * 获取本地保存的用户信息
     */
    public static UserInfo getUserInfo() {
        UserInfo userInfo = null;
        try {
            String json = SpUtils.getString(USERINFO_KEY);
            LogUtils.e("本地保存的用户登录信息json:"+json);
            if (!TextUtils.isEmpty(json)) {
                userInfo = JSON.parseObject(json, UserInfo.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    /**
     * 获取本地保存的token
     * @return
     */
    public static String getToken() {
        return  SpUtils.getString(TOKEN_KEY);
    }

    /**
     * 退出登录,或者登录失效后清除本地保存的信息
     */
    public static void clearUserInfo() {
        SpUtils.removeKey(USERINFO_KEY);
        SpUtils.removeKey(TOKEN_KEY);
        SpUtils.removeKey(LOGIN_TYPE_KEY);
    }

    /**
     * 判断登录信息是否有效
     * 读取时间22ms
     * @return
     */
    public static UserInfo isValidLogin(){
        UserInfo userInfo=getUserInfo();
        String token=getToken();
        if (userInfo!=null&&!TextUtils.isEmpty(token)){
            //判断登录信息是否有效
            //1.本地是否保存账号和密码
            //2.登录信息是否完整
            boolean isAccountEmpty= TextUtils.isEmpty(getAccount());
            boolean isPassEmpty= TextUtils.isEmpty(getPassword());
            //登录类型
            String loginType=SpUtils.getString(LOGIN_TYPE_KEY);

            if (LOGIN_TYPE_PASS.equals(loginType)){
                //账号密码登录
                if (!userInfo.isUserInfoFull()||isAccountEmpty||isPassEmpty){
                    return null;
                }else{
                    return userInfo;
                }
            }else{
                //验证码登录或注册后直接登录
                if (!userInfo.isUserInfoFull()){
                    return null;
                }else{
                    return userInfo;
                }
            }

        }else{
            if (Constant.IS_DEBUG){
                LogUtils.e("userInfo==null?"+(userInfo==null)+"---token==null?"+ TextUtils.isEmpty(token));
            }
            return null;
        }
    }

    /**
     * 更新本地保存的用户信息,用于在应用内更新资料后同步保存到本地
     * @param userInfo
     * @param isThread 是否开启线程保存
     */
    public synchronized static void updateUserInfo(@NonNull final UserInfo userInfo, boolean isThread){
        if (isThread){
            ThreadPoolManager.getProxy().execute(new Runnable() {
                @Override
                public void run() {
                    String json=JSON.toJSONString(userInfo);
                    SpUtils.setString(USERINFO_KEY,json);
                }
            });
        }else{
            String json=JSON.toJSONString(userInfo);
            SpUtils.setString(USERINFO_KEY,json);
        }
    }

    /**
     * 保存账号
     * @param account
     */
    public static void saveAccount(@NonNull String account){
        SpUtils.setString(ACCOUNT_KEY,account);
    }

    /**
     * 保存密码
     * @param password
     */
    public static void savePassword(@NonNull String password){
        SpUtils.setString(PASSWORD_KEY,password);
    }

    /**
     * 获取账号
     * @return
     */
    public static String getAccount(){
        return SpUtils.getString(ACCOUNT_KEY);
    }

    /**
     * 获取本地的密码
     * @return
     */
    public static String getPassword(){
        return SpUtils.getString(PASSWORD_KEY);
    }

    /**
     * 清除账号
     */
    public static void clearAccount(){
        SpUtils.removeKey(ACCOUNT_KEY);
    }

    /**
     * 清除密码
     */
    public static void clearPassword(){
        SpUtils.removeKey(PASSWORD_KEY);
    }

    /**
     * 同时清除
     */
    public static void clearAccountAndPass(){
        clearAccount();
        clearPassword();
    }

}
