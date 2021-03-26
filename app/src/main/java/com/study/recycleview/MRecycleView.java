package com.study.recycleview;
//com.study.recycleview.MRecycleView
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.badlogic.utils.ALog;

public class MRecycleView extends RecyclerView {

    public MRecycleView(Context context) {
        super(context);
    }

    public MRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public String mark_str = "";

    public MRecycleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void removeView(View view) {
        super.removeView(view);
        ALog.i("200723a-MRecycleView-removeView-this->"+this);

        if (view instanceof MFrameLayout) {
            ALog.i("200723a-MRecycleView-removeView-((MFrameLayout)view).getTag()->"+((MFrameLayout)view).getTag());
        }

    }

    @Override
    public void removeViewAt(int index) {
        View view = this.getChildAt(index);
        if (view instanceof MFrameLayout) {
            ALog.i("200729a-MRecycleView-removeViewAt-((MFrameLayout)view).getTag()->"+((MFrameLayout)view).getTag()+"-view->"+view);
        } else {
            ALog.i("200729a-MRecycleView-removeViewAt-no->");
        }
        super.removeViewAt(index);
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
        if (child instanceof MFrameLayout) {
            ALog.i("200729a-MRecycleView-addView-index-((MFrameLayout)child).getTag()->"+((MFrameLayout)child).getTag()+"-child->"+child);
        } else {
            ALog.i("200729a-MRecycleView-addView-index->"+index);
        }
    }




    @Override
    public void addView(View child) {
        super.addView(child);
        ALog.i("200723a-MRecycleView-addView->");
    }



    @Override
    public void removeViews(int start, int count) {
        super.removeViews(start, count);
        ALog.i("200723a-MRecycleView-removeViews-this->");
    }


    @Override
    protected void removeDetachedView(View child, boolean animate) {
        super.removeDetachedView(child, animate);
        ALog.i("200723a-MRecycleView-removeDetachedView-this->");
    }

    /*@Override
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        ALog.i("200723a-MRecycleView-"+mark_str+"-onViewRemoved-this->"+this);
    }*/


}
