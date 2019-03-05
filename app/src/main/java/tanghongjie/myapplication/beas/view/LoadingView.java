package tanghongjie.myapplication.beas.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import tanghongjie.myapplication.R;
import tanghongjie.myapplication.common.utils.UIUtils;
import tanghongjie.myapplication.constast.Constant;
import tanghongjie.myapplication.httpManager.ThreadPoolManager;
import tanghongjie.myapplication.httpManager.utils.ResultStatus;
import tanghongjie.myapplication.interfaces.ILoadingPager;


/**
 * 创建时间: 2017/08/30 11:57
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:自定义网络请求的加载页面
 * 修改时间:
 * 修改描述:
 */
public  class LoadingView extends FrameLayout implements OnClickListener {
	private static final String TAG="--LoadingView--";
	// 初始化的状态
	public static final int STATE_UNKNOW = 88;
	// 未加载的状态
	public static final int STATE_UNLOADING = 100;
	// 正在加载数据的状态
	public static final int STATE_LOADING = 101;
	// 加载错误的状态
	public static final int STATE_ERROR = 102;
	// 空的状态的状态
	public static final int STATE_EMPTY = 103;
	// 加载成功的状态
	public static final int STATE_SUCCESS = 104;

	// 默认的当前状态是没有加载
	private int currentState = STATE_UNLOADING;
	private View llError;
	private View llEmpty;
	private View loadingView;
	private View errorView;
	private View emptyView;
	private View successView;
	private TextView btnErrorAgain;
	private TextView btnEmptyAgain;
	//防止内存泄漏,需要使用软引用
	private WeakReference<ILoadingPager> listener;

	//是在代码创建视图的时候被调用
	public LoadingView(@NonNull Context context, @NonNull ILoadingPager listener) {
		super(UIUtils.getContext());
		this.listener=new WeakReference<>(listener);
		initView();
	}

	public LoadingView(@NonNull ILoadingPager listener) {
		super(UIUtils.getContext());
		this.listener=new WeakReference<>(listener);
		initView();
	}

	public ILoadingPager getListener(){
		return listener == null ? null : listener.get();
	}

	private void initView() {
		addStateView();
		showPager();
	}

	// 2. 添加视图成功后就显示界面
	private void showPager() {
		//1.加载中的状态
		if (currentState == STATE_LOADING || currentState == STATE_UNLOADING) {
			loadingView.setVisibility(View.VISIBLE);
		} else {
			loadingView.setVisibility(View.GONE);
		}

		//2.空页面
		if (currentState == STATE_EMPTY) {
			emptyView.setVisibility(View.VISIBLE);
			if (getListener() != null) {
				getListener().onShowEmptyPager();
			}
		} else {
			emptyView.setVisibility(View.GONE);
		}
		
		//3.错误页面
		if (currentState == STATE_ERROR) {
			//页面没有加载成功过,加载失败直接显示失败的页面
			if (successView == null) {
				errorView.setVisibility(View.VISIBLE);
				if (getListener() != null) {
					getListener().onShowErrorPager();
				}
			} else {
				//页面之前加载成功过,再次刷新的时候请求失败了,此时不显示错误的页面,
				errorView.setVisibility(View.GONE);
				if (getListener() != null) {
					getListener().onLoadErrorNoShowErrorPager();//加载失败,不显示失败的页面
				}
			}
		} else {
			errorView.setVisibility(View.GONE);
		}

		//4.添加请求成功的界面
		if (successView == null && currentState == STATE_SUCCESS&&getListener()!=null) {
			//第一次请求成功
			//创建成功的界面:具体的界面由子类去实现
			successView = getListener().createSuccessView();
			if (successView != null) {
				addView(successView);
				successView.setVisibility(View.VISIBLE);
			}
		} else if (successView != null && currentState == STATE_SUCCESS&&getListener()!=null) {
			// 刷新成功了
			successView.setVisibility(View.VISIBLE);
			getListener().updateSuccessView();
		} else {
			//之前已经加载成功过,再次加载的时候失败了或者为空
			//如果为空隐藏成功的页面,如果失败了,不处理
			if (currentState == STATE_EMPTY) {
				if (successView != null) {
					successView.setVisibility(View.GONE);
				}
			}
		}
	}

	private void addStateView() {
		// 添加请求网络的页面
		addLoadingView();
		// 添加错误的页面
		addErrorView();
		// 添加请求为空的界面
		addEmptyView();
	}

