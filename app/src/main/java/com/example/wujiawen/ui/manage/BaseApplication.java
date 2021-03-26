package com.example.wujiawen.ui.manage;//com.yunbi.ui.manage.MApplication

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;


import com.badlogic.utils.ALog;

import java.util.LinkedList;
import java.util.List;

public class BaseApplication extends Application {

	static private Handler mHandler=new Handler();
	static private BaseApplication mMApplication;
	//--------
	public ActivityLifecycleCallbacks callback;
	public List<Activity> mListActivity = new LinkedList<Activity>();
	//--------
    //-------------------------------------------
    @Override
	public void onCreate() {
		super.onCreate();
		mMApplication=this;
		//========================================================================
		deal_registerActivity();
    }

	public static BaseApplication getApplication(){
		return mMApplication;
	}

	public static Handler getHandler(){
		return mHandler;
	}

    public void deal_registerActivity(){
	    callback=new ActivityLifecycleCallbacks(){
			@Override
			public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
				try {
					mListActivity.add(activity);
				} catch (Exception e) {
					e.printStackTrace();
				} 
				/*myLog.i("_log_Activity", "--MApplication--ActivityLifecycleCallbacks--onActivityCreated--01-->>"+activity.getLocalClassName());
				myLog.i("_log_Activity", "--MApplication--ActivityLifecycleCallbacks--onActivityCreated--02-->>"+activity.getPackageName());
				myLog.i("_log_Activity", "--MApplication--ActivityLifecycleCallbacks--onActivityCreated--03-->>"+activity.getClass().getName());*/
			}

			@Override
			public void onActivityStarted(Activity activity) {
				//--myLog.i("_log_Activity", "---MApplication----ActivityLifecycleCallbacks--onActivityStarted-->>"+activity.getLocalClassName());
			}

			@Override
			public void onActivityResumed(Activity activity) {
				//--myLog.i("_log_Activity", "---MApplication----ActivityLifecycleCallbacks--onActivityResumed-->>"+activity.getLocalClassName());
			}

			@Override
			public void onActivityPaused(Activity activity) {
				//--myLog.i("_log_Activity", "---MApplication----ActivityLifecycleCallbacks--onActivityPaused-->>"+activity.getLocalClassName());
			}

			@Override
			public void onActivityStopped(Activity activity) {
				//--myLog.i("_log_Activity", "---MApplication----ActivityLifecycleCallbacks--onActivityStopped-->>"+activity.getLocalClassName());
			}

			@Override
			public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
				//--myLog.i("_log_Activity", "---MApplication----ActivityLifecycleCallbacks--onActivitySaveInstanceState-->>"+activity.getLocalClassName());
			}

			@Override
			public void onActivityDestroyed(Activity activity) {
				try {
					mListActivity.remove(activity);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}};
			registerActivityLifecycleCallbacks(callback);
    }
    
	/*@Override
	public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
		super.registerActivityLifecycleCallbacks(callback);
	}  */
    
	@Override
	public void onTerminate() {
		ALog.e("_log_Activity", "---MApplication----onTerminate-->>>");
		super.onTerminate();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		ALog.e("_log_Activity", "---MApplication----onConfigurationChanged-->>>");
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onLowMemory() {
		ALog.e("_log_Activity", "---MApplication----onLowMemory-->>>");
		super.onLowMemory();
	}
	
	//=============================================================================
	public void exit() {
		try {
			for (Activity activity : mListActivity) {
				if (activity != null){
					ALog.i("_log_Activity", "--MApplication--exit-->>"+activity.getClass().getName());
					activity.finish();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				System.exit(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void finishActivity() {
		try {
			for (Activity activity : mListActivity) {
				if (activity != null){
					ALog.i("_log_Activity", "--MApplication--finishActivity-->>"+activity.getClass().getName());
					activity.finish();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getActivityCount() {
		return mListActivity.size();
	}

	//=============================================================================
	/**
	 * 全包名+类名
	 * @param PackageName
	 */
	public boolean isContainActivity(String PackageName) {
		try {
			for (Activity activity : mListActivity) {
				if (activity != null){
					if(activity.getClass().getName().equals(PackageName)){
						ALog.i("_log_Activity", "--MApplication--isContainActivity-->>"+activity.getClass().getName());
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}
}