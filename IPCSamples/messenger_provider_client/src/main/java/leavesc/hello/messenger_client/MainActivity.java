package leavesc.hello.messenger_client;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.grandcentrix.tray.AppPreferences;

import java.util.List;

/**
 * 作者：leavesC
 * 时间：2019/4/4 10:46
 * 描述：
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int CODE_MESSAGE = 1;

    private Messenger messenger;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            messenger = null;
        }
    };

    private EditText et_message;

    private Messenger replyMessenger;

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_MESSAGE: {
                    Log.e(TAG, "客户端收到了服务端回复的消息：" + msg.arg1);
                    break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(leavesc.hello.messenger_client.R.layout.activity_main);
        bindService();
        initView();
        replyMessenger = new Messenger(new MessengerHandler());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setClassName("leavesc.hello.messenger_server", "leavesc.hello.messenger_server.MessengerService");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void initView() {
        et_message = findViewById(leavesc.hello.messenger_client.R.id.et_message);
        Button btn_sendMessage = findViewById(leavesc.hello.messenger_client.R.id.btn_sendMessage);
        btn_sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messenger == null) {
                    return;
                }
                String content = et_message.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    return;
                }
                int arg1 = Integer.valueOf(content);
                Intent intent = new Intent("Action");
                Message message = new Message();
                message.what = CODE_MESSAGE;
                message.arg1 = arg1;
                message.obj = intent;
                //双向通信时需要加上这一句
                message.replyTo = replyMessenger;
                try {
                    messenger.send(message);
                    Log.e(TAG, "消息发送成功");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btn_provider).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dealProvider();
            }
        });

        findViewById(R.id.btn_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dealPermissionActivity();
            }
        });

        findViewById(R.id.btn_receiver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBroadcast_Permission();
            }
        });

        findViewById(R.id.btn_ContentProviderActi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(MainActivity.this, ContentProviderTest.class);
                MainActivity.this.startActivity(intent);*/
                ContentProviderTest.dealProvider(MainActivity.this);
            }
        });


    }

    //============================================

    public static final String AUTOHORITY = "leavesc.hello.messenger_server.MyProvider";

    private void dealProvider(){
        /**
         * 对user表进行操作
         */

        // 设置URI
        Uri uri_user = Uri.parse("content://"+AUTOHORITY+"/user");

        // 插入表中数据
        ContentValues values = new ContentValues();
        values.put("_id", 4);
        values.put("name", "Jordan");


        // 获取ContentResolver
        ContentResolver resolver =  getContentResolver();
//        resolver.acquireProvider();


        // 通过ContentResolver 根据URI 向ContentProvider中插入数据
//        try {
//            IContentProvider provider = resolver.acquireProvider(url);
            resolver.insert(uri_user,values);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.i("wjw02","190512CProvider-MainActivity-query Exception" +e.getMessage());
//        }

        // 通过ContentResolver 向ContentProvider中查询数据
        Cursor cursor = resolver.query(uri_user, new String[]{"_id","name"}, null, null, null);
        while (cursor.moveToNext()){
            Log.i("wjw02","190512CProvider-MainActivity-query book:" +
                    cursor.getInt(0) +" "+ cursor.getString(1));
            // 将表中数据全部输出
        }

        resolver.insert(uri_user,values);

        cursor.close();
        // 关闭游标
        Log.i("wjw02","190512CProvider-MainActivity-query 09" );

        /**
         * 对job表进行操作
         */
        // 和上述类似,只是URI需要更改,从而匹配不同的URI CODE,从而找到不同的数据资源
        Uri uri_job = Uri.parse("content://"+AUTOHORITY+"/job");

        // 插入表中数据
        ContentValues values2 = new ContentValues();
        values2.put("_id", 4);
        values2.put("job", "NBA Player");

        // 获取ContentResolver
        ContentResolver resolver2 =  getContentResolver();
        // 通过ContentResolver 根据URI 向ContentProvider中插入数据
        resolver2.insert(uri_job,values2);

        // 通过ContentResolver 向ContentProvider中查询数据
        Cursor cursor2 = resolver2.query(uri_job, new String[]{"_id","job"}, null, null, null);
        while (cursor2.moveToNext()){
            Log.i("wjw02","190512CProvider-MainActivity-query job:"
                    + cursor2.getInt(0) +" "+ cursor2.getString(1));
            // 将表中数据全部输出
        }
        cursor2.close();
        // 关闭游标
    }

    //============================================

    private void dealPermissionActivity(){
        AppPreferences mAppPreferences = new AppPreferences(this);
        mAppPreferences.put("AppPreferences_messenger_client_key","from messenger_client");
        Intent intent = new Intent();
        intent.setAction("leavesc.hello.messenger_server.PermissionActivity");
        startActivity(intent);
    }



    public void sendBroadcast_Permission(){

        Intent intent = new Intent();
        intent.setAction("com.itheima.action.mybroadcast");
//		sendBroadcast(intent);

//        String receiverPermission="com.itheima.permission.mybroadcast";
//        //参数2：广播接收者应该具备的权限
//        sendBroadcast(intent, receiverPermission);

        sendImplicitBroadcast(this,intent);
    }

    private void sendImplicitBroadcast(Context ctxt, Intent intent) {
        PackageManager pm=ctxt.getPackageManager();
        List<ResolveInfo> matches=pm.queryBroadcastReceivers(intent, 0);

        for (ResolveInfo resolveInfo : matches) {
            Intent explicit=new Intent(intent);
            ComponentName cn = new ComponentName(resolveInfo.activityInfo.applicationInfo.packageName,
                    resolveInfo.activityInfo.name);
            explicit.setComponent(cn);

            String receiverPermission="com.itheima.permission.mybroadcast";
            ctxt.sendBroadcast(explicit,receiverPermission);
        }
    }


}
