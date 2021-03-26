package com.study.RxAndroid;
//com.study.RxAndroid.HandlerLoopActiv
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.badlogic.utils.ALog;
import com.badlogic.utils.Tools;

import java.lang.reflect.Method;

public class HandlerLoopActiv__think extends Activity {


    //todo 欠 Handler Looper MessageQueue Thread hreadLocal<Looper>
    //todo mAsynchronous 同步分隔栏 的相关用法，已被 隐藏、停止
    /*
    主线程Looper死循环为什么没有ANR?
造成ANR的不是主线程阻塞，而是主线程的Looper消息处理过程发生了任务阻塞，无法响应手势操作，不能及时刷新UI。

mTraversalRunnable调用了performTraversals执行measure、layout、draw
为了让mTraversalRunnable尽快被执行，在发消息之前调用MessageQueue.postSyncBarrier设置了同步屏障


postSyncBarrier方法是私有的，如果我们想调用它就得使用反射。
插入普通消息会唤醒消息队列，但是插入屏障不会。

IdelHandler是什么，有啥用？
注：a，添加IdelHandler时，消息队列不为空，当消息处理完或者剩下消息还没到触发时间，会回调方法 b，当添加IdelHandler时，消息队列为空，则当时不会触发回调
onCreate ，a，延迟执行
b，批量任务，任务密集，且只关注最终结果
     */
    Handler mHandlerB=new Handler(Looper.getMainLooper(),null){
        @Override public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
/**         1. Message有<runnable>接口，直接执行，结束
            2. 如果有传入的<callback>，用他来处理Message，结束
            3. 执行下面的方法<handleMessage>，结束                                                                                     */
        }
        @Override public void handleMessage(Message msg) {}
    };
/**     <removeCallbacksAndMessages>只清空<关联的Handler>中已经有的消息,
 *      post和sendMessage消息，会在<下次loop循环>的时候开始执行
 *      若在<onCreate>方法直接使用 <Handler.post()>,
 *      则任务一定在 ViewConstant 绘制任务之<前>（<同一个线程队列机制>）                                                       */
    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout mLinearLayout = new LinearLayout(this);
        this.setContentView(mLinearLayout);
        new Thread(){
            @Override public void run() {
                try {Thread.sleep(100);} catch (Exception e) {}
                addTestView(mLinearLayout, "","").setText(
                "在onCreate()或onStart()或onResume()里支线程中更新UI,只是刷新属性,"
                +"因为还没有进行绘制,但是要避免");

                AlertDialog.Builder builder =new AlertDialog.Builder(mContext);
                TextView hTextView=getTextView("点击结束此loop，执行其后面的代码");
                builder.setView(hTextView);
                Looper.prepare();
                final AlertDialog hAlertDialog =  builder.show();
                hTextView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //todo Looper中止,其后的代码才能执行，主Looper不允许终止
                        /** <quitSafely>,清空延迟消息,处理非延迟消息 */
                        Looper.myLooper().quit(); //todo 不再接收消息
                        hAlertDialog.dismiss();
                    }
                });
                Handler handler = new Handler();//todo 对应于此线程
                Looper.loop();
                ALog.i("Looper对象通过MessageQueue来存放消息," +
                        "一个线程只能有一个Looper，对应一个MessageQueue"+
                        "此时添加，更新ui会报错");
            }
        }.start();
        //todo HandlerThread是已经 内建立一个looper、handler的线程
/**     Looper的loop方法的  在获取 <下一条> 消息时,没有消息时，阻塞。                                                 */




    }




    ///============================================================================

    Handler mHandler;
    public TextView addTestView(LinearLayout mLinearLayout,String text,Runnable hRunnable){

        mHandler.sendMessage(new Message());
        return null;
    }

    public TextView addTestView(LinearLayout mLinearLayout,String name,String text){
        return null;
    }

    public TextView getTextView(String text){
        return null;
    }





    ///============================================================================





    int dp_5;
    String Tag = "handler";
    String TAG = Tag;
    TextView textTop;
    Handler mmHandler;
    Looper mmLooper;
    Context mContext = null;



}
