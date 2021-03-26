package com.study.Fragment;
//com.study.Fragment.ContentProviderActi
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.badlogic.utils.ALog;
import com.example.wujiawen.a_Main.R;
import com.study.Fragment.fragment.FragmentA;
import com.study.Fragment.fragment.FragmentB;
import com.study.Fragment.fragment.FragmentC;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnClickListener {


	private FragmentA fragmentA;
	private FragmentB fragmentB;
	private FragmentC fragmentC;
	private List<Fragment> fragmentsList = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		onCreateImpl(savedInstanceState);
		setContentView(R.layout.activity_fragment_a);
		
		findViewById(R.id.btnA).setOnClickListener(this);
		findViewById(R.id.btnB).setOnClickListener(this);
		findViewById(R.id.btnC).setOnClickListener(this);
		
		fragmentA = new FragmentA();
		fragmentB = new FragmentB();
		fragmentC = new FragmentC();

		/**


		getFragmentManager、getSupportFragmentManager其实获取的都是
		Activity里面的<Fragment的管理器>，
		getFragmentManager是Activtiy的方法，
		getSupportFragmentManager是FragmentActivity的方法，
		FragmentActivity是V4包的类，3.0系统之前先有的。

		getChildFragmentManager是Fragment中的方法,app包还是v4包都有此方法，
		获取的是当前这个<Fragment中子一级的Fragment的管理器>，
		比如Activity中有个Fragment，Fragment里面又有Fragment。
		注意：Fragment中也有getFragmentManager，获取的是这个Fragment的管理器

		用了v4包中的Fragment必须使用v4包中的FragmentActivity，
		保持一致，不然会报错的，什么转化类型错误，其他随意																*/

		//将三个Fragment添加到帧布局中
		
		// 1. 获取FragmentManger
		FragmentManager fragmentManager = getSupportFragmentManager();
		// 2. 通过FragmentManager获取到事务管理器
		FragmentTransaction transaction=fragmentManager.beginTransaction();
		// 3. 将Fragment添加到帧布局中
		transaction.
		add(R.id.fl, fragmentA, "FragmentA").
		add(R.id.fl, fragmentB,"FragmentB").
		add(R.id.fl, fragmentC,"FragmentC").
		hide(fragmentB).
		hide(fragmentC).
		commitAllowingStateLoss();
		///commit();
		addToBackStack(fragmentA);//这个系统的 回退栈 不好用
		
	}

	private void addToBackStack(Fragment fragment) {
		if (fragmentsList.contains(fragment)) {
			//先将老的对象移除
			fragmentsList.remove(fragment);
			//将Fragment添加到集合的最后
			fragmentsList.add(fragment);
		}else {
			fragmentsList.add(fragment);
		}
	}
	
	@Override
	public void onBackPressed() {
//		super.onBackPressed();
		if (fragmentsList.size()>1) {
			//移除最顶端的Fragment
			fragmentsList.remove(fragmentsList.size()-1);
			//将下一个Fragment给显示出来
			showFragment(fragmentsList.get(fragmentsList.size()-1));
		}else {
			//如果当前栈中没有Fragment或者只有一个Fragment，直接退出Activity
			finish();
		}
		
		
	}

	private void showFragment(Fragment fragment) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.hide(fragmentA).
		hide(fragmentB).
		hide(fragmentC).
		show(fragment)
		.commit();
		
	}

	@Override
	public void onClick(View v) {
		
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		
		transaction.hide(fragmentA).hide(fragmentB).hide(fragmentC);
		
		switch (v.getId()) {
		case R.id.btnA:
			transaction.show(fragmentA);
			addToBackStack(fragmentA);
			break;
		case R.id.btnB:
			transaction.show(fragmentB);
			addToBackStack(fragmentB);
			break;
		case R.id.btnC:
			transaction.show(fragmentC);
			addToBackStack(fragmentC);
			break;

		default:
			break;
		}
		
		//把当前事务添加到会退栈中，系统的不太好用
		///transaction.addToBackStack(null);
		
		transaction.commit();
		
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
