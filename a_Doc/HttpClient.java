package com.yunbi.net;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.yunbi.ui.manage.BaseApplication;
import com.yunbi.utils.ALog;
import com.yunbi.utils.BaseUserInfor;
import com.yunbi.utils.Constants;
import com.yunbi.utils.DeviceUtils;
import com.yunbi.utils.Md5Utils;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wujiawen on 2017/12/11.
 */

public class HttpClient {

    private static final String TAG = "wjw02";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final long READ_TIMEOUT = 30;
    public static final long CONNECT_TIMEOUT = 15;
    public static final long WRITE_TIMEOUT = 30;

    public static final int HTTP_BACK_DEAL_SUCCESSFUL = 288;// code >= 200 && code < 300;
    public static final int HTTP_BACK_DEAL_EXCEPTION = -900002;
    public static final int HTTP_BACK_DEAL_UnknownHostException = -900003;
    public static final int HTTP_BACK_DEAL_SocketTimeoutException = -900004;


    public static HashMap getUrlParams() {
        String plat= Constants.plat;
        String ver= Constants.ver;
        String devid= DeviceUtils.getIMEI2(BaseApplication.getApplication());
        HashMap<String, String> urlMap = new HashMap<String, String>();
        urlMap.put("r",System.currentTimeMillis()+"");
        urlMap.put("plat",plat);
        urlMap.put("ver",ver);
        urlMap.put("devid",devid);
        return urlMap;
    }

    public static String getSign_2(HashMap<String, String> urlMap,HashMap<String, String> bodyMap) {
        StringBuilder tempParams = new StringBuilder();
        if(urlMap!=null){
            for (String key : urlMap.keySet()) {
                tempParams.append(String.format("%s=%s", key,urlMap.get(key)));
            }
        }
        if(bodyMap!=null){
            for (String key : bodyMap.keySet()) {
                tempParams.append(String.format("%s=%s", key,bodyMap.get(key)));
            }
        }
        tempParams.append(Constants.secret_key_user);
        return DeviceUtils.SHA512(tempParams.toString());
    }

    public static String getSign(HashMap<String, String> urlMap,HashMap<String, String> bodyMap) {
        List<String> keys = new ArrayList<String>();
        if (urlMap != null) {
            for (String key : urlMap.keySet()) {
                keys.add(key);
            }
        }
        if (bodyMap != null) {
            for (String key : bodyMap.keySet()) {
                keys.add(key);
            }
        }

        //???key??????
        Collections.sort(keys);

        StringBuilder sb = new StringBuilder();
        for(String key : keys) {
            if (urlMap != null && urlMap.containsKey(key)) {
                sb.append(key).append('=').append(urlMap.get(key));
            }else if (bodyMap != null && bodyMap.containsKey(key)) {
                sb.append(key).append('=').append(bodyMap.get(key));
            }
        }
        sb.append(Constants.secret_key_user);
        ALog.i("getSign", sb.toString());
        return DeviceUtils.SHA1(sb.toString());
    }

    public static void requestPostSyncCutUrl(String url_content, HashMap<String, String> urlMap, HashMap<String, String> bodyMap, HashMap<String, String> headerMap, @NonNull HttpCallback mHttpCallback) {
        requestPostSyncCutUrl(url_content,urlMap,bodyMap,headerMap,false,mHttpCallback);
    }

    public static void requestPostSyncCutUrl(final String url_content, final HashMap<String, String> urlMap, final HashMap<String, String> bodyMap,
                                             final HashMap<String, String> headerMap, final boolean saveCookie, final @NonNull HttpCallback mHttpCallback){
        String url_all=Constants.url_prefix+url_content;
        requestPostSync(url_all,urlMap,bodyMap,headerMap,saveCookie,mHttpCallback);
    }

    /**
     *
     * @param url ????????????URL,????????????
     */
    public static void requestPostSync(String url, HashMap<String, String> urlMap, HashMap<String, String> bodyMap, HashMap<String, String> headerMap, @NonNull HttpCallback mHttpCallback) {
        requestPostSync(url,urlMap,bodyMap,headerMap,false,mHttpCallback);
    }

