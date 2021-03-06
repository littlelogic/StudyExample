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
                textTop.setText("???onCreate()(??????onStart()?????????onResume())??????UI???????????????UI,??????????????????,???????????????????????????\n"+textTop.getText());
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
                        +"\n " + "removeCallbacksAndMessages???????????????Handler?????????"
                        +"\n " + "post???sendMessage?????????????????????loop???????????????????????????"
                , new Runnable() {
            @Override
            public void run() {
                startTestHandler();
            }
        });
        addTestView(mLinearLayout, "testHandlerB_2",
                "\n " +" removeCallbacksAndMessages???????????????????????????????????????");

        addTestView(mLinearLayout, "test_1",
                "\n " +" ????????????Looper???handler?????????toast");

        addTestView(mLinearLayout, "test_2",
                "\n " +" ???????????????Looper???prepare???loop???????????????toast???dialog");

        addTestView(mLinearLayout, "test_3",
                "\n " +" ???????????????Looper???prepare???loop???????????????dialog?????????loop???????????????????????????");

        addTestView(mLinearLayout, "test_4",
                "\n " +" ??????????????????getMainLooper?????????handler????????????????????????????????????");
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
                builder.setTitle("??????????????????");
                //    ?????????????????????????????????
                final String[] cities = {"??????", "??????"};
                //    ????????????????????????????????????
                builder.setItems(cities, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        ALog.i(ALog.Tag2,"LoopActivity--onCreate--currentThread().getName()--"+Thread.currentThread().getName());
                        String text="???????????????????????????"+Thread.currentThread().getName();
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
                hTextView.setText("???????????????loop???????????????????????????");
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
                ALog.i(ALog.Tag2,"HandlerLoopActiv-test_3-run-Looper??????->");
                //---
                /**
                 * ????????????Looper.loop()???????????????????????????????????????????????????
                 * mHandler.getLooper().quit()??????loop???????????????????????????????????????????????????
                 * Looper????????????MessageQueue??????????????????????????????????????????????????????Looper???
                 * ????????????MessageQueue????????????Android API?????????????????????Looper thread?????????
                 */
                try {
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(HandlerLoopActiv.this);
                    TextView hTextView2 = getTextView();
                    hTextView2.setText("mHandler.getLooper().quit()?????????????????????");
                    builder2.setView(hTextView2);
                    builder2.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandlerB.post(new Runnable() {
                        @Override
                        public void run() {
                            String text_str = "View???????????????Looper.prepare()???Looper.loop()?????????????????????????????????";
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
                Handler handler = new Handler(Looper.getMainLooper()) { // ????????????????????????
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


    // ????????????????????????????????????WMS???
    private void installFloatingWindow() {
        // ??? ????????????WindowManager??????
        final WindowManager wm =
                (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        // ??? ????????????????????????
        final Button btn = new Button(this.getBaseContext());
        btn.setText("Click me to dismiss!");
        // ??? ????????????WindowManager.LayoutParams?????????????????????????????????????????????
        WindowManager.LayoutParams lp = createLayoutParams();
        // ??? ??????WindowManager.addView()???????????????????????????????????????????????????
        wm.addView(btn, lp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ?????????????????????????????????????????????????????????
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
