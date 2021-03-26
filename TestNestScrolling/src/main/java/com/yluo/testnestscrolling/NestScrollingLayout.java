
package com.yluo.testnestscrolling;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

@SuppressLint("NewApi")
public class NestScrollingLayout extends FrameLayout implements NestedScrollingParent{

	private static final String TAG = "NestScrollingLayout";

	private  NestedScrollingParentHelper mParentHelper;

	public NestScrollingLayout(Context context, AttributeSet attrs,
							   int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public NestScrollingLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public NestScrollingLayout(Context context) {
		super(context);
		init();

	}


	@SuppressLint("NewApi")
	private void init() {
		mParentHelper = new NestedScrollingParentHelper(this);
	}
	@Override
	public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {

		Log.d(TAG, "child==target:" + (child == target));

		Log.d(TAG, "----父布局onStartNestedScroll----------------target:" + target + ",----+++this:" + this);

		return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
	}
	@SuppressLint("NewApi") @Override
	public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {

		Log.d(TAG, "----父布局onNestedScrollAccepted---------------");

		mParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
	}

	@Override
	public void onStopNestedScroll(View target) {
		Log.d(TAG, "----父布局onStopNestedScroll----------------");
		mParentHelper.onStopNestedScroll(target);
	}
	// 剩余没有消费的
	@Override
	public void onNestedScroll(View target, int dxConsumed, int dyConsumed,
							   int dxUnconsumed, int dyUnconsumed) {
		Log.d(TAG, "----父布局onNestedScroll----------------");
	}

	@Override
	public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
		// 这里要消费的

		scrollBy(0, -dy);

		consumed[0] = 0;

		consumed[1] = 10;
		Log.d(TAG, "----父布局onNestedPreScroll----------------");
	}

	@Override
	public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
		Log.d(TAG, "----父布局onNestedFling----------------");
		return true;
	}
	@Override
	public boolean onNestedPreFling(View target, float velocityX, float velocityY)  {
		Log.d(TAG, "----父布局onNestedPreFling----------------");
		return true;
	}
	@Override
	public int getNestedScrollAxes() {
		Log.d(TAG, "----父布局getNestedScrollAxes----------------");
		return mParentHelper.getNestedScrollAxes();
	}

}
























