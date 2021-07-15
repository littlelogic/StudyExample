package com.example.wujiawen.a_Main;//com.example.wujiawen.a_Main.BaseApplication

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.badlogic.utils.ALog;
import com.badlogic.utils.Tools;
import com.example.wujiawen.ExampleLanguage.LanguageUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by wujiawen on 2018/3/2.
 */

public class BaseApplication extends Application {


    public static BaseApplication Me = null;
    //--------
    public ActivityLifecycleCallbacks callback;
    public List<Activity> mListActivity = new LinkedList<Activity>();
    //--------
    //-------------------------------------------
    @Override
    public void onCreate() {
        super.onCreate();
        Me = this;
        ALog.i(ALog.Tag2,"BaseApplication--onCreate->");
        dealAppLanguage();
        //========================================================================
        deal_registerActivity();
    }

    boolean languageFollowSystem;
    String languageSetting ="";

    public void dealAppLanguage() {
        languageFollowSystem =LanguageUtil.getAppFollowSystem(this);
        languageSetting =LanguageUtil.getAppLanguage(this)+"_"+LanguageUtil.getAppCountry(this);
        ALog.i(ALog.Tag2,"BaseApplication--dealAppLanguage--languageFollowSystem->"+ languageFollowSystem);
        if(languageFollowSystem){
        }else{
            LanguageUtil.changeAppLanguage(this,LanguageUtil.getAppLocale(this));
        }
    }


    /**
     * 更改应用语言
     * @param context
     * @param locale 语言地区
     */

    public static void changeAppLanguage(Context context, Locale locale) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration, metrics);
    }

    public void deal_registerActivity(){
        callback=new ActivityLifecycleCallbacks(){
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                boolean follow= languageFollowSystem;
                ALog.i(ALog.Tag2,"BaseApplication--deal_registerActivity--onActivityCreated--follow->"+follow);
                if(follow){
                }else{
                    Locale locale = getResources().getConfiguration().locale;
                    String baseLanguage= locale.getLanguage()+"_"+locale.getCountry();
                    String r_id_base_language=activity.getString(R.string.base_language);
                    boolean same=baseLanguage.equals(languageSetting);
                    ALog.i(ALog.Tag2,"BaseApplication--deal_registerActivity--onActivityCreated--baseLanguage->"+
                            baseLanguage+"--languageSetting->"+ languageSetting +"--same->"+same);
                    if(same){
                    }else{
                        changeAppLanguage(activity,LanguageUtil.getAppLocale(activity));
                    }
                }


                try {
                    mListActivity.add(activity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
				/*myLog.i("_log_Activity", "--MApplication--ActivityLifecycleCallbacks--onActivityCreated--01-->>"+activity.getLocalClassName());
				myLog.i("_log_Activity", "--MApplication--ActivityLifecycleCallbacks--onActivityCreated--02-->>"+activity.getPackageName());
				myLog.i("_log_Activity", "--MApplication--ActivityLifecycleCallbacks--onActivityCreated--03-->>"+activity.getClass().getName());*/
            }

            @Override
            public void onActivityStarted(Activity activity) {
                //--myLog.i("_log_Activity", "---MApplication----ActivityLifecycleCallbacks--onActivityStarted-->>"+activity.getLocalClassName());
            }

            @Override
            public void onActivityResumed(Activity activity) {
                //--myLog.i("_log_Activity", "---MApplication----ActivityLifecycleCallbacks--onActivityResumed-->>"+activity.getLocalClassName());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                //--myLog.i("_log_Activity", "---MApplication----ActivityLifecycleCallbacks--onActivityPaused-->>"+activity.getLocalClassName());
            }

            @Override
            public void onActivityStopped(Activity activity) {
                //--myLog.i("_log_Activity", "---MApplication----ActivityLifecycleCallbacks--onActivityStopped-->>"+activity.getLocalClassName());
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                //--myLog.i("_log_Activity", "---MApplication----ActivityLifecycleCallbacks--onActivitySaveInstanceState-->>"+activity.getLocalClassName());
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                try {
                    mListActivity.remove(activity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }};
        registerActivityLifecycleCallbacks(callback);
    }


    @Override
    public void onTerminate() {
        ALog.e("_log_Activity", "---MApplication----onTerminate-->>>");
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        ALog.e("_log_Activity", "---MApplication----onConfigurationChanged-->>>");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        ALog.e("_log_Activity", "---MApplication----onLowMemory-->>>");
        super.onLowMemory();
    }

    //=============================================================================
    public void exit() {
        try {
            for (Activity activity : mListActivity) {
                if (activity != null){
                    ALog.i("_log_Activity", "--MApplication--exit-->>"+activity.getClass().getName());
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void finishActivity() {
        try {
            for (Activity activity : mListActivity) {
                if (activity != null){
                    ALog.i("_log_Activity", "--MApplication--finishActivity-->>"+activity.getClass().getName());
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getActivityCount() {
        return mListActivity.size();
    }

    //=============================================================================
    /**
     * 全包名+类名
     * @param PackageName
     */
    public boolean isContainActivity(String PackageName) {
        try {
            for (Activity activity : mListActivity) {
                if (activity != null){
                    if(activity.getClass().getName().equals(PackageName)){
                        ALog.i("_log_Activity", "--MApplication--isContainActivity-->>"+activity.getClass().getName());
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}