package com.study.recycleview;
//com.study.recycleview.MTextView
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.badlogic.utils.ALog;

public class MTextView extends TextView {
    public MTextView(Context context) {
        super(context);
    }

    public MTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public String mark_str = "";

    public MTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAnimation(Animation animation) {
        super.setAnimation(animation);
        ALog.i("200723a-MTextView-"+mark_str+"-setAnimation-this->"+this);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ALog.i("200723a-MTextView-"+mark_str+"-onDetachedFromWindow-this->"+this);
    }
    /**
     * Cancels any animations for this view.
     */
    public void clearAnimation() {
        super.clearAnimation();
        ALog.i("200723a-MTextView-"+mark_str+"-clearAnimation-this->"+this );
    }

}
