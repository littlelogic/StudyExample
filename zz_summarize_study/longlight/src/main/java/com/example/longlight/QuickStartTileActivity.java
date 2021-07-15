package com.example.longlight;
//com.example.longlight.QuickStartTileActivity
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.service.quicksettings.Tile;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.badlogic.utils.Tools;

public class QuickStartTileActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("11","210630p-QuickStartTileActivity-onCreate-wws-"
//                + "-differ_time->"+differ_time
        );
        try {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_SETTINGS}, 123);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("11","210630p-QuickStartTileActivity-onCreate-Exception-"+e
            );
//
        }

        TextView nTextView = new TextView(this);
        nTextView.setBackgroundColor(Color.WHITE);
        nTextView.setText("WRITE_SETTINGS");
        this.setContentView(nTextView);
        nTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///sett();
                try {
                    ActivityCompat.requestPermissions(QuickStartTileActivity.this, new String[]{Manifest.permission.WRITE_SETTINGS}, 123);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("11","210630p-QuickStartTileActivity-onCreate-Exception-"+e
                    );
//
                }
            }
        });

        dealIntent(this.getIntent());
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sett();
        }*/
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        dealIntent(intent);
    }

    private void dealIntent(Intent intent){
        if (intent != null) {
            boolean mark = intent.getBooleanExtra("longlight",false);
            if (mark) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    sett();
                }
            }
        }
    }

    public static QuickStartTileService mQuickStartTileService;

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sett(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.System.canWrite((Context)this)) {
                Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
                intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                startActivity(intent);
            } else {
                boolean mark = Tools.checkPermissionGranted(this.getApplication(), Manifest.permission.WRITE_SETTINGS);
                mark = Settings.System.canWrite((Context)this);

                SharedPreferences sp = this.getApplication().getSharedPreferences("QuickStartTileService210630", Context.MODE_PRIVATE);
                boolean light = sp.getBoolean("SCREEN_OFF_TIMEOUT",true);
                Log.e("11","210630p-QuickStartTileActivity-onClick-wws-"
                        + "-mark->"+mark
                        + "-light->"+light
                );
                boolean set_mark;
                try {
//                    Thread.sleep(5 * 1000);
//                    Thread.sleep(3 * 1000);
//                    Thread.sleep(2 * 1000);
//                    Thread.sleep(1 * 1000);
//                    Thread.sleep(800);
//                    Thread.sleep(700);
                    Thread.sleep(600);//todo 为什么要停留这个时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (light) {
                    set_mark = Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,2 * 60 * 1000);
                    if (mQuickStartTileService != null){
                        mQuickStartTileService.getQsTile().setState(Tile.STATE_INACTIVE);
                        mQuickStartTileService.getQsTile().updateTile();
                    }
                    sp.edit().putBoolean("SCREEN_OFF_TIMEOUT",false).commit();
                    Toast.makeText(this.getApplication(),"已设置息屏时间3秒",Toast.LENGTH_LONG).show();
                } else {
//            set_mark = Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,-1);
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,Integer.MAX_VALUE);
//            set_mark = Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT,30 * 000);
                    set_mark = Settings.System.putInt(this.getContentResolver(), "screen_off_timeout", 86400000);
                    if (mQuickStartTileService != null){
                        mQuickStartTileService.getQsTile().setState(Tile.STATE_ACTIVE);
                        mQuickStartTileService.getQsTile().updateTile();
                    }
                    sp.edit().putBoolean("SCREEN_OFF_TIMEOUT",true).commit();
                    Toast.makeText(this.getApplication(),"已设置屏幕长亮",Toast.LENGTH_LONG).show();
                }
                int  timeOut = Settings.System.getInt(getContentResolver(), "screen_off_timeout", -1212);
                Log.e("11","210630p-QuickStartTileActivity-onClick-89-"
                        + "-set_mark->"+set_mark
                        + "-timeOut->"+timeOut
                );
                mQuickStartTileService.stopSelf();
                this.finish();

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

    }
}