    static ExecutorService executorService = Executors.newFixedThreadPool(30);

    public static void requestPostSync(final String url,final HashMap<String, String> urlMap,final HashMap<String, String> bodyMap,
                                       final HashMap<String, String> headerMap,final boolean saveCookie,final @NonNull HttpCallback mHttpCallback) {
        Runnable mRunnable=new Runnable() {
            @Override
            public void run() {
                requestPostSyncDo(url,urlMap,bodyMap,headerMap,saveCookie,mHttpCallback);
            }
        };
        //---new Thread(mRunnable).start();
        mHttpCallback.mFuture =executorService.submit(mRunnable);
    }

    public static void requestPostSyncDo(final String url,HashMap<String, String> urlMap,HashMap<String, String> bodyMap,HashMap<String, String> headerMap,boolean saveCookie,@NonNull HttpCallback mHttpCallback) {
        if(mHttpCallback==null){
            return;
        }
        StringBuilder url_all = new StringBuilder();
        url_all.append(url);
        if(urlMap!=null){
            if(urlMap.size()>0){
                url_all.append("?");
            }
            int pos = 0;
            for (String key : urlMap.keySet()) {
                if (pos > 0) {
                    url_all.append("&");
                }
                url_all.append(String.format("%s=%s", key,urlMap.get(key)));
                pos++;
            }
        }
        //------------
        RequestBody request_body=null;
        if(bodyMap!=null){
            FormBody.Builder form_builder = new FormBody.Builder();
            for (String key : bodyMap.keySet()) {
                ALog.i(ALog.Tag2,"HttpClient--requestPostSyncCutUrl-Body--key-->"+key+"-value->"+bodyMap.get(key));
                form_builder.add(key,bodyMap.get(key));
            }
            if(bodyMap.size()>0){
                request_body = form_builder.build();
            }
        }
        //---------------------
        OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();
        clientBuilder.readTimeout(HttpClient.READ_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.connectTimeout(HttpClient.CONNECT_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(HttpClient.WRITE_TIMEOUT, TimeUnit.SECONDS);
        OkHttpClient client = clientBuilder.build();
        //----
        Request.Builder mBuilder=new Request.Builder();
        ALog.i(ALog.Tag2,"HttpClient--requestPostSyncCutUrl--url_all.toString()-->"+url_all.toString());
        mBuilder.url(url_all.toString());
        if(request_body!=null){
            mBuilder.post(request_body);
        }
        if(headerMap!=null){//????????????????????????cookie
           /* String cookieStr="";
            if(mHttpCallback.cookie_mark){
                cookieStr=mHttpCallback.getCookie();
            }else{
                cookieStr=BaseUserInfor.getTokenAll();
            }*/
            if(headerMap.containsKey("cookie")){
            }else{
                headerMap.put("cookie",BaseUserInfor.getTokenAll());
            }
            //-----------
            for (String key : headerMap.keySet()) {
                ALog.i(ALog.Tag2,"HttpClient--requestPostSyncCutUrl-header--key-->"+key+"-value->"+headerMap.get(key));
                mBuilder.addHeader(key,headerMap.get(key));
                /*if(key.equals("cookie")){
                    ALog.i(ALog.Tag2,"HttpClient--requestPostSyncCutUrl-header--key-->"+key+"-value->"+UserInfor.getTokenAll());
                    mBuilder.addHeader(key,UserInfor.getTokenAll());
                }else{
                    ALog.i(ALog.Tag2,"HttpClient--requestPostSyncCutUrl-header--key-->"+key+"-value->"+headerMap.get(key));
                    mBuilder.addHeader(key,headerMap.get(key));
                }*/
            }
        }
        //-------------------
        Request request = mBuilder.build();
        try {
            Response response = client.newCall(request).execute();
            if(saveCookie){
                String cookie_here=response.header("cookie");
                BaseUserInfor.cookie_here=cookie_here;
                ALog.i(ALog.Tag2,"HttpClient--requestPostSyncCutUrl-cookie_here-->"+cookie_here);
            }

            if(response.isSuccessful()){
                String result_str=response.body().string();
                ALog.i(ALog.Tag2,"HttpClient--requestPostSyncCutUrl--result_str-->"+result_str);
                if(mHttpCallback.backTotalDataMark){
                    dealSuccess(mHttpCallback,result_str,true);
                    return;
                }
                 /*{
                    errno: 0,
                    errmsg: "",
                    data: {
                        token: 'adsfadsfasdfadsfada.11.1512627046',
                    }
                }*/
                JSONObject mJSONObject=new JSONObject(result_str);
                int errno=mJSONObject.getInt("errno");
                if(errno==0){
                        dealSuccess(mHttpCallback,mJSONObject.getString("data"),true);
                    }else{
                        ALog.i(ALog.Tag2,"HttpClient--requestPostSyncDo--error-->"+mJSONObject.getString("errmsg"));
                        //--String error_str="???????????????";
                        String error_str=mJSONObject.getString("errmsg");
                    dealError(mHttpCallback,errno,error_str,true);
                }
            }else{
                dealError(mHttpCallback,response.code(),"???????????????????????????",true);
                ALog.i(ALog.Tag2,"HttpClient--requestPostSyncCutUrl--???????????????????????????--response.code()-->"+response.code());
            }
        } catch (Exception e) {
            String error_str="";
            int exceptionCode=HttpClient.HTTP_BACK_DEAL_EXCEPTION;
            if(e instanceof SocketTimeoutException){
                exceptionCode=HTTP_BACK_DEAL_SocketTimeoutException;
                error_str="????????????";
            }else if(e instanceof UnknownHostException){
                exceptionCode=HttpClient.HTTP_BACK_DEAL_UnknownHostException;
                //--error_str="??????????????????";
                error_str="????????????";
            }else{
                error_str="????????????";
            }
            dealError(mHttpCallback,exceptionCode,error_str,true);
            ALog.i(ALog.Tag2,"HttpClient--requestPostSyncCutUrl--Exception-->"+e);
            e.printStackTrace();
        }
    }

    public static void dealSuccess(@NonNull final HttpCallback mHttpCallback ,final Object data,boolean backMianThreadMark ) {
        if(mHttpCallback==null){
            return;
        }
        if(mHttpCallback.getAbandon()){
            return;
        }
        if(backMianThreadMark){
            BaseApplication.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    try {
                        if(mHttpCallback.getAbandon()){
                            return;
                        }
                        mHttpCallback.onSuccess(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }else{
            try {
                mHttpCallback.onSuccess(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void dealError(@NonNull final HttpCallback mHttpCallback ,final int errno,final String errmsg,boolean backMianThreadMark ) {
        if(mHttpCallback==null){
            return;
        }
        if(mHttpCallback.getAbandon()){
            return;
        }
        if(backMianThreadMark){
            BaseApplication.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    try {
                        if(mHttpCallback.getAbandon()){
                            return;
                        }
                        mHttpCallback.onError(errno,errmsg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }else{
            try {
                mHttpCallback.onError(errno,errmsg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private <T> Call postSync(String actionUrl, HashMap<String, String> paramsMap, final ReqCallBack<T> callBack) {
        try {
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            String params = tempParams.toString();
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, params);
            String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
            final Request request = addHeaders().url(requestUrl).post(body).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //failedCallBack("????????????", callBack);
                    ALog.e(TAG, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        ALog.e(TAG, "response ----->" + string);
                        //successCallBack((T) string, callBack);
                    } else {
                        //failedCallBack("???????????????", callBack);
                    }
                }
            });
            return call;
        } catch (Exception e) {
            ALog.e(TAG, e.toString());
        }
        return null;
    }



    /**
     * ????????????
     * @param fileUrl ??????url
     * @param destFileDir ??????????????????
     */
    public static <T> void downLoadFile(String fileUrl, final String destFileDir, final ReqCallBack<T> callBack) {
        final String fileName = Md5Utils.md5(fileUrl);
        final File file = new File(destFileDir, fileName);
        if (file.exists()) {
            successCallBack((T) file, callBack);
            return;
        }
        final Request request = new Request.Builder().url(fileUrl).build();
        //---------------------
        OkHttpClient.Builder mBuilder = new OkHttpClient().newBuilder();
        mBuilder.readTimeout(HttpClient.READ_TIMEOUT, TimeUnit.SECONDS);
        mBuilder.connectTimeout(HttpClient.CONNECT_TIMEOUT, TimeUnit.SECONDS);
        mBuilder.writeTimeout(HttpClient.WRITE_TIMEOUT, TimeUnit.SECONDS);
        OkHttpClient mOkHttpClient = mBuilder.build();
        //----
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                failedCallBack("????????????", callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    long total = response.body().contentLength();
                    ALog.e(TAG, "total------>" + total);
                    long current = 0;
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        current += len;
                        fos.write(buf, 0, len);
                        //--ALog.e(TAG, "current------>" + current);
                    }
                    fos.flush();
                    successCallBack((T) file, callBack);
                } catch (IOException e) {
                    ALog.e(TAG, e.toString());
                    failedCallBack("????????????", callBack);
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        ALog.e(TAG, e.toString());
                    }
                }
            }
        });
    }

    public static <T> void successCallBack(final T t,final ReqCallBack<T> callBack){
        callBack.onReqSuccess(t);
    }

    public static <T> void failedCallBack(final String textStr,final ReqCallBack<T> callBack){
        callBack.onReqFailed(textStr);
    }


    /**
     * ????????????
     * @param fileUrl ??????url
     * @param destFileDir ??????????????????
     */
    public <T> void downLoadFile(String fileUrl, final String destFileDir, final ReqProgressCallBack<T> callBack) {
        final String fileName = Md5Utils.md5(fileUrl);
        final File file = new File(destFileDir, fileName);
        if (file.exists()) {
            successCallBack((T) file, callBack);
            return;
        }
        final Request request = new Request.Builder().url(fileUrl).build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                failedCallBack("????????????", callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    long total = response.body().contentLength();
                    ALog.e(TAG, "total------>" + total);
                    long current = 0;
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        current += len;
                        fos.write(buf, 0, len);
                        ALog.e(TAG, "current------>" + current);
                        progressCallBack(total, current, callBack);
                    }
                    fos.flush();
                    successCallBack((T) file, callBack);
                } catch (IOException e) {
                    ALog.e(TAG, e.toString());
                    failedCallBack("????????????", callBack);
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        ALog.e(TAG, e.toString());
                    }
                }
            }
        });
    }

