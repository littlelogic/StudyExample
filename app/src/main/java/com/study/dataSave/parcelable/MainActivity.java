package com.study.dataSave.parcelable;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.badlogic.utils.ALog;
import com.example.wujiawen.a_Main.R;

import net.grandcentrix.tray.AppPreferences;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends Activity {

	public static String share_infor = "init";
	private static AppPreferences appPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appPreferences = new AppPreferences(this);
		setContentView(R.layout.activ_parcelable_main);
		share_infor = "onCreate";
	}
	
	//启动SecondActivity并传递数据
	public void send(View view){
		User user = new User();
		user.writeData();
		user.setAge(24);
		user.setName("张三");
		
		Intent intent = new Intent(this, SecondActivity.class);
		intent.putExtra("data", user);
		Bundle mBundle = new Bundle();
		mBundle.putParcelable("data", user);
		//--mBundle.putSerializable("");
		intent.putExtra("Bundle",mBundle);
		startActivity(intent);


		/*

		多进程支持的SharedPreferences（不推荐）
		我们原来的做法是使用SharedPreferences, 自然而然想到，SharedPreferences
		在MODE_PRIVATE MODE_PUBLIC 之外其实还可以设置多进程的Flag ———— MODE_MULTI_PROCESS
		SharedPreferences myPrefs = context.getSharedPreferences(MY_FILE_NAME,
		Context.MODE_MULTI_PROCESS | Context.MODE_PRIVATE);
		一旦我们设置了这个Flag，每次调用Context.getSharedPreferences 的时候系统会重新从SP文件中读入数据，
		因此我们在使用的时候每次读取和存入都要使用Context.getSharedPreferences 重新获取SP实例。
		即使是这样，由于SP本质上并不是多进程安全的，所以还是无法保证数据的同步，因此该方法我们并没有使用，我们也不推荐使用。
		首先官方已经在API23后废除了SharedPreference的多进程模式:

		而在Github上的开源项目** TrayPreferences ** 底层通过ContentProvider实现了跨进程共享数据的安全性，
		并且将上层API的使用封装的更像SharedProference。对跨进程SharedPreference有需求的同学不妨尝试使用它。

		 */
		//--------------------
		appPreferences.put("appPreferences_key", "from com.study.dataSave.parcelable.MainActivity");

	}

	//=========================================

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			unbindService(mConnection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	//=========================================

	private boolean mBond;
	private Messenger mService;

	private void sendMessengerDo(){
		//创建Message对象
		Message message = Message.obtain();
		Bundle bundle = new Bundle();
		bundle.putString("client","今天出去玩吗？");
		message.setData(bundle);
		//在message中添加一个回复mRelyMessenger对象
		message.replyTo=mRelyMessenger;
		try {
			mService.send(message);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			ALog.i(ALog.Tag2, "ContentProviderActi-onServiceConnected"
					+"-name->"+name
					+"-IBinder->"+service
					+"-IBinder.hashCode()->"+service.hashCode()
			);
			//获取服务端关联的Messenger对象
			mService=new Messenger(service);
			mBond = true;

		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mBond = false;
			mService =null;
		}
	};

	private GetRelyHandler mGetRelyHandler = new GetRelyHandler();
	private Messenger mRelyMessenger = new Messenger(mGetRelyHandler);

	///public static class  GetRelyHandler extends Handler {
	public class  GetRelyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			Bundle bundle = msg.getData();
			String serviceMsg = bundle.getString("service");
			String contentstr = "来自服务端的回复："+serviceMsg;
			ALog.i(ALog.Tag2, contentstr);
			Toast.makeText(MainActivity.this.getApplication(), contentstr, Toast.LENGTH_SHORT).show();
		}
	}


}
