package com.example.longlight;
//com.example.wujiawen.a_Main.QuickStartTileService

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.PermissionChecker;

@RequiresApi(api = Build.VERSION_CODES.N)
public class QuickStartTileService extends TileService {

    public QuickStartTileService() {
        super();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();
    }

    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
    }

    @Override
    public void onClick() {
        super.onClick();

        QuickStartTileActivity.mQuickStartTileService = this;
        Intent intent = new Intent(this,com.example.longlight.QuickStartTileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("longlight",true);
        this.startActivity(intent);
        if (true) {
            return;
        }

        boolean mark = /*Tools.*/checkPermissionGranted(this.getApplication(), Manifest.permission.WRITE_SETTINGS);
        mark = Settings.System.canWrite((Context)this);

        SharedPreferences sp = this.getApplication().getSharedPreferences("QuickStartTileService210630", Context.MODE_PRIVATE);
        boolean light = sp.getBoolean("SCREEN_OFF_TIMEOUT",true);
        Log.e("11","210630p-QuickStartTileService-onClick-wws-"
                + "-mark->"+mark
                + "-light->"+light
        );
        boolean set_mark;
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (light) {
            set_mark = Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,2 * 60 * 000);
            getQsTile().setState(Tile.STATE_INACTIVE);
            getQsTile().updateTile();
            sp.edit().putBoolean("SCREEN_OFF_TIMEOUT",false).commit();
            Toast.makeText(this.getApplication(),"已设置息屏时间3秒",Toast.LENGTH_LONG).show();
        } else {
//            set_mark = Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,-1);
//            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,Integer.MAX_VALUE);
//            set_mark = Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,30 * 000);
                      set_mark = Settings.System.putInt(this.getContentResolver(), "screen_off_timeout", 86400000);
            getQsTile().setState(Tile.STATE_ACTIVE);
            getQsTile().updateTile();
            sp.edit().putBoolean("SCREEN_OFF_TIMEOUT",true).commit();
            Toast.makeText(this.getApplication(),"已设置屏幕长亮",Toast.LENGTH_LONG).show();
        }
        int  timeOut = Settings.System.getInt(getContentResolver(), "screen_off_timeout", -1212);
        Log.e("11","210630p-QuickStartTileService-onClick-89-"
                + "-set_mark->"+set_mark
                + "-timeOut->"+timeOut
        );



        /*if (true || mark) {
            Settings.System.putInt(getContentResolver(),android.provider.Settings.System.SCREEN_OFF_TIMEOUT,-1);
            getQsTile().setState(Tile.STATE_ACTIVE);
            getQsTile().updateTile();
        } else {
            Intent intent = new Intent(this,com.example.wujiawen.a_Main.QuickStartTileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
        }*/


    }

    public static boolean checkPermissionGranted(Context context, String permission) {
        // Android 6.0 以前，全部默认授权
        boolean result = true;
        int targetSdkVersion = 21;
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                // targetSdkVersion >= 23, 使用Context#checkSelfPermission
                result = context.checkSelfPermission(permission)
                        == PackageManager.PERMISSION_GRANTED;
            } else {
                // targetSdkVersion < 23, 需要使用 PermissionChecker
                result = PermissionChecker.checkSelfPermission(context, permission)
                        == PermissionChecker.PERMISSION_GRANTED;
            }
        }
        return result;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
