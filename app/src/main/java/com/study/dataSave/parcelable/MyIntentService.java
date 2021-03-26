package com.study.dataSave.parcelable;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class MyIntentService extends IntentService {
	
	
	public MyIntentService(){
		this("myThreadName");
	}

	/*
	 * name:用于处理onHandleIntent方法的线程的名称
	 */
	public MyIntentService(String name) {
		super(name);
	}

	/*
	 * 用于处理Intent请求，该方法是在子线程中执行的。给这个子线程起个名称。
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d("tag", "ThreadName="+Thread.currentThread().getName());
		String url = intent.getStringExtra("url");
		Log.d("tag", "开始下载："+url);
		//模拟耗时操作
		SystemClock.sleep(5000);
		Log.d("tag", "执行完任务了");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("tag", "onDestroy");
	}

}
