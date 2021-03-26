package com.example.wujiawen.ExampleLoopThread;//com.example.wujiawen.ExampleLoopThread.LoopActivity

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.badlogic.utils.ALog;
import com.badlogic.utils.MyCompatView;
import com.badlogic.utils.Tools;

public class LoopActivity extends Activity implements View.OnClickListener{
    TextView titleTextView;
    LinearLayout mLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //---------------
        mLinearLayout = new LinearLayout(this);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mLinearLayout.setBackgroundColor(Color.WHITE);
        mLinearLayout.setPadding(Tools.dip2px(this, 20), Tools.dip2px(this, 20), Tools.dip2px(this, 20), Tools.dip2px(this, 20));
        //--setContentView(mLinearLayout);
        //-----------
        ScrollView mScrollView=new ScrollView(this);
        mScrollView.setBackgroundColor(Color.WHITE);
        setContentView(mScrollView);
        mScrollView.addView(mLinearLayout);
        //-------
        titleTextView = new TextView(this);
        LinearLayout.LayoutParams params_b = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params_b.setMargins(0,0,0,Tools.dip2px(this, 20));
        titleTextView.setBackgroundColor(this.getResources().getColor(com.yunbi.utils.R.color.lighter_gray));
        titleTextView.setTextColor(this.getResources().getColor(com.yunbi.utils.R.color.colorPrimaryDark));
        titleTextView.setTextSize(17);
        titleTextView.setLayoutParams(params_b);
        mLinearLayout.addView(titleTextView);
        titleTextView.setText("Loop-Thread-test\n所有的ui操作都应该放在loop的prepare和loop之间，标准，兼容");
        titleTextView.setGravity(Gravity.CENTER);

//        ActivityThread mActivityThread;
        //-------

