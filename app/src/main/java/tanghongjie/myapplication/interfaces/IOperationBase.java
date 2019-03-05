package tanghongjie.myapplication.interfaces;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 创建时间: 2016/08/02 21:45
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:封装ui相关的操作:主要用于BaseActivity,BaseFragment
 * 修改时间:
 * 修改描述:
 */
public interface IOperationBase extends OnClickListener {
    /**
     * 获取布局id
     * @return 布局id
     */
    @LayoutRes
    int getLayoutRes();

    /**
     * 创建presenter
     */
    void initPresenter();

    /**
     * 用于页面创建的时候对一些sdk初始化,获取上个页面传递的参数
     */
    void firstInit();

    /**
     * 用于findViewById
     * @param successView 根布局
     */
    void initView(View successView);

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始监听器
     */
    void initListener();

    /**
     * 控件点击事件
     * @param v 点击控件
     * @param id 点击控件id
     */
    void onClick(View v, int id);
}
