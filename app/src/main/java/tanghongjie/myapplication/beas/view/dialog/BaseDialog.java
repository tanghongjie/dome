package tanghongjie.myapplication.beas.view.dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import tanghongjie.myapplication.R;
import tanghongjie.myapplication.beas.activity.BaseActivity;
import tanghongjie.myapplication.common.utils.UIUtils;


/**
 * 创建时间: 2017/05/17 11:57
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:通用的提示对话框基类,处理好了基本的通用属性
 * 修改时间:
 * 修改描述:
 */
public  abstract class BaseDialog extends Dialog implements View.OnClickListener {
	public final String TAG = "--"+getClass().getSimpleName()+"--";
	public BaseActivity activity;
	public static final int _WRAP_CONTENT=-1;
	private int width;
	private int height;
	private int gravity;
	/**
	 * 是否能通过返回按钮关闭
	 */
	private boolean cancelable;

	public BaseDialog(@Nullable BaseActivity context) {
		super(context, R.style.common_loaddialog_style);
		this.activity= context;
		cancelable=isCancelable();
	}

	@Override
	public void show() {
		init();
		super.show();
	}

	private void init() {
		Window window = getWindow();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		window.setBackgroundDrawable(new ColorDrawable(UIUtils.getColor(android.R.color.transparent)));

		setContentView(getLayoutResId());

		width=getDialogWidth();
		height=getDialogHeight();
		gravity=getDialogGravity();

		LayoutParams params = window.getAttributes();

		if (width==_WRAP_CONTENT){
			params.width= LayoutParams.WRAP_CONTENT;
		}else{
			params.width=width;
		}

		if (height==_WRAP_CONTENT){
			params.height= LayoutParams.WRAP_CONTENT;
		}else{
			params.height=height;
		}

		params.gravity = gravity;
		window.setAttributes(params);

		setCanceledOnTouchOutside(isCanceledOnTouchOutside());
		setCancelable(cancelable);
		
		initView();
		initData();
		initListener();
	}

	/***
	 * 点击弹窗外部是否能关闭
	 * @return
	 */
	protected boolean isCanceledOnTouchOutside() {
		return false;
	}

	/***
	 * 点击返回按钮是否能关闭
	 * @return
	 */
	protected boolean isCancelable() {
		return true;
	}

	/***
	 * 点击返回按钮是否能关闭
	 * @param cancelable
	 */
	public void setIsCancelable(boolean cancelable) {
		this.cancelable = cancelable;
	}

	protected int getDialogGravity(){
		return  Gravity.CENTER;
	}

	protected int getDialogHeight() {
		return _WRAP_CONTENT;
	}

	protected int getDialogWidth(){
		return  (int) (UIUtils.getScreenWidth() * 0.9);
	}

	/**
	 * 获取布局id
	 * @return
     */
	protected abstract @LayoutRes
    int getLayoutResId();

	/**
	 * 初始化view控件
	 */
	protected abstract void initView();

	/**
	 * 初始化数据
	 */
	protected abstract void initData();

	/**
	 * 初始化监听
	 */
	protected abstract void initListener();

	public interface OnClickCommonDialogListener<T>{
		/**
		 * 点击取消
		 * @param data
         */
		void onClickCommonDialogCancel(T data);

		/**
		 * 点击确定
		 * @param data
         */
		void onClickCommonDialogCommit(T data);
	}

}
