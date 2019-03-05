package tanghongjie.myapplication.httpManager;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tanghongjie.myapplication.beas.BaseApplication;
import tanghongjie.myapplication.common.utils.AppUtils;
import tanghongjie.myapplication.constast.Constant;
import tanghongjie.myapplication.httpManager.constant.HttpConstant;
import tanghongjie.myapplication.httpManager.utils.HttpsUtils;


/**
 * 创建时间: 2016/08/02 21:45
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述: okHttp网络请求管理 单例
 * 修改时间:
 * 修改描述:
 */
public class OkHttpClientManager {
    private static OkHttpClientManager mInstance = new OkHttpClientManager();
    private static OkHttpClient.Builder okHttpClientBuilder;
    private static OkHttpClient mOkHttpClient;

    private OkHttpClientManager() {
        okHttpClientBuilder = new OkHttpClient.Builder();
        mOkHttpClient=okHttpClientBuilder.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(new StethoInterceptor())
                .sslSocketFactory(HttpsUtils.getSslContextByCustomTrustManager(BaseApplication.getInstance()).getSocketFactory())
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                      //  LogUtils.d("OkHttpClientManager--Https--"+"hostname = " + hostname+"--isServerTrusted="+HttpsUtils.isIsServerTrusted());
                        return HttpsUtils.isIsServerTrusted();
                    }})
                .build();
    }

    public static OkHttpClientManager getInstance() {
        return mInstance;
    }

    public void cancelAll() {
        mOkHttpClient.dispatcher().cancelAll();
    }

    public void setConnectTimeout(long timeout, TimeUnit units) {
        mOkHttpClient = okHttpClientBuilder.connectTimeout(timeout, units).build();
    }

    public void setReadTimeout(long timeout, TimeUnit units) {
        mOkHttpClient = okHttpClientBuilder.readTimeout(timeout, units).build();
    }

    public void setWriteTimeout(long timeout, TimeUnit units) {
        mOkHttpClient = okHttpClientBuilder.writeTimeout(timeout, units).build();
    }

    /***
     * 拼接get请求参数
     * @param url
     * @param params
     * @return
     */
    private String attachHttpGetParams(String url, Map<String, Object> params) {
        Iterator<String> keys = params.keySet().iterator();
        Iterator<Object> values = params.values().iterator();

        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append("?");

        for (int i = 0; i < params.size(); i++) {
            String value = "";
            try {
                value = URLEncoder.encode(String.valueOf(values.next()), "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

            sb.append(keys.next()).append("=").append(value);
            if (i != params.size() - 1) {
                sb.append("&");
            }
        }

        return sb.toString();
    }

    /**
     * 异步get请求
     * @param url
     * @param requestMap 参数
     * @param callback
     */
    public void getAsync(String url, Map<String, Object> requestMap, Callback callback) {
        String requestUrl = attachHttpGetParams(url, requestMap);
        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .addHeader(HttpConstant.HTTP_AUTHORIZATION_KEY, AppUtils.getHttpAuthorizationValue())
                .addHeader(HttpConstant.HTTP_HEAD_KEY_APPID, HttpConstant.HTTP_APPID_VALUE)
                .build();

        mOkHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 同步get请求
     * @param url
     * @param requestMap 参数
     */
    public Response getSync(String url, Map<String, Object> requestMap) {
        try {
            String requestUrl = attachHttpGetParams(url, requestMap);
          //  LogUtils.d("同步getSync:" + requestUrl, true);
            Request request = new Request.Builder()
                    .url(requestUrl)
                    .get()
                    .addHeader(HttpConstant.HTTP_AUTHORIZATION_KEY, AppUtils.getHttpAuthorizationValue())
                    .addHeader(HttpConstant.HTTP_HEAD_KEY_APPID, HttpConstant.HTTP_APPID_VALUE)
                    .build();

            // 执行请求
            return mOkHttpClient.newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * get请求，已经将参数拼接到了url
     * @param url
     * @param callback
     */
    public void getAsync(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader(HttpConstant.HTTP_AUTHORIZATION_KEY, AppUtils.getHttpAuthorizationValue())
                .addHeader(HttpConstant.HTTP_HEAD_KEY_APPID, HttpConstant.HTTP_APPID_VALUE)
                .build();

        mOkHttpClient.newCall(request).enqueue(callback);
    }


    /**
     * 同步post,BaseProtocol使用
     * 根据后台约定的请求格式修改此方法
     * @param url
     * @return
     */
    public Response postSync(@NonNull String url, @NonNull Map<String, Object> requestMap) {
        try {
            if (Constant.IS_DEBUG){
                String json = JSON.toJSONString(requestMap);
              //  LogUtils.e("--postUrl:" + url);
              //  LogUtils.postJson(json);
            }

            RequestBody body = getBody(requestMap);
            // 创建请求
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("charset", "UTF-8")
                    .addHeader(HttpConstant.HTTP_AUTHORIZATION_KEY, AppUtils.getHttpAuthorizationValue())
                    .addHeader(HttpConstant.HTTP_HEAD_KEY_APPID, HttpConstant.HTTP_APPID_VALUE)
                    .build();
            // 执行请求
            return mOkHttpClient.newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * post异步请求
     *
     * @param url
     * @param body     请求实体
     * @param callback 回调接口
     */
    public void postAsync(String url, RequestBody body, Callback callback) {
        // 创建请求
        Request request = new Request.Builder().url(url).post(body)
                .addHeader(HttpConstant.HTTP_AUTHORIZATION_KEY, AppUtils.getHttpAuthorizationValue())
                .addHeader(HttpConstant.HTTP_HEAD_KEY_APPID, HttpConstant.HTTP_APPID_VALUE)
                .build();
        // 执行请求
        mOkHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * post异步请求
     *
     * @param url
     * @param requestMap
     * @param callback
     * @return
     */
    public void postAsync(@NonNull String url, @NonNull Map<String, Object> requestMap,
                          @NonNull Callback callback) {
        if (Constant.IS_DEBUG){
            String json = JSON.toJSONString(requestMap);
          //  LogUtils.e("--postUrl:" + url);
          //  LogUtils.postJson(json);
        }

        RequestBody body = getBody(requestMap);
        // 创建请求
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("charset", "UTF-8")
                .addHeader(HttpConstant.HTTP_AUTHORIZATION_KEY, AppUtils.getHttpAuthorizationValue())
                .addHeader(HttpConstant.HTTP_HEAD_KEY_APPID, HttpConstant.HTTP_APPID_VALUE)
                .build();
        //执行请求
        okhttp3.Call call = mOkHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public  OkHttpClient.Builder getOkHttpBuilder() {
        return okHttpClientBuilder;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * 转为请求body
     * @param map
     * @return
     */
    private static RequestBody getBody(@NonNull Map<String, Object> map) {
        FormBody.Builder params = new FormBody.Builder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            params.add(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return params.build();
    }

}
