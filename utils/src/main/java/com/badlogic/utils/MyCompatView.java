package com.badlogic.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MyCompatView extends View {

	public MyCompatView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public MyCompatView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MyCompatView(Context context) {
		super(context);
		init(context);
	}
	
	//========================================================

	LinearLayout mLinearLayout;
	FrameLayout mFrameLayout;
	RelativeLayout mRelativeLayout;
	ListView mListView;

	private int mTouchSlop ;
	private int mMinimumVelocity;
	private int mMaximumVelocity;

	private int mOverscrollDistance;
	private int mOverflingDistance;

	public void init(Context context){
		final ViewConfiguration configuration = ViewConfiguration.get(context);
		mTouchSlop= configuration.getScaledTouchSlop();
		mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
		mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
		mOverscrollDistance = configuration.getScaledOverscrollDistance();
		mOverflingDistance = configuration.getScaledOverflingDistance();
		//---------------
		Log.i("wjw02","PushApplication--onCreate--Binder.getCallingPid()->"+ Binder.getCallingPid());
		Log.i("wjw02","PushApplication--onCreate--android.os.Process.myPid()->"+android.os.Process.myPid());
		Log.i("wjw02","PushApplication--onCreate--android.os.Process.myPid()->"+android.os.Process.myUid());


//		CPUFrameworkHelper kk;
	}

	
	public void study() {
		android.os.Debug.waitForDebugger();
	}
	
	public static void SetNetAlertDialog(final Context  context) {
		new AlertDialog.Builder(context)
	    .setTitle("??????")
		.setMessage("????????????????????????????????????????????????")
	    .setPositiveButton("????????????", 
	    new DialogInterface.OnClickListener(){
		public void onClick(DialogInterface dialog, int which) {
			context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
		}                   
		})
		
		.setNegativeButton("??????",
		new DialogInterface.OnClickListener(){
	    public void onClick(DialogInterface dialoginterface, int i){
	    	
	    }
		})
		.show();

		//--Bitmap mBitmap=null;mBitmap.recycle();
	}

	
	//========================================================
	
	/*
	 * 
	 
	 ?????????????????????????????????
	  Canvas
	clipPath()
	clipRegion()
	drawPicture()
	drawTextOnPath()
	drawVertices()
	???Paint
	setLinearText()
	setMaskFilter()
	setRasterizer()
	???Xfermodes
	AvoidXfermode
	PixelXorXfermode
	?????????????????????????????????????????????????????????
	???Canvas
	clipRect()???XOR, Difference???ReverseDifference????????????????????????3D????????????????????????????????????
	drawBitmapMesh()????????????????????????
	???Paint
	setDither()?????????
	setFilterBitmap()???????????????????????????
	setShadowLayer()?????????text?????????
	???PorterDuffXfermode
	PorterDuff.Mode.DARKEN ?????????framebuffer??????SRC_OVER???????????????
	PorterDuff.Mode.LIGHTEN ?????????framebuffer??????SRC_OVER?????????????????
	PorterDuff.Mode.OVERLAY ?????????framebuffer??????SRC_OVER?????????????????
	???ComposeShader
	ComposeShader ????????????????????????????????????
	ComposeShader ??????????????????ComposeShader
	??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
	
	*/
	
    /*	
     * 
     * 
	???????????????????????????Android??????2D?????????????????????????????????????????????Canvas?????????????????????????????????????????????????????????
	????????????????????????????????????Android??????????????????????????????????????????????????????????????????????????????
	????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
	* Canvas
	     ** clipPath()
	     ** clipRegion()
	     ** drawPicture()
	     ** drawTextOnPath()
	     ** drawVertices()
	* Paint
	     ** setLinearText()
	     ** setMaskFilter()
	     ** setRasterizer()
	* Xfermodes
	     ** AvoidXfermode
	     ** PixelXorXfermode

	??????????????????????????????????????????????????????????????????
	* Canvas
	     ** clipRect(): XOR, Difference ??? ReverseDifference ?????????????????????????????????3D??????????????????????????????
	     ** drawBitmapMesh(): ?????????????????????
	* Paint
	     ** setDither(): ?????????
	     ** setFilterBitmap(): ??????????????????????????????
	     ** setShadowLayer(): ???????????????????????????
	* PorterDuffXfermode
	     ** PorterDuff.Mode.DARKEN ????????? SRC_OVER ?????????????????????????????????
	     ** PorterDuff.Mode.LIGHTEN ????????? SRC_OVER ?????????????????????????????????
	     ** PorterDuff.Mode.OVERLAY ????????? SRC_OVER ?????????????????????????????????
	* ComposeShader
	     ** ComposeShader ????????????????????????????????????(?????????????????? BitmapShader ?????????????????? Li
	    		 nearGradient???????????????????????????????????? BitmapShader ????????????????????????)
	     ** ComposeShader ?????????????????? ComposeShader

	?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????View
	?????????setLayerType(ViewConstant.LAYER_TYPE_SOFTWARE, null)???????????????????????????????????????????????????????????????????????????????????????????????????????????????
	
	*/
	
	/***
	 * @SuppressLint("NewApi") ????????????View????????????????????????????????? ????????????????????????SDK??????
	 */
	@SuppressLint("NewApi")
	public static boolean get_HardwareAccelerated(android.view.View mView){
		if(android.os.Build.VERSION.SDK_INT>=11){//????????????????????????
			return mView.isHardwareAccelerated();
		}else{
			return false;
		}
	}
	
	@SuppressLint("NewApi")
	public static void set_HARDWARE_ACCELERATED(Activity mActivity){
		if(android.os.Build.VERSION.SDK_INT>=11){//????????????????????????
			mActivity.getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
					WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
		}
	}
	
	@SuppressLint("NewApi")
	public static void cannel_HARDWARE_ACCELERATED(android.view.View mView){
		if(android.os.Build.VERSION.SDK_INT>=11){//????????????????????????
			mView.setLayerType(android.view.View.LAYER_TYPE_SOFTWARE,null);
		}
	}
	
	
	@SuppressLint("NewApi")
	public static void setMotionEventSplittingEnabled(ViewGroup mView){
		mView.setMotionEventSplittingEnabled(false);
		
		/*
		 <LinearLayout android:splitMotionEvents="false" ... >
		 ...???????????????view???????????????
		 </LinearLayout>
		*/
		
		
		/*
		 <style name="NoSplitMotionEvents" parent="android:Theme.Holo">
	     <item name="android:windowEnableSplitTouch">false</item>
	     ...
	     </style>
	    */
	    
	}

	//========================================================
	
	/**
	 * GridView,ListView
	 * ??????????????????????????????????????????????????????????????????
	 * MyView.cannel_OverScrollMode(this);
	 * 
	 */
	@SuppressLint("NewApi")
	public static void cannel_OverScrollMode(AbsListView mView){
		if(android.os.Build.VERSION.SDK_INT>=9){
			try {
				//this.setOverScrollMode(2);//??????????????????????????????????????????????????????????????????
				mView.setOverScrollMode(android.view.View.OVER_SCROLL_NEVER);
			} catch (Exception e) {
				e.printStackTrace();
			}       
		}
	}
	
	/**
	 * ViewPager,ScrollView
	 * @param mView
	 * MyView.cannel_OverScrollMode(this);
	 */
	@SuppressLint("NewApi")
	public static void cannel_OverScrollMode(android.view.View mView){
		if(android.os.Build.VERSION.SDK_INT>=9){
			try {
				//this.setOverScrollMode(2);//??????????????????????????????????????????????????????????????????
				//--mView.setOverScrollMode(android.view.ViewConstant.OVER_SCROLL_NEVER);
				//--mView.setOverScrollMode(android.view.ViewConstant.OVER_SCROLL_ALWAYS);
			} catch (Exception e) {
				e.printStackTrace();
			}       
		}
	}
	
	//========================================================
	public static Drawable getDrawableFromXml(Resources res,int id){
		//--------
		XmlResourceParser xrp = null;
		try {
			xrp = res.getXml(id);
			//--???????????????????????????????????????xrp = res.getXml(333);
		} catch (Resources.NotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Drawable drawable = Drawable.createFromXml(res, xrp);
			return drawable;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ColorStateList getColorStateListFromXml(Resources res, int id){
		XmlResourceParser xpp=res.getXml(id);
		//--XmlResourceParser xpp=Resources.getSystem().getXml(id);
		try {
			ColorStateList cs2= ColorStateList.createFromXml(res,xpp);
			return cs2;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ColorStateList getColorStateListFromXml_b(Resources res, int id){
		try {
			ColorStateList csl=(ColorStateList)res.getColorStateList(id);
			return csl;
		} catch (Resources.NotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	//========================================================
	
	public static class Build {
		public static class VERSION_CODES {
			 public static final int KITKAT = 19;
		}
	}

	public static class ViewConstant {
		public static final int SYSTEM_UI_FLAG_LAYOUT_STABLE = 0x00000100;
		public static final int SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION = 0x00000200;
		public static final int SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN = 0x00000400;
		public static final int SYSTEM_UI_FLAG_HIDE_NAVIGATION = 0x00000002;
		public static final int SYSTEM_UI_FLAG_FULLSCREEN = 0x00000004;
		public static final int SYSTEM_UI_FLAG_IMMERSIVE_STICKY = 0x00001000;
	}
	
	//========================================================
	//========================================================
	//========================================================
	//========================================================
	//========================================================
	//========================================================
	
	 public void add_selector_total_CodeFromXml(LinearLayout ParentView,String text_str,Context mContext){
	       /* TextView mTextView=new TextView(mContext);
	        mTextView.setText(text_str);
	        mTextView.setGravity(Gravity.CENTER);
	        //--mTextView.setBackgroundDrawable(mDrawable);
	        mTextView.setTextColor(Color.WHITE);
	        LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 40);
	        mTextView.setLayoutParams(Params_b);
	        ParentView.addView(mTextView);
	        //--------
	        Resources res = ParentView.getResources();
	        XmlResourceParser xrp = null;
	        try {
	            xrp = res.getXml(R.drawable.bg_selector_total);
	        } catch (Resources.NotFoundException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        try {
	            Drawable drawable = Drawable.createFromXml(getResources(), xrp);
	            mTextView.setBackgroundDrawable(drawable);
	        } catch (XmlPullParserException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }*/
	    }


	//========================================================
	//--@Subscribe(threadMode = ThreadMode.MAIN)
	public static void setTextViewId(TextView mView, int id){
		mView.setId(id);
	}

	@SuppressWarnings("unused")
	//--@Description
	/**
	 * @Description ?????????????????????
	 */
	private void self_dispatchTouchEvent() {
		long down_time= SystemClock.uptimeMillis();
		this.dispatchTouchEvent(
				MotionEvent.obtain(down_time, SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
		this.dispatchTouchEvent(
				MotionEvent.obtain(down_time, SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0, 0, 0));
	}

	//========================================================



	void setTouch(View view){
		view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(android.view.View v, MotionEvent event) {


				return true;
			}
		});
	}





}