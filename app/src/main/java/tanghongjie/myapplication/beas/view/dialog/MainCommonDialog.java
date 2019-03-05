package tanghongjie.myapplication.beas.view.dialog;

import android.app.Dialog;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import tanghongjie.myapplication.R;
import tanghongjie.myapplication.beas.activity.BaseActivity;
import tanghongjie.myapplication.common.utils.UIUtils;


/**
 * 创建时间:2018/04/03 11:28
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:主界面使用的通用提示框
 * 修改时间:
 * 修改描述:
 */
public class MainCommonDialog extends BaseDialog {
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvOk;
    private String title;
    private String content;
    private String btnTips;
    private OnClickMainCommonButtonListener listener;
    //底部按钮颜色
    private @ColorInt
    int buttonColorId;

    public MainCommonDialog(@Nullable BaseActivity context) {
        super(context);
        this.buttonColorId=UIUtils.getResources().getColor(R.color.main_text);
    }

    public MainCommonDialog(@Nullable BaseActivity context, OnClickMainCommonButtonListener
            listener) {
        super(context);
        this.buttonColorId=UIUtils.getResources().getColor(R.color.main_text);
        this.listener = listener;
    }

    @Override
    protected int getDialogWidth() {
        return (int) (UIUtils.getScreenWidth() * 0.85);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_maincommon;
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvOk = (TextView) findViewById(R.id.tv_ok);
    }

    @Override
    protected void initData() {
        tvTitle.setText(title);
        tvContent.setText(content);
        if (TextUtils.isEmpty(btnTips)) {
            tvOk.setText(activity.getString(R.string.iknow));
        } else {
            tvOk.setText(btnTips);
        }
        tvOk.setTextColor(buttonColorId);

    }

    @Override
    protected void initListener() {
        tvOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_ok) {
            if (listener != null) {
                listener.onClickMainCommonButton(this);
            }
            dismiss();
        }
    }

    /***
     *
     * @param title
     * @param content
     * @param btnTips
     */
    public void setData(String title, String content, String btnTips) {
        this.title = title;
        this.content = content;
        this.btnTips = btnTips;
    }

    /***
     * 设置按钮颜色
     */
    public void setBottomButtonTextColor(@ColorInt int colorId){
        this.buttonColorId=colorId;
    }

    /***
     *
     * @param title
     * @param content
     */
    public void setData(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public interface OnClickMainCommonButtonListener {
        void onClickMainCommonButton(Dialog dialog);
    }

}
