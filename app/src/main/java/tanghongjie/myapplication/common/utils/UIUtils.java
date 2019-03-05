package tanghongjie.myapplication.common.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * 创建时间:2018/02/02 11:59
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:处理UI相关的工具类
 * 修改时间:
 * 修改描述:
 */
public class UIUtils {

	/**
	 * 获取状态栏的高度
	 * @return
	 */
	public static int getStatusBarHeight() {
		int height=0;
		Resources resources = getContext().getResources();
		int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
		if (resourceId>0){
			height= resources.getDimensionPixelSize(resourceId);
		}
		return height;
	}

	/**
	 * 获取导航栏的高度
	 * @return
	 */
	public static int getNavigationBarHeight() {
		Resources resources = getContext().getResources();
		int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
		int height = resources.getDimensionPixelSize(resourceId);
		return height;
	}

	/**
	 * 查找一个布局里的所有的按钮并设置点击事件
	 * @param rootView
	 * @param listener
	 */
	public static void findButtonAndSetOnClickListener(View rootView, OnClickListener listener) {
		if (rootView==null||listener==null){
			return;
		}

		if (rootView instanceof ViewGroup) {
			ViewGroup parent = (ViewGroup) rootView;
			if (parent==null){
				return;
			}

			for (int i = 0; i < parent.getChildCount(); i++) {
				View child = parent.getChildAt(i);
				if (child==null){
					continue;
				}

				// 如果是按钮设置点击事件
				if (child instanceof Button || child instanceof ImageButton) {
					child.setOnClickListener(listener); // 设置点击事件
				}

				if (child instanceof ViewGroup) {
					findButtonAndSetOnClickListener(child, listener);
				}
			}
		}

	}

	/**
	 * 清空布局里面所有的edittext的焦点
	 * @param rootView
	 */
	public static void findEditTextAndClearFocus(View rootView) {
		if (rootView==null) {
			return;
		}

		if (rootView instanceof ViewGroup) {
			ViewGroup parent = (ViewGroup) rootView;
			if (parent==null){
				return;
			}

			for (int i = 0; i < parent.getChildCount(); i++) {
				View child = parent.getChildAt(i);
				if (child==null){
					continue;
				}

				// 如果是EditText,清除光标
				if (child instanceof EditText) {
					EditText et= (EditText) child;
					if (et.hasFocus()){
						et.clearFocus();
					}
				}
				if (child instanceof ViewGroup) {
					findEditTextAndClearFocus(child);
				}
			}
		}
	}

	// 获取全局上下文
	public static Context getContext() {
		return GlobalUtils.getContext();
	}

	public static AssetManager getAssets(){
       return getContext().getAssets();
	}

	//获取handler
	public static Handler getHandler() {
		return GlobalUtils.getHandler();
	}

	//获取主线程
	public static Thread getMainThread() {
		return GlobalUtils.getMainThread();
	}

	//获取主线程的id
	public static int getMainThreadId() {
		return GlobalUtils.getMainThreadId();
	}

	//实例化View对象
	public static View inflate(int layoutResId) {
		return View.inflate(getContext(), layoutResId, null);
	}


	//取得项目的资源
	public static Resources getResources() {
		return getContext().getResources();
	}

	public static String getString(@StringRes int resId){
		return getResources().getString(resId);
	}

	public static int getColor(@ColorRes int resId){
		return getResources().getColor(resId);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dp2px(float dpValue) {
		final float scale = getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}


	public static int px2dp(float pxValue) {
		final float scale = getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	//任务在主线程中运行
	public static void runOnMainThread(Runnable r) {
		if (isRunMainThread()) {
			r.run();
		} else {
			getHandler().post(r);
		}
	}

	public static void runOnMainThread(Runnable r, long delayTime) {
		if (isRunMainThread()) {
			r.run();
		} else {
			getHandler().postDelayed(r,delayTime);
		}
	}

	// 判断当前是否在主线程中运行
	public static boolean isRunMainThread() {
		return Looper.getMainLooper() == Looper.myLooper();
	}

	// 获取LayoutInflate
	public static LayoutInflater getInflater() {
		return LayoutInflater.from(getContext());
	}

	// 获取屏幕的宽和高
	public static int getScreenHeight() {
		return GlobalUtils.getScreenHeight();
	}

	// 获取屏幕的宽度
	public static int getScreenWidth() {
		return GlobalUtils.getScreenWidth();
	}

	/** 提示toast可以在子线程调用*/
	public static void showToast(final String text) {
		runOnMainThread(new Runnable() {
			@Override
			public void run() {
				if (!TextUtils.isEmpty(text)){
					Ts.show(text);
				}
			}
		});
	}

	public static void showToast(@StringRes final int textResId) {
		runOnMainThread(new Runnable() {
			@Override
			public void run() {
				String text=getString(textResId);
				if (!TextUtils.isEmpty(text)){
					Ts.show(text);
				}
			}
		});
	}


	public static void showLongToast(final String text) {
		runOnMainThread(new Runnable() {
			@Override
			public void run() {
				if (!TextUtils.isEmpty(text)){
					Ts.showToastLong(text);
				}
			}
		});
	}

	public static void showLongToast(@StringRes final int textResId) {
		runOnMainThread(new Runnable() {
			@Override
			public void run() {
				String text=getString(textResId);
				if (!TextUtils.isEmpty(text)){
					Ts.showToastLong(text);
				}
			}
		});
	}

}
