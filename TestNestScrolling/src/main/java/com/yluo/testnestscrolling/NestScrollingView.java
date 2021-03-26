package com.yluo.testnestscrolling;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class NestScrollingView extends View implements NestedScrollingChild{

	private static final String TAG = "NestScrollingView";

	private NestedScrollingChildHelper mChildHelper;

	private int[] mConsumed = new int[2];

	private int[] mOffset = new int[2];

	public NestScrollingView(Context context, AttributeSet attrs,
							 int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();

	}

	public NestScrollingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public NestScrollingView(Context context) {
		super(context);
		init();


	}

	private void init() {
		mChildHelper = new NestedScrollingChildHelper(this);
		setNestedScrollingEnabled(true);
	}

	@Override
	public void setNestedScrollingEnabled(boolean enabled) {
		mChildHelper.setNestedScrollingEnabled(enabled);
	}
	@Override
	public boolean isNestedScrollingEnabled() {
		return mChildHelper.isNestedScrollingEnabled();
	}
	@Override
	public boolean startNestedScroll(int axes) {
		Log.d(TAG, "-----------子View开始滚动---------------");
		return mChildHelper.startNestedScroll(axes);
	}
	@Override
	public void stopNestedScroll() {
		Log.d(TAG, "-----------子View停止滚动---------------");
		mChildHelper.stopNestedScroll();
	}
	@Override
	public boolean hasNestedScrollingParent() {
		return mChildHelper.hasNestedScrollingParent();
	}
	@Override
	public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed,
										int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
		Log.d(TAG, "-----------子View把剩余的滚动距离传给父布局---------------");

		return mChildHelper.dispatchNestedScroll(dxConsumed,dyConsumed,
				dxUnconsumed,dyUnconsumed,offsetInWindow);
	}
	@Override
	public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
		Log.d(TAG, "-----------子View把总的滚动距离传给父布局---------------");
		return mChildHelper.dispatchNestedPreScroll(dx,dy,
				consumed,offsetInWindow);


	}

	@Override
	public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed){
		return mChildHelper.dispatchNestedFling(velocityX,velocityY,
				consumed);
	}
	@Override
	public boolean dispatchNestedPreFling(float velocityX, float velocityY){
		return mChildHelper.dispatchNestedPreFling(velocityX,velocityY);

	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
				break;
			case MotionEvent.ACTION_MOVE:

				dispatchNestedPreScroll(0,20,mConsumed,mOffset);

				Log.d(TAG, "offset--x:" + mOffset[0] + ",offset--y:" + mOffset[1]);
				dispatchNestedScroll(50,50,50,50,mOffset);

				break;
			case MotionEvent.ACTION_UP:
				stopNestedScroll();
				break;
			default:
				break;
		}
		return true;
	}

}

















