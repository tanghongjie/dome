package tanghongjie.myapplication.beas.view.dialog;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import tanghongjie.myapplication.R;
import tanghongjie.myapplication.beas.activity.BaseActivity;
import tanghongjie.myapplication.common.utils.UIUtils;


/**
 * 创建时间:2018/04/03 11:28
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:通用提示框
 *          显示左右2个按钮
 * 修改时间:
 * 修改描述:
 */
public class CommonDialog extends BaseDialog {
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvLeft;
    private TextView tvRight;

    private String title;
    private String content;
    private String leftTips;
    private String rightTips;

    //左边按钮颜色
    private @ColorInt
    int buttonLeftColorId=UIUtils.getResources().getColor(R.color.main_text);
    //右边按钮颜色
    private @ColorInt
    int buttonRightColorId=UIUtils.getResources().getColor(R.color.main_color);
    private OnClickCommonDialogListener listener;

    public CommonDialog(@Nullable BaseActivity context) {
        super(context);
    }

    public CommonDialog(@Nullable BaseActivity context, @NonNull OnClickCommonDialogListener
            listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected int getDialogWidth() {
        return (int) (UIUtils.getScreenWidth() * 0.85);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_common;
    }

    @Override
    protected void initView() {

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvLeft = (TextView) findViewById(R.id.tv_left);
        tvRight = (TextView) findViewById(R.id.tv_right);
    }

    @Override
    protected void initData() {
        tvTitle.setText(title);
        tvContent.setText(content);
        tvLeft.setText(leftTips);
        tvRight.setText(rightTips);

        tvLeft.setTextColor(buttonLeftColorId);
        tvRight.setTextColor(buttonRightColorId);
    }

    @Override
    protected void initListener() {
        tvLeft.setOnClickListener(this);
        tvRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()){

            case R.id.tv_left:
                listener.onClickCommonDialogCancel(null);
                break;

            case R.id.tv_right:
                listener.onClickCommonDialogCommit(null);
                break;

            default:break;
        }
    }

    /***
     *
     * @param title
     * @param content
     * @param leftTips
     */
    public void setData(String title, String content, String leftTips, String rightTips) {
        this.title = title;
        this.content = content;
        this.leftTips = leftTips;
        this.rightTips = rightTips;
    }

    /***
     * 设置按钮颜色
     */
    public void setBottomButtonLeftTextColor(@ColorInt int colorId){
        this.buttonLeftColorId =colorId;
    }

    /***
     * 设置按钮颜色
     */
    public void setBottomButtonRightTextColor(@ColorInt int colorId){
        this.buttonRightColorId =colorId;
    }

}
