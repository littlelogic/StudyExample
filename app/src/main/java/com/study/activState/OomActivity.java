package com.study.activState;
// com.study.activState.OomActivity
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
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Printer;
import android.view.Choreographer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.badlogic.utils.ALog;
import com.badlogic.utils.Tools;
import com.example.wujiawen.a_Main.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class OomActivity extends Activity implements View.OnClickListener{

    private static final String ERROR_HINT = "Error ! please input a number in upper EditText First";
    private static final String TAG = "wjw";
    public static final float UNIT_M = 1024 * 1024;
    private TextView dashboard;
    private EditText etDigtal;
    private int digtal=-1;
    private List<byte[]> heap=new ArrayList<>();
    private Runnable increaseFDRunnable=new Runnable() {
        @Override
        public void run() {
            try {
                new BufferedReader(new FileReader("/proc/"+ Process.myPid()+"/status"));
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    };
    private Runnable emptyRunnable=new Runnable() {
        @Override
        public void run() {
            try{
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oom);
        dashboard = (TextView) findViewById(R.id.tv_dashboard);
        etDigtal=(EditText)findViewById(R.id.et_digtal);
        findViewById(R.id.bt1).setOnClickListener(this);
        findViewById(R.id.bt1a).setOnClickListener(this);
        findViewById(R.id.bt1b).setOnClickListener(this);
        findViewById(R.id.bt2).setOnClickListener(this);
        findViewById(R.id.bt3).setOnClickListener(this);
        findViewById(R.id.bt4).setOnClickListener(this);
        findViewById(R.id.bt5).setOnClickListener(this);
        findViewById(R.id.bt6).setOnClickListener(this);
        findViewById(R.id.bt7).setOnClickListener(this);
        findViewById(R.id.bt8).setOnClickListener(this);
        ///--
        printThread();
    }

    private void test() {
        try {
            Debug.dumpHprofData("/sdcard/11/dumpHprofData.hprof");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "-OomActivity-test"
                    + "-e.printStackTrace()->"+e.toString()
            );
        }


//        // 1.构建内存映射的 HprofBuffer 针对大文件的一种快速的读取方式，其原理是将文件流的通道与  ByteBuffer 建立起关联，并只在真正发生读取时才从磁盘读取内容出来。
//        HprofBuffer buffer = new MemoryMappedFileBuffer(heapDumpFile);
//// 2.构造 Hprof 解析器
//        HprofParser parser = new HprofParser(buffer);
//// 3.获取快照
//        Snapshot snapshot = parser.parse();
//// 4.去重 gcRoots
//        deduplicateGcRoots(snapshot);
    }

    @Override
    public void onClick(View view) {
        try{
            digtal=Integer.valueOf(etDigtal.getText().toString());
        }catch (Exception e){
            digtal=-1;
        }
        switch (view.getId()) {
            case R.id.bt1:
                showFileContent("/proc/"+ Process.myPid()+"/limits");

                break;
            case R.id.bt1a:
                String text_sr = "/proc/sys/kernel/threads-max";
                File threadsFile = new File(text_sr);

//                Tools.getFileContent(threadsFile);
                dashboard.setText(  readFile(threadsFile));

                showFileContent(text_sr);

//                File threadsFile22 = new File("/proc/sys/kernel/");

//                runtimExec("cat /proc/sys/kernel/threads-max");

                break;
            case R.id.bt1b:
                test();
                break;
            case R.id.bt2:
                if (digtal<=0){
                    dashboard.setText(ERROR_HINT);
                }else {
                    for (int i=0;i<digtal;i++){
                        new Thread(increaseFDRunnable).start();
                    }
                }
                break;
            case R.id.bt3:
                File fdFile=new File("/proc/" + Process.myPid() + "/fd");
                File[] files = fdFile.listFiles();
                if (files!=null){
                    dashboard.setText("current FD numbler is "+files.length);
                }else{
                    dashboard.setText("/proc/pid/fd is empty ");
                }
                break;
            case R.id.bt4:
                ///todo  /proc/pid/status中threads项（当前线程数目）
                showFileContent("/proc/"+ Process.myPid()+"/status");
                break;
            case R.id.bt5:
                if (digtal<=0){
                    dashboard.setText(ERROR_HINT);
                }else {
                    for (int i=0;i<digtal;i++){
                        new Thread(emptyRunnable).start();
                    }
                }
                break;
            case R.id.bt6:
                StringBuilder stringBuilder=new StringBuilder();
                stringBuilder.append("Java Heap Max : ").append(Runtime.getRuntime().maxMemory()/UNIT_M).append(" MB\r\n");
                stringBuilder.append("Current used  : ").append((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/UNIT_M).append(" MB\r\n");
                dashboard.setText(stringBuilder.toString());
                break;
            case R.id.bt7:
                if (digtal<=0){
                    dashboard.setText(ERROR_HINT);
                }else {
                    byte[] bytes = new byte[digtal];
                    heap.add(bytes);
                }
                break;
            case R.id.bt8:
                heap=new ArrayList<>();
                System.gc();
                break;
        }
    }

    public static String getFileContent(File file) {
        String content = "";
        if (!file.isDirectory()) {  //检查此路径名的文件是否是一个目录(文件夹)
            if (file.getName().endsWith("txt")) {//文件格式为""文件
                try {
                    InputStream instream = new FileInputStream(file);
                    if (instream != null) {
                        InputStreamReader inputreader
                                = new InputStreamReader(instream, "UTF-8");
                        BufferedReader buffreader = new BufferedReader(inputreader);
                        String line = "";
                        //分行读取
                        while ((line = buffreader.readLine()) != null) {
                            content += line + "\n";
                        }
                        instream.close();//关闭输入流
                    }
                } catch (java.io.FileNotFoundException e) {
                    Log.d("TestFile", "The File doesn't not exist.");
                } catch (IOException e) {
                    Log.d("TestFile", e.getMessage());
                }
            }
        }
        return content;
    }

    public static String readFile(File srcFile) {
        if (!srcFile.exists() || !srcFile.canRead()) {
            return "";
        }
        FileInputStream inputStream = null;
        StringBuilder sb = new StringBuilder("");
        try {
            //获得原文件流
            inputStream = new FileInputStream(srcFile);
            byte[] data = new byte[4096];
            //输出流开始处理流
            int byteCount = 0;
            while ((byteCount=inputStream.read(data)) != -1) {
                ALog.i(ALog.Tag2,"CopyAccoutFragment--getAccountData--byteCount->"+byteCount);
                sb.append(new String(data, 0, byteCount));
            }
        }catch (Throwable e) {
            Log.d(TAG, "-OomActivity-readFile"
                    + "-e.toString()->"+e.toString()
            );
            return "";
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    inputStream = null;
                }catch (Throwable e) {}
            }
        }
        return sb.toString();
    }

    private void runtimExec(String exec_str) {
        Runtime runtime = Runtime.getRuntime();
        java.lang.Process ipProcess = null;
        try {
            //-c 后边跟随的是重复的次数，-w后边跟随的是超时的时间，单位是秒，不是毫秒，
            ipProcess = runtime.exec(exec_str);
            InputStream input = ipProcess.getInputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                ///------
                stringBuffer.append(content);
            }
            System.out.println("runtime.exec ping: "+stringBuffer);
            ///int exitValue = ipProcess.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ipProcess != null) {
                ipProcess.destroy();
            }
            runtime.gc();
        }
    }

    private void printThread() {

        ThreadGroup hThreadGroup = Thread.currentThread().getThreadGroup();
        Log.d(TAG, "-OomActivity-printThread"
                + "-hThreadGroup.activeCount()->"+hThreadGroup.activeCount()
                + "-hThreadGroup.activeGroupCount()->"+hThreadGroup.activeGroupCount()
        );

        Thread.currentThread().getAllStackTraces();
        Map<Thread, StackTraceElement[]> stacks = Thread.getAllStackTraces();
        Set<Thread> set = stacks.keySet();
        for (Thread key : set) {
            StackTraceElement[] stackTraceElements = stacks.get(key);
            Log.d(TAG, "-OomActivity-printThread---- print thread: " + key.getName() + " start ----");
            for (StackTraceElement st : stackTraceElements) {
                Log.d(TAG, "-OomActivity-printThread-StackTraceElement: " + st.toString());
            }
            Log.d(TAG, "-OomActivity-printThread---- print thread: " + key.getName() + " end ----");
        }
    }

    private void showFileContent(String path){
        if (TextUtils.isEmpty(path)){
            return;
        }
        try{
            RandomAccessFile randomAccessFile=new RandomAccessFile(path,"r");
            StringBuilder stringBuilder=new StringBuilder();
            String s;
            while ((s=randomAccessFile.readLine())!=null){
                stringBuilder.append(s).append("\r\n");
            }
            dashboard.setText(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "-OomActivity-showFileContent"
                    + "-e.toString()->"+e.toString()
            );
        }
    }

    ///=======================================================

    private void showFPS() {
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            long lastFrameTimeNanos = 0;
            long currentFrameTimeNanos = 0;

            @Override
            public void doFrame(long frameTimeNanos) {
                if (lastFrameTimeNanos == 0) {
                    lastFrameTimeNanos = frameTimeNanos;
                }
                currentFrameTimeNanos = frameTimeNanos;
                long diffMs = TimeUnit.MILLISECONDS.convert(currentFrameTimeNanos - lastFrameTimeNanos, TimeUnit.NANOSECONDS);
                lastFrameTimeNanos = currentFrameTimeNanos;
                if (diffMs == 0) {
                    diffMs = (long) 16.7;
                }

                /*if (isShowFPS) {
                    long current = System.currentTimeMillis();
                    if (current - mLastFPSRefreshTs > refreshInterval) {
                        int fps = (int) (1000 / diffMs);
                        refreshFPS(fps);
                        mLastFPSRefreshTs = current;
                    }
                }
                if (diffMs > 16.7f) {
                    long droppedCount = (long) (diffMs / 16.7f);
                    if (droppedCount > 1) {
                        System.out.println("掉帧数 : " + droppedCount);
                    }
                }
                if (UiBlockLogMonitor.getInstance().isMonitor()) {
                    UiBlockLogMonitor.getInstance().stopMonitor();
                }
                if (isDetectContinue) {
                    UiBlockLogMonitor.getInstance().startMonitor();
                    Choreographer.getInstance().postFrameCallback(this);
                }*/

            }
        });
    }




}
