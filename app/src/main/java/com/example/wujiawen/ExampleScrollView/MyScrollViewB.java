package com.example.wujiawen.ExampleScrollView;//com.example.wujiawen.ExampleScrollView.MyScrollView

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.OverScroller;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.badlogic.utils.ALog;

public class MyScrollViewB extends ScrollView {

	private boolean ScrollViewTouchMark=true;

	public MyScrollViewB(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public MyScrollViewB(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public MyScrollViewB(Context context) {
		super(context);
		init();
	}

	public void init(){
		this.setOverScrollMode(OVER_SCROLL_NEVER);
	}

	public void setScrollViewTouchMark(boolean mboolean) {
		ScrollViewTouchMark=mboolean;
	}

	public boolean getScrollViewTouchMark() {
		return ScrollViewTouchMark;
	}

	float lastY;

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		ALog.i(ALog.Tag2,"MyScrollViewB--dispatchTouchEvent--v.getAction()->"+ev.getAction());

		/*ALog.i(ALog.Tag2,"MyScrollViewB--dispatchTouchEvent--ev.getAction()->"+ev.getAction());
		ALog.i(ALog.Tag2,"MyScrollViewB--dispatchTouchEvent--ev.getDownTime()->"+ev.getDownTime());
		ALog.i(ALog.Tag2,"MyScrollViewB--dispatchTouchEvent--ev.getEventTime()->"+ev.getEventTime());
		ALog.i(ALog.Tag2,"MyScrollViewB--dispatchTouchEvent--ev.getX()->"+ev.getX());
		ALog.i(ALog.Tag2,"MyScrollViewB--dispatchTouchEvent--ev.getY()->"+ev.getY());*/

		/*if(ev.getAction()==MotionEvent.ACTION_DOWN){
			if(this.getScrollY()<=0||this.getScrollY()>=maxScrollY){
				((MyScrollView)(this.getParent().getParent())).setScrollViewTouchMark(true);
				((MyScrollView)(this.getParent().getParent())).onTouchEvent(ev);
				return false;
			}
		}*/


		/*if(ev.getAction()==MotionEvent.ACTION_DOWN){
			((MyScrollView)(this.getParent().getParent())).setScrollViewTouchMark(true);
			((MyScrollView)(this.getParent().getParent())).onTouchEvent(ev);
		}
		if(ev.getAction()==MotionEvent.ACTION_MOVE){
			ALog.i(ALog.Tag2,"MyScrollViewB--dispatchTouchEvent--this.getScrollY()->"+this.getScrollY());
			if(this.getScrollY()<=0){
				float deltaY=ev.getY()-lastY;
				lastY=ev.getY();
				ALog.i(ALog.Tag2,"MyScrollViewB--dispatchTouchEvent--deltaY->"+deltaY);
				if(deltaY>0){
					((MyScrollView)(this.getParent().getParent())).setScrollViewTouchMark(true);
					((MyScrollView)(this.getParent().getParent())).onTouchEvent(ev);
					return true;
				}
			}
			if(this.getScrollY()>=maxScrollY){
				float deltaY=ev.getY()-lastY;
				lastY=ev.getY();
				if(deltaY<0){
					((MScrollView)(this.getParent().getParent())).onTouchEvent(ev);
					return true;
				}
			}
		}*/



		lastY=ev.getY();
		return super.dispatchTouchEvent(ev);
	}



	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		ALog.i(ALog.Tag2,"MyScrollViewB--onInterceptTouchEvent--ScrollViewTouchMark->"+ScrollViewTouchMark);

		if(ScrollViewTouchMark){
			return super.onInterceptTouchEvent(ev);
		}else{
			return false;
		}
	}

	VelocityTracker mVelocityTracker = VelocityTracker.obtain();

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		ALog.i(ALog.Tag2,"MyScrollViewB--onTouchEvent--ScrollViewTouchMark->"+ScrollViewTouchMark);
		if(ScrollViewTouchMark){

			if(ev.getAction()==MotionEvent.ACTION_DOWN){
				mVelocityTracker = VelocityTracker.obtain();
			}else if(ev.getAction()==MotionEvent.ACTION_MOVE){

			}
			if(mVelocityTracker!=null){
				mVelocityTracker.addMovement(ev);
			}
			return super.onTouchEvent(ev);
		}else{
			return false;
		}
	}

	@Override
	public void onScrollChanged(int l, int t, int oldl, int oldt) {
		ALog.i(ALog.Tag2,"MyScrollViewB--onScrollChanged--t->"+t+"-oldt->"+oldt);

		if(t==0&&oldl==0){

		}else if(t<=0){
			t=0;
			super.onScrollChanged(l,t,oldl,oldt);
		}else{
			super.onScrollChanged(l,t,oldl,oldt);
		}
	}


	@Override
	public void onSizeChanged(int l, int t, int oldl, int oldt) {
		super.onSizeChanged(l,t,oldl,oldt);
		int height = getHeight() - this.getPaddingBottom() - this.getPaddingTop();
		int bottom = getChildAt(0).getHeight();
		maxScrollY= Math.abs(bottom - height);
	}


	@Override
	public void computeScroll() {
		ALog.i(ALog.Tag2,"MyScrollViewB--computeScroll--getScrollY->"+this.getScrollY());
		super.computeScroll();
		/*if(this.getScrollY()<=1){
		}else{
			super.computeScroll();
		}*/

		if (mScroller!=null) {
			mScroller.computeScrollOffset();
			if(this.getScrollY()<=0||this.getScrollY()>=maxScrollY){
				mScroller.computeScrollOffset();
				int Velocity=(int)mScroller.getCurrVelocity()/5*2;
				ALog.i(ALog.Tag2,"MyScrollViewB--computeScroll--Velocity->"+Velocity);
				if(this.getScrollY()<=0){
					Velocity=-Velocity;
				}
				((ScrollView)(this.getParent().getParent())).fling(Velocity);
				mScroller=null;
			}
		}
	}

	Scroller mScroller;
	OverScroller mOverScroller;
	int maxScrollY;
	@Override
	public void fling(int velocityY) {
		super.fling(velocityY);
		if(mScroller==null){
			mScroller= new Scroller(this.getContext(), new LinearInterpolator());
		}
		/*if(mOverScroller==null){
			mOverScroller= new OverScroller(this.getContext(), new LinearInterpolator());
		}*/
		int height = getHeight() - this.getPaddingBottom() - this.getPaddingTop();
		int bottom = getChildAt(0).getHeight();
		maxScrollY= Math.abs(bottom - height);
		mScroller.fling(this.getScrollX(),this.getScrollY(),0,velocityY, 0, 0, 0,Math.max(0, maxScrollY)*2);

	}

	@Override
	protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
		ALog.i(ALog.Tag2,"MyScrollView--onOverScrolled--scrollY->"+scrollY+"-clampedY->"+clampedY);
		/*if(scrollY<=0){
			scrollY=1;
		}*/
		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
	}

	@Override
	public void scrollTo(int x, int y) {
		ALog.i(ALog.Tag2,"MyScrollViewB--scrollTo--y->"+y);
		super.scrollTo(x,y);
	}

	@Override
	public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
		ALog.i(ALog.Tag2,"MyScrollViewB--onNestedFling--velocityY->"+velocityY);
		return super.onNestedFling(target, velocityX, velocityY, consumed);
	}
}
