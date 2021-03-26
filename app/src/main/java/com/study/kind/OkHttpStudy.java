package com.study.kind;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.TimeUtils;

import com.badlogic.utils.ALog;
import com.example.wujiawen.a_Main.R;
import com.study.z_glide.proress_down.ProgressInterceptor;
import com.study.z_glide.proress_down.ProgressListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.internal.connection.ConnectInterceptor;
import okhttp3.internal.http.BridgeInterceptor;
import okhttp3.internal.http.CallServerInterceptor;
import okhttp3.internal.http.RealInterceptorChain;

import static com.study.z_Reflection.ReflectionTest.TAG;



public class OkHttpStudy extends Activity {

    /*
    9、okhttp实现带进度上传下载
    OkHttp把请求和响应分别封装成了RequestBody和ResponseBody，下载进度的实现可以自定义ResponseBody，
    重写source()方法，上传进度自定义RequestBody，重写writeTo()方法。
    下载 https://www.jianshu.com/p/df7d4945f007
    上传 https://blog.csdn.net/u011247387/article/details/83027254

    为什么response.body().string() 只能调用一次
    我们可能习惯在获取到Response对象后，先response.body().string()打印一遍log，
    再进行数据解析，却发现第二次直接抛异常，其实直接跟源码进去看就发现，通过source拿到字
    节流以后，直接给closeQuietly悄悄关闭了，这样第二次再去通过source读取就直接流已关闭的异常了。
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /**
         * 下载文件和更新进度条
         */
        (new DownloadUtil()).download("http://publicobject.com/helloworld.txt", Environment.getExternalStorageDirectory()+"sss.txt",
                new DownloadUtil.OnDownloadListener(){
            @Override
            public void onDownloadSuccess() {

            }

            @Override
            public void onDownloading(int progress) {

            }

            @Override
            public void onDownloadFailed() {

            }
        });
        ///----------
        ProgressInterceptor.addListener("http://publicobject.com/helloworld.txt", new ProgressListener() {
            @Override
            public void onProgress(int progress) {
                ALog.d(TAG, "OkHttpStudy-onCreate-onProgress: " + progress);
            }
        });

    }

    /*Response getResponseWithInterceptorChain() throws IOException {
        // Build a full stack of interceptors.
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.addAll(getInstance() .interceptors());
        interceptors.add(retryAndFollowUpInterceptor);
        interceptors.add(new BridgeInterceptor(getInstance() .cookieJar()));
        interceptors.add(new CacheInterceptor(getInstance().internalCache()));
        interceptors.add(new ConnectInterceptor(getInstance() ));
        boolean forWebSocket = false;
        if (!forWebSocket) {
            interceptors.addAll(getInstance() .networkInterceptors());
        }
        interceptors.add(new CallServerInterceptor(forWebSocket));

        Interceptor.Chain chain = new RealInterceptorChain(
                interceptors,
                null,
                null,
                null,
                0,
                originalRequest, this, eventListener, client.connectTimeoutMillis(),
                getInstance().readTimeoutMillis(), getInstance().writeTimeoutMillis());

        return chain.proceed(originalRequest);
    }*/

    void test_with(){
        Request request = new Request.Builder()
                .cacheControl(new CacheControl.Builder().noCache().build())
                .url("http://publicobject.com/helloworld.txt")
                .build();

    }

    public static void getVpnInfo(okhttp3.Callback callback) {
        FormBody.Builder formBody = getCommonPostParameters();
        RequestBody requestBody = formBody.build();
        String url = "http://publicobject.com/helloworld.txt";
        Request.Builder builder = new Request.Builder()
                .post(requestBody)
                .url(url);
        builder = setCommonHeaders(builder);
        Request request = builder.build();
        //通过client发起请求
        getInstance().newCall(request).enqueue(callback);
        try {
            Response hResponse = getInstance().newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** 公共参数 */
    public static FormBody.Builder getCommonPostParameters(){

        Map<String,String> map = createSign();
        FormBody.Builder formBody = new FormBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            formBody.add(entry.getKey(), entry.getValue());
        }
        return formBody;
    }

    private static Map createSign(){
        Map<String,String> map = new HashMap();
        //参与生成签名的参数
        map.put("device","1");
        map.put("format","JSON");
        map.put("sign_method", "SHA256");
        map.put("sign_version", "1.0.0");
        map.put("signnonce", System.currentTimeMillis()+""+ (int)Math.random()*10000);
        map.put("version", "20200316");
        ///---
        map.put("sign", "sign");
        //非参与生成签名的参数
        map.put("timezone", TimeZone.getDefault().getID());
        return map;
    }

    public static Request.Builder setCommonHeaders(Request.Builder requestBody){
        if(requestBody == null)return null;
        requestBody.addHeader("Cookie", "ACCESS_TOKEN=");
        return requestBody;
    }

    ////////======================================================================

    /**
     * 上传文件
     * @param url
     * @param filePath
     * @param fileName
     * @return
     * @throws Exception
     */
    static ResponseBody upload(String url, String filePath, String fileName) throws Exception {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName,
                        RequestBody.create(MediaType.parse("multipart/form-data"), new File(filePath)))
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + UUID.randomUUID())
                .url(url)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        return response.body();
    }


    static void main(String[] args) throws IOException {
        try {
            String fileName = "com.jdsoft.biz.test.zip";
            String filePath = "D:\\ExtJsTools\\Sencha\\Cmd\\repo\\pkgs\\test.zip";
            String url = "http://localhost:9990/upload_app_package";
            System.out.println(upload(url, filePath, fileName).string());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ////////======================================================================

    private static OkHttpClient okHttpClient;
    private static OkHttpClient okHttpNoCookieClient;

    private static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            synchronized (OkHttpStudy.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient.Builder()
                            .hostnameVerifier(new HostnameVerifier() {
                                @Override
                                public boolean verify(String hostname, SSLSession session) {
                                    return true;
                                }
                            })
                            .cookieJar(CookieShareManager.getInstance())
                            .connectTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return okHttpClient;
    }

}
