package com.example.wujiawen.ExampleLanguage;//com.example.wujiawen.ExampleLanguage.LanguageActiv

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;

import com.example.wujiawen.ExampleTextView.ExpandTextView;
import com.example.wujiawen.a_Main.R;

import java.util.Locale;


public class LanguageActiv extends Activity implements View.OnClickListener{

    CheckBoxSow EnglishCheck;
    CheckBoxSow SimplifiedChineseCheck;
    CheckBoxSow TraditionalChineseCheck;
    CheckBoxSow FollowSystemChineseCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        EnglishCheck         =(CheckBoxSow)findViewById(R.id.ll_a_Check);
        SimplifiedChineseCheck=(CheckBoxSow)findViewById(R.id.ll_b_Check);
        TraditionalChineseCheck=(CheckBoxSow)findViewById(R.id.ll_c_Check);
        FollowSystemChineseCheck=(CheckBoxSow)findViewById(R.id.ll_d_Check);
        findViewById(R.id.ll_a).setOnClickListener(this);
        findViewById(R.id.ll_b).setOnClickListener(this);
        findViewById(R.id.ll_c).setOnClickListener(this);
        findViewById(R.id.ll_d).setOnClickListener(this);
        //-----------
        boolean languageFollowSystem=LanguageUtil.getAppFollowSystem(this);
        String languageSetting=LanguageUtil.getAppLanguage(this)+"_"+LanguageUtil.getAppCountry(this);
        if(languageFollowSystem){
            setAllChecked(FollowSystemChineseCheck);
        }else{
            if(languageSetting.equals("zh_CN")){
                setAllChecked(SimplifiedChineseCheck);
            }else if(languageSetting.equals("zh_TW")){
                setAllChecked(TraditionalChineseCheck);
            }else if(languageSetting.equals("en_")){
                setAllChecked(EnglishCheck);
            }else{
                setAllChecked(SimplifiedChineseCheck);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_a:
                setAllChecked(EnglishCheck);
                LanguageUtil.saveLanguageSetting(LanguageActiv.this,Locale.ENGLISH,false);
                changeAppLanguage(Locale.ENGLISH);
                break;
            case R.id.ll_b:
                setAllChecked(SimplifiedChineseCheck);
                LanguageUtil.saveLanguageSetting(LanguageActiv.this,Locale.SIMPLIFIED_CHINESE,false);
                changeAppLanguage(Locale.SIMPLIFIED_CHINESE);
                break;
            case R.id.ll_c:
                setAllChecked(TraditionalChineseCheck);
                LanguageUtil.saveLanguageSetting(LanguageActiv.this,Locale.TAIWAN,false);
                changeAppLanguage(Locale.TAIWAN);
                break;
            case R.id.ll_d:
                setAllChecked(FollowSystemChineseCheck);
                LanguageUtil.saveLanguageSetting(LanguageActiv.this,Locale.getDefault(),true);
                changeAppLanguage(Locale.getDefault());
                break;
            default:
        }
    }

    public void setAllChecked(View v) {
        switch (v.getId()){
            case R.id.ll_a_Check:
                EnglishCheck.setChecked(true);
                SimplifiedChineseCheck.setChecked(false);
                TraditionalChineseCheck.setChecked(false);
                FollowSystemChineseCheck.setChecked(false);
                break;
            case R.id.ll_b_Check:
                EnglishCheck.setChecked(false);
                SimplifiedChineseCheck.setChecked(true);
                TraditionalChineseCheck.setChecked(false);
                FollowSystemChineseCheck.setChecked(false);
                break;
            case R.id.ll_c_Check:
                EnglishCheck.setChecked(false);
                SimplifiedChineseCheck.setChecked(false);
                TraditionalChineseCheck.setChecked(true);
                FollowSystemChineseCheck.setChecked(false);
                break;
            case R.id.ll_d_Check:
                EnglishCheck.setChecked(false);
                SimplifiedChineseCheck.setChecked(false);
                TraditionalChineseCheck.setChecked(false);
                FollowSystemChineseCheck.setChecked(true);
                break;
            default:
        }

    }

    public void changeAppLanguage(Locale mLocale) {
        /*Locale myLocale = mLocale;
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);*/

        LanguageUtil.changeAppLanguage(this,mLocale);
        //-----------
        Intent intent = new Intent(this, com.example.wujiawen.a_Main.MainActivity.class);
        //开始新的activity同时移除之前所有的activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        /**
         * 假如你的 App 存在某个 activity 和当前设置页 activity 不在一个 task 栈内的
         * 话（比如你从某个通知页用 FLAG_ACTIVITY_NEW_TASK 启动的一个 activity），就不
         * 会应用语言设置。因此可以直接杀掉当前 App 的进程，保证是“整个”重启了：
         */
        //-杀掉进程
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }









    @Override
    protected void onResume() {
        super.onResume();
//        textView.updateText(getResources().getString(R.string.test_expandtext));
    }
}
