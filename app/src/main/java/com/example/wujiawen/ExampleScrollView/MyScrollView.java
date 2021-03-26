package com.example.wujiawen.ExampleScrollView;//com.example.wujiawen.ExampleScrollView.MyScrollView

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.OverScroller;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.badlogic.utils.ALog;

public class MyScrollView extends ScrollView {
	
	private boolean ScrollViewTouchMark=true;

//	OverScroller mOverScroller;
//	Scroller mScroller;

	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyScrollView(Context context) {
		super(context);
	}
	
	public void setScrollViewTouchMark(boolean mboolean) {
		ScrollViewTouchMark=mboolean;
	}
	
	public boolean getScrollViewTouchMark() {
		return ScrollViewTouchMark;
	}

	MyScrollViewB mMyScrollViewB;

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		ALog.i(ALog.Tag2,"MyScrollView--dispatchTouchEvent--v.getAction()->"+ev.getAction());


		final float offsetX = this.getScrollX() - this.getChildAt(0).getLeft();
		final float offsetY = this.getScrollY() - this.getChildAt(0).getTop();
		ev.offsetLocation(offsetX, offsetY);
		boolean handled = this.getChildAt(0).dispatchTouchEvent(ev);
		if(handled&&!ScrollViewTouchMark){

		}
		ev.offsetLocation(-offsetX, -offsetY);




		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		ALog.i(ALog.Tag2,"MyScrollView--onInterceptTouchEvent--v.getAction()->"+ev.getAction()+"-ScrollViewTouchMark->"+ScrollViewTouchMark);
		if(ScrollViewTouchMark){
			return super.onInterceptTouchEvent(ev);
		}else{
			return false;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		ALog.i(ALog.Tag2,"MyScrollView--onTouchEvent--v.getAction()->"+ev.getAction()+"-ScrollViewTouchMark->"+ScrollViewTouchMark);
		if(ScrollViewTouchMark){
			return super.onTouchEvent(ev);
		}else{
			return false;
		}
	}

	/*@Override
	public void onScrollChanged(int l, int t, int oldl, int oldt) {
		ALog.i(ALog.Tag2,"MyScrollView--onScrollChanged--t->"+t+"-oldt->"+oldt);
		super.onScrollChanged(l,t,oldl,oldt);
	}*/

	@Override
	public void computeScroll() {
		super.computeScroll();
	}

	@Override
	protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
	}

	@Override
	public void scrollTo(int x, int y) {
		super.scrollTo(x,y);
	}
	

}
