package com.study.dataSave.parcelable;
//com.study.dataSave.parcelable.MessengerService
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.badlogic.utils.ALog;

public class MessengerService extends Service {

    public MessengerService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        IBinder mIBinder =  mMessenger.getBinder();
        ALog.i(ALog.Tag2, "MessengerService-onBind"
                +"-mIBinder->"+mIBinder
                +"-IBinder.hashCode()->"+mIBinder.hashCode()
        );
        return mIBinder;
    }

    private MessengerHandler mHandler=new MessengerHandler();

    private Messenger mMessenger=new Messenger(mHandler);

    ///private static class MessengerHandler extends Handler {
    private class MessengerHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //取出客户端的消息内容
            Bundle bundle = msg.getData();
            String clientMsg = bundle.getString("client");
            String contentstr = "来自客户端的消息："+clientMsg
                    + "\n" +MainActivity.share_infor
                    ;
            ALog.i(ALog.Tag2,contentstr);
            Toast.makeText(MessengerService.this.getApplication(), contentstr, Toast.LENGTH_SHORT).show();
            //新建一个Message对象，作为回复客户端的对象
            Message message = Message.obtain();
            Bundle bundle1 = new Bundle();
            bundle1.putString("service","今天就不去了，还有很多东西要学！！");
            message.setData(bundle1);
            try {
                msg.replyTo.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
