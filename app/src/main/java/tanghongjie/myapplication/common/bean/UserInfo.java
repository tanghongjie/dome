package tanghongjie.myapplication.common.bean;

import android.text.TextUtils;

import java.io.Serializable;

import tanghongjie.myapplication.constast.Constant;

/**
 * 创建时间:2018/03/15 14:24
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:登录成功后返回的用户信息
 * 修改时间:
 * 修改描述:
 */
public class UserInfo implements Serializable {
    //用户id
    private String id;
    //昵称
    private String nickName;
    //手机
    private String mobile;
    //性别 0-未知；1—男；2—女
    private String gender;
    //生日格式 1991-01-01
    private String birthday;

    //认证状态，1未认证 2认证成功 3认证失败
    //默认是未认证
    private String authcStatus= Constant.AUTHCSTATUS_NO;

    //会员等级
    private int level=1;
    //星级:1~10级
    private int star=1;
    //用户推送和IM
    private String pushId;
    //用户图像
    private String avatarPath;

    //当前星级值
    private int starValue;
    //星级名称
    private String starName;

    //距离下一个星级差距的星级值
    private int differenceStarValue;

    //实名认证后的姓名
    private String name;
    //身份证号
    private String idNo;

    public String getStarName() {
        return starName;
    }

    public void setStarName(String starName) {
        this.starName = starName;
    }

    public int getStarValue() {
        return starValue;
    }

    public void setStarValue(int starValue) {
        this.starValue = starValue;
    }

    public int getDifferenceStarValue() {
        return differenceStarValue;
    }

    public void setDifferenceStarValue(int differenceStarValue) {
        this.differenceStarValue = differenceStarValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAuthcStatus() {
        return authcStatus;
    }

    public void setAuthcStatus(String authcStatus) {
        this.authcStatus = authcStatus;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    /***
     * 获取用户图像
     * @return
     */
    public String getUserPhoto(){
        return getAvatarPath();
    }

    /**
     * 登录成功后,判断关键字段用户信息是否完整
     * 信息完整才能保存到本地
     * 1.用户id
     * 4.pushId
     * 其它未做校验的字段使用时需要非空判断
     *
     * @return
     */
    public boolean isUserInfoFull() {
        return !TextUtils.isEmpty(id) && !TextUtils.isEmpty(pushId);
    }

    /***
     * 判断身份认证是否通过
     * @return
     */
    public boolean isIdentityAuthPass(){
        return Constant.AUTHCSTATUS_PASS.equals(authcStatus);
    }

}
