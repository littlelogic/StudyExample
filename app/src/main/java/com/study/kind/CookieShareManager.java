package com.study.kind;

import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class CookieShareManager implements CookieJar {

    public CookieManager cookieManager = CookieManager.getInstance();

    private static CookieShareManager instance;

    private CookieShareManager(){
        cookieManager.setAcceptCookie(true);
    }

    public static CookieShareManager getInstance() {
        if (instance == null){
            synchronized (CookieShareManager.class){
                if(instance == null)
                    instance = new CookieShareManager();
            }
        }
        return instance;
    }

    public static void cleanInstance() {
        instance = null;
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        String urlString = url.toString();

        for (Cookie cookie : cookies) {
            cookieManager.setCookie(urlString, cookie.toString());
        }


    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        String urlString = url.toString();
        String cookiesString = cookieManager.getCookie(urlString);
        //cookiesString = "VISITOR_INFO1_LIVE=uzI6TiqGZWo; CONSENT=YES+CN.zh-CN+201910; PREF=al=zh-CN&f4=4000000&f1=10000000; YSC=Gcmg6RQ6rhY; SID=zQd2cspONQhB0-sq9Obqa2ghd10fX_aDxPGLtLVZ8kZ175CRArUWLw-nN-3EG7eSL0rnBg.; __Secure-3PSID=zQd2cspONQhB0-sq9Obqa2ghd10fX_aDxPGLtLVZ8kZ175CRww9Mky3QNiOFC6YZpSKy5w.; HSID=AVb_UvY66OPcKFnRu; SSID=A1t4c-IFCWh8LgfFQ; APISID=YugGLE2sIC7smvp4/A42dBnD6Who276HFN; SAPISID=r35ae_8-zNpcTW-8/AcEwsKXE3asmDd9aB; __Secure-HSID=AVb_UvY66OPcKFnRu; __Secure-SSID=A1t4c-IFCWh8LgfFQ; __Secure-APISID=YugGLE2sIC7smvp4/A42dBnD6Who276HFN; __Secure-3PAPISID=r35ae_8-zNpcTW-8/AcEwsKXE3asmDd9aB; LOGIN_INFO=AFmmF2swRQIgakUClwPP_067Mp1sbu4i38qjEVWS8O2RajO16ZWRS0QCIQC3oXM9HAJnHskzVCSeBNRelxVW0JmU1qN9lPlYSaxJDw:QUQ3MjNmejl3eEZzOWlhVER4REhLdDI2anNIYXAweC1odkJvZXp0TmN5bW01eDIxbHo5WG9pa0lMZE42VHpuU3VTWTM1c2J3aXdzSHo4R1hPVWxjMTIzbzFSRmYxRS04aW42dUd0Mmlac0ZzdXJyZlhCU0RNZ3o0SkxyeFNmc0hDU3lVQ2NVOUtFdzhkUmRXSENFb0w1cXN6dElpSDVIUkQyMlUwRk52V0tsSlFESUhBQ0FETUFQNnVubzRrX1BUTTNDbXlCTmhUZ1Vk; SIDCC=AJi4QfGLxlhyxZmvZ2M81Un-LnVdp_betatwRn571B9YeoxESZ-bpdI6kww9g_lQ7oxJleI2; ST-u58m9p=itct=CAMQybcCIhMIp-fqnoDX6gIVBsJMAh0vcgdE";

        cookiesString = setUnloginCookie(cookiesString);
        if (cookiesString != null && !cookiesString.isEmpty()) {
            String[] cookieHeaders = cookiesString.split(";");
            List<Cookie> cookies = new ArrayList<>(cookieHeaders.length);


            for (String header : cookieHeaders) {
                cookies.add(Cookie.parse(url, header));
            }
//            LoginYoutubeToken.getInstance().setCookie(cookies);
            return cookies;
        }
        return Collections.emptyList();
    }


    public static String VISITOR_INFO1_LIVE;
    private String setUnloginCookie(String cookiesString){
        if(TextUtils.isEmpty(cookiesString)){
            String visitor;
            if(!TextUtils.isEmpty(VISITOR_INFO1_LIVE)){
                visitor = "VISITOR_INFO1_LIVE=" + VISITOR_INFO1_LIVE;
            }else{
                visitor = "VISITOR_INFO1_LIVE=uzI6TiqGZWo";
            }
            cookiesString = visitor+"; f4=4000000&f1=10000000; YSC=gonc28O2ivU;";
            return cookiesString;
        }

        if(!cookiesString.contains("VISITOR_INFO1_LIVE")){
            String visitor;
            if(!TextUtils.isEmpty(VISITOR_INFO1_LIVE)){
                visitor = VISITOR_INFO1_LIVE;
            }else{
                visitor = "uzI6TiqGZWo";
            }
            cookiesString += ";VISITOR_INFO1_LIVE="+visitor;
        }else{
            try{

                int startIndex = cookiesString.indexOf("VISITOR_INFO1_LIVE");
                int endIndex = -1;
                String temp = cookiesString.substring(startIndex);
                if(temp.contains(";")){
                    endIndex = temp.indexOf(";");
                }else {
                    endIndex = temp.length();
                }
                temp = temp.substring(0,endIndex);
                if(temp.contains("=")){
                    VISITOR_INFO1_LIVE = temp.substring(temp.indexOf("=")+1);
                    //Log.d("mtest"," VISITOR_INFO1_LIVE: "+VISITOR_INFO1_LIVE);
                }
            }catch (Exception e){
                e.printStackTrace();
                VISITOR_INFO1_LIVE = "";
            }
        }

        return cookiesString;
    }

    public void clean(){
        cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
            @Override
            public void onReceiveValue(Boolean aBoolean) {
                //Log.d("mtest","clean cookie :"+aBoolean);
            }
        });
    }
}