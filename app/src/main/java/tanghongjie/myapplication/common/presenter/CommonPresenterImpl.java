package tanghongjie.myapplication.common.presenter;


import tanghongjie.myapplication.beas.mvp.BasePresenter;
import tanghongjie.myapplication.common.contract.CommonContract;

/**
 * 创建时间: 2017/08/20 14:47
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:通用的presenter,适用于逻辑简单的页面
 *          配合{@link CommonContract}和{@link BaseSimpleActivity} {@link BaseSimpleFragment}使用
 * 修改时间:
 * 修改描述:
 */
public class CommonPresenterImpl extends BasePresenter<CommonContract.ICommonView>
        implements CommonContract.ICommonPresenter {
        //当前presenter仅仅是一个空实现,多处公用,不要在此写逻辑代码.

}
