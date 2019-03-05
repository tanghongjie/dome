package tanghongjie.myapplication.common.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 创建时间:2018/03/07 16:14
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:格式化时间日期相关的工具类
 * 修改时间:
 * 修改描述:
 */
public class DatesUtils {

    /****
     * 协力通用的,日期字符串转long
     * 固定格式2018-05-04 14:56:52
     * @param date
     * @return
     */
    public static long dateStringToLong(String date){
        if (TextUtils.isEmpty(date)){
            date="1970-01-01 12:00:00";
        }

        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        return c.getTimeInMillis();
    }

    public static String formatDate(long date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(date));
    }

    public static String formatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static String formatDate2(long date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(date));
    }

    /***
     * 2018-05-06 12:25:56 转为年月日2018-05-06
     * @param date
     * @return
     */
    public static String formatDate3(String date) {
        return formatDate(dateStringToLong(date));
    }

    /***
     * 获取2个日期间隔天数
     * @param beginDate 时间戳
     * @param endDate
     * @return
     */
    public static int getTimeDistance(long beginDate , long endDate ) {
        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTimeInMillis(beginDate);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTimeInMillis(endDate);

        long beginTime = beginCalendar.getTime().getTime();
        long endTime = endCalendar.getTime().getTime();
        int betweenDays = (int)((endTime - beginTime) / (1000 * 60 * 60 *24));//先算出两时间的毫秒数之差大于一天的天数

        endCalendar.add(Calendar.DAY_OF_MONTH, -betweenDays);//使endCalendar减去这些天数，将问题转换为两时间的毫秒数之差不足一天的情况
        endCalendar.add(Calendar.DAY_OF_MONTH, -1);//再使endCalendar减去1天
        if(beginCalendar.get(Calendar.DAY_OF_MONTH)==endCalendar.get(Calendar.DAY_OF_MONTH)) {
            //比较两日期的DAY_OF_MONTH是否相等
            return betweenDays + 1; //相等说明确实跨天了
        } else{
            return betweenDays + 0; //不相等说明确实未跨天
        }


    }

}
