package com.swordintent.chatgpt.client;

import com.swordintent.chatgpt.utils.JsonUtils;
import okhttp3.*;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * @author liuhe
 */
public class DataClient {

    private OkHttpClient httpClient;

    private final TokenInterceptor interceptor;


    public DataClient(TokenInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    public void init() {
//        OkHttpClient.Builder builder = null;
//        try {
//            builder = getDebugHttpClientBuilder();
//            httpClient = builder.build();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        OkHttpClient.Builder builder = getHttpClientBuilder();
        httpClient = builder.build();
    }

    private OkHttpClient.Builder getDebugHttpClientBuilder() throws Exception {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));
        final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain,
                                                   String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain,
                                                   String authType) throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };

        // Install the all-trusting trust manager
        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        // Create an ssl socket factory with our all-trusting manager
        final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .proxy(proxy)
                .addInterceptor(interceptor)
                .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
    }

    public <R> R getData(String url, Class<R> responseType) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(null, "{}"))
                .build();
        return makeRequest(request, responseType);
    }

    public <T, R> R getData(T requestData, String url, Class<R> responseType) throws Exception {
        String content = JsonUtils.toJson(requestData);
        RequestBody body = RequestBody.create(
                null, content);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return makeRequest(request, responseType);
    }

    private <R> R makeRequest(Request request, Class<R> responseType) throws IOException {
        Call call = httpClient.newCall(request);
        try (Response response = call.execute()) {
            ResponseBody body = response.body();
            if(body == null){
                return null;
            }
            String string = body.string();
            return JsonUtils.fromJson(string, responseType);
        }
    }

    private OkHttpClient.Builder getHttpClientBuilder() {
        return new OkHttpClient.Builder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .addInterceptor(interceptor);
    }

}
