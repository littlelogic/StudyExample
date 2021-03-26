package leavesc.hello.messenger_server;
//leavesc.hello.messenger_server.MyBroadcastReceiver

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("tag", "接收到广播："+intent);
		Toast.makeText(context, "接收到广播："+intent, Toast.LENGTH_LONG).show();
	}

}
