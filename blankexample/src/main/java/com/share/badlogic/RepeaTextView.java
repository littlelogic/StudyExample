package com.share.badlogic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.widget.EditText;

/**
 * Created by wujiawen on 2018/4/9.
 */
class RepeaTextView extends EditText {
//class RepeaTextView extends TextView {

    public RepeaTextView(Context context) {
        super(context);
        init(context);
    }

    public void init(Context context) {

    }

    Paint mPaint_2=new Paint();
    RectF targetRect = new RectF();
    Paint mPaint=new Paint();
    Bitmap startBmp=null;
    Bitmap  endBmp=null;
    Bitmap  targetBmp=null;
    int startX;
    int endX;
    int paddingRight;
    int  targetBmpWidth;
    int  targetBmpHeight;
    int heightBitmap;

    public void setData(Bitmap mBitmap,int startX,int endX){
        heightBitmap=mBitmap.getHeight();
        this.startX=startX;
        this.endX=endX;
        startBmp=Bitmap.createBitmap(mBitmap,0,0,startX,mBitmap.getHeight(),null,true);
        endBmp=Bitmap.createBitmap(mBitmap,endX,0,mBitmap.getWidth()-endX,mBitmap.getHeight(),null,true);
        targetBmp=Bitmap.createBitmap(mBitmap,startX,0,endX-startX,mBitmap.getHeight(),null,true);
        paddingRight=mBitmap.getWidth()-endX;
        //-------------
        BitmapShader mShader = new BitmapShader(targetBmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        targetBmpWidth=targetBmp.getWidth();
        targetBmpHeight=targetBmp.getHeight();
        mPaint.setTextSize(22);
        mPaint.setColor(Color.RED);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mPaint.setShader(mShader);
        mPaint.setAntiAlias(true);

        mPaint_2.setColor(Color.RED);
            /*mPaint_b.setStrokeWidth((float) 60);
            mPaint_b.setStyle(Paint.Style.STROKE);
            mPaint_b.setStrokeCap(Paint.Cap.ROUND);*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.argb(100,0,0,0));

        canvas.drawBitmap(startBmp,0,0,null);
        canvas.drawBitmap(endBmp,this.getWidth()-paddingRight,0,null);

        targetRect.set(startX,0,this.getWidth()-paddingRight,heightBitmap);
        canvas.drawRect(targetRect,mPaint);

        super.onDraw(canvas);
    }
}