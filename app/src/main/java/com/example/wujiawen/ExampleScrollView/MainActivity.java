package com.example.wujiawen.ExampleScrollView;//com.example.wujiawen.ExampleScrollView.ContentProviderActi

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.badlogic.utils.ALog;
import com.badlogic.utils.Tools;
import com.example.wujiawen.Example_b.ProgressView;
import com.example.wujiawen.Example_b.SpringProgressView;
import com.example.wujiawen.a_Main.R;

import java.util.Random;

/***
 * 设置界面
 * @author smz
 * 创建时间：2014-1-7上午10:51:21
 */
public class MainActivity extends Activity {

	Activity mActivity;
	TextView hintTextView;
	TextView hintUnderTextView;
	TextView contentTextView;
	MyScrollView mScrollViewOut;
	ScrollView contentScrollView;
	Handler mHandler=new Handler();
	String contentStr="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activ_scrollview_test);


		mScrollViewOut=(MyScrollView)this.findViewById(R.id.ScrollView_out);
		contentScrollView=(ScrollView)this.findViewById(R.id.ScrollView_content);
		hintTextView=(TextView)this.findViewById(R.id.hint);
		hintUnderTextView=(TextView)this.findViewById(R.id.hint_b);
		contentTextView=(TextView)this.findViewById(R.id.content);
		String hint_all_final="";
		String hint_all="分红IASH佛iHFHAIS发货单ASOHDOFBpkasfGHPH 哦i【a{sF]PIasF HFSAoH合法搜返回回复OASIHFgas哈佛哈是否\n\n\n\n";
		//--hint_all_final=hint_all+hint_all+hint_all+hint_all+hint_all+hint_all;//---------
		for(int i=0;i<6;i++){
			hint_all_final+=hint_all;
		}
		hintTextView.setText(hint_all+hint_all+hint_all);
		hintUnderTextView.setText(hint_all_final);
		//---------
		contentScrollView.setOnTouchListener(new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					mScrollViewOut.setScrollViewTouchMark(false);
				}else if(event.getAction()==MotionEvent.ACTION_UP){
					mScrollViewOut.setScrollViewTouchMark(true);
				}else if(event.getAction()==MotionEvent.ACTION_CANCEL){
					mScrollViewOut.setScrollViewTouchMark(true);
				}
				return false;
			}
		});

		for(int i=0;i<700;i++){
			contentStr+=i;
		}
		contentTextView.setText(contentStr);
		//-----------
		//-----------
		mVelocityTracker = VelocityTracker.obtain();
		final ViewConfiguration configuration = ViewConfiguration.get(this);
		mTouchSlop= configuration.getScaledTouchSlop();
		mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
		mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
		ALog.i(ALog.Tag2,"ContentProviderActi--onCreate--mTouchSlop->"+mTouchSlop);
		ALog.i(ALog.Tag2,"ContentProviderActi--onCreate--mMinimumVelocity->"+mMinimumVelocity);
		ALog.i(ALog.Tag2,"ContentProviderActi--onCreate--mMaximumVelocity->"+mMaximumVelocity);
		//-----------

		findViewById(R.id.copy_do).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				ALog.i(ALog.Tag2,"ContentProviderActi--OnClickListener--01->");

				long down_time= SystemClock.uptimeMillis();
//				contentScrollView.dispatchTouchEvent(
//						MotionEvent.obtain(down_time, SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 1, 1, 0));
//				contentScrollView.dispatchTouchEvent(
//						MotionEvent.obtain(down_time, SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 1, 100, 0));


//				MotionEvent downMotionEvent=MotionEvent.obtain(down_time, down_time, MotionEvent.ACTION_DOWN, contentScrollView.getLeft()+100, 100, 0);
//				MotionEvent moveMotionEvent=MotionEvent.obtain(down_time, down_time+50, MotionEvent.ACTION_MOVE, contentScrollView.getLeft()+100, 200, 0);
//				MotionEvent upMotionEvent  =MotionEvent.obtain(down_time, down_time+55, MotionEvent.ACTION_UP, contentScrollView.getLeft()+100, 600, 0);

				MotionEvent downMotionEvent=MotionEvent.obtain(down_time, down_time, MotionEvent.ACTION_DOWN, contentScrollView.getLeft()+100, 100, 0);
				MotionEvent moveMotionEvent=MotionEvent.obtain(down_time, down_time+8, MotionEvent.ACTION_MOVE, contentScrollView.getLeft()+100, 101, 0);
				MotionEvent moveMotionEvent2=MotionEvent.obtain(down_time, down_time+18, MotionEvent.ACTION_MOVE, contentScrollView.getLeft()+100, 108, 0);
				MotionEvent upMotionEvent  =MotionEvent.obtain(down_time, down_time+28, MotionEvent.ACTION_UP, contentScrollView.getLeft()+100, 301, 0);


				boolean downDeal=contentScrollView.dispatchTouchEvent(downMotionEvent);
				boolean moveDeal=contentScrollView.dispatchTouchEvent(moveMotionEvent);
				boolean upDeal=contentScrollView.dispatchTouchEvent(upMotionEvent);

				ALog.i(ALog.Tag2,"ContentProviderActi--OnClickListener--01-downDeal->"+downDeal);
				ALog.i(ALog.Tag2,"ContentProviderActi--OnClickListener--01-moveDeal->"+moveDeal);
				ALog.i(ALog.Tag2,"ContentProviderActi--OnClickListener--01-upDeal->"+upDeal);
				mVelocityTracker = VelocityTracker.obtain();
				mVelocityTracker.addMovement(downMotionEvent);
				mVelocityTracker.addMovement(moveMotionEvent);
				mVelocityTracker.addMovement(moveMotionEvent2);
				mVelocityTracker.addMovement(upMotionEvent);
				mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
				int initialVelocity = (int) mVelocityTracker.getYVelocity();
				mVelocityTracker.clear();

//				contentScrollView.fling(3000);
				mScrollViewOut.fling(7000);

				ALog.i(ALog.Tag2,"ContentProviderActi--OnClickListener--01-initialVelocity->"+initialVelocity);

				downMotionEvent.recycle();
				upMotionEvent.recycle();

//				contentScrollView.dispatchTouchEvent(
//						MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, contentScrollView.getLeft()+5, contentScrollView.getTop()+5, 0));
//				contentScrollView.dispatchTouchEvent(
//						MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, contentScrollView.getLeft()+5, contentScrollView.getTop()+105, 0));


//				new Thread(new Runnable() {
//					@Override
//					public void run() {
//						Instrumentation m_Instrumentation = new Instrumentation();
//						m_Instrumentation.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),
//								SystemClock.uptimeMillis(),MotionEvent.ACTION_DOWN,100, 100, 0));
//						m_Instrumentation.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(),
//								SystemClock.uptimeMillis(),MotionEvent.ACTION_UP,100, 200, 0));
//					}
//				});











				ALog.i(ALog.Tag2,"ContentProviderActi--OnClickListener--last->");
			}
		});


	}

	private int mTouchSlop ;
	private int mMinimumVelocity;
	private int mMaximumVelocity;

	VelocityTracker mVelocityTracker = VelocityTracker.obtain();

}
