package com.example.wujiawen.ExampleLanguage;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import com.badlogic.utils.ALog;
import com.example.wujiawen.a_Main.R;

import java.util.Locale;

/**
 * 语言工具类
 * Created by littlejie on 2017/5/17.
 */

public class LanguageUtil {
    private static LanguageUtil instance = new LanguageUtil();

    //--public static final String SPName = LanguageUtil.class.getSimpleName();
    public static final String SPName = "Language0302";
    public static final String LANGUAGE = "Language";
    public static final String COUNTRY = "country";
    public static final String FOLLOW_SYSTEM = "FOLLOW_SYSTEM";

    public static final String SCRIPT = "script";

    private BroadcastReceiver receiver = new LocaleChangeReceiver();
    private LanguageChangeReceiver languageChangeReceiver;
    private Context context;

    private LanguageUtil() {
    }

    public static LanguageUtil getInstance() {
        return instance;
    }

    public Context initAttach(Context context, LanguageChangeReceiver languageChangeReceiver) {
        this.context = context;
        this.languageChangeReceiver = languageChangeReceiver;
        IntentFilter filter = new IntentFilter(Intent.ACTION_LOCALE_CHANGED);
        context.registerReceiver(receiver, filter);

        if (checkDiff(context)) {
            context = changeAppLanguage(context, getAppLocale(context));
        }
        return context;
    }

    public Context onAttach(Context context) {
        if (checkDiff(context)) {
            context = changeAppLanguage(context, getAppLocale(context));
        }
        return context;
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, Locale locale) {
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);

        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    private static Context updateResourcesLegacy(Context context, Locale locale) {
        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();

        configuration.locale = locale;

//        configuration.setLocale(locale);
//        configuration.setLayoutDirection(locale);

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return context;
    }

    class LocaleChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //改变系统语言会接收到 ACTION_LOCALE_CHANGED
            //改变应用内部语言不会收到 ACTION_LOCALE_CHANGED
            if (!Intent.ACTION_LOCALE_CHANGED.equals(intent.getAction())) {
                return;
            }

            //如果用户手动更改过应用语言设置，则收到系统语言切换广播时，需要保持语言不变
            if (checkDiff(context)) {
                changeAppLanguage(context, getAppLocale(context));
            } else {
                languageChangeReceiver.onReceive(context, intent);
            }
        }
    }

    private static void saveLocale(Context context, Locale locale) {
        SharedPreferences preferences = context.getSharedPreferences(SPName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LANGUAGE, locale.getLanguage());
        editor.putString(COUNTRY, locale.getCountry());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editor.putString(SCRIPT, locale.getScript());
        }
        editor.apply();
    }

    private static String getAppScript(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SPName,
                Context.MODE_PRIVATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return preferences.getString(SCRIPT,
                    Locale.getDefault().getScript());
        } else {
            return "";
        }
    }

    private boolean checkDiff(Context context) {
        Locale appLocale = getAppLocale(context);
        return !appLocale.equals(Locale.getDefault());
    }

    public boolean isChineseInApp() {
        String lang = context.getString(R.string.base_language);
        return "zh".equals(lang);
    }

    public interface LanguageChangeReceiver {
        void onReceive(Context context, Intent intent);
    }
    //==========================================================================
    //==========================================================================

    public static String getAppLanguage(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SPName, Context.MODE_PRIVATE);
        return preferences.getString(LANGUAGE,  Locale.getDefault().getLanguage());
    }

    public static void saveLanguageSetting(Context context, Locale locale,boolean followSystem) {
        ALog.i(ALog.Tag2,"LanguageUtil--saveLanguageSetting--followSystem->"+followSystem);
        SharedPreferences preferences =context.getSharedPreferences(SPName, Context.MODE_PRIVATE);
        preferences.edit().putString(LANGUAGE, locale.getLanguage()).commit();
        preferences.edit().putBoolean(FOLLOW_SYSTEM,followSystem).commit();
        preferences.edit().putString(COUNTRY, locale.getCountry()).commit();
    }

    public static String getAppCountry(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SPName, Context.MODE_PRIVATE);
        return preferences.getString(COUNTRY,Locale.getDefault().getCountry());
    }

    public static boolean getAppFollowSystem(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SPName, Context.MODE_PRIVATE);
        return preferences.getBoolean(FOLLOW_SYSTEM,true);
    }
    public static Locale getAppLocale(Context context) {
        String language=getAppLanguage(context);
        String country=getAppCountry(context);
        ALog.i(ALog.Tag2,"LanguageUtil--getAppLocale--language->"+(language+"_"+country));
        Locale locale = new Locale(language,country);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new Locale.Builder()
                    .setLocale(locale)
                    .setScript(getAppScript(context))
                    .build();
        } else {
            return locale;
        }
    }

    public static Context changeAppLanguage(Context context, Locale locale) {

        /*saveLocale(context, locale);
        Locale.setDefault(locale);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, locale);
        }
        return updateResourcesLegacy(context, locale);*/

        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration, metrics);
        return context;
    }





}
