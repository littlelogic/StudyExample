package com.example.wujiawen.Example_b;//com.example.wujiawen.studyexample_b.ContentProviderActi
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.wujiawen.a_Main.R;

/***
 * 设置界面
 * @author smz
 * 创建时间：2014-1-7上午10:51:21
 */
public class MainActivity extends Activity implements OnClickListener {

	private TextView textView;
	private SpringProgressView progressView;
	private Random random = new Random(System.currentTimeMillis());
	private ProgressView mProgressView;
	private Button test;
	private int value;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout_12);
		textView = (TextView) findViewById(R.id.textview);
		progressView = (SpringProgressView) findViewById(R.id.spring_progress_view);
		progressView.setMaxCount(1000.0f);
		findViewById(R.id.click).setOnClickListener(this);
		
		
		mProgressView = (ProgressView)findViewById(R.id.my_progress);
		mProgressView.setMaxCount(100.0f);
		value = random.nextInt(100)+1;
		mProgressView.setCurrentCount(value);
		textView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});


		progressView.setCurrentCount(1000);
		value = 100;
		mProgressView.setCurrentCount(value);
		mProgressView.setScore(value,false);
	}
	@Override
	public void onClick(View v) {
		progressView.setCurrentCount(random.nextInt(1000));
		textView.setText("最大值："+progressView.getMaxCount()+"   当前值："+progressView.getCurrentCount());

		value = random.nextInt(100)+1;
		mProgressView.setCurrentCount(value);
		mProgressView.setScore(value,false);
	}
}
