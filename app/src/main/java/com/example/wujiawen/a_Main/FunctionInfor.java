package com.example.wujiawen.a_Main;

import android.content.Context;
import android.content.Intent;

import com.example.wujiawen.ExampleDrawable.DrawableStudyActiv;
import com.example.wujiawen.ExampleShortcut.ShortActivity;
import com.example.wujiawen.ExampleTextView.EditTextCursorActiv;
import com.example.wujiawen.ExampleTextView.SpannableStringExampleActiv;
import com.example.wujiawen.ExamplesShader.ShaderExampleActiv;
import com.study.RxAndroid.HandlerLoopActiv;
import com.study.diyView.DiyPerViewActiv;
import com.study.diyView.DiyPerViewActiv2;
import com.study.z_glide.GlideActiv;
import com.study.z_reflection_view.ReflectViewActi;

import java.util.ArrayList;

/**
 * Created by wujiawen on 2018/1/10.
 */

public class FunctionInfor {

    public String des;
    public Class<?>  activ_name;
    public static ArrayList<FunctionInfor> functionInforList=new ArrayList<FunctionInfor>();

    public FunctionInfor(Class<?> activ_name,String des) {
        this.des = des;
        this.activ_name = activ_name;
    }


    static{

        functionInforList.add(new FunctionInfor(com.example.wujiawen.Tools.AlphaActivity.class,"alpha小数转16进制"));
        functionInforList.add(new FunctionInfor(com.example.wujiawen.ExampleScrollView.MainActivity.class,"ScrollView嵌套"));
        functionInforList.add(new FunctionInfor(com.example.wujiawen.ExampleLoopThread.LoopActivity.class,"Loop Thread"));
        functionInforList.add(new FunctionInfor(DrawableStudyActiv.class,"selector"));
        functionInforList.add(new FunctionInfor(SpannableStringExampleActiv.class,"SpannableStringExampleActiv"));
        functionInforList.add(new FunctionInfor(ShaderExampleActiv.class,"Shader"));
        functionInforList.add(new FunctionInfor(ShortActivity.class,"ShortActivity-快捷方式-"));
        functionInforList.add(new FunctionInfor(EditTextCursorActiv.class,"EditTextCursorActiv"));
        functionInforList.add(new FunctionInfor(com.example.wujiawen.ExampleDragFillBlankView.MainActivity.class,"DragFillBlank"));
        functionInforList.add(new FunctionInfor(com.example.wujiawen.Example_b.MainActivity.class,"111"));
//        functionInforList.add(new FunctionInfor(com.badlogic.ui.MainActivity.class,"-listview-"));
//        functionInforList.add(new FunctionInfor(com.badlogic.ui.MainActivity_pull.class,"-MainActivity_pull-上,下拉动刷新"));
//        functionInforList.add(new FunctionInfor(MainActivity_edge.class,"-MainActivity_edge-"));
//        functionInforList.add(new FunctionInfor(com.badlogic.ui.MainActivity_error.class,"-listview_error-"));
//        functionInforList.add(new FunctionInfor(MainActivity_pull_now.class,"-MainActivity_pull-上,下拉动刷新-now-"));

        functionInforList.add(new FunctionInfor(ReflectViewActi.class,"-注解反射注入-"));
        functionInforList.add(new FunctionInfor(com.study.Fragment.MainActivity.class,
                "-Fragment的回退栈管理（手动维护回退栈)-"));
        functionInforList.add(new FunctionInfor(com.study.activState.StateActiv_A.class,
                "-StateActiv_A-"));

        functionInforList.add(new FunctionInfor(com.study.dataSave.parcelable.MainActivity.class,
                "-Parcelable数据传递-"));
        functionInforList.add(new FunctionInfor(com.study.contentProvider.ContentProviderActi.class,
                "-ContentProvider数据传递-"));
        functionInforList.add(new FunctionInfor(com.study.database.SqlActivity.class,
                "-Sqlite数据库操作-"));
        functionInforList.add(new FunctionInfor(GlideActiv.class,
                "-GlideActiv操作-"));
        functionInforList.add(new FunctionInfor(com.study.bigPic.GigPicActiv.class,
                "-加载巨图操作-"));
        functionInforList.add(new FunctionInfor(com.study.activState.EventBusActiv.class,
                "-eventbus-"));
        functionInforList.add(new FunctionInfor(com.study.RxAndroid.RxAndroidActiv.class,
                "-RxAndroid-"));
        functionInforList.add(new FunctionInfor(com.study.RxAndroid.AsyncTaskActiv.class,
                "-AsyncTaskActiv-"));
        functionInforList.add(new FunctionInfor(HandlerLoopActiv.class,
                "-HandlerLoopActiv-"));
        functionInforList.add(new FunctionInfor(DiyPerViewActiv.class,
                "-DiyViewActiv父子View大小位置变化同时-"));
        functionInforList.add(new FunctionInfor(DiyPerViewActiv2.class,
                "-DiyViewActiv父子View大小位置变化同时-"));

        functionInforList.add(new FunctionInfor(com.study.recycleview.RecycleviewActivity.class,
                "-RecycleviewActivity-"));

        functionInforList.add(new FunctionInfor(com.study.diyView.TestActivity.class,
                "-mLeft mScrollX translationX-"));
        functionInforList.add(new FunctionInfor(com.study.surfaceview.surfaceViewActivity.class,
                "-surfaceViewActivity-"));
        functionInforList.add(new FunctionInfor(com.example.wujiawen.proxy.ProxyActivity.class,
                "-ProxyActivity-"));
        functionInforList.add(new FunctionInfor(com.study.activState.AnrActivity.class,
                "-AnrActivity-"));
        functionInforList.add(new FunctionInfor(com.study.activState.OomActivity.class,
                "-OomActivity-"));
        functionInforList.add(new FunctionInfor(com.study.activState.JavassistActivity.class,
                "-JavassistActivity-"));
        functionInforList.add(new FunctionInfor(com.study.z_reference.ReferenceActivity.class,
                "-ReferenceActivity-"));




    }

    public static void testFirstStartActivity(Context mContext){
        Class targetClass = com.study.z_reference.ReferenceActivity.class;
//        Intent mIntent = new Intent(mContext, HandlerLoopActiv.class);
//        Intent mIntent = new Intent(mContext, com.study.RxAndroid.RxAndroidActiv.class);
//        Intent mIntent = new Intent(mContext, com.study.RxAndroid.RxAndroidActiv__think.class);

//        Intent mIntent = new Intent(mContext, com.study.diyView.TestActivity.class);
//        Intent mIntent = new Intent(mContext, com.study.surfaceview.surfaceViewActivity.class);
//        Intent mIntent = new Intent(mContext, com.study.activState.AnrActivity.class);
//        Intent mIntent = new Intent(mContext, com.study.activState.OomActivity.class);

        Intent mIntent = new Intent(mContext, targetClass);

        String ddddd= null;
//        Intent mIntent = new Intent(mContext, DiyPerViewActiv.class);
//        Intent mIntent = new Intent(mContext, DiyPerViewActiv2.class);
        mContext.startActivity(mIntent);






    }

    public static void main(String[] args){

        System.out.println("--------->>");
        System.out.println("---1------>>");
        System.out.println("---2------>>");
        System.out.println("---3------>>");

    }

}
