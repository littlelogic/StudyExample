package com.study.dataSave.parcelable;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;

import com.badlogic.utils.Log;



class MessengerServ extends Service {
    public MessengerServ() {

    }
    public IBinder onBind(Intent intent) {
        return  mMessenger.getBinder();
    }
    private MessengerHandler mHandler=new MessengerHandler();
    private Messenger mMessenger=new Messenger(mHandler);
    private class MessengerHandler extends Handler {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("客户端的消息："+msg.obj.toString());
            Message message = Message.obtain();
            message.obj = "今天就不去了，还有很多东西要学！！";
            try {msg.replyTo.send(message);/*回复*/}catch(Exception e){}
        }
    }
}
public class Messenger__think extends Activity {
    private boolean mBond;
    private Messenger mMessenger;
    private GetRelyHandler mGetRelyHandler = new GetRelyHandler();
    private Messenger mRelyMessenger = new Messenger(mGetRelyHandler);
    private void sendMessengerDo(){
        Message message = Message.obtain();
        message.obj = "今天出去玩吗?";
        /** <在message中添加一个回复mRelyMessenger对象> */
        message.replyTo = mRelyMessenger;
        try { mMessenger.send(message);}catch(RemoteException e){}
    }
    public /*static*/ class  GetRelyHandler extends Handler {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("服务端的回复："+msg.obj.toString());
        }
    }
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName name,IBinder service){
            mMessenger =new Messenger(service);//获取服务端关联的Messenger对象
            mBond = true;                                                 }
        public void onServiceDisconnected(ComponentName name) {
            mBond = false;  mMessenger =null;                 }
    };
    public void sendMessenger(View view){//todo 开始
        if (mMessenger == null) {
            Intent intent=new Intent(this,MessengerServ.class);
            bindService(intent,mConnection,BIND_AUTO_CREATE);
        } else  sendMessengerDo();
    }
    protected void onDestroy() {
        if (mBond) unbindService(mConnection);
        super.onDestroy();
    }
}
