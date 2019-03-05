package tanghongjie.myapplication.constast;

/**
 * 创建时间: 2017/08/02 21:29
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:全局url
 * 修改时间:
 * 修改描述:
 */
public interface HttpUrls {
    //是否是正式环境 todo 修改当前字段切换环境
    boolean IS_RELEASE = true;

    //测试服务器
   // String HOST_HTTP_TEST = "http://xxl.2callcar.com/";
    String HOST_HTTP_TEST = "http://test.2callcar.com:180/";
    String HOST_H5_TEST = "http://test.2callcar.com:180/";
    String HOST_IMAGE_TEST = "http://test.2callcar.com:180/";

    //正式服务器
    String HOST_HTTP_RELEASE = "https://app.2callcar.com/";
    String HOST_H5_RELEASE = "https://app.2callcar.com/";
    String HOST_IMAGE_RELEASE = "https://app.2callcar.com/";

    // TODO 发布时修改服务器地址
    String HOST = (IS_RELEASE ? HOST_HTTP_RELEASE : HOST_HTTP_TEST)+"openApis/";

    // todo 图片使用
    String IMAGE_HOST = IS_RELEASE ? HOST_IMAGE_RELEASE : HOST_IMAGE_TEST;

    // todo H5使用
    String WEB_HOST = IS_RELEASE ? HOST_H5_RELEASE : HOST_H5_TEST;

    // todo 协议使用
    String PROTOCOL_HOST = WEB_HOST+"agreement/";

    // todo 分享使用的host
    String SHARE_HOST= (IS_RELEASE ? HOST_HTTP_RELEASE : HOST_HTTP_TEST)+"mobile/#/";

    //登录接口
    String LOGIN = HOST + "open/login";

    //退出登录
    String LOGOUT = HOST + "open/logout";

    //注册
    String REGISTER = HOST + "open/member/register";

    //获取短信验证:注册、登录、重置登录密码、重置支付密码
    String GET_SMSCODE = HOST + "open/member/sendSms";

    //验证手机号及短信验证码（会员注册、重置登录密码、重置支付密码）
    String CHECK_PHONE = HOST + "open/member/verifyMobile";

    //验证手机号及短信验证码（注册，重置登录密码，重置支付密码）
    String CHECK_PHONE_AND_CODE = HOST + "open/member/verifySmsCode";

    //重置登录密码
    String RESET_LOGINPWD = HOST + "open/member/resetLoginPwd";

    //查询全国网点信息-查所有网点信息关联的城市，省以及网点的车辆总数
    String GET_ALL_BRANCH_INFO = HOST + "open/content/district/listDots";

    //查询当前行政区域网点(用车前搜索)
    String SEARCH_DOTS_BY_CURRENTREGION = HOST + "open/content/shareDot/searchDotsByCurrentRegion";

    //城市查询:搜索公用的
    String LIST_CITYS = HOST + "open/content/district/listCitys";

    //热门城市 todo 暂时没有接口
    String HOT_CITY = HOST + "";

    //上传大字段图片 :认证图片专用
    String UPLOAD_AUTH_FILE = HOST + "open/content/blob/uploadImage";

    //上传资源文件
    String UPLOAD_RESOURCE_FILE = HOST + "open/content/resource/uploadFile";

    //提交认证资料
    String IDENTITY_AUTH = HOST + "open/member/certified/certified";

    //订车时，获取资费套餐列表
    String EXPENSES_SETMEAL_LIST = HOST + "open/share/goods";

    //订车时，获取各套餐的资费详情
    String EXPENSESETMEAL_DETIAL = HOST + "open/share/rule";

    //确认还车页面
    String CINFIRM_BACK_CAR = HOST + "open/order/over";

    //离线确认还车
    String OFF_LINE_CINFIRM_BACK_CAR = HOST + "open/order/overOffline";

    //获取共享汽车订单详情
    String GET_SHARCAR_ORDER_DETIAL = HOST + "open/order/myDetail";

    //获取订单列表
    String GET_ORDER_LIST = HOST + "open/order/list";

    //附加费用列表
    String ADDITIONAL_CHARGE_LIST = HOST + "open/order/extra/list";

    //附加费用详情
    String ADDITIONAL_CHARGE_DETIAL = HOST + "open/order/extra/detail";

