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
        titleTextView.setText("Loop-Thread-test\n?????????ui?????????????????????loop???prepare???loop????????????????????????");
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
                titleTextView.setText("???onCreate()(may??????onStart()???may??????onResume())??????UI???????????????UI,??????????????????\n"+titleTextView.getText());
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
                        builder.setTitle("??????????????????");
                        //    ?????????????????????????????????
                        final String[] cities = {"??????", "??????"};
                        //    ????????????????????????????????????
                        builder.setItems(cities, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                ALog.i(ALog.Tag2,"LoopActivity--onCreate--currentThread().getName()--"+Thread.currentThread().getName());
                                String text="???????????????????????????"+Thread.currentThread().getName();
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
                        Toast.makeText(getApplicationContext(), "??????????????????2", Toast.LENGTH_SHORT).show();
                        Looper.loop();

                        ALog.i(ALog.Tag2,"LoopActivity--onCreate--b--2--"+v.getId()+"->");
                        //---
                        /**
                         * ????????????Looper.loop()???????????????????????????????????????????????????
                         * mHandler.getLooper().quit()??????loop???????????????????????????????????????????????????
                         * Looper????????????MessageQueue??????????????????????????????????????????????????????Looper???
                         * ????????????MessageQueue????????????Android API?????????????????????Looper thread?????????
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
                        Handler handler = new Handler(Looper.getMainLooper()) { // ????????????????????????
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
                //--???????????????????????????UI??????????????????Looper.prepare();???Looper.loop();???????????????????????????
                new Thread() {
                    @Override
                    public void run() {
                        titleTextView.setText("??????UI???????????????UI???");
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
                        Toast.makeText(getApplicationContext(), "-???????????????????????????->"+Thread.currentThread().getName(), Toast.LENGTH_LONG).show();
                    };
                }).start();
                break;
            default:
        }
    }

    String textStr[]={
            "1????????????Looper???handler?????????toast",
            "2???????????????Looper???prepare???loop???????????????toast",
            "3???????????????Looper???prepare???loop???????????????toast???loop????????????????????????",
            "4MainLooper??????handler?????????toast",
            "5??????UI???????????????UI",
            "6???????????????????????????????????????",
            "7???????????????????????????toast",
    };

}