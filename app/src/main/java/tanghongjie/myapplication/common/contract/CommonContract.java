package tanghongjie.myapplication.common.contract;




import tanghongjie.myapplication.beas.mvp.BaseContract;

/**
 * 创建时间: 2017/08/20 20:04
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:通用的contract,供全局使用
 *         针对一些逻辑简单的页面,直接使用CommonContract,业务代码直接写在View层里面
 *         避免创建过多的类
 *         配合{@link CommonPresenterImpl}和{@link BaseSimpleActivity} {@link BaseSimpleFragment}使用
 * 修改时间:
 * 修改描述:
 */
public interface CommonContract {
    interface ICommonView extends BaseContract.IBaseView {
        //多处公用类,不做任何修改
    }

    interface ICommonPresenter {
        //多处公用类,不做任何修改
    }

}
