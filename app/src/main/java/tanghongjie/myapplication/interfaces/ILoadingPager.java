package tanghongjie.myapplication.interfaces;

import android.view.View;

import tanghongjie.myapplication.httpManager.utils.ResultStatus;


/**
 * 创建时间: 2016/08/02 21:45
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:loadingPager加载结果状态
 * 修改时间:
 * 修改描述:
 */
public interface ILoadingPager {

    /**
     * 请求网络时调用:子线程中被调用
     * @return 请求结果
     */
    ResultStatus onLoading();

    /**
     * 加载失败,显示失败的页面
     */
    void onShowErrorPager();

    /**
     * 加载数据为空,显示的页面
     */
    void onShowEmptyPager();

    /**
     * 加载失败,但是不显示失败的界面,
     * 用于切换页面,刷新结果的监听
     */
    void onLoadErrorNoShowErrorPager();

    /**
     * 刷新页面成功时,刷新界面调用
     */
    void updateSuccessView();

    /**
     * 加载成功时调用的方法
     * @return 成功布局view
     */
    View createSuccessView();

}
