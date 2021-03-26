package com.share.badlogic;//com.share.blankexample.TensileBgEditText

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

public class TensileBgEditText extends TextView {

    /**
     * TODO: textview的属性综合起来，如果可以确定显示的范围，canvas就会被切割刀这个范围，应该是在父方法里面切割的
     * TODO: 可以在背景的drawable里面画
     * TODO: 背景的蓝色是父view的背景，居然可以设置包裹是有效的
     *
     */


    private final String TAG = "TensileBgEditText";
    private Bitmap startBmp=null;
    private Bitmap  endBmp=null;
    private Bitmap centerBmp=null;
    private Paint paint;
    private RectF rectFLeft,rectFCenter,rectFRight;

    public TensileBgEditText(Context context) {
        super(context);
        init();
    }

    public TensileBgEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TensileBgEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        rectFLeft = new RectF();
        rectFCenter = new RectF();
        rectFRight = new RectF();
        //---------
        this.setBackgroundDrawable(new Mrawable());
    }

    public void setData(Bitmap bitmap, int leftWidth, int rightWidth){
        int bitmapHeight = bitmap.getHeight();
        int endX = bitmap.getWidth() - rightWidth;
        startBmp = Bitmap.createBitmap(bitmap,0,0,leftWidth,bitmapHeight);
        centerBmp=Bitmap.createBitmap(bitmap,leftWidth,0,endX-leftWidth,bitmapHeight);
        endBmp=Bitmap.createBitmap(bitmap,endX,0,rightWidth,bitmapHeight);
    }

    @Override
    public void draw(Canvas canvas) {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawCircle(30,30,30,paint);
        super.draw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle(30,30,30,paint);

        super.dispatchDraw(canvas);
/*        paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle(30,30,30,paint);*/

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //--canvas.drawColor(Color.GREEN);

        canvas.translate(0,0);

        /*Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        RectF mRectF=new RectF(0,0,200,200);
        canvas.drawOval(mRectF,paint);*/


        super.onDraw(canvas);


        paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle(30,30,20,paint);
    }


    public class Mrawable extends Drawable{

        public Mrawable() {

        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            paint = new Paint();
            paint.setColor(Color.RED);
            canvas.drawCircle(30,30,30,paint);
        }

        @Override
        public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return 0;
        }
    }

}


