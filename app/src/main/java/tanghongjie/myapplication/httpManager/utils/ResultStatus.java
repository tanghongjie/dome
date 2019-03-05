package tanghongjie.myapplication.httpManager.utils;


import tanghongjie.myapplication.beas.view.LoadingView;

/**
 * 创建时间: 2016/08/02 21:45
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:请求网络返回的状态,根据返回状态显示对应的UI界面`
 * 修改时间:
 * 修改描述:
 */
public enum ResultStatus {
	/***
	 * 102 请求失败响应码
	 */
	ERROR(LoadingView.STATE_ERROR),

	/***
	 *  103 请求为空响应码
	 */
	EMPTY(LoadingView.STATE_EMPTY),

	/***
	 * 104 请求成功
	 */
	SUCCESS(LoadingView.STATE_SUCCESS);

	private int state;

	ResultStatus(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}

}