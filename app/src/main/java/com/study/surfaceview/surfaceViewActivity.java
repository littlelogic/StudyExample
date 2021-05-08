package com.study.surfaceview;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.Nullable;

import com.badlogic.utils.MyAbsoluteLayout;
import com.example.wujiawen.a_Main.R;


// com.study.surfaceview.surfaceViewActivity

public class surfaceViewActivity extends Activity {


    MyAbsoluteLayout rootview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activ_surfaceview);
        rootview = this.findViewById(R.id.rootview);
        test_a();
        test_b();


        Handler nHandler;
        Looper hLooper;
    }


    void test_a(){
        SurfaceView sv = (SurfaceView) findViewById(R.id.sv);
        SurfaceHolder sfh = sv.getHolder();
        //对 surfaceView 进行操作
        sfh.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Canvas c = sfh.lockCanvas(/*new Rect(0, 0, 2000, 2000)*/);
                //2.开画
                Paint p = new Paint();
                p.setColor(Color.RED);
                Rect aa = new Rect(0, 0, 2000, 2000);
                c.drawRect(aa, p);
                //3. 解锁画布   更新提交屏幕显示内容
                sfh.unlockCanvasAndPost(c);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("11","--->");
                rootview.invalidate();
            }
        });
    }

    SurfaceView sv_mini;
    void test_b(){
        sv_mini = (SurfaceView) findViewById(R.id.sv_mini);
        SurfaceHolder sfh = sv_mini.getHolder();

//        sv_mini.setZOrderOnTop(true);
//        sfh.setFormat(PixelFormat.TRANSPARENT);

        //对 surfaceView 进行操作
        sfh.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Canvas c = sfh.lockCanvas(new Rect(0, 0, 600, 600));
                //2.开画
                Paint p = new Paint();
                p.setColor(Color.GREEN);
                Rect aa = new Rect(0, 0, 600, 600);
                c.drawRect(aa, p);
                //3. 解锁画布   更新提交屏幕显示内容
                sfh.unlockCanvasAndPost(c);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }
}
