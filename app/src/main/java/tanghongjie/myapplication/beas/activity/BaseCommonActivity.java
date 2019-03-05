package tanghongjie.myapplication.beas.activity;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.ButterKnife;
import tanghongjie.myapplication.R;
import tanghongjie.myapplication.beas.mvp.BaseContract;
import tanghongjie.myapplication.beas.mvp.BasePresenter;
import tanghongjie.myapplication.beas.view.LoadingView;
import tanghongjie.myapplication.common.utils.LogUtils;
import tanghongjie.myapplication.httpManager.utils.ResultStatus;
import tanghongjie.myapplication.interfaces.ILoadingPager;


/**
 * 创建时间: 2016/08/02 21:45
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:mvp  activity二次封装的基类
 * 使用场景:进入页面时需要调接口获取数据的activity继承当前基类
 * 			 继承该类时,会自动添加标题栏,只需要设置相应的标题
 * 修改时间:
 * 修改描述:
 */
public abstract class BaseCommonActivity<V extends BaseContract.IBaseView, P extends BasePresenter<V>>
		extends BaseActivity<V,P> implements ILoadingPager {
	/**
	 * 根布局
	 */
	private FrameLayout commRootView;
	/**
	 * 自定义网络请求的加载页面
	 */
	private LoadingView loadingPager;
	/**
	 * 加载成功后显示成功的view
	 */
	private View successView;

	@Override
	public int getLayoutRes() {
		return R.layout.layout_activity_common;
	}

	/**
	 * 加载成功后显示的布局文件id
	 * @return
	 */
	public abstract @LayoutRes
    int  getSuccessLayoutResId();

	@Override
	protected void onCommonBaseActivityInit() {
		commRootView= findView(R.id.fl_layout_common_id);
		loadingPager = new LoadingView(this,this);

		//设置自定义页面
		loadingPager.setCustomLoadingUI(getCustomLoadingView());
		loadingPager.setCustomEmptyUI(getCustomEmptyView(),isEmptyCanRetry());
		loadingPager.setCustomErrorUI(getCustomErrorView());

		commRootView.removeAllViews();
		commRootView.addView(loadingPager);
		//自动请求网络
		loadingPager.refreshData();
	}

	/*******************************************默认的实现方法************************************/
	/**
	 * 添加自定义加载页面,不重写使用默认的页面
	 * @return
	 */
	public View getCustomLoadingView() {
		return null;
	}

	/**
	 * 设置自定义加载为空的页面,不重写使用默认的页面
	 * @return
     */
	public View getCustomEmptyView() {
		return null;
	}

	/**
	 * 请求结果为空的页面是否能点击重新请求
	 * @return
	 */
	public boolean isEmptyCanRetry() {
		return true;
	}

	/**
	 * 设置自定义加载失败的页面,不重写使用默认的页面
	 * @return
     */
	public View getCustomErrorView() {
		return null;
	}

	/****************************************自定义方法*******************************************/

	/**
	 * 设置加载失败文字提示
	 * @param errorTips
	 */
	public void setErrorText(String errorTips){
		if (loadingPager!=null) {
			loadingPager.setErrorText(errorTips);
		}
	}
	
	/**
	 * 设置加载为空文字提示
	 * @param errorTips
	 */
	public void setEmptyText(String errorTips){
		if (loadingPager!=null) {
			loadingPager.setEmptyText(errorTips);
		}
	}

	/**
	 * 显示加载为空的页面时，是否能点击重新加载
	 * @param isEnable
	 */
	public void setEmptyClickable(boolean isEnable){
		if (loadingPager!=null) {
			loadingPager.setEmptyClickEnable(isEnable);
		}
	}

	/**********************************http加载结果回调方法******************************************/
	/**
	 * 请求网络成功后调用,在此方法中进行页面初始化操作
	 * @return
	 */
	@Override
	public View createSuccessView(){
		if (isFinishing()){
			return successView;
		}

		//加载布局
		successView= LayoutInflater.from(this).inflate(getSuccessLayoutResId(), null);

		//2018/05/05 请求成功后获取view后,再绑定
		unbinder= ButterKnife.bind(this,successView);

		//初始化控件相关
		initView(successView);
		//初始化数据
		initData();
		//初始化监听器
		initListener();
		return successView;
	}

	/***
	 * 在BaseActivity基类中不绑定控件
	 * 在当前类请求成功后才绑定view
	 * @return
	 */
	@Override
	protected boolean isBindView() {
		return false;
	}

	/**
	 * 调用refreshData()刷新成功后回调,子类根据需求重写此方法
	 */
	@Override
	public void updateSuccessView() {
		if (isFinishing()){
			return;
		}
		LogUtils.d(TAG+"updateSuccessView");
		/**
		 * 刷新成功后,直接调用初始化数据的方法
		 */
		initData();
	}

	/**
	 * 加载失败时被调用
	 */
	@Override
	public void onShowErrorPager() {
		LogUtils.d(TAG+"onShowErrorPager");
	}

	/**
	 * 数据为空时被调用
	 */
	@Override
	public void onShowEmptyPager() {
		LogUtils.d(TAG+"onShowEmptyPager");
	}

	/**
	 * 加载失败,但是不显示失败的界面,用于切换页面,刷新结果的监听
	 */
	@Override
	public void onLoadErrorNoShowErrorPager() {
		LogUtils.d(TAG+"onLoadErrorNoShowErrorPager");
	}

	/*************************************网络请求相关**********************************************/
	/**
	 * 刷新页面
	 * 会触发onLoading()方法
	 */
	public void refreshData(){
		if (!isFinishing()&&loadingPager!=null&&!isLoading()) {
			loadingPager.refreshData();
		}
	}

	/**
	 * 请求网络时调用:子线程中被调用
	 * @return 请求结果
	 */
	@Override
	public ResultStatus onLoading() {
		if (basePresenter != null) {
			return basePresenter.onLoading();
		} else {
			return ResultStatus.ERROR;
		}
	}

	/**
	 * 页面是否需要自动请求
	 * 用于在viewpager切换页面的时候自动刷新,
	 * 如果是没有请求过,或者是请求失败了,才自动请求,否则不请求
	 * @return
	 */
	public boolean isNeedAutoUpdate() {
		return loadingPager != null && loadingPager.isNeedAutoUpdate();
	}

	/**
	 * 是否正在请求网络
	 * @return
     */
	public boolean isLoading() {
		return loadingPager == null || loadingPager.isLoading();
	}

	@Override
	protected void onDestroy() {
		if (loadingPager!=null){
			loadingPager.onDestroy();
		}
		super.onDestroy();
	}


}
