package leavesc.hello.aidl_client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import leavesc.hello.aidl_server.IOnOperationCompletedListener;
import leavesc.hello.aidl_server.IOperationManager;
import leavesc.hello.aidl_server.Parameter;

/**
 * 作者：leavesC
 * 时间：2019/4/4 10:45
 * 描述：客户端
 */
public class MainActivity22 extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText et_param1;
    private EditText et_param2;
    private EditText et_result;

    private IOperationManager iOperationManager;
    IOnOperationCompletedListener listener=new IOnOperationCompletedListener.Stub(){
        public void onOperationCompleted(final Parameter result) throws RemoteException{
            runOnUiThread(new Runnable() {//todo 接收的线程是client_send ，一般转到主线程处理
                @Override public void run() {
                    et_result.setText("运算结果： " + result.getParam());
                }
            });
        }
    };
    private void sendData() {
        Thread hThread=new Thread(new Runnable() {//todo 转到子线程处理
            @Override public void run() {
                if (iOperationManager != null)
                    try {
                        iOperationManager.operation(new Parameter(12),new Parameter(34));
                    } catch (RemoteException e) {  }
            }
        }); hThread.setName("client_send"); hThread.start();
    }
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override public void onServiceConnected(ComponentName name,IBinder service) {
            iOperationManager = IOperationManager.Stub.asInterface(service);
        }
        @Override public void onServiceDisconnected(ComponentName name) {
            iOperationManager = null;
        }
    };
    private void bindService() {
        Intent intent = new Intent();
        intent.setClassName("leavesc.hello.aidl_server","leavesc.hello.aidl_server.AIDLService");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
    @Override protected void onDestroy() { super.onDestroy();
        if (serviceConnection != null) unbindService(serviceConnection);
    }







    private void initView() {
        et_param1 = findViewById(R.id.et_param1);
        et_param2 = findViewById(R.id.et_param2);
        et_result = findViewById(R.id.et_result);
        Button btn_registerListener = findViewById(R.id.btn_registerListener);
        Button btn_unregisterListener = findViewById(R.id.btn_unregisterListener);
        Button btn_operation = findViewById(R.id.btn_operation);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_registerListener: {
                        if (iOperationManager != null) {
                            try {
                                Log.i("wjw02","190806a-client-MainActivity-iOperationManager->");
                                iOperationManager.registerListener(listener);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                                Log.i("wjw02","190806a-client-MainActivity-onOperationCompleted-RemoteException->"+e.getMessage());
                            }
                        }
                        break;
                    }
                    case R.id.btn_unregisterListener: {
                        if (iOperationManager != null) {
                            try {
                                Log.i("wjw02","190806a-client-MainActivity-unregisterListener->");
                                iOperationManager.unregisterListener(listener);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case R.id.btn_operation: {
                        if (TextUtils.isEmpty(et_param1.getText()) || TextUtils.isEmpty(et_param2.getText())) {
                            return;
                        }
                        final int param1 = Integer.valueOf(et_param1.getText().toString());
                        final int param2 = Integer.valueOf(et_param2.getText().toString());
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Parameter parameter1 = new Parameter(param1);
                                Parameter parameter2 = new Parameter(param2);
                                if (iOperationManager != null) {
                                    try {
                                        Log.i("wjw02","190806a-client-MainActivity-operation->");
                                        iOperationManager.operation(parameter1, parameter2);
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();
                        break;
                    }
                }
            }
        };
        btn_registerListener.setOnClickListener(clickListener);
        btn_unregisterListener.setOnClickListener(clickListener);
        btn_operation.setOnClickListener(clickListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("wjw02","190806a-client-MainActivity-onCreate->");
        setContentView(R.layout.activity_main);
        initView();
        bindService();
    }



}
