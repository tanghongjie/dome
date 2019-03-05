package tanghongjie.myapplication.httpManager.utils;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

/**
 * 创建时间:2018/06/28 16:12
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:
 * 修改时间:
 * 修改描述:
 */
public class HttpsUtils {
    //CRT文件
    private static final String KEY_CRT_CLIENT_PATH = "xxlvip.crt";

    private static SSLContext sslContext = null;
    private static boolean isServerTrusted = false;

    public static boolean isIsServerTrusted() {
        return isServerTrusted;
    }

    /***
     * 直接通过运维提供的 crt 证书自己写整个校验逻辑
     * @param context
     * @return
     */
    public static synchronized SSLContext getSslContextByCustomTrustManager(Context context) {
        if (sslContext == null) {
            try {
                CertificateFactory cf = CertificateFactory.getInstance("X.509","BC");
                InputStream caInput = new BufferedInputStream(context.getResources().getAssets()
                        .open(KEY_CRT_CLIENT_PATH));
                Certificate ca = null;
                try {
                    ca = cf.generateCertificate(caInput);
                } catch (Exception e) {
                   // LogUtils.e("getSslContextByCustomTrustManager", "cf.generateCertificate" + "(caInput)出现异常");
                    e.printStackTrace();
                } finally {
                    caInput.close();
                }

                final Certificate finalCa = ca;
                sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new X509TrustManager[]{new X509TrustManager() {

                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                    //    LogUtils.d("getSslContextByCustomTrustManager", "checkClientTrusted --> authType = " + authType);
                        //校验客户端证书
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
                                                   String authType) throws java.security.cert.CertificateException {
                     //   LogUtils.d("getSslContextByCustomTrustManager", "checkServerTrusted --> authType = " + authType);

                        if (isServerTrusted){
                            return;
                        }

                        //校验服务器证书
                        for (java.security.cert.X509Certificate cert : chain) {
                            cert.checkValidity();
                            try {
                                finalCa.verify(cert.getPublicKey());
                                isServerTrusted = true;
                                //LogUtils.d("getSslContextByCustomTrustManager", "证书校验成功了" );
                                break;
                            } catch (Exception e) {
                               // LogUtils.e("getSslContextByCustomTrustManager", "异常" );
                                e.printStackTrace();
                                isServerTrusted = false;
                            }
                        }
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[0];
                    }
                }}, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sslContext;
    }



}
