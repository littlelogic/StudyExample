package com.study.recycleview;
//com.download.ytb.view.MImageView
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.badlogic.utils.ALog;


public class MImageView extends ImageView {

    public MImageView(Context context) {
        super(context);
    }

    public MImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String mark_str = "";

    /*public void startAnimation(Animation animation) {
        super.startAnimation(animation);
        ALog.i("200723a-MImageView-startAnimation-this->"+this );
    }*/

    public void setAnimation(Animation animation) {
        super.setAnimation(animation);
        ALog.i("200723a-MImageView-"+mark_str+"-setAnimation-this->"+this);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ALog.i("200723a-MImageView-"+mark_str+"-onDetachedFromWindow-this->"+this);
    }
    /**
     * Cancels any animations for this view.
     */
    public void clearAnimation() {
        super.clearAnimation();
        ALog.i("200723a-MImageView-"+mark_str+"-clearAnimation-this->"+this );
    }



}
