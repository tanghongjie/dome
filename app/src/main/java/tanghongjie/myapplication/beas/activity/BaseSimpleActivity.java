package tanghongjie.myapplication.beas.activity;

import android.support.annotation.NonNull;

import tanghongjie.myapplication.common.contract.CommonContract;
import tanghongjie.myapplication.common.presenter.CommonPresenterImpl;
import tanghongjie.myapplication.httpManager.utils.ResultStatus;


/**
 * 创建时间:2018/03/08 17:49
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:activity基类,对mvp相关的类进行了默认的实现
 *         对于一些逻辑简单的页面可以直接继承该类,减少不必要的代码
 *         使用默认的mvp实现类,减少类的个数,页面相关的逻辑直接写在activity里面
 *         类似mvc模式
 * 修改时间:
 * 修改描述:
 */
public abstract class BaseSimpleActivity extends BaseCommonActivity<CommonContract.ICommonView,
        CommonPresenterImpl> implements CommonContract.ICommonView{

    /**
     * 使用通用的CommonPresenterImpl
     * @return
     */
    @NonNull
    @Override
    protected CommonPresenterImpl createPresenter() {
        return new CommonPresenterImpl();
    }

    /**
     * 子类如果在进入页面时需要请求网络时,可以直接重写该方法
     * 在方法中进行网络请求,子线程中调用,网络请求配合{@link BaseSyncPostHttpProtocol}子类使用
     * @return
     */
    @Override
    public ResultStatus onLoading() {
        return super.onLoading();
    }


}
