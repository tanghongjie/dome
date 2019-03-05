package tanghongjie.myapplication.common.utils;

import android.graphics.Paint;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 创建时间:2018/02/02 11:59
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:textview显示富文本
 * 修改时间:
 * 修改描述:
 */
public class TextViewUtils {

    /**
     *   给TextView设置部分大小
     *   包头不包尾部
     */
    public static void setPartialSize(TextView tv, int start, int end, int textSize) {
        try{
            String s = tv.getText().toString();
            if (TextUtils.isEmpty(s)){
                return;
            }

            Spannable spannable = new SpannableString(s);
            spannable.setSpan(new AbsoluteSizeSpan(textSize), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv.setText(spannable);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /***
     * 给TextView设置部分颜色
     * @param tv
     * @param start
     * @param end
     * @param textColor
     */
    public static void setPartialColor(TextView tv, int start, int end, int textColor) {
        try{
            String s = tv.getText().toString();
            Spannable spannable = new SpannableString(s);
            spannable.setSpan(new ForegroundColorSpan(textColor), start, end,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            tv.setText(spannable);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //给TextView设置部分字体大小和颜色
    public static void setPartialSizeAndColor(TextView tv, int start, int end, int textSize, int textColor) {
        String s = tv.getText().toString();
        Spannable spannable = new SpannableString(s);
        spannable.setSpan(new AbsoluteSizeSpan(textSize, false), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(textColor), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spannable);
    }

    //给TextView设置下划线
    public static void setUnderLine(TextView tv) {
        if (tv.getText() != null) {
            String udata = tv.getText().toString();
            SpannableString content = new SpannableString(udata);
            content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
            tv.setText(content);
            content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
        } else {
            tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
    }

    //给TextView设置下划线
    public static void setUnderLine(TextView tv, int startPosition, int endPosition) {
        if (tv.getText() != null) {
            String udata = tv.getText().toString();
            SpannableString content = new SpannableString(udata);
            content.setSpan(new UnderlineSpan(), startPosition, endPosition, 0);
            tv.setText(content);
            content.setSpan(new UnderlineSpan(), startPosition, endPosition, 0);
        } else {
            tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
    }


    //取消TextView的置下划线
    public static void clearUnderLine(TextView tv) {
        tv.getPaint().setFlags(0);
    }

    //半角转换为全角
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375){
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    //去除特殊字符或将所有中文标号替换为英文标号
    public static String replaceCharacter(String str) {
        str = str.replaceAll("【", "[").replaceAll("】", "]").replaceAll("！", "!")
                .replaceAll("：", ":").replaceAll("（", "(").replaceAll("（", ")");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static void setEditTextMulti(EditText et){
        et.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        et.setGravity(Gravity.TOP);
        et.setSingleLine(false);
        et.setHorizontallyScrolling(false);
    }

}