    public interface ReqProgressCallBack<T>  extends ReqCallBack<T>{
        /**
         * ??????????????????
         */
        void onProgress(long total, long current);
    }

    /**
     * ????????????????????????
     * @param total    ????????????
     * @param current  ????????????
     * @param callBack
     * @param <T>
     */
    private <T> void progressCallBack(final long total, final long current, final ReqProgressCallBack<T> callBack) {
        BaseApplication.getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onProgress(total, current);
                }
            }
        });
    }
















    public static void getSyncgg(String url){
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        System.out.println(index);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }
//        fixedThreadPool.submit(null);
    }

































    public static void getSync(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void postSync(String url,String json) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ??????enqueue????????????call???????????????????????????okHttp?????????????????????????????????????????????????????????????????????????????????UI????????????
     * ?????????????????????????????????Handler????????????Looper?????????Looper.getMainLooper()?????????Handler???????????????Handler???????????????~
     * @param url
     * @param params
     */
    public static void getAsync(String url, Map<String, String> params) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://github.com").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                response.body().string();
                //--mHandler.sendMessage(msg);
            }
        });
    }

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");//mdiatype ????????????????????????????????????
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");//mdiatype ????????????????????????????????????
    private static final String BASE_URL = "http://xxx.com/openapi";//?????????????????????
    public static final int TYPE_GET = 0;//get??????
    public static final int TYPE_POST_JSON = 1;//post???????????????json
    public static final int TYPE_POST_FORM = 2;//post?????????????????????
    private OkHttpClient mOkHttpClient;//okHttpClient ??????
    private Handler okHttpHandler;//????????????????????????M???????????????

    /**
     * okHttp post????????????
     * @param actionUrl ????????????
     * @param paramsMap ????????????
     * @param callBack ????????????????????????
     * @param <T> ????????????
     * @return
     */
    private <T> Call requestPostByAsyn(String actionUrl, HashMap<String, String> paramsMap, final ReqCallBack<T> callBack) {
        try {
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            String params = tempParams.toString();
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, params);
            String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
            final Request request = addHeaders().url(requestUrl).post(body).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //failedCallBack("????????????", callBack);
                    ALog.e(TAG, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        ALog.e(TAG, "response ----->" + string);
                        //successCallBack((T) string, callBack);
                    } else {
                        //failedCallBack("???????????????", callBack);
                    }
                }
            });
            return call;
        } catch (Exception e) {
            ALog.e(TAG, e.toString());
        }
        return null;
    }

    /**
     * ??????????????????????????????
     * @return
     */
    private Request.Builder addHeaders() {
        Request.Builder builder = new Request.Builder()
                .addHeader("Connection", "keep-alive")
                .addHeader("platform", "2")
                .addHeader("phoneModel", Build.MODEL)
                .addHeader("systemVersion", Build.VERSION.RELEASE)
                .addHeader("appVersion", "3.2.0");
        return builder;
    }

    /**
     * HTTP ????????????????????? Map<String, List<String>>???????????????????????????????????? HTTP ?????????????????????????????????????????? HTTP ??????????????????????????????????????? HTTP ?????????????????????OkHttp?????????????????????
     * ??????header(name,value)?????????HTTP???????????????
     * ??????addHeader(name,value)???????????????
     * ??????header(name)?????????????????????????????????????????????
     * ??????headers(name)???????????????
     */
    public static void getAsync___() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://github.com")
                .header("User-Agent", "My super agent")
                .addHeader("Accept", "text/html")
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                //--throw new IOException("??????????????????: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getEarnings (Context mContext,String token) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(new CookieJar() {
            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<String, List<Cookie>>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url.host(), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        });
        OkHttpClient okHttpClient = builder.build();

    }

    /**
     * @Description ?????????OkHttp
     */
