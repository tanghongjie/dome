package tanghongjie.myapplication.beas.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import tanghongjie.myapplication.beas.activity.BaseActivity;
import tanghongjie.myapplication.beas.mvp.BaseContract;
import tanghongjie.myapplication.beas.mvp.BasePresenter;
import tanghongjie.myapplication.common.utils.LogUtils;
import tanghongjie.myapplication.common.utils.PermissionUtils;
import tanghongjie.myapplication.common.utils.UIUtils;
import tanghongjie.myapplication.constast.PermissionConstant;
import tanghongjie.myapplication.interfaces.IOperationBase;

/**
 * 创建时间: 2016/05/01 22:21
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:mvp Fragment顶层基类
 * 使用场景:进入页面时不需要请求网络的fragment页面继承当前基类
 * 修改时间:
 * 修改描述:
 */
public abstract class BaseFragment<V extends BaseContract.IBaseView, P extends BasePresenter<V>>
		extends Fragment implements IOperationBase,BaseContract.IBaseView  {
	public final String TAG = "--" + getClass().getSimpleName() + "--";
	public P basePresenter;
	public BaseActivity mActivity;
	public View mRoot;
	public ImmersionBar mImmersionBar;
	public Unbinder unbinder;

	public BaseFragment() {
		super();
		LogUtils.d("Fragment--构造" + TAG);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		LogUtils.d("Fragment--onAttach()" + TAG);
		mActivity = (BaseActivity) getActivity();
		initPresenter();
		firstInit();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtils.d("Fragment--onCreate()" + TAG);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LogUtils.d("Fragment--onCreateView()" + TAG);

		if (isShowImmerseLayout()){
			initImmersionBar();
		}

		//避免重复创建Fragment的布局
		if (mRoot == null) {
			mRoot = LayoutInflater.from(mActivity).inflate(getLayoutRes(), container, false);
			//返回一个Unbinder值（进行解绑）
			unbinder = ButterKnife.bind(this, mRoot);

			initView(mRoot);
			initData();
			initListener();
		} else {
			// 解除mRoot与父控件的父子关系
			ViewGroup parent = (ViewGroup) mRoot.getParent();
			if (parent != null) {
				parent.removeView(mRoot);
			}
		}

		return mRoot;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LogUtils.d("Fragment--onActivityCreated()" + TAG);
	}

	/**********************************默认实现的方法***********************************************/
	/**
	 * 是否使用沉浸式布局
	 * @return
	 */
	public boolean isShowImmerseLayout() {
		return false;
	}

	/**
	 * 初始化沉浸式
	 */
	protected void initImmersionBar() {
		mImmersionBar = ImmersionBar.with(this);
		mImmersionBar.keyboardEnable(true).navigationBarWithKitkatEnable(false).init();
	}

	/**
	 * 在页面创建后立即调用,用于在做逻辑操作前的一些初始化任务,根据需求重写此方法
	 */
	@Override
	public void firstInit() {

	}

	/**********************************MVP相关的方法封装***********************************************/

	/**
	 * 初始化Presenter对象
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
	protected abstract @NonNull P createPresenter();

	/**
	 * 显示加载进度条
	 * @param tips 提示语
	 */
	@Override
	public void showLoading(String tips) {
		if (!isFinishActivity()){
			mActivity.showLoading(tips);
		}
	}

	/***
	 * 显示加载框
	 * @param tips 提示语
	 * @param isCanCancel 是否能通过返回按钮关闭加载框
	 */
	@Override
	public void showLoading(String tips, boolean isCanCancel) {
		if (!isFinishActivity()){
			mActivity.showLoading(tips,isCanCancel);
		}
	}

	@Override
	public void hideLoading() {
		if (!isFinishActivity()){
			mActivity.hideLoading();
		}
	}

	/**
	 * 用于下拉结果的监听,处理UI相关的逻辑:
	 * {@link BasePresenter#onPullRefreshComplete()}时触发回调
	 */
	@Override
	public void pullRefreshCompleteUI() {

	}

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

	/******************************************自定义方法*****************************************/
	/**
	 * 当前activity是否销毁
	 * @return
	 */
	public boolean isFinishActivity() {
        return mActivity == null || mActivity.isFinishing();
	}

	/**
	 * 显示toast
	 * @param tips
	 */
	@Override
	public void showToast(String tips) {
		UIUtils.showToast(tips);
	}

	/***
	 * 显示toast
	 * @param tips 提示
	 * @param isShowLong  是否显示长时间toast
	 */
	@Override
	public void showToast(String tips,boolean isShowLong) {
		if (isShowLong){
			UIUtils.showLongToast(tips);
		}else{
			showToast(tips);
		}
	}

	public void loopActivity(Class clazz) {
		if (!isFinishActivity()){
			Intent intent = new Intent(mActivity, clazz);
			startActivity(intent);
		}
	}

	public void loopActivity(Intent intent) {
		if (!isFinishActivity()){
			startActivity(intent);
		}
	}

	/**************************************生命周期方法/系统方法********************************************/
	@Override
	public void onClick(View v) {
		onClick(v, v.getId());
	}


	@Override
	public void onResume() {
		super.onResume();
		LogUtils.d("Fragment--onResume()" + TAG);
	}

	@Override
	public void onPause() {
		super.onPause();
		LogUtils.d("Fragment--onPause()" + TAG);
	}

	@Override
	public void onStop() {
		super.onStop();
		LogUtils.d("Fragment--onStop()" + TAG);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		LogUtils.d("Fragment--onSaveInstanceState()" + TAG);
	}

	@Override
	public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		LogUtils.d("Fragment--onViewStateRestored()" + TAG);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		LogUtils.d("Fragment--onDestroyView()" + TAG);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtils.d("Fragment--onDestroy()" + TAG);
		//使用 RefWatcher 监控 Fragment
		//BaseApplication.getRefWatcher(getActivity()).watch(this);
		if (mImmersionBar != null){
			mImmersionBar.destroy();
		}

		if (basePresenter != null){
			basePresenter.detachView();
		}

		if (unbinder!=null){
			unbinder.unbind();
		}
	}

	/******************************************android6.0权限适配***********************************/
	/**
	 * 是否有权限
	 * @param permissions
	 * @return
	 */
	public boolean hasPermission(String... permissions) {
		if (Build.VERSION.SDK_INT < VERSION_CODES.M) {
			//如果是android6.0之前的就不用申请权限,直接返回true
			return true;
		}
		for (String permission : permissions) {
			if (ContextCompat.checkSelfPermission(mActivity, permission) != PermissionConstant.HAS_PERMISSION) {
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
	public void requestPermission(int requestCode, String... permissions) {
		requestPermissions(permissions, requestCode);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		//目前在fragment中申请权限后,还会回调到对应的父Activity里面中,已经通过代码处理了
		//处理权限回调逻辑
		PermissionUtils.onRequestPermissionsResultForFragment(this,mActivity,requestCode, permissions, grantResults);
	}

}