	/**
	 * 刷新页面
	 */
	public void refreshData() {
		if (currentState == STATE_EMPTY || currentState == STATE_ERROR) {
			loadingView.setVisibility(View.VISIBLE);
			emptyView.setVisibility(View.GONE);
			errorView.setVisibility(View.GONE);
		}

		// 在请求网络之前初始化页面状态
		if (currentState == STATE_EMPTY
				|| currentState == STATE_ERROR
				|| currentState == STATE_SUCCESS) {
			currentState = STATE_UNLOADING;
		}

		// 还有一种状态是,页面正在加载网络,此时就不能再次请求
		// 只有是无状态的情况下才去加载网络
		if (currentState == STATE_UNLOADING) {
			ThreadPoolManager.getProxy().execute(new Runnable() {
				@Override
				public void run() {
					if (Constant.IS_DEBUG){
					//	LogUtils.d(TAG+"load当前线程：" + Thread.currentThread().getName()+ Thread.currentThread().getId());
					}

					ResultStatus result;

					if (getListener()!=null){
						//修改状态为正在加载
						currentState= STATE_LOADING;

						try{
							result = getListener().onLoading();
						}catch (Exception e){
							e.printStackTrace();
							result= ResultStatus.ERROR;
						}

						final ResultStatus finalResult = result;

						UIUtils.runOnMainThread(new Runnable() {
							@Override
							public void run() {
								if (finalResult != null) {
									currentState = finalResult.getState();
								}else{
									currentState=STATE_ERROR;
								}
								showPager();
							}
						});
					}
				}
			});
		}
	}

	/**
	 * 页面关闭时调用
	 */
	public void onDestroy() {
		if (listener != null) {
			listener.clear();
			listener = null;
		}
	}

	/**
	 * 默认为空的页面点击刷新
	 */
	private void addEmptyView() {
		emptyView = UIUtils.inflate(R.layout.layout_common_empty);
		btnEmptyAgain = (TextView) emptyView.findViewById(R.id.bt_empty_again);
		llEmpty = emptyView.findViewById(R.id.ll_empty);
		llEmpty.setOnClickListener(this);
		addView(emptyView);
	}

	// 设置空页面按钮的文字
	public void setEmptyText(String emptyText) {
		if (btnEmptyAgain != null) {
			btnEmptyAgain.setText(emptyText);
		}
	}
	
	public void setEmptyClickEnable(boolean isEnable) {
		if (llEmpty != null) {
			llEmpty.setEnabled(isEnable);
		}
	}

	// 设置加载错误页面按钮的文字提示
	public void setErrorText(String emptyText) {
		if (btnErrorAgain != null) {
			btnErrorAgain.setText(emptyText);
		}
	}

	private void addErrorView() {
		errorView = UIUtils.inflate(R.layout.layout_common_error);
		llError = errorView.findViewById(R.id.root_loading_error);
		btnErrorAgain = (TextView) errorView.findViewById(R.id.bt_again);
		llError.setOnClickListener(this);
		addView(errorView);
	}

	private void addLoadingView() {
		loadingView = UIUtils.inflate(R.layout.layout_common_loading);
		addView(loadingView);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.root_loading_error:
			refreshData();
			//LogUtils.d(TAG+"error--重新请求网络加载");
			break;
			
		case R.id.ll_empty:
			refreshData();
			//LogUtils.d(TAG+"empty--重新请求网络加载");
			break;

		default:
			break;
		}
	}

	public View getEmptyView() {
		return emptyView;
	}

	public View getLoadingView() {
		return loadingView;
	}

	public int getResultState(){
		return  currentState;
	}

	//是否正在加载
	public boolean isLoading(){
        return currentState == STATE_LOADING;
	}

	/**
	 * 添加自定义加载页面
	 * @param customView
     */
	public void setCustomLoadingUI(View customView) {
		if (loadingView!=null&&customView!=null){
			ViewGroup rootLoadingLoading= (ViewGroup) loadingView.findViewById(R.id.root_loading_loading);
			if (rootLoadingLoading!=null){
				rootLoadingLoading.removeAllViews();
				customView.setLayoutParams(new ViewGroup.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.MATCH_PARENT));

				rootLoadingLoading.addView(customView);
			}
		}
	}

	/**
	 * 添加自定义为空的页面并设置点击事件
	 * @param customEmptyView
	 * @param isCanClickRetry
     */
	public void setCustomEmptyUI(View customEmptyView, boolean isCanClickRetry) {

		if (emptyView!=null&&customEmptyView!=null){
			ViewGroup rootLoadingEmpty= (ViewGroup) emptyView.findViewById(R.id.root_loading_empty);
			rootLoadingEmpty.removeAllViews();

			customEmptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup
					.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

			rootLoadingEmpty.addView(customEmptyView);
			if (isCanClickRetry){
				customEmptyView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						refreshData();
					}
				});
			}
		}
	}

	/**
	 * 设置自定义加载失败的页面
	 * @param customView
     */
	public void setCustomErrorUI(View customView) {
		if (errorView!=null&&customView!=null){
			ViewGroup rootLoadingEmpty= (ViewGroup) errorView.findViewById(R.id.root_loading_error);
			rootLoadingEmpty.removeAllViews();

			customView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup
					.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

			rootLoadingEmpty.addView(customView);
		}
	}

	/**
	 * 页面是否需要自动请求
	 * 用于在viewpager切换页面的时候自动刷新,
	 * 如果是没有请求过,或者是请求失败了,才自动请求,否则不请求
	 * @return
	 */
	public boolean isNeedAutoUpdate() {
		int state=getResultState();
        //正在加载或者加载成功不自动请求
        return state == STATE_EMPTY
                || state == STATE_ERROR
                || state == STATE_UNLOADING
                || state == STATE_UNKNOW;
	}

}
