package tanghongjie.myapplication.common.utils.glideutils;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tanghongjie.myapplication.httpManager.OkHttpClientManager;

/**
 * 创建时间: 2017/08/30 11:57
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:获取下载进度
 * 修改时间:
 * 修改描述:
 */
public class ProgressDataFetcher implements DataFetcher<InputStream> {
    private ProgressResponseListener progressListener;
    private String url;
    private Call progressCall;
    private InputStream stream;
    private boolean isCancelled;

    public ProgressDataFetcher(String url, ProgressResponseListener progressListener) {
        this.url = url;
        this.progressListener = progressListener;
    }

    @Override
    public InputStream loadData(Priority priority) {
        Request request = new Request.Builder().url(url).addHeader("Accept-Encoding", "identity").build();
        // 创建客户端
        OkHttpClient client = OkHttpClientManager.getInstance().getOkHttpBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder()
                        .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                        .build();
            }
        }).build();

        try {
            progressCall = client.newCall(request);
            Response response = progressCall.execute();
            if (isCancelled) {
                return null;
            }
            if (!response.isSuccessful()){
                throw new IOException("Unexpected code " + response);
            }
            stream = response.body().byteStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return stream;
    }

    @Override
    public void cleanup() {
        if (stream != null) {
            try {
                stream.close();
                stream = null;
            } catch (IOException e) {
                stream = null;
            }
        }
        if (progressCall != null) {
            progressCall.cancel();
        }
    }

    @Override
    public String getId() {
        return url;
    }

    @Override
    public void cancel() {
        isCancelled = true;
    }
}