        for(int i=1;i<=textStr.length;i++){
            ALog.i(ALog.Tag2,"LoopActivity--onCreate--b--i->"+i);
            TextView mTextView = new TextView(this);
            LinearLayout.LayoutParams params_a = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Tools.dip2px(this, 40));
            params_a.setMargins(0,0,0,Tools.dip2px(this, 20));
            mTextView.setBackgroundColor(this.getResources().getColor(com.yunbi.utils.R.color.lighter_gray));
            mTextView.setTextColor(this.getResources().getColor(com.yunbi.utils.R.color.darker_gray));
            mTextView.setTextSize(15);
            mTextView.setLayoutParams(params_a);
            mLinearLayout.addView(mTextView);
            mTextView.setText(textStr[i-1]);
            MyCompatView.setTextViewId(mTextView,i);
            mTextView.setOnClickListener(this);
            mTextView.setGravity(Gravity.CENTER);
        }


        //-------------------------------------
        new Thread(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                titleTextView.setText("在onCreate()(may或者onStart()，may或者onResume())里非UI线程中更新UI,只是刷新属性\n"+titleTextView.getText());
            }

        }.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //-------------------------------------

        if (2 < 10) {
            return;
        }


        /*

        public boolean isMainThread() {
            return Looper.getMainLooper() == Looper.myLooper();
        }


        public boolean isMainThread() {
            return Looper.getMainLooper().getThread() == Thread.currentThread();
        }

        public boolean isMainThread() {
            return Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId();
        }

        */
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case 1:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Handler handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                Toast.makeText(getApplicationContext(), "handler msg 1--Thread-Name->"+Thread.currentThread().getName(), Toast.LENGTH_LONG).show();
                            }
                        };
                        handler.sendEmptyMessage(1);
                        Looper.loop();
                    };
                }).start();
                break;
            case 2:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        //-----------------
                        TextView mTextView = new TextView(LoopActivity.this);
                        LinearLayout.LayoutParams params_a = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Tools.dip2px(LoopActivity.this, 40));
                        params_a.setMargins(0,0,0,Tools.dip2px(LoopActivity.this, 20));
                        mTextView.setBackgroundColor(LoopActivity.this.getResources().getColor(com.yunbi.utils.R.color.lighter_gray));
                        mTextView.setTextColor(LoopActivity.this.getResources().getColor(com.yunbi.utils.R.color.darker_gray));
                        mTextView.setTextSize(20);
                        mTextView.setLayoutParams(params_a);
                        mTextView.setText("Loop-Thread-");
                        mTextView.setOnClickListener(LoopActivity.this);
                        mTextView.setGravity(Gravity.CENTER);
                        mTextView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ALog.i(ALog.Tag2,"LoopActivity--onCreate--currentThread().getName()--"+Thread.currentThread().getName());
                            }
                        });
                        //-----
                        Toast mToast=new Toast(getApplicationContext());
                        mToast.setView(mTextView);
                        mToast.show();
                        //-----------------
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoopActivity.this);
                        builder.setTitle("选择一个城市");
                        //    指定下拉列表的显示数据
                        final String[] cities = {"广州", "上海"};
                        //    设置一个下拉的列表选择项
                        builder.setItems(cities, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                ALog.i(ALog.Tag2,"LoopActivity--onCreate--currentThread().getName()--"+Thread.currentThread().getName());
                                String text="当前组件所在线程："+Thread.currentThread().getName();
                                Toast.makeText(LoopActivity.this, text, Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.show();
                        Looper.loop();
                    } ;
                }).start();
                break;
            case 3:
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        ALog.i(ALog.Tag2,"LoopActivity--onCreate--b--1--"+v.getId()+"->");
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(), "图片格式有误2", Toast.LENGTH_SHORT).show();
                        Looper.loop();

                        ALog.i(ALog.Tag2,"LoopActivity--onCreate--b--2--"+v.getId()+"->");
                        //---
                        /**
                         * 注：写在Looper.loop()之后的代码不会被立即执行，当调用后
                         * mHandler.getLooper().quit()后，loop才会中止，其后的代码才能得以运行。
                         * Looper对象通过MessageQueue来存放消息和事件。一个线程只能有一个Looper，
                         * 对应一个MessageQueue。以下是Android API中的一个典型的Looper thread实现：
                         */


                        TextView mTextView = new TextView(LoopActivity.this);
                        LinearLayout.LayoutParams params_a = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Tools.dip2px(LoopActivity.this, 40));
                        params_a.setMargins(0,0,0,Tools.dip2px(LoopActivity.this, 20));
                        mTextView.setBackgroundColor(LoopActivity.this.getResources().getColor(com.yunbi.utils.R.color.lighter_gray));
                        mTextView.setTextColor(LoopActivity.this.getResources().getColor(com.yunbi.utils.R.color.darker_gray));
                        mTextView.setTextSize(20);
                        mTextView.setLayoutParams(params_a);
                        mLinearLayout.addView(mTextView);
                        mTextView.setText("Loop-Thread-");
                        mTextView.setOnClickListener(LoopActivity.this);
                        mTextView.setGravity(Gravity.CENTER);

                        ALog.i(ALog.Tag2,"LoopActivity--onCreate--b--3--"+v.getId()+"->");

                    }
                }).start();
                break;
            case 4:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Handler handler = new Handler(Looper.getMainLooper()) { // 区别在这！！！！
                            @Override
                            public void handleMessage(Message msg) {
                                Toast.makeText(getApplicationContext(), "handler msg 2--Thread-Name->"+Thread.currentThread().getName(), Toast.LENGTH_LONG).show();
                            }
                        };
                        handler.sendEmptyMessage(1);
                    };
                }).start();
                break;
            case 5:
                //--第三种：在须要更新UI的代码行后加Looper.prepare();与Looper.loop();两句话就可以。如：
                new Thread() {
                    @Override
                    public void run() {
                        titleTextView.setText("在非UI线程中更新UI！");
                        Looper.prepare();
                        Looper.loop();
                    }

                }.start();
                break;
            case 6:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ALog.i(ALog.Tag2,"LoopActivity--onCreate--6--Thread.currentThread().getName()-->"+Thread.currentThread().getName());

                        Looper mLooper = Looper.myLooper();
                        ALog.i(ALog.Tag2,"LoopActivity--onCreate--6--mLooper--"+mLooper);
                        Handler mHandler = new Handler();
                        mLooper = Looper.myLooper();
                        ALog.i(ALog.Tag2,"LoopActivity--onCreate--6--mLooper--"+mLooper);

                        Looper.prepare();
                        Looper.loop();
                    };
                }).start();
                break;
            case 7:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ALog.i(ALog.Tag2,"LoopActivity--onCreate--7--Thread.currentThread().getName()-->"+Thread.currentThread().getName());
                        Toast.makeText(getApplicationContext(), "-在支线程里直接执行->"+Thread.currentThread().getName(), Toast.LENGTH_LONG).show();
                    };
                }).start();
                break;
            default:
        }
    }

    String textStr[]={
            "1支线程的Looper，handler中执行toast",
            "2在支线程的Looper，prepare，loop，之间执行toast",
            "3在支线程的Looper，prepare，loop，之间执行toast，loop的代码没被执行！",
            "4MainLooper的，handler中执行toast",
            "5在非UI线程中更新UI",
            "6不同手机不同系统不同的异常",
            "7在支线程里直接执行toast",
    };

}