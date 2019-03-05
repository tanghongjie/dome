package tanghongjie.myapplication.common.utils;

import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import tanghongjie.myapplication.R;
import tanghongjie.myapplication.beas.activity.BaseActivity;


/**
 * 创建时间: 2017/08/20 14:47
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:加载进度条对话框使用的工具类
 * 修改时间:
 * 修改描述:
 */
public class LoadDialogUtils {
    private BaseActivity activity;
    private Dialog progressDialog;

    public LoadDialogUtils(BaseActivity activity) {
        super();
        this.activity = activity;
    }

    public void dismissLoadDialog() {
        if (progressDialog != null&&progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public void showLoadDialog(String message, boolean isCanCancel) {
        if (activity==null||activity.isFinishing()){
            return;
        }

        if (progressDialog == null) {
            progressDialog = new Dialog(activity, R.style.pb_dialog_style);
            progressDialog.setContentView(R.layout.layout_loaddialog);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            progressDialog.getWindow().setWindowAnimations(R.style.loading_dialog_style);
        }
        progressDialog.setCancelable(isCanCancel);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        ImageView ivLoading = (ImageView) progressDialog.findViewById(R.id.iv_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) ivLoading.getDrawable();
        // 开启帧动画
        animationDrawable.start();
        if (TextUtils.isEmpty(message)) {
            msg.setText(activity.getString(R.string.loading));
        } else {
            msg.setText(message);
        }
        if (!activity.isFinishing()){
            progressDialog.show();
        }
    }
}
