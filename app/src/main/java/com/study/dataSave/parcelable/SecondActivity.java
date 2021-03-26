package com.study.dataSave.parcelable;

//com.study.dataSave.parcelable.SecondActivity

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.badlogic.utils.ALog;
import com.example.wujiawen.a_Main.R;

import net.grandcentrix.tray.AppPreferences;

public class SecondActivity extends Activity {

	private TextView tv_data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activ_parcelable_second);
		tv_data = (TextView) findViewById(R.id.tv_data);
		
		//获取MainActivity传递过来的数据
		Intent intent = getIntent();
		if(intent != null) {

			AppPreferences appPreferences = new AppPreferences(this);
			String appSp = appPreferences.getString("appPreferences_key", "defaul");

			User user = intent.getParcelableExtra("data");
			String contentStr = user.toString()
					+ "\n-ContentProviderActi.share_infor->"+MainActivity.share_infor
					+ "\n-appSp->"+appSp
					;


				tv_data.setText(contentStr);
			Toast.makeText(this, contentStr, Toast.LENGTH_LONG).show();
		}
	}




}
