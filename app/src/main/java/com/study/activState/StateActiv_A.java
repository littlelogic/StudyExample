package com.study.activState;
//com.study.activState.GlideActiv
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.badlogic.utils.ALog;
import com.example.wujiawen.a_Main.R;
import com.study.Fragment.fragment.FragmentA;
import com.study.Fragment.fragment.FragmentB;
import com.study.Fragment.fragment.FragmentC;

import java.util.ArrayList;
import java.util.List;

public class StateActiv_A extends FragmentActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		onCreateImpl(savedInstanceState);
		setContentView(R.layout.activity_fragment_a);

		test();
	}

	void test(){
		WebView webview = (WebView)this.findViewById(R.id.webview);
		////String url = "http://c.kuai.360.cn/kjji/activity/m/tutorial_collection.html?_id=31&m2=d25714862e2b89292be814195267a080";
		String url = "https://c.kuai.360.cn/kjji/activity/m/ceshi.html#/";
		try {
			////webview.loadUrl(url);
			url = "http://dv.browser.360.cn/Object.access/upload4cutter/M0E0REFBNDQtNzRCMS00MzY4LTlDNjUtRDU4ODI0QkQ1RDUxMTU1OTgxNzkwNzI3OTE1NTk4MTcwNTczNDdfY29tcG9zZVZpZGVvLm1wNA==?time=1582819200&sign=26da3a80331641a2005513d4799fed5c";
//			webview.loadUrl(url);
			///webview.loadUrl("http://www.baidu.com");

			//获取剪贴板管理器：
			ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
			ClipData mClipData = ClipData.newPlainText("Label", url);
// 将ClipData内容放到系统剪贴板里。
			cm.setPrimaryClip(mClipData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public void onClick(View v) {
		
	}

	//===============================

	@Override
	protected void onStop() {
		super.onStop();
		ALog.d(ALog.Tag2, "190506state-onStop");
	}

	//===============================

	protected void onCreateImpl(Bundle savedInstanceState) {
		ALog.d(ALog.Tag2, "190506state-onCreateImpl");
		if(savedInstanceState!=null){
			String data = savedInstanceState.getString("data");
			ALog.d(ALog.Tag2, "onCreateImpl-获取到之前保存的数据："+data);
			Toast.makeText(this, "onCreateImpl-data="+data, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		ALog.d(ALog.Tag2, "190506state-onPause");
	}

	/*
	  在Activity的onStop方法被调用之前肯定会调用，用于保存当前的状态。
	  默认的Activity onSaveInstanceState方法会做保存带id的view（Edittext这样的控件）的状态
	  onSaveInstanceState方法和onRestoreInstanceState方法“不一定”是成对的被调用的，
	  onRestoreInstanceState被调用的前提是，activity A“确实”被系统销毁了
	  bac键的返回，不触发保存
	  数据序列化到本地的android的缓存文件中
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		ALog.d(ALog.Tag2, "190506state-onSaveInstanceState");
		outState.putString("data", "这是我保存的数据");
	}

	@Override
	protected void onRestoreInstanceState(Bundle outState) {
		super.onRestoreInstanceState(outState);
		ALog.d(ALog.Tag2, "190506state-onRestoreInstanceState");
		if(outState!=null){
			String data = outState.getString("data");
			ALog.d(ALog.Tag2, "onRestoreInstanceState-获取到之前保存的数据："+data);
			Toast.makeText(this, "onRestoreInstanceState-data="+data, Toast.LENGTH_SHORT).show();
		}
	}


}
