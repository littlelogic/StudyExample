package com.study.RxAndroid;
//com.study.RxAndroid.HandlerLoopActiv
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.badlogic.utils.ALog;
import com.badlogic.utils.Tools;
import com.example.wujiawen.ExampleLoopThread.LoopActivity;

import java.lang.reflect.Method;

public class HandlerLoopActiv extends Activity {

    int dp_5;
    String Tag = "handler";
    String TAG = Tag;
    TextView textTop;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout mRelativeLayout = new RelativeLayout(this);
        mRelativeLayout.setBackgroundColor(Color.WHITE);
        this.setContentView(mRelativeLayout);

        ScrollView mScrollView = new ScrollView(this);
        mRelativeLayout.addView(mScrollView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        final LinearLayout mLinearLayout = new LinearLayout(this);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mLinearLayout.setBackgroundColor(Color.WHITE);
        mScrollView.addView(mLinearLayout, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ///-------------
        dp_5 = Tools.dip2px(this,5);
        textTop = addTestView(mLinearLayout, "","textTop");
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                textTop = addTestView(mLinearLayout, "","textTop");
                textTop.setText("在onCreate()(或者onStart()，或者onResume())里非UI线程中更新UI,只是刷新属性,因为还没有进行绘制\n"+textTop.getText());
                textTop.setTextColor(Color.BLUE);
            }
        }.start();
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ///-------------
        addTestView(mLinearLayout, "testHandlerA "
                        +"\n " + "removeCallbacksAndMessages只清空当前Handler的消息"
                        +"\n " + "post和sendMessage消息，会在下次loop循环的时候开始执行"
                , new Runnable() {
            @Override
            public void run() {
                startTestHandler();
            }
        });
        addTestView(mLinearLayout, "testHandlerB_2",
                "\n " +" removeCallbacksAndMessages的清空不影响后续批次的消息");

        addTestView(mLinearLayout, "test_1",
                "\n " +" 支线程的Looper，handler中执行toast");

        addTestView(mLinearLayout, "test_2",
                "\n " +" 在支线程的Looper，prepare，loop，之间执行toast和dialog");

        addTestView(mLinearLayout, "test_3",
                "\n " +" 在支线程的Looper，prepare，loop，之间执行dialog，结束loop，执行其后面的代码");

