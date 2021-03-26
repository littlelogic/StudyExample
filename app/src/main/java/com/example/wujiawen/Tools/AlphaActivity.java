package com.example.wujiawen.Tools;//com.example.wujiawen.Tools.AlphaActivity

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.badlogic.utils.ALog;
import com.example.wujiawen.ExampleScrollView.MyScrollView;
import com.example.wujiawen.a_Main.R;

/***
 * 设置界面
 * @author smz
 * 创建时间：2014-1-7上午10:51:21
 */
public class AlphaActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activ_alpha_test);


		EditText  hEditText = (EditText)this.findViewById(R.id.edit);
		final TextView Text_10 = (TextView)this.findViewById(R.id.text_10);
		final TextView Text_16 = (TextView)this.findViewById(R.id.text_16);
		hEditText.addTextChangedListener(new TextWatcher(){
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,int count) {
			}

			@Override
			public void afterTextChanged(Editable editable) {

				try {
					String contentStr = editable.toString().trim();
					float value = Float.parseFloat(contentStr);
					if (value > 1) {
						value = 1;
					}
					if (value < 0) {
						value = 0;
					}

					int value_10 = (int)(value * 255);
					String value_16 = getSexteen(value_10);
					Text_10.setText("10进制："+value_10);
					Text_16.setText("16进制："+value_16);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}});



	}

	public String getSexteen(int ten) {
		int sexteen = 0;
		int max = 0;
		int result [] = new int[20];
		do{
			sexteen = ten % 16;
			ten = ten / 16;

			if(sexteen > 9) {
				sexteen = (sexteen-10)+'A';
				result[max] = sexteen;
				max++;
			}else {
				result[max] = sexteen;
				max++;
			}
		}while (ten != 0);

		//显示
		StringBuilder mStringBuilder = new StringBuilder();
		for(int i=max-1;i>=0;i--) {
			if(result[i] > 9) {
				char char_value = (char)result[i];
				char[] array_code = {char_value};
				mStringBuilder.append(new String(array_code));
			}else {
				mStringBuilder.append(result[i]);
			}
		}
		return mStringBuilder.toString();
	}
}