//    private void initOkHttp() {
//        File cache = getExternalCacheDir();
//        int cacheSize = 10 * 1024 * 1024;
//
//        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(mContext));
//        Https.SSLParams sslParams = Https.getSslSocketFactory(null, null, null);
//
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(15, TimeUnit.SECONDS)//????????????(??????:???)
//                .writeTimeout(20, TimeUnit.SECONDS)//????????????(??????:???)
//                .readTimeout(20, TimeUnit.SECONDS)//????????????(??????:???)
//                .pingInterval(20, TimeUnit.SECONDS) //websocket????????????(??????:???)
//                .cache(new Cache(cache.getAbsoluteFile(), cacheSize))//????????????
//                .cookieJar(cookieJar)//Cookies?????????
//                .hostnameVerifier(new HostnameVerifier() {
//                    @Override
//                    public boolean verify(String hostname, SSLSession session) {
//                        return true;
//                    }
//                })
//                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)//https??????
//                .build();
//
//        OkHttpUtilds.initClient(okHttpClient);
//    }


//    public static void getSync(String url, HttpCallback callback) {
//        Request request = OkHttpRequest.builderRequest(HttpMethodType.GET, url, null, null);
//        OkHttpRequest.doExecute(request, callback);
//    }

//    public static void getSync(String url, Map<String, String> params, HttpCallback callback) {
//        if (params != null && !params.isEmpty()) {
//            url = OkHttpRequest.appendGetParams(url, params);
//        }
//        Request request = OkHttpRequest.builderRequest(HttpMethodType.GET, url, null, null);
//        OkHttpRequest.doExecute(request, callback);
//    }

//    public static void postSync(String url, Map<String, String> params, HttpCallback callback) {
//        Request request = OkHttpRequest.builderRequest(HttpMethodType.POST, url, params, null);
//        OkHttpRequest.doExecute(request, callback);
//    }







}