        addTestView(mLinearLayout, "test_4",
                "\n " +" 在支线程的用getMainLooper创建的handler，消息的投递是在主线程中");
    }

    public TextView addTestView(LinearLayout mLinearLayout,String text,final Runnable hRunnable){
        TextView hTextView_1 = new TextView(this);
        LinearLayout.LayoutParams ParamsTop_1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mLinearLayout.addView(hTextView_1);
        hTextView_1.setPadding(0,dp_5,0,dp_5);
        ParamsTop_1.topMargin = Tools.dip2px(this,10);
        hTextView_1.setLayoutParams(ParamsTop_1);
        hTextView_1.setGravity(Gravity.CENTER);
        hTextView_1.setTextColor(Color.BLACK);
        hTextView_1.setBackground(Tools.getCornerDrawable(Color.LTGRAY,0));
        hTextView_1.setText(text);
        hTextView_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hRunnable.run();
            }
        });
        return hTextView_1;
    }

    public TextView addTestView(LinearLayout mLinearLayout,final String methodName,String text){
        TextView hTextView_1 = new TextView(this);
        LinearLayout.LayoutParams ParamsTop_1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mLinearLayout.addView(hTextView_1);
        hTextView_1.setPadding(0,dp_5,0,dp_5);
        ParamsTop_1.topMargin = Tools.dip2px(this,10);
        hTextView_1.setLayoutParams(ParamsTop_1);
        hTextView_1.setGravity(Gravity.CENTER);
        hTextView_1.setTextColor(Color.BLACK);
        hTextView_1.setBackground(Tools.getCornerDrawable(Color.LTGRAY,0));
        hTextView_1.setText(methodName + " " +text);
        hTextView_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Method hMethod = HandlerLoopActiv.class.getDeclaredMethod(methodName);
                    hMethod.setAccessible(true);
                    hMethod.invoke(HandlerLoopActiv.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return hTextView_1;
    }

    public TextView getTextView(){
        TextView hTextView_1 = new TextView(this);
        ViewGroup.LayoutParams ParamsTop_1 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        hTextView_1.setPadding(0,dp_5,0,dp_5);
        hTextView_1.setLayoutParams(ParamsTop_1);
        hTextView_1.setGravity(Gravity.CENTER);
        hTextView_1.setTextColor(Color.BLACK);
        hTextView_1.setBackground(Tools.getCornerDrawable(Color.LTGRAY,0));
        return hTextView_1;
    }


    ///============================================================================


    Looper  mLooper = null;
    Handler mHandlerA = new Handler(Looper.getMainLooper());
    Handler mHandlerB = new Handler(Looper.getMainLooper()){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            ALog.i(ALog.Tag2,"HandlerLoopActiv-mHandlerB-dispatchMessage"
                    + "-msg.what->"+ msg.what
                    + "-msg.getCallback->"+ msg.getCallback()
            );
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ALog.i(ALog.Tag2,"HandlerLoopActiv-mHandlerB-handleMessage"
                    + "-msg.what->"+ msg.what
            );
        }
    };

    private void startTestHandler(){
        testHandlerA();
        testHandlerB();
    }

    int testHandlerA_num = 0;
    private void testHandlerA(){
        ALog.w(ALog.Tag2,"HandlerLoopActiv-testHandlerA-testHandlerA_num->"+(testHandlerA_num++));
        mHandlerA.postDelayed(new Runnable() {
            @Override
            public void run() {
                ALog.w(ALog.Tag2,"HandlerLoopActiv-testHandlerA-postDelayed-testHandlerA_num->"+testHandlerA_num);
                if (testHandlerA_num < 11) {
                    testHandlerA();
                } else {
                    ALog.w(ALog.Tag2,"HandlerLoopActiv-testHandlerA-postDelayed-stop->");
                }
            }
        },500);
    }

    Runnable HandlerB_Runnable_C = new Runnable() {
        @Override
        public void run() {
            ALog.i(ALog.Tag2,"HandlerLoopActiv-testHandlerB-postDelayed-C->");
        }
    };
    Runnable HandlerB_Runnable_D = new Runnable() {
        @Override
        public void run() {
            ALog.i(ALog.Tag2,"HandlerLoopActiv-testHandlerB-postDelayed-D->");
            mHandlerB.removeCallbacksAndMessages(null);
        }
    };

    private void testHandlerB(){
        mHandlerB.postDelayed(new Runnable() {
            @Override
            public void run() {
                ALog.i(ALog.Tag2,"HandlerLoopActiv-testHandlerB-postDelayed-100->");
                ///mHandlerB.removeCallbacksAndMessages(null);
            }
        },100);
        mHandlerB.postDelayed(new Runnable() {
            @Override
            public void run() {
                mHandlerB.post(new Runnable() {
                    @Override
                    public void run() {
                        ALog.i(ALog.Tag2,"HandlerLoopActiv-testHandlerB-postDelayed-A->");
                    }
                });

                Message HandlerB_Message_B = new Message();
                HandlerB_Message_B.what = 3003;
                mHandlerB.sendMessage(HandlerB_Message_B);

                mHandlerB.removeCallbacksAndMessages(null);

                mHandlerB.post(HandlerB_Runnable_C);
                mHandlerB.postDelayed(HandlerB_Runnable_D,2000);

                Message HandlerB_Message_E = new Message();
                HandlerB_Message_E.what = 3001;
                HandlerB_Message_E.getCallback();
                mHandlerB.sendMessage(HandlerB_Message_E);

                Message HandlerB_Message_F = Message.obtain(mHandlerB, new Runnable() {
                    @Override
                    public void run() {
                        ALog.i(ALog.Tag2,"HandlerLoopActiv-testHandlerB-Runnable-F->");
                    }
                });
                HandlerB_Message_F.what = 3005;
                mHandlerB.sendMessage(HandlerB_Message_F);

                Message HandlerB_Message_G = Message.obtain(mHandlerB, new Runnable() {
                    @Override
                    public void run() {
                        ALog.i(ALog.Tag2,"HandlerLoopActiv-testHandlerB-sendMessage-G->");
                    }
                });

            }
        },2000);

        mHandlerB.postDelayed(new Runnable() {
            @Override
            public void run() {
                ALog.i(ALog.Tag2,"HandlerLoopActiv-testHandlerB-postDelayed-2200->");
                mHandlerB.removeCallbacksAndMessages(null);
            }
        },2200);
    }

    private void testHandlerB_2(){
       
        mHandlerB.postDelayed(new Runnable() {
            @Override
            public void run() {
                ALog.i(ALog.Tag2,"HandlerLoopActiv-testHandlerB_2-postDelayed-200->");
                ///mHandlerB.removeCallbacksAndMessages(null);
            }
        },200);
    }

    ///============================================================================

    private void test_1() {
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
    }

    private void test_2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                //-----------------
                TextView mTextView = new TextView(HandlerLoopActiv.this);
                LinearLayout.LayoutParams params_a = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        Tools.dip2px(HandlerLoopActiv.this, 40));
                params_a.setMargins(0,0,0,Tools.dip2px(HandlerLoopActiv.this, 20));
                mTextView.setBackgroundColor(HandlerLoopActiv.this.getResources().getColor(com.yunbi.utils.R.color.lighter_gray));
                mTextView.setTextColor(HandlerLoopActiv.this.getResources().getColor(com.yunbi.utils.R.color.darker_gray));
                mTextView.setTextSize(20);
                mTextView.setLayoutParams(params_a);
                mTextView.setText("Loop-Thread-");
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
                AlertDialog.Builder builder = new AlertDialog.Builder(HandlerLoopActiv.this);
                builder.setTitle("选择一个城市");
                //    指定下拉列表的显示数据
                final String[] cities = {"广州", "上海"};
                //    设置一个下拉的列表选择项
                builder.setItems(cities, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        ALog.i(ALog.Tag2,"LoopActivity--onCreate--currentThread().getName()--"+Thread.currentThread().getName());
                        String text="当前组件所在线程："+Thread.currentThread().getName();
                        Toast.makeText(HandlerLoopActiv.this, text, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                Looper.loop();
            } ;
        }).start();
    }

    private void test_3() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ALog.i(ALog.Tag2,"HandlerLoopActiv-test_3-run-01->");



                /*Looper.prepare();
                if (true) {
                    return;
                }*/


                AlertDialog.Builder builder = new AlertDialog.Builder(HandlerLoopActiv.this);
                TextView hTextView = getTextView();
                hTextView.setText("点击结束此loop，执行其后面的代码");
                builder.setView(hTextView);


                ///Toast.makeText(getApplicationContext(),"----",Toast.LENGTH_SHORT).show();


                /*if (true) {
                    return;
                }*/

                ALog.i(ALog.Tag2,"HandlerLoopActiv-test_3-run-Looper.myLooper()->"+Looper.myLooper());
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                final AlertDialog hAlertDialog =  builder.show();

                {
//                    final AlertDialog hAlertDialog =  builder.show();
                    hTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Looper.myLooper().quit();
                            hAlertDialog.dismiss();
                        }
                    });
                }
                ///-- installFloatingWindow();

                ALog.i(ALog.Tag2,"HandlerLoopActiv-test_3-run-02->");
                Looper.loop();
                ALog.i(ALog.Tag2,"HandlerLoopActiv-test_3-run-Looper后面->");
                //---
                /**
                 * 注：写在Looper.loop()之后的代码不会被立即执行，当调用后
                 * mHandler.getLooper().quit()后，loop才会中止，其后的代码才能得以运行。
                 * Looper对象通过MessageQueue来存放消息和事件。一个线程只能有一个Looper，
                 * 对应一个MessageQueue。以下是Android API中的一个典型的Looper thread实现：
                 */
                try {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(HandlerLoopActiv.this);
                    TextView hTextView2 = getTextView();
                    hTextView2.setText("mHandler.getLooper().quit()结束此后的显示");
                    builder2.setView(hTextView2);
                    builder2.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandlerB.post(new Runnable() {
                        @Override
                        public void run() {
                            String text_str = "View的显示要在Looper.prepare()和Looper.loop()之间才可以，不然会报错";
                            ALog.i(ALog.Tag2,"HandlerLoopActiv-test_3-run-79-text_str->"+text_str);
                            Toast.makeText(HandlerLoopActiv.this, text_str, Toast.LENGTH_LONG).show();
                        }
                    });
                }
                ALog.i(ALog.Tag2,"HandlerLoopActiv-test_3-run-99->");
            }
        }).start();
    }

    private void test_4() {
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
    }

    ///============================================================================

    public class MyHandlerThread extends HandlerThread {
        private Handler mHandler;

        public MyHandlerThread() {
            super("MyHandlerThread", 1);
        }

        @Override
        protected void onLooperPrepared() {
            super.onLooperPrepared();
            mHandler = new Handler(getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 1:
                            // Handle message
                            break;
                        case 2:
                            // Handle message
                            break;
                    }
                }
            };
        }

        public void publishedMethod1() {
            mHandler.sendEmptyMessage(1);
        }

        public void publishedMethod2() {
            mHandler.sendEmptyMessage(2);
        }
    }

    ///============================================================================

    Handler mHandlerC = new Handler(Looper.getMainLooper());

    @SuppressLint("NewApi")
    private void test_5() {
        ///---mHandlerC = new Handler(Looper.getMainLooper(),null,true);

        Message hMessage1 = new Message();
        hMessage1.what = 5001;
        hMessage1.setAsynchronous(true);
        mHandlerC.sendMessage(hMessage1);
        mHandlerC.getLooper().quitSafely();

    }


    // 将按钮作为一个窗口添加到WMS中
    private void installFloatingWindow() {
        // ① 获取一个WindowManager实例
        final WindowManager wm =
                (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        // ② 新建一个按钮控件
        final Button btn = new Button(this.getBaseContext());
        btn.setText("Click me to dismiss!");
        // ③ 生成一个WindowManager.LayoutParams，用以描述窗口的类型与位置信息
        WindowManager.LayoutParams lp = createLayoutParams();
        // ④ 通过WindowManager.addView()方法将按钮作为一个窗口添加到系统中
        wm.addView(btn, lp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ⑤当用户点击按钮时，将按钮从系统中删除
                wm.removeView(btn);
                ///stopSelf();
            }
        });
    }
    private WindowManager.LayoutParams createLayoutParams() {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.type = WindowManager.LayoutParams.TYPE_PHONE;
        lp.gravity = Gravity.CENTER;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        return lp;
    }

    @SuppressLint("NewApi")
    private void test_6() {

        Message msg = mHandlerC.obtainMessage();
        msg.isAsynchronous();
        msg.setAsynchronous(true);
        mHandlerC.sendMessage(msg);



//        mHandlerC.getLooper().getQueue().postSyncBarrier();




    }



}
