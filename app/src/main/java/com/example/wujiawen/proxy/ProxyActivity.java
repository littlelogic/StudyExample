package com.example.wujiawen.proxy;//com.example.wujiawen.proxy.ProxyActivity

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.wujiawen.Example_b.ProgressView;
import com.example.wujiawen.Example_b.SpringProgressView;
import com.example.wujiawen.a_Main.R;

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
	}
}
