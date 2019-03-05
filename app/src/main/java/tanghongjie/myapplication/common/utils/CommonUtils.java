package tanghongjie.myapplication.common.utils;

import android.content.Context;
import android.text.TextUtils;

import java.text.DecimalFormat;

import tanghongjie.myapplication.R;


/**
 * 创建时间:2018/02/02 11:59
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:通用的工具类
 * 修改时间:
 * 修改描述:
 */
public class CommonUtils {

    /***
     * 数字转中文
     * 1转一
     * @param context
     * @param num
     * @return
     */
    public static String numToChinese(Context context, int num){
        String tempString ;
        String[] arrays=context.getResources().getStringArray(R.array.array_nums);
        if (num<=0){
            tempString= String.valueOf(num);
        }else if(num>10){
            tempString= String.valueOf(num);
        }else {
            num--;
            tempString = arrays[num];
        }

        return tempString;
    }

    /***
     * 秒转时长字符串显示
     * @param context
     * @param second 时长:秒
     * @return
     */
    public static String secondToHourString(Context context, int second) {
        int days = second / ( 60 * 60 * 24);
        int hours = (second % ( 60 * 60 * 24)) / (60 * 60);
        int minutes = (second % ( 60 * 60)) / 60;
        int seconds = second %60;
        StringBuffer sb=new StringBuffer();
        if (days>0){
            sb.append(days);
            sb.append(context.getString(R.string.day));
        }

        if (days>0||hours>0){
            sb.append(hours);
            sb.append(context.getString(R.string.hour));
        }
        sb.append(minutes);
        sb.append(context.getString(R.string.minute));
        return sb.toString();
    }


    /***
     * 预约中和用车中显示时间专用的方法
     * 秒转时长字符串显示
     *
     * @param context
     * @param second 时长:秒
     * @return
     */
    public static String appointmentAndUseCarSecondToHourString(Context context, int second) {
        int days = second / ( 60 * 60 * 24);
        int hours = (second % ( 60 * 60 * 24)) / (60 * 60);
        int minutes = (second % ( 60 * 60)) / 60;
        int seconds = second %60;

        //目前不显示秒:如果是14分钟30秒-->直接显示15分钟
        //剩余30秒,显示一分钟
        if (seconds>0){
            minutes++;
            if (minutes>=60){
                hours++;
                minutes=0;
                if (hours>=24){
                    hours=0;
                    days++;
                }
            }
        }

        StringBuffer sb=new StringBuffer();

        if (days>0){
            sb.append(days);
            sb.append(context.getString(R.string.day));
        }

        if (days>0||hours>0){
            sb.append(hours);
            sb.append(context.getString(R.string.hour));
        }

        if ((days>0||hours>0)&&minutes<=0){
            //不显示分钟

        }else {
            if (minutes<0){
                LogUtils.e("appointmentAndUseCarSecondToHourString()分钟小于0:"+minutes);
                minutes=0;
            }
            sb.append(minutes);
            sb.append(context.getString(R.string.minute));
        }

        return sb.toString();
    }

    /***
     * 仅仅在获取实时费用后返回的时长，格式化
     * @param context
     * @param minute 时长:分钟
     * @return
     */
    public static String realTimeMinuteToHourString(Context context, int minute) {
        int days = minute / (60 * 24);
        int hours = (minute % (60 * 24)) / 60;
        int minutes =minute%60;

        StringBuffer sb=new StringBuffer();

        if (days>0){
            sb.append(days);
            sb.append(context.getString(R.string.day));
        }

        if (days>0||hours>0){
            sb.append(hours);
            sb.append(context.getString(R.string.hour));
        }

        if ((days>0||hours>0)&&minutes<=0){

        }else{
            sb.append(minutes);
            sb.append(context.getString(R.string.minute));
        }

        return sb.toString();
    }


    /***
     * 还车时,支付页面格式化时长(精确到分钟)
     * 秒转时长字符串显示
     *
     * @param context
     * @param second 时长:秒
     * @return
     */
    public static String backCarSecondToHourString(Context context, int second) {
        int days = second / ( 60 * 60 * 24);
        int hours = (second % ( 60 * 60 * 24)) / (60 * 60);
        int minutes = (second % ( 60 * 60)) / 60;
        int seconds = second %60;

        if (seconds>0) {
            minutes++;
            if (minutes >= 60) {
                hours++;
                minutes = 0;
                if (hours >= 24) {
                    hours = 0;
                    days++;
                }
            }
        }

        StringBuffer sb=new StringBuffer();

        if (days>0){
            sb.append(days);
            sb.append(context.getString(R.string.day));
        }

        if (days>0||hours>0){
            sb.append(hours);
            sb.append(context.getString(R.string.hour));
        }

        if ((days>0||hours>0)&&minutes>0){
            //分钟
            sb.append(minutes);
            sb.append(context.getString(R.string.minute2));
        }else if(days==0&&hours==0){
            //分钟
            sb.append(minutes);
            sb.append(context.getString(R.string.minute2));
        }

        return sb.toString();
    }

    /**
     * 四舍五入保留指定位的小数
     * @param value
     * @param count
     * @return
     */
    public static String decimalFormat(double value , int count){
        StringBuffer sb=new StringBuffer();
        sb.append("#.");
        if (count<=0){
            sb.append("0");
        }

        for (int i=0;i<count;i++){
            sb.append("0");
        }

        String temp=new DecimalFormat(sb.toString()).format(value);
        if (temp.startsWith(".")){
            return "0"+temp;
        }else{
            return temp;
        }
    }

    /***
     * 将手机号码格式化
     * 137****5555
     * @param phoneNum
     * @return
     */
    public static String formatPhone(String phoneNum) {
        int count=phoneNum.length();
        if (count!=11){
            return phoneNum;
        }else{
            int startIndex=3;
            int endIndex=6;
            StringBuilder sb=new StringBuilder();
            for (int i=0;i<11;i++){
                if (i>=startIndex&&i<=endIndex){
                    sb.append("*");
                }else{
                    sb.append(phoneNum.charAt(i));
                }
            }
            return sb.toString();
        }
    }

    /***
     * 格式银行卡号
     * 格式如下 **** **** **** 6666
     * @param mContext
     * @param accNo
     * @return
     */
    public static String formatBankCardNum(Context mContext, String accNo) {
        if (TextUtils.isEmpty(accNo)){
            return "";
        }else{
            StringBuffer sb=new StringBuffer();

            //每隔4位添加空格
            int count=accNo.length();
            int groupCount=count/4;
            if (groupCount*4==count){
                //卡号位数是4的倍数
                //获取最后4位
                String strLast = accNo.substring(count-4,count);
                for (int i=0;i<groupCount-1;i++){
                    sb.append("****  ");
                }
                sb.append(strLast);
            }else{
                //卡号位数不是4的倍数
                int temp=count-groupCount*4;
                String strLast = accNo.substring(count-temp,count);
                for (int i=0;i<groupCount;i++){
                    sb.append("****  ");
                }
                sb.append(strLast);
            }
            return sb.toString();
        }

    }

}
