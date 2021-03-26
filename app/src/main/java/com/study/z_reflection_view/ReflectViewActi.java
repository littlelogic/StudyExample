package com.study.z_reflection_view;
//com.study.z_reflection_view.ReflectViewActi
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wujiawen.a_Main.R;


public class ReflectViewActi extends Activity {
	
	@ViewInject(R.id.tv1)
	private TextView tv;
	
	@ViewInject(R.id.tv2)
	private TextView tv2;
	
	private  int count;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_reflection);
		
		ViewUtils.inject(this);
		
		Log.d("tag", "tv1="+tv.getText()+",tv2="+tv2.getText());
	}
	
	@OnClick(R.id.btn)
	private void clickMe(View view){
		Toast.makeText(this, "点击我了", Toast.LENGTH_SHORT).show();
	}
	
	public void test(){
		
	}

}
