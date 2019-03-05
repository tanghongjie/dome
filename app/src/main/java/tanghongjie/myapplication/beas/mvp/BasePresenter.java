package tanghongjie.myapplication.beas.mvp;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tanghongjie.myapplication.beas.activity.BaseActivity;
import tanghongjie.myapplication.common.utils.UIUtils;
import tanghongjie.myapplication.constast.PermissionConstant;
import tanghongjie.myapplication.httpManager.utils.ResultStatus;

/**
 * 创建时间: 2017/05/01 22:21
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述: Presenter基类,所有的presenter必须继承该类
 * 修改时间:
 * 修改描述:
 */
public abstract  class BasePresenter<V extends BaseContract.IBaseView> implements BaseContract.IBasePresenter<V> {
    public final String TAG = "--"+getClass().getSimpleName()+"--";
    /**
     * 引用view对象
     */
    private WeakReference<V> mViewRef;
    /**
     * 保存V层传递过来的参数,用于网络请求
     */
    public Object params;

    /**
     * Presenter绑定view
     * @param view
     */
    @Override
    public void attachView(@NonNull V view) {
        mViewRef=new WeakReference<>(view);
    }

    /**
     * Presenter解除绑定view
     */
    @Override
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    /**
     * 获取view对象,用于刷新UI
     * @return
     */
    @Override
    public V getView() {
        return mViewRef == null ? null : mViewRef.get();
    }

    /**
     * @return 判断view不为空,在使用view前做非空判断
     */
    @Override
    public boolean isActive() {
        return getView()!=null;
    }

    /**
     * 网络请求结束
     */
    @Override
    public void onHttpComplete() {
        if (isActive()){
            getView().hideLoading();
        }
    }

    /**
     * 网络请求开始
     * @param tips 加载提示语
     */
    @Override
    public void onHttpStart(String tips) {
        if (isActive()){
            getView().showLoading(tips);
        }
    }

    /***
     * 网络请求开始
     * @param tips 提示语
     * @param isCanCancel 是否能通过返回按钮关闭加载框
     */
    @Override
    public void onHttpStart(String tips, boolean isCanCancel) {
        if (isActive()){
            getView().showLoading(tips,isCanCancel);
        }
    }

    /***
     *  显示toast
     * @param tips
     */
    @Override
    public void onShowToast(String tips) {
        if (isActive()){
            getView().showToast(tips);
        }
    }

    /***
     * 显示toast
     * @param tips
     * @param isShowLong
     */
    @Override
    public void onShowToast(String tips, boolean isShowLong) {
        if (isActive()){
            getView().showToast(tips,isShowLong);
        }
    }

    /**
     * V层调用设置参数到P层,设置网络请求的参数
     * 参数一般由上个页面传递过来
     * 在firstInit()方法中获取了参数后立即调用设置到presenter
     * @param params
     */
    @Override
    public void setParams(@NonNull Object params) {
        this.params=params;
    }

    /**
     * 进入页面请求接口获取数据,默认实现返回成功,根据需求重写此方法
     * 1.对于进入页面时需要请求数据的场景,需要重新此方法,获取后台数据
     *      重写此方法的前提是activity继承CommonBaseActivity
     *      或Fragment继承CommonBaseFragment
     * @return
     */
    @Override
    public ResultStatus onLoading(){
        return ResultStatus.SUCCESS;
    }

    /**
     * P下拉刷新完成后通知view层更新UI
     * 已经在主线程调用了
     */
    @Override
    public void onPullRefreshComplete() {
        if (isActive()){
            if (UIUtils.isRunMainThread()){
                if (isActive()){
                    getView().pullRefreshCompleteUI();
                }
            }else{
                UIUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isActive()){
                            getView().pullRefreshCompleteUI();
                        }
                    }
                });
            }
        }
    }

    /***
     * 检测应用是否有定位和存储权限
     * @param activity
     */
    @Override
    public void onCheckLocationAndStoragePermission(BaseActivity activity) {
        List<String> tempLists = new ArrayList<>();
        if (!activity.hasPermission(PermissionConstant.STORAGE)) {
            Collections.addAll(tempLists, PermissionConstant.STORAGE);
        }
        if (!activity.hasPermission(PermissionConstant.LOCATION)) {
            Collections.addAll(tempLists, PermissionConstant.LOCATION);
        }

        if (tempLists.isEmpty()) {
            //有了定位的权限了,跳转页面
          //  LogUtils.e("之前已经获取定位和存储的权限----跳转页面");
            if (isActive()){
                getView().hadLocationAndStoragePermission();
            }
        } else {
            //没有权限
            if (isActive()){
                getView().noLocationAndStoragePermission();
            }
        }
    }

    /***
     * 请求定位和存储权限
     * @param activity
     */
    @Override
    public void onRequestLocationAndStoragePermission(BaseActivity activity) {
        List<String> tempLists = new ArrayList<>();
        if (!activity.hasPermission(PermissionConstant.STORAGE)) {
            Collections.addAll(tempLists, PermissionConstant.STORAGE);
        }
        if (!activity.hasPermission(PermissionConstant.LOCATION)) {
            Collections.addAll(tempLists, PermissionConstant.LOCATION);
        }

        if (tempLists.isEmpty()){
            //已经有权限
            if (isActive()){
                getView().hadLocationAndStoragePermission();
            }
        }else{
            //请求权限
            activity.requestPermission(PermissionConstant.LOCATION_STORAGE_REQUEST_CODE,tempLists.toArray(new String[tempLists.size()]));
        }
    }

}