    //附加费用线下支付
    String ADDITIONAL_CHARGE_OFFLINE_PAY = HOST + "open/order/extra/pay";

    //消息列表
    String MSG_LIST = HOST + "open/content/message/listMessageByMember";

    //更新用户信息
    String UPDATE_USERINFO = HOST + "open/member/updateMemberInfo";

    //获取用户信息
    String GET_USERINFO = HOST + "open/member/queryMemberInfo";

    //获取优惠券列表
    String LIST_COUPONS = HOST + "open/member/coupon/listCoupons";

    //获取有效的优惠券
    String EFFECTIVE_COUPONS = HOST + "open/member/coupon/listAvailableCoupons";

    //获取广告
    String AD_LIST = HOST + "open/content/advertisement/listAd";

    //获取反馈回复信息
    String GET_FEEDBACK_REPLY = HOST + "open/order/question/info";

    //问题反馈
    String FEEDBACK_QUESTION = HOST + "open/order/question/create";

    //帮助中心
    String LIST_HELP_CENTER = HOST + "open/content/help/listHelpCenter";

    //获取进行中的订单
    String ORDER_DOING = HOST + "open/order/shareDetail";

    //查询指定网点车辆信息
    String BRANCH_CARLIST = HOST + "open/car/carInfo/listCarsByDotId";

    //获取最近的网点
    String LISTCARS_BY_RECENT_DOT = HOST + "open/content/shareDot/listCarsByRecentDot";

    //获取还车网点信息(用车中搜索停车网点信息)
    String GET_BACKCAR_BRANCH = HOST + "open/content/shareDot/searchDotsByReturnCar";

    //确定用车:订车时，提交订车订单
    String COMMIT_USE_CAR_ORDER = HOST + "open/order/car";

    //判断是否可以订车
    String USECAR_CONDITION = HOST + "open/order/book";

    //找车
    String FIND_CAR = HOST + "open/car/carInfo/seekCar";
    //取车
    String TAKE_CAR = HOST + "open/order/takeCar";

    //获取实时费用
    String GET_REALTIME_MONEY = HOST + "open/order/current";

    //用车中,获取车辆实时信息
    String GET_REALTIME_CARINFO = HOST + "open/car/carInfo/current";

    //取消用车
    String CANCEL_USECAR = HOST + "open/order/cancel";

    //还车接口
    String BACK_CAR = HOST + "open/car/carInfo/checkDot";

    //确认还车,提交还车订单
    String AFFIRM_BACKCAR = HOST + "open/order/submit";

    //检查支付密码状态
    String CHECK_PAYPWD = HOST + "open/member/checkPayPwd";

    //设置支付密码
    String SET_PAY_PASS = HOST + "open/member/setPayPwd";

    //校验支付密码是否正确
    java.lang.String CHECK_PAYPWD_VERIFY = HOST + "open/member/verifyPayPwd";

    //获取支付银行卡列表
    java.lang.String PAY_BANKCARD_LIST = HOST + "open/order/cust/queryBankUnionCard";

    //获取提现银行卡列表
    String DEPOSIT_BANKCARD_LIST = HOST + "open/order/cust/queryCustBindInfo";

    //跳转绑定银联支付银行卡html页面，跳转到银联绑卡界面
    String ADD_PAY_BANKCARD_H5 = WEB_HOST + "bank/bankUnionCardBind.html?account=";

    //查询会员钱包余额，共享押金，代驾押金
    java.lang.String QUERY_CUSTACCOUNT_MONEY = HOST + "open/order/cust/queryCustAccountMoney";

    //根据银行卡号获取银行信息
    String QUERY_BANKCARD_INFO = HOST + "open/order/cust/queryBankCardInfo";

    //银行会员子账户绑定提现银行卡鉴权:绑定提现银行卡,校验预留手机号,发送验证码
    String BIND_ACCOUNT_AUTHC = HOST + "open/order/cust/bindAccountAuthc";

    //银行会员子账户绑定提现鉴权验证:绑定提现银行卡,最后调用的接口
    String BIND_ACCOUNT_AUTHCVERIFY = HOST + "open/order/cust/bindAccountAuthcVerify";

    //解绑提现银行卡
    String UNBIND_CUSTACCOUNT = HOST + "open/order/cust/unbindCustAccount";

    //创建充值订单
    String CREATE_RECHARGE_ORDER = HOST + "open/order/createRechargeOrder";

