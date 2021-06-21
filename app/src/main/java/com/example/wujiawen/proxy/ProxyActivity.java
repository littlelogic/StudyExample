package com.example.wujiawen.proxy;//com.example.wujiawen.proxy.ProxyActivity

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.wujiawen.Example_b.ProgressView;
import com.example.wujiawen.Example_b.SpringProgressView;
import com.example.wujiawen.a_Main.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Random;

/***
 * 设置界面
 * @author smz
 * 创建时间：2014-1-7上午10:51:21
 */
public class ProxyActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Test.main(null);

		try {



			TelephonyManager hTelephonyManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
			Class clazz_ = hTelephonyManager.getClass();
			Method method = clazz_.getDeclaredMethod("getITelephony");
			method.setAccessible(true);
			Object return_back = method.invoke(hTelephonyManager);
			if (return_back != null) {
				try {
					boolean b_back = (boolean)return_back;
					System.out.println(b_back);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}


		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}



	}
}
