package com.badlogic.utils;

/// com.badlogic.utils.MyImageView

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.yunbi.utils.R;


public class MyImageView extends ImageView {

    public MyImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public MyImageView(@NonNull Context context) {
        super(context);
        init(context);
    }

    Bitmap testBmp;

    protected void init(Context context) {
        this.setScaleType(ImageView.ScaleType.FIT_XY);
        testBmp = Tools.getBitmapOriginalBgResId(this.getResources(), R.drawable.ring_a11);

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }


    @Override
    public void draw(Canvas canvas) {
        Log.i("22","210324p-MyImageView-draw-01->");
//        canvas.drawBitmap(testBmp,0,0,null);
        super.draw(canvas);


    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.i("22","210324p-MyImageView-dispatchDraw-01->");
        super.dispatchDraw(canvas);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onDraw(Canvas canvas) {

        Log.i("22","210324p-MyImageView-onDraw-01->");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            try {
                Log.i("22","-MyImageView-onDraw"
                        + "-getX->"+this.getX()
                        + "-(20+50)dp)->"+Tools.dip2px(this.getContext(),20+50)
                );
                Log.i("22","-MyImageView-onDraw-getClipBounds->"+this.getClipBounds().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        if (false) {
            super.onDraw(canvas);
        }

        if (this.getDrawable() == null) {
            return; // couldn't resolve the URI
        }

        if (this.getDrawable().getIntrinsicWidth() == 0 || this.getDrawable().getIntrinsicHeight() == 0) {
            return;     // nothing to draw (empty bounds)
        }

        if (this.getImageMatrix() == null && this.getPaddingTop() == 0 && this.getPaddingLeft() == 0) {
            this.getDrawable().draw(canvas);
        } else {
            final int saveCount = canvas.getSaveCount();
            canvas.save();

            if (getCropToPadding()) {
                final int scrollX = this.getScrollX();
                final int scrollY = this.getScrollY();
                canvas.clipRect(scrollX + this.getPaddingLeft(), scrollY + this.getPaddingTop(),
                        scrollX + this.getRight() - this.getLeft() - this.getPaddingRight(),
                        scrollY + this.getBottom() - this.getTop() - this.getPaddingBottom());

            }

            canvas.translate(this.getPaddingLeft(), this.getPaddingTop());



            if (this.getImageMatrix() != null) {
                canvas.concat(this.getImageMatrix());
            }
            this.getDrawable().draw(canvas);


            canvas.drawBitmap(testBmp,this.getWidth() - testBmp.getWidth() + this.getScrollX(),200,null);


            canvas.restoreToCount(saveCount);

        }


    }


    void test_a() {

    }

}
