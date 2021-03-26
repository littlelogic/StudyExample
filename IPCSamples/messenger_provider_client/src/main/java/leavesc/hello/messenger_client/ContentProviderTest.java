package leavesc.hello.messenger_client;

import android.app.Activity;
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



public class ContentProviderTest {
    public static void dealProvider(Context mContext){
        String AUTOHORITY = "leavesc.hello.messenger_server.ProviderStudy";
        Uri uri_user = Uri.parse("content://"+AUTOHORITY+"/user");
        ContentResolver resolver =  mContext.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("_id", 4);
        values.put("name", "Jordan");
        // 通过ContentResolver 根据URI 向ContentProvider中插入数据
        resolver.insert(uri_user,values);//插入数据
        // 通过ContentResolver 向ContentProvider中查询数据
        Cursor cursor = resolver.query(uri_user, new String[]{"_id","name"},null, null, null);
        while (cursor.moveToNext())
            Log.i("wjw02","190512CProvider-ContentProviderTest-dealProvider :" +
                    cursor.getInt(0) +" "+ cursor.getString(1));
        cursor.close();// 关闭游标
    }
}
