package com.pzf.liaotian;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by wujiawen on 2018/4/4.
 */

public class PhotoSelectRecieveService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("wjw02","PhotoSelectRecieveService--onCreate--01->");
        String callingApp=this.getPackageManager().getNameForUid(Binder.getCallingUid());
        Log.i("wjw02","PhotoSelectRecieveService--onBind--callingApp->"+callingApp);

    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.i("wjw02","PhotoSelectRecieveService--onStart--01->");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        Log.i("wjw02","PhotoSelectRecieveService--onStartCommand--01->");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("wjw02","PhotoSelectRecieveService--onBind--01->");

        String callingApp=this.getPackageManager().getNameForUid(Binder.getCallingUid());
        Log.i("wjw02","PhotoSelectRecieveService--onBind--callingApp->"+callingApp);

        return null;
    }




}