    //支付接口
    String PAY = HOST + "open/order/pay";

    //微信app支付前获取订单详情
    String WECHAT_ORDER_PAY = HOST + "open/order/mixpay";

    //创建退费订单:押金提现
    String CREATE_REFUND_ORDER = HOST + "open/order/createRefundOrder";

    //支付交易发送短信验证码
    String SENDSMS_FORPAY = HOST + "open/order/sendSmsForPay";

    //根据业务订单号查询订单钱包支付记录
   // String QUERY_ORDERWALLETRECORD = HOST + "open/order/queryOrderWalletRecord";
    String QUERY_ORDERWALLETRECORD = HOST + "open/order/queryWXOrderRecord";

    //查询钱包订单列表
    String QUERY_CUSTORDERLIST = HOST + "open/order/cust/queryCustOrderList";

    //查询星级
    String LIST_STARLEVELS = HOST + "open/member/star/listStarLevels";

    //查询押金账单详情
    String QUERY_CUSTORDERDETAIL = HOST + "open/order/cust/queryCustOrderDetail";

    //行车轨迹
    String WHEEL_PATH = HOST + "open/car/carInfo/queryHistoryTravel";

    //登录成后绑定pushid
    String BIND_PUSHID = HOST + "open/member/bindPush/appDeviceBingPush";

    //获取服务器当前时间
    String SERVER_TIMESTAMP = HOST + "open/member/time/currentTime";

    //百度地图坐标转GPS坐标
    String CONVERT_GPS = HOST + "open/car/carInfo/convertGPS";

    //兑换优惠券
    String EXCHANGE_COUPON_BY_PASSWORD = HOST + "open/member/coupon/exchangeCouponByPassword";

    //发送开锁/锁车命令
    String SEND_CAR_COMMAND = HOST + "open/order/sendCommand";

    //查询车辆是否在线
    String CHECK_ON_LINE = HOST + "open/car/carInfo/checkOnline";

    //设备离线取车
    String TAKE_CAR_OFFLINE = HOST + "open/order/takeCarOffline";

    //离线还车判断电子围栏
    String CHECK_DOT_OFFLINE = HOST + "open/car/carInfo/checkDotOffline";

    //删除消息
    String DELETE_MSG = HOST + "open/content/message/deleteMessageByIds";

    //查询订单商品折扣/优惠信息
    String QUERY_DISCOUNT_STATUS = HOST + "open/share/selectById";

    //查询能兑换的优惠券
    String LIST_CONVERTIBLE_COUPONS = HOST + "/open/member/coupon/listConvertibleCoupons";

    //签到送积分
    String SING_IN = HOST + "open/member/point/signIn";

    //查询我的当前总积分
    String HAVE_POINT = HOST + "open/member/point/havePoint";

    //查询我的积分记录
    String LIST_RECORD = HOST + "open/member/point/listRecord";

    //兑换积分
    String EXCHANGE_COUPON = HOST + "open/member/point/exchangeCoupon";

    //分享记录
    String SHEAN_HISTIRY = HOST + "open/member/invitation/listHistory";

    //用户注册协议
    String USER_REGISTER_PROTOCOL = PROTOCOL_HOST + "userAgreement.html";

    //钱包充值协议
    String RECHARGE_PROTOCOL = PROTOCOL_HOST + "rechargeAgreement.html";

    //积分协议
    String INTEGRAL_PROTOCOL = PROTOCOL_HOST + "integralAgreement.html";

    //押金说明
    String CASH_PLEDGE_PROTOCOL = PROTOCOL_HOST + "depositAgreement.html";

    //优惠券说明
    String DISCOUNT_COUPON_REGISTER_PROTOCOL = PROTOCOL_HOST + "CouponAgreement.html";

    //邀请分享页面
    String SHARE_INVITATION=SHARE_HOST+"invitation/";

    //行程分享页面
    String SHARE_TRAVEL=SHARE_HOST+"travelSharing/";

    //分享使用的图标路径
    String SHARE_ICON_URL=HttpUrls.HOST_HTTP_RELEASE+"download/xlcx.png";

    //分享送积分
    String SHARE_FEED=HOST+"open/member/point/share";

    //发票H5的url
    String INVOICE_URL=WEB_HOST+"invoice/index.html?token=";

}
