package tanghongjie.myapplication.common.utils.glideutils;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 创建时间: 2017/08/30 11:57
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:下载进度拦截器
 * 修改时间:
 * 修改描述:
 */
public class ProgressInterceptor implements Interceptor {

    private ProgressResponseListener progressListener;

    public ProgressInterceptor(ProgressResponseListener progressListener) {
        this.progressListener = progressListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                .build();
    }



}
