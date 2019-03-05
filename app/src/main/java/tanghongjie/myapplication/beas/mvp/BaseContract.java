package tanghongjie.myapplication.beas.mvp;

import android.support.annotation.NonNull;

import tanghongjie.myapplication.beas.activity.BaseActivity;
import tanghongjie.myapplication.httpManager.OkHttpClientManager;
import tanghongjie.myapplication.httpManager.utils.ResultStatus;
import tanghongjie.myapplication.interfaces.IState;


/**
 * 创建时间: 2017/08/30 22:40
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:mvp顶层契约接口
 * 修改时间:
 * 修改描述:
 */
public interface BaseContract {

    interface IBaseModel {
        /**
         * okHttp的请求的单例对象
         */
        OkHttpClientManager HTTP_CLIENT = OkHttpClientManager.getInstance();

        /**
         * 发起请求
         */
        void post();
    }

    interface IBaseView {
        /**
         * 显示加载进度条
         * @param tips 提示语
         */
        void showLoading(String tips);

        /***
         * 显示加载框
         * @param tips 提示语
         * @param isCanCancel 是否能通过返回按钮关闭加载框
         */
        void showLoading(String tips, boolean isCanCancel);

        /**
         * 隐藏加载对话框
         */
        void hideLoading();

        /**
         * 显示toast
         * @param tips 提示
         */
        void showToast(String tips);

        /***
         * 显示toast
         * @param tips 提示
         * @param isShowLong  是否显示长时间toast
         */
        void showToast(String tips, boolean isShowLong);

        /**
         * 用于下拉结果的监听,处理UI相关的逻辑:
         * {@link BasePresenter#onPullRefreshComplete()}时触发回调
         */
        void pullRefreshCompleteUI();

        /***
         * 检测有定位和存储的权限
         */
        void hadLocationAndStoragePermission();

        /***
         * 检测没有定位和存储的权限
         */
        void noLocationAndStoragePermission();

    }

    interface IBasePresenter<V extends IBaseView> extends IState {
        /**
         * Presenter绑定view
         * @param view
         */
        void attachView(@NonNull V view);

        /**
         * Presenter解除绑定view
         */
        void detachView();

        /**
         * 获取view对象,用于刷新UI
         * @return
         */
        V getView();

        /**
         * 判断view不为空,在使用view引用前必须非空判断
         * @return 页面是否激活状态
         */
        boolean isActive();

        /**
         * 上一个页面传递过来的参数:view层中设置参数
         * @param params
         */
        void setParams(Object params);

        /**
         * Presenter网络请求获取数据
         * @return 请求结果状态
         */
        ResultStatus onLoading();

        /**
         * 一般用于刷新完成后在{@link BasePresenter#onLoading()}中被调用
         * 会触发{@link IBaseView#pullRefreshCompleteUI()}方法
         */
        void onPullRefreshComplete();

        /**
         * 检测应用是否有定位和存储权限
         * @param activity
         */
        void onCheckLocationAndStoragePermission(BaseActivity activity);

        /***
         * 请求定位和存储权限
         * @param activity
         */
        void onRequestLocationAndStoragePermission(BaseActivity activity);

        /***
         *  显示toast
         * @param tips
         */
        void onShowToast(String tips);

        /***
         * 显示toast
         * @param tips
         * @param isShowLong
         */
        void onShowToast(String tips, boolean isShowLong);
    }

}
