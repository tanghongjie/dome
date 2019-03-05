package tanghongjie.myapplication.beas.activity;

import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import tanghongjie.myapplication.R;
import tanghongjie.myapplication.beas.BaseApplication;
import tanghongjie.myapplication.beas.mvp.BaseContract;
import tanghongjie.myapplication.beas.mvp.BasePresenter;
import tanghongjie.myapplication.beas.view.dialog.MainCommonDialog;
import tanghongjie.myapplication.common.utils.AppUtils;
import tanghongjie.myapplication.common.utils.LoadDialogUtils;
import tanghongjie.myapplication.common.utils.LogUtils;
import tanghongjie.myapplication.common.utils.PermissionUtils;
import tanghongjie.myapplication.common.utils.SoftKeyBoardUtils;
import tanghongjie.myapplication.common.utils.UIUtils;
import tanghongjie.myapplication.constast.PermissionConstant;
import tanghongjie.myapplication.interfaces.IOperationActivity;
import tanghongjie.myapplication.interfaces.TransitionMode;


/**
 * 创建时间: 2016/08/02 21:45
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:mvp activity顶层基类
 * 使用场景:进入页面时不需要调接口获取数据的activity继承当前基类
 * 修改时间:
 * 修改描述:
 */
public abstract class BaseActivity<V extends BaseContract.IBaseView, P extends BasePresenter<V>>
		extends AppCompatActivity implements IOperationActivity,BaseContract.IBaseView{
	public final String TAG = "--"+getClass().getSimpleName()+"--";
	/**
	 * Presenter对象
	 */
	public P basePresenter;
	/**
	 * 页面是否可见
	 */
	public boolean isResume;
	/**
	 * 全局app
	 */
	public BaseApplication baseApp=BaseApplication.getInstance();
	/**
	 * 适配沉浸式状态栏
	 */
	public ImmersionBar mImmersionBar;
	/**
	 * 适配沉浸式状态栏使用的view
	 */
	public View topView;
	/**
	 * 标题文本控件
	 */
	public TextView tvTitle;
	/**
	 * handler对象
	 */
	private Handler mHandler;
	/**
	 * 加载提示框
	 */
	private LoadDialogUtils dialogUtils;
	/**
	 * 整个标题栏控件View
	 */
	private View titleBar;
	/**
	 * 标题右边的控件:imageview或textview
	 */
	private View rightView;
	/**
	 * 标题栏返回控件
	 */
	private View btnBack;
	/**
	 * 当前页面根布局
	 */
	public ViewGroup rootView;
	/**
	 * 注解查找控件
	 */
	public Unbinder unbinder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtils.d("onCreate()" +TAG);

		/*
		 * todo 仅调试时，测试适配功能
		 */
/*		if (Constant.IS_DEBUG){
			setCustomDensity(this,BaseApplication.getInstance());
		}*/

		/*
		 * 对于一些特殊的activity,可以直接重写该方法
		 */
		onBaseCreate();
	}

	/***
	 * 参考今日头条，屏幕适配方案
	 * https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA
	 * @param activity
	 * @param application
	 */
	private void setCustomDensity(Activity activity, Application application){
		final DisplayMetrics appDisplayMetrics=application.getResources().getDisplayMetrics();
		final float targetDensity=appDisplayMetrics.widthPixels/360;
		final int targetDensityDpi= (int) (160*targetDensity);

		appDisplayMetrics.density=appDisplayMetrics.scaledDensity=targetDensity;
		appDisplayMetrics.densityDpi=targetDensityDpi;

		final DisplayMetrics activityDisplayMetrics=activity.getResources().getDisplayMetrics();
		activityDisplayMetrics.density=activityDisplayMetrics.scaledDensity=targetDensity;
		activityDisplayMetrics.densityDpi=targetDensityDpi;
	}

	protected void onBaseCreate() {
		//activity切换动画
		myOverridePendingTransition(false);
		//固定竖屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		//背景颜色
		getWindow().setBackgroundDrawableResource(R.color.context_bg);
		//设置布局
		setContentView(getLayoutRes());
		//当前对象添加到集合保存
		AppUtils.addActivity(this);

		if (isBindView()){
			unbinder= ButterKnife.bind(this);
		}

		initPresenter();
		firstInit();

		rootView = (ViewGroup) findViewById(android.R.id.content);
		titleBar = findView(R.id.layout_titlebar_id);
		btnBack = findView(R.id.title_btn_back_id);
		rightView = findView(R.id.title_right_view_id);
		tvTitle = findView(R.id.tv_title_id);
		topView =  findView(R.id.title_top_view_id);

		setRightViewVisible(false);
		setBackButtonVisible(true);

		//沉浸式状态栏相关的配置
		if (isShowImmerseLayout()&&topView!=null){
			mImmersionBar = ImmersionBar.with(this);
			initImmersionBar();
		}

		//设置标题
		initTitleView();
		onCommonBaseActivityInit();
	}

	/**
	 * CommonBaseActivity 二次封装的基类中重写此方法
	 */
	protected void onCommonBaseActivityInit() {
		initView(rootView);
		initData();
		initListener();
	}

	/*************************************默认实现的方法*******************************************/
	/***
	 * 是否绑定view,实现注入
	 * 只在 {@link BaseCommonActivity }中重写此方法,返回false
	 * @return
	 */
	protected boolean isBindView(){
		return true;
	}

	/**
	 * 是否开启activity动画:子类根据需求重写
	 * @return
     */
	protected boolean toggleOverridePendingTransition(){
		return true;
	}

	/**
	 * activity切换动画类型:根据需求重写,默认是左右滑动
	 * @return
     */
	protected int getOverridePendingTransitionMode() {
		return TransitionMode.LEFT_RIGHT;
	}

	/**
	 * activity切换动画
	 * @param isDestroy 是否是销毁时调用的
     */
	private void myOverridePendingTransition(boolean isDestroy){
		if (toggleOverridePendingTransition()) {
			switch (getOverridePendingTransitionMode()) {
				case TransitionMode.LEFT_RIGHT:
					/**
					 * 左右滑动动画
					 */
					if (isDestroy){
						overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
					}else{
						overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
					}
					break;

				case TransitionMode.TOP_BOTTOM:
					/*
					 * 从下向上
					 */
					break;

				case TransitionMode.FADE:
					/*
					 * 渐变动画
					 */
					overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					break;

				default:
					break;
			}
		}
	}

	/**
	 * 是否沉浸式状态栏,根据需求重写
	 * @return
	 */
	public boolean isShowImmerseLayout(){
		return true;
	}

	/**
	 * 子类根据需求重写沉浸式状态的样式
	 */
	protected void initImmersionBar() {
		mImmersionBar.statusBarColor(R.color.main_color)
				.statusBarView(topView)
				.init();
	}

	/**
	 * 如果子类需要在所有操作前做初始化操作,可以重这个方法
	 * 在{@link BaseActivity#initPresenter()}后面调用
	 */
	@Override
	public void firstInit() {

	}

	/*************************************MVP相关的方法封装*****************************************/

	/**
	 * 创建presenter
	 */
	@Override
	public void initPresenter() {
		basePresenter = createPresenter();
		basePresenter.attachView((V) this);
	}

	/**
	 * 创建presenter对象
	 * @return
	 */
	protected abstract @NonNull
    P createPresenter();

	/**
	 * 显示加载进度条
	 * @param tips 提示语
	 */
	@Override
	public void showLoading(String tips) {
		showLoading(tips,false);
	}

	/***
	 * 显示加载框
	 * @param tips 提示语
	 * @param isCanCancel 是否能通过返回按钮关闭加载框
	 */
	@Override
	public void showLoading(String tips, boolean isCanCancel) {
		if (dialogUtils==null) {
			dialogUtils=new LoadDialogUtils(this);
		}
		dialogUtils.showLoadDialog(tips,false);
	}

	/**
	 * 隐藏加载对话框
	 */
	@Override
	public void hideLoading() {
		if (dialogUtils!=null) {
			dialogUtils.dismissLoadDialog();
		}
	}

	/**
	 * 用于下拉结果的监听,处理UI相关的逻辑:
	 * {@link BasePresenter#onPullRefreshComplete()}时触发回调
	 */
	@Override
	public void pullRefreshCompleteUI() {}

	/***
	 * 调用{@link BasePresenter#onCheckLocationAndStoragePermission(BaseActivity)} 检测定位和存储权限
	 * 有权限时回调
	 */
	@Override
	public void hadLocationAndStoragePermission() {}

	/***
	 *  调用{@link BasePresenter#onCheckLocationAndStoragePermission(BaseActivity)}检测定位和存储权限
	 *  没有权限时回调
	 */
	@Override
	public void noLocationAndStoragePermission() {}

	/******************************************自定义方法**************************************/
	public <K> K findView(int id) {
		return (K) findViewById(id);
	}

	public synchronized Handler getHandler(){
		if (mHandler!=null){
			return mHandler;
		}

		if (UIUtils.isRunMainThread()){
			mHandler=new Handler();
		}else{
			//如果是子线程
			mHandler=new Handler(Looper.getMainLooper());
		}
		return mHandler;
	}

	/**
	 * 显示toast
	 * @param tips
     */
	@Override
	public void showToast(String tips){
		UIUtils.showToast(tips);
	}

	/**
	 * 显示toast
	 * @param tips
	 * @param isShowLong 是否长时间显示
     */
	@Override
	public void showToast(String tips, boolean isShowLong){
		if (isShowLong){
			UIUtils.showLongToast(tips);
		}else{
			UIUtils.showToast(tips);
		}
	}

	/**
	 * 设置界面标题文字
	 * @param title
	 */
	public void setTitleText(String title) {
		if (tvTitle != null) {
			tvTitle.setText(title);
		}
	}

	/**
	 * 设置界面标题文字
	 * @param titleResId
	 */
	public void setTitleText(@StringRes int titleResId) {
		if (tvTitle != null) {
			tvTitle.setText(titleResId);
		}
	}

	/**
	 * 设置右边按钮的文本:默认当成TextView或Button处理
	 * 如果右边是ImageView 需要单独处理
	 * @param rightText
	 */
	protected void setRightViewText(String rightText){
		if (rightView!=null&&!TextUtils.isEmpty(rightText)&&rightView instanceof TextView){
			((TextView) rightView).setText(rightText);
		}
	}

	/**
	 * 设置右边按钮的文本:默认当成TextView或Button处理
	 * 如果右边是ImageView 需要单独处理
	 * @param rightTextResId
	 */
	protected void setRightViewText(@StringRes int  rightTextResId){
		if (rightView!=null&&rightView instanceof TextView){
			((TextView) rightView).setText(rightTextResId);
		}
	}

	/**
	 * 如果标题右边是ImageView设置图片资源
	 * @param iconId
	 */
	protected void setRightImageViewResource(@DrawableRes int iconId){
		if (rightView!=null&&rightView instanceof ImageView){
			((ImageView) rightView).setImageResource(iconId);
		}
	}

	/**
	 * 如果标题右边是ImageView设置padding
	 * @param paddingDp
	 */
	protected void setRightImageViewPadding(int paddingDp){
		if (rightView!=null&&rightView instanceof ImageView){
			rightView.setPadding(paddingDp,paddingDp,paddingDp,paddingDp);
		}
	}

	/**
	 * 设置返回按钮是否可见
	 * @param isVisible
     */
	public void setBackButtonVisible(boolean isVisible){
		if (btnBack!=null){
			btnBack.setVisibility(isVisible? View.VISIBLE: View.INVISIBLE);
			btnBack.setEnabled(isVisible);
			btnBack.setOnClickListener(this);
		}
	}

	/**
	 * 右上角的按钮是否可见
	 * @param isVisible
     */
	protected void setRightViewVisible(boolean isVisible) {
		if (rightView != null){
			rightView.setVisibility(isVisible? View.VISIBLE: View.INVISIBLE);
			rightView.setEnabled(isVisible);
			if (isVisible){
				rightView.setOnClickListener(this);
			}
		}
	}

	/**
	 * 跳转页面
	 * @param clazz
     */
	public void loopActivity(Class clazz){
		Intent intent=new Intent(this,clazz);
		startActivity(intent);
	}

	/**
	 * 跳转页面
	 * @param intent
	 */
	public void loopActivity(Intent intent){
		startActivity(intent);
	}

	/***
	 * 显示通用的弹窗:字体颜色固定
	 * @param title
	 * @param content
	 * @param btnTips
	 */
	public void showCommonDialog(String title, String content, String btnTips) {
		MainCommonDialog dialog=new MainCommonDialog(this,null);
		dialog.setBottomButtonTextColor(getResources().getColor(R.color.main_color));
		dialog.setData(title,content,btnTips);
		dialog.show();
	}

	/**************************************生命周期方法/系统方法*******************************************/
	@Override
	public void onClick(View v) {
		if (isClickButtonHideSoftKey()){
			//关闭软键盘
			SoftKeyBoardUtils.hideSoftKeyboard(this);
			//清除edittext焦点
			UIUtils.findEditTextAndClearFocus(rootView);
		}

		//如果标题栏左上角的返回按钮
		if (v.getId() == R.id.title_btn_back_id) {
			//关闭页面
			onClickBackButton();
		}else{
			onClick(v, v.getId());
		}
	}

	/**
	 * 点击按钮是否隐藏软键盘
	 * 根据需求重写
	 */
	public boolean isClickButtonHideSoftKey() {
		return true;
	}

	/**
	 * 点击标题栏的返回按钮,默认是关闭页面
	 * 子类可以根据需求来重写
	 */
	public void onClickBackButton() {
		finish();
	}

	/**
	 * 设置字体不随系统字体变化
	 * @return
	 */
	@Override
	public Resources getResources() {
		Resources res=super.getResources();
		if (res.getConfiguration().fontScale!=1){
			Configuration newConfig=new Configuration();
			newConfig.setToDefaults();
			res.updateConfiguration(newConfig,res.getDisplayMetrics());
		}
		return res;
	}

	@Override
	protected void onResume() {
		super.onResume();
		isResume=true;
		LogUtils.d("onResume()" + TAG);
		//MobclickAgent.onResume(this);
		AppUtils.addActivityTag(TAG);
	}

	@Override
	protected void onPause() {
		super.onPause();
		LogUtils.d("onPause()" + TAG);
		//MobclickAgent.onPause(this);
		isResume=false;
	}

	@Override
	protected void onStop() {
		super.onStop();
		LogUtils.d("onStop()" + TAG);
		AppUtils.removeActivityTag(TAG);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		LogUtils.d("onSaveInstanceState()" + TAG);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		LogUtils.d("onRestoreInstanceState()" + TAG);
	}

	@Override
	public void finish() {
		super.finish();
		myOverridePendingTransition(true);
	}

	@Override
	protected void onDestroy() {
		LogUtils.d("onDestroy()" +TAG);
		hideLoading();
		AppUtils.removeActivity(this);
		if (mHandler!=null){
			mHandler.removeCallbacksAndMessages(null);
		}
		if (mImmersionBar != null){
			mImmersionBar.destroy();
		}
		if (basePresenter != null){
			basePresenter.detachView();
		}
		if (unbinder!=null){
			unbinder.unbind();
		}
		super.onDestroy();
		//使用 RefWatcher 监控 Fragment
		//BaseApplication.getRefWatcher(this).watch(this);
	}

	/*************************************Android6.0权限适配****************************************/
	/**
	 * 判断是否有权限
	 * @param permissions
	 * @return
     */
	public boolean hasPermission(String... permissions){
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
			//如果是android6.0之前的就不用申请权限,直接返回true
			return true;
		}

		for(String permission:permissions){
			if(ContextCompat.checkSelfPermission(this,permission)!= PermissionConstant.HAS_PERMISSION){
				return false;
			}
		}

		return true;
	}

	/**
	 * 请求权限
	 * @param requestCode
	 * @param permissions
     */
	public void requestPermission(int requestCode,String... permissions){
		ActivityCompat.requestPermissions(this,permissions,requestCode);
	}

	/**
	 * 授权回调
	 * @param requestCode
	 * @param permissions
	 * @param grantResults
     */
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		//此处基类必须调用,否则fragment请求权限后只会回调activity的方法,不会回调fragment的方法
		//虽然在fragment中请求权限会回调activity的方法,但是请求的CODE在activity与fragment中不一样,所以不会影响代码正常运行
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		//处理权限回调逻辑
		PermissionUtils.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
	}

	/***
	 * 请求定位和存储权限前弹窗提示用户,用户确定后开始请求定位和存储权限
	 * @param isRequestPermission 点击确定后是否自动请求权限
	 */
	public void showRequestPermissionBeforeTipsDialog(final boolean isRequestPermission) {
		Builder builder =  new Builder(this);
		builder.setTitle(R.string.friendly_tips);
		builder.setCancelable(false);
		builder.setMessage(R.string.permission_tips);
		builder.setPositiveButton(R.string.permission_iknow, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (isRequestPermission){
							//申请权限
							basePresenter.onRequestLocationAndStoragePermission(BaseActivity.this);
						}
					}
				});
		builder.show();
	}

	/***
	 * 调用{@link BasePresenter#onRequestLocationAndStoragePermission}请求了定位和存储权限回调
	 * 写在BasePresenter基类的目的是因为多处要检测请求这2个权限,避免重复代码
	 */
    public void onGetLocationAndStoragePermission() {}

	/**
	 * 获取了相机权限回调
	 */
	public void onCameraPermission() {}

	/***
	 * 获取了存储权限回调
	 */
	public void onStoragePermission() {}

}
