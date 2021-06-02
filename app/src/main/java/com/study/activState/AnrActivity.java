package com.study.activState;
// com.study.activState.AnrActivity
import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.util.Printer;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.badlogic.utils.Tools;

import java.lang.reflect.Method;

public class AnrActivity extends Activity {


    private static final String TAG = "22";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout mLinearLayout = new LinearLayout(this);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        this.setContentView(mLinearLayout);

        TextView mTextView = new TextView(this);
        mTextView.setPadding(0,30,0,30);
        mTextView.setBackgroundColor(Color.argb(44,255,0,0));
        mTextView.setText("anr-检测");
        LinearLayout.LayoutParams Params_mTextView = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        Params_mTextView.topMargin = 40;
        mLinearLayout.addView(mTextView,Params_mTextView);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("11","-AnrActivity-onCreate-onClick-01>");


                /*try {
                    Thread.sleep(7 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

                long last_time = System.currentTimeMillis();
                for (;;) {
                    if (System.currentTimeMillis() - last_time > 1000 * 10) {
                        break;
                    } else {
                        Tools.getCornerDrawable(Color.WHITE,23);
                    }
                }


                Log.i("11","-AnrActivity-onCreate-onClick-99>");
            }
        });

        TextView mTextView2 = new TextView(this);
        mTextView2.setPadding(0,30,0,30);
        mTextView2.setBackgroundColor(Color.argb(44,255,0,0));
        mTextView2.setText("click-检测");
        LinearLayout.LayoutParams Params_mTextView2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        Params_mTextView2.topMargin = 40;
        mLinearLayout.addView(mTextView2, Params_mTextView2);
        mTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("11","-AnrActivity-onCreate-click-检测-01>");


//                Log.i("11","-AnrActivity-onCreate-click-检测-99>");
            }
        });

        TextView mTextView3 = new TextView(this);
        mTextView3.setPadding(0,30,0,30);
        mTextView3.setBackgroundColor(Color.argb(44,255,0,0));
        mTextView3.setText("悬浮窗-检测");
        LinearLayout.LayoutParams Params_mTextView3 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        Params_mTextView3.topMargin = 40;
        mLinearLayout.addView(mTextView3, Params_mTextView3);
        mTextView3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                showFloatingWindow(Thread.currentThread());
            }
        });

        this.getMainLooper().setMessageLogging(new MyPrinter());

        timingCheck(Thread.currentThread());
        new Handler(Looper.getMainLooper()).post(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {//循环套循环
                            try {
                                /*if (timingCheckMark) {
                                    timingCheck(Thread.currentThread());
                                }*/
                                Log.i("11","-AnrActivity-onCreate-post-01->");
                                Looper.loop();//主线程的异常会从这里抛出
                                Log.i("11","-AnrActivity-onCreate-post-99->");
                            } catch (Throwable e) {
                            }}}});


        new Handler(Looper.getMainLooper()).post(new  Runnable(){
            @Override
            public void run() {

            }
        });


    }


    static Runnable mMyRunnable = new  Runnable(){
        @Override
        public void run() {
            ///--AnrActivity.this.targetHandler = null;
        }
    };


    static class  MyRunnable implements Runnable{
        @Override
        public void run() {

        }
    }

    class MyPrinter implements Printer {
        @Override
        public void println(String x) {
            Log.i("11","-AnrActivity-MyPrinter-println-x->"+x);
        }
    }

    ///========================================================

    volatile boolean timingCheckMark = true;
    Thread timingCheckThread;
    Handler targetHandler = new Handler(Looper.getMainLooper());
    private Boolean checkMsgFinish = true;

    public boolean isCheckMsgFinish() {
        synchronized (checkMsgFinish) {
            return checkMsgFinish;
        }
    }

    public void setCheckMsgFinish(boolean checkMsgFinish_) {
        synchronized (checkMsgFinish) {
            checkMsgFinish = checkMsgFinish_;
        }
    }

    void timingCheck(Thread targetThread){
        timingCheckThread =  new Thread(){
            @Override
            public void run() {
                super.run();
                for (;;) {
                    try {
                        setCheckMsgFinish(false);
                        targetHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                setCheckMsgFinish(true);
                            }
                        });
                        Thread.sleep(5 * 1000);
                        Log.e("11","-AnrActivity-timingCheck-run-"
                                + "-isCheckMsgFinish()->"+isCheckMsgFinish()
                        );
                        if (isCheckMsgFinish()) {
                        } else {
                            //todo 打印主线程的调用栈信息
                            for (StackTraceElement item : targetThread.getStackTrace()) {
                                Log.e("11","-AnrActivity-timingCheck-run-"
                                        + "-getClassName->"+item.getClassName()
                                        + "-getMethodName->"+item.getMethodName()
                                        + "-getLineNumber->"+item.getLineNumber()
                                        + "-item.toString()->"+item.toString()
                                );
                            }
                            ///--return;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Log.i("11","-AnrActivity-timingCheck-run-"
                                + "-InterruptedException->"+e.getMessage()
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i("11","-AnrActivity-timingCheck-run-"
                                + "-InterruptedException->"+e.getMessage()
                        );
                    }
                }

            }
        };
        timingCheckThread.start();

    }

    ///========================================================

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showFloatingWindow(Thread targetThread) {
        if (Settings.canDrawOverlays(this)) {

            new Thread(){
                @Override
                public void run() {
                    super.run();
                    Looper.prepare();


                    // 获取WindowManager服务
                    WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

                    // 新建悬浮窗控件
                    Button button = new Button(getApplicationContext());
                    button.setText("Floating Window");
                    button.setBackgroundColor(Color.BLUE);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i("11","-AnrActivity-showFloatingWindow-onClick-01->");
                            //todo 打印主线程的调用栈信息
                            for (StackTraceElement item : targetThread.getStackTrace()) {
                                Log.e("11","-AnrActivity-showFloatingWindow-onClick-"
                                        + "-getLineNumber->"+item.getLineNumber()
                                        + "-item.toString()->"+item.toString()
                                );
                            }
                        }
                    });

                    // 设置LayoutParam
                    final int layoutParamType = Build.VERSION.SDK_INT < Build.VERSION_CODES.O
                            ? WindowManager.LayoutParams.TYPE_PHONE
                            : WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                    final int flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                            | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
                    final int IDLE_WINDOW_FLAGS =
                            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                    | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                                    | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;

                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.type = layoutParamType;
                    layoutParams.flags = IDLE_WINDOW_FLAGS;
                    layoutParams.format = PixelFormat.RGBA_8888;
                    layoutParams.width = 500;
                    layoutParams.height = 100;
                    layoutParams.x = 300;
                    layoutParams.y = 300;
                    // 将悬浮窗控件添加到WindowManager
                    windowManager.addView(button, layoutParams);


                    Looper.loop();
                }
            }.start();

        } else {
            SettingsCompat.manageDrawOverlays(this);
        }
    }

    public static void manageDrawOverlays(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (manageDrawOverlaysForRom(context)) {
                return;
            }
        }
    }

    private static boolean manageDrawOverlaysForRom(Context context) {
        if (RomUtil.isMiui()) {
            return manageDrawOverlaysForMiui(context);
        }
        if (RomUtil.isEmui()) {
            return manageDrawOverlaysForEmui(context);
        }
        if (RomUtil.isFlyme()) {
            return manageDrawOverlaysForFlyme(context);
        }
        if (RomUtil.isOppo()) {
            return manageDrawOverlaysForOppo(context);
        }
        if (RomUtil.isVivo()) {
            return manageDrawOverlaysForVivo(context);
        }
        if (RomUtil.isQiku()) {
            return manageDrawOverlaysForQihu(context);
        }
        if (RomUtil.isSmartisan()) {
            return manageDrawOverlaysForSmartisan(context);
        }
        return false;
    }

    private static boolean checkOp(Context context, int op) {
        AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        try {
            Method method = AppOpsManager.class.getDeclaredMethod("checkOp", int.class, int.class, String.class);
            return AppOpsManager.MODE_ALLOWED == (int) method.invoke(manager, op, Binder.getCallingUid(), context.getPackageName());
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        return false;
    }

    // 可设置Android 4.3/4.4的授权状态
    private static boolean setMode(Context context, int op, boolean allowed) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2 || Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return false;
        }

        AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        try {
            Method method = AppOpsManager.class.getDeclaredMethod("setMode", int.class, int.class, String.class, int.class);
            method.invoke(manager, op, Binder.getCallingUid(), context.getPackageName(), allowed ? AppOpsManager.MODE_ALLOWED : AppOpsManager
                    .MODE_IGNORED);
            return true;
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));

        }
        return false;
    }

    private static boolean startSafely(Context context, Intent intent) {
        if (context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } else {
            Log.e(TAG, "Intent is not available! " + intent);
            return false;
        }
    }

    // 小米
    private static boolean manageDrawOverlaysForMiui(Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.putExtra("extra_pkgname", context.getPackageName());
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        if (startSafely(context, intent)) {
            return true;
        }
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
        if (startSafely(context, intent)) {
            return true;
        }
        // miui v5 的支持的android版本最高 4.x
        // http://www.romzj.com/list/search?keyword=MIUI%20V5#search_result
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Intent intent1 = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent1.setData(Uri.fromParts("package", context.getPackageName(), null));
            return startSafely(context, intent1);
        }
        return false;
    }

    private final static String HUAWEI_PACKAGE = "com.huawei.systemmanager";

    // 华为
    private static boolean manageDrawOverlaysForEmui(Context context) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.setClassName(HUAWEI_PACKAGE, "com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity");
            if (startSafely(context, intent)) {
                return true;
            }
        }
        // Huawei Honor P6|4.4.4|3.0
        intent.setClassName(HUAWEI_PACKAGE, "com.huawei.notificationmanager.ui.NotificationManagmentActivity");
        intent.putExtra("showTabsNumber", 1);
        if (startSafely(context, intent)) {
            return true;
        }
        intent.setClassName(HUAWEI_PACKAGE, "com.huawei.permissionmanager.ui.MainActivity");
        if (startSafely(context, intent)) {
            return true;
        }
        return false;
    }

    // VIVO
    private static boolean manageDrawOverlaysForVivo(Context context) {
        // 不支持直接到达悬浮窗设置页，只能到 i管家 首页
        Intent intent = new Intent("com.iqoo.secure");
        intent.setClassName("com.iqoo.secure", "com.iqoo.secure.MainActivity");
        // com.iqoo.secure.ui.phoneoptimize.SoftwareManagerActivity
        // com.iqoo.secure.ui.phoneoptimize.FloatWindowManager
        return startSafely(context, intent);
    }

    // OPPO
    private static boolean manageDrawOverlaysForOppo(Context context) {
        Intent intent = new Intent();
        intent.putExtra("packageName", context.getPackageName());
        // OPPO A53|5.1.1|2.1
        intent.setAction("com.oppo.safe");
        intent.setClassName("com.oppo.safe", "com.oppo.safe.permission.floatwindow.FloatWindowListActivity");
        if (startSafely(context, intent)) {
            return true;
        }
        // OPPO R7s|4.4.4|2.1
        intent.setAction("com.color.safecenter");
        intent.setClassName("com.color.safecenter", "com.color.safecenter.permission.floatwindow.FloatWindowListActivity");
        if (startSafely(context, intent)) {
            return true;
        }
        intent.setAction("com.coloros.safecenter");
        intent.setClassName("com.coloros.safecenter", "com.coloros.safecenter.sysfloatwindow.FloatWindowListActivity");
        return startSafely(context, intent);
    }

    // 魅族
    private static boolean manageDrawOverlaysForFlyme(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.setClassName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity");
        intent.putExtra("packageName", context.getPackageName());
        return startSafely(context, intent);
    }

    // 360
    private static boolean manageDrawOverlaysForQihu(Context context) {
        Intent intent = new Intent();
        intent.setClassName("com.android.settings", "com.android.settings.Settings$OverlaySettingsActivity");
        if (startSafely(context, intent)) {
            return true;
        }
        intent.setClassName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
        return startSafely(context, intent);
    }

    // 锤子
    private static boolean manageDrawOverlaysForSmartisan(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 锤子 坚果|5.1.1|2.5.3
            Intent intent = new Intent("com.smartisanos.security.action.SWITCHED_PERMISSIONS_NEW");
            intent.setClassName("com.smartisanos.security", "com.smartisanos.security.SwitchedPermissions");
            intent.putExtra("index", 17); // 不同版本会不一样
            return startSafely(context, intent);
        } else {
            // 锤子 坚果|4.4.4|2.1.2
            Intent intent = new Intent("com.smartisanos.security.action.SWITCHED_PERMISSIONS");
            intent.setClassName("com.smartisanos.security", "com.smartisanos.security.SwitchedPermissions");
            intent.putExtra("permission", new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW});

            //        Intent intent = new Intent("com.smartisanos.security.action.MAIN");
            //        intent.setClassName("com.smartisanos.security", "com.smartisanos.security.MainActivity");
            return startSafely(context, intent);
        }
    }


}
