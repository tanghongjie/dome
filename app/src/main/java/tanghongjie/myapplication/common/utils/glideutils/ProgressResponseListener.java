package tanghongjie.myapplication.common.utils.glideutils;

/**
 * 创建时间: 2017/08/30 11:57
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:
 * 修改时间:okhttp下载进度监听
 * 修改描述:
 */
public interface ProgressResponseListener {
	/**
	 * @param bytesRead 已经下载的字节数
	 * @param contentLength 总共的字节数
	 * @param done 是否下载完成
	 */
	void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
