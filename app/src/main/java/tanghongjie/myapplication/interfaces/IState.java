package tanghongjie.myapplication.interfaces;

/**
 * 创建时间: 2016/08/17 23:05
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:网络请求的状态
 * 修改时间:
 * 修改描述:
 */
public interface IState {
    /**
     * 网络请求开始
     * @param tips 加载提示语
     */
    void onHttpStart(String tips);

    /***
     * 网络请求开始
     * @param tips 提示语
     * @param isCanCancel 是否能通过返回按钮关闭加载框
     */
    void onHttpStart(String tips, boolean isCanCancel);

    /**
     * 网络请求结束
     */
    void onHttpComplete();
}
