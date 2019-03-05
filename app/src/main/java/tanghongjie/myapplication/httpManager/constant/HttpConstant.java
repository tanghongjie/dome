package tanghongjie.myapplication.httpManager.constant;

/**
 * 创建时间: 2017/08/02 22:04
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:http请求使用的常量
 * 修改时间:
 * 修改描述:
 */
public interface HttpConstant {
    /**
     * 网络请求:登录成功后可以获取到token作为接口调用的身份凭证，token参数统一写在请求的head中
     */
    String HTTP_AUTHORIZATION_KEY="Authorization";

    /**
     *登录成功后可以获取到token作为接口调用的身份凭证，token参数统一写在请求的head中。
     * Bearer+空格+token
     */
    String HTTP_AUTHORIZATION="Bearer ";

    /**
     * 添加到http头部：key
     */
    String HTTP_HEAD_KEY_APPID="AppId";

    /**
     * 添加到http头部：value
     */
    String HTTP_APPID_VALUE="xxl";

    /**
     * 上传文件使用的常量字段
     * 1-会员 2-企业用户
     */
    String ACCOUNT_TYPE_MEMBER = "1";

    //上传文件类型
    /**
     * 1-图片
     */
    String UPLOAD_FILE_IMAGE="1";
    /**
     * 2-文档
     */
    String UPLOAD_FILE_DOC="2";
    /**
     * 3-表格
     */
    String UPLOAD_FILE_EXCEL="3";
    /**
     *  4-文本
     */
    String UPLOAD_FILE_TEXT="4";
    /**
     * 5-音频
     */
    String UPLOAD_FILE_AUDIO="5";
    /**
     * 6-视频
     */
    String UPLOAD_FILE_VEDIO="6";

    /******************************http接口返回的状态code**********************************/
    /**
     * 请求成功
     */
    String HTTP_RESULT_STATUS_SUCCESS="1";
    /**
     * 请求失败
     */
    String HTTP_RESULT_STATUS_ERROR="0";
    /**
     * 被挤下线
     */
    String HTTP_RESULT_STATUS_KICKOUT="102108";
    /**
     * token失效
     */
    String HTTP_RESULT_STATUS_TOKEN_ERROR ="102101";
    /**
     * 自定义错误状态
     */
    String HTTP_RESULT_STATUS_CUSTOM_ERROR="custom_error";

    /***
     * 账号不存在:短信登录时,直接帮用户注册账号
     */
    String SYS_ACCOUNT_NO_EXIST  = "102115";

    /**
     * 当code为 106101 时，data表示过多少分钟后才能再次订车
     */
    String USECAR_DELAY_MIN_CODE  = "106101";

}
