package com.study.diyView;
// com.study.diyView.RippleMeshActiv

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.wujiawen.a_Main.R;

public class RippleMeshActiv extends Activity
{

    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this, R.drawable.instagram_1593746967784));
    }

    private class MyView extends View
    {

        //定义两个常量，这两个常量指定该图片横向、纵向上被划分为40格
        private final int WIDTH = 40;
        private final int HEIGHT = 40;

        //记录该图片上包含41*41个顶点
        private final int COUNT = (WIDTH +1) * (HEIGHT + 1);

        //定义一个数组，保存Bitmap上的41*41个点得坐标
        private final float[] orig = new float[COUNT*2];

        //定义一个数组，记录Bitmap上的41*41个点经过扭曲后的坐标
        //对 图片进行扭曲的关键就是修改该数组里的元素的值
        private final float[] verts = new float[COUNT*2];

        public MyView(Context context, int drawable_id)
        {
            super(context);
            // TODO Auto-generated constructor stub
            setFocusable(true);

            //根据指定资源加载图片
            bitmap = BitmapFactory.decodeResource(getResources(), drawable_id);

            //获取图片的宽和高
            float bitmapWidth = bitmap.getWidth();
            float bitmapHeight = bitmap.getHeight();
            int index = 0;
            for (int y = 0; y<= HEIGHT; y++)
            {
                float fy = bitmapHeight*y/HEIGHT;
                for (int x = 0; x<= WIDTH; x++)
                {
                    float fx = bitmapWidth*x/WIDTH;
                    /*初始化orig、verts数组。
                    初始化后，orig、verts两个数组均匀地保存了
                    41*41个点得x、y坐标*/
                    orig[index*2+0] = verts[index*2+0] = fx;
                    orig[index*2+1] = verts[index*2+1] = fy;
                    index += 1;
                }
            }

            //设置背景颜色
            setBackgroundColor(Color.WHITE);
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            /*对bitmap按verts数组进行扭曲
            从第一个点(由第5个参数0开始)开始扭曲*/
            canvas.drawBitmapMesh(bitmap, WIDTH, HEIGHT, verts, 0, null, 0, null);
        }

        //工具方法，用于根据触摸事件的位置计算verts数组里各元素的值
        private void warp(float cx, float cy)
        {
            for (int i = 0; i< COUNT * 2; i += 2)
            {
                float dx = cx - orig[i +0];
                float dy = cy - orig[i +1];
                float dd = dx*dx + dy*dy;
                //计算每个坐标点与当前点（cx、cy）之间的距离
                float d = (float)Math.sqrt(dd);

                //计算扭曲度，距离当前点（cx，cy）越远，扭曲度越小
                float pull = 80000 / ((float) (dd *d));

                //对verts数组（保存bitmap上41*41个点经过扭曲后的坐标）重新辅助
                if(pull >= 1)
                {
                    verts[i + 0] = cx;
                    verts[i + 1] = cy;
                }
                else
                {
                    //控制各定点向触摸事件发生点偏移
                    verts[i +0] = orig [i +0] +dx*pull;
                    verts[i +1] = orig [i +1] +dy*pull;
                }
            }
            //通知View组建重绘
            invalidate();
        }
        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            //调用warp方法根据触摸屏事件的坐标点来扭曲verts数组
            warp(event.getX(),event.getY());
            return true;
        }

    }

}