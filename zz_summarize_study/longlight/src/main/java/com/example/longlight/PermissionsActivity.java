package com.example.longlight;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PermissionsActivity extends Activity {
    private Button l;

    private Button m;

    private Button n;

    public void onBackPressed() {
        Log.i("ME", "PermissionsActi- onBackPressed");
        moveTaskToBack(true);
        finish();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
//        setContentView(2131296284);
//        this.l = (Button)findViewById(2131165225);
//        this.m = (Button)findViewById(2131165224);
//        this.n = (Button)findViewById(2131165223);
        if (Build.VERSION.SDK_INT >= 23)
            if (!Settings.System.canWrite((Context)this)) {
                Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
                intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                startActivity(intent);
            } else {
//                startActivity(new Intent((Context)this, OperationActivity.class));
            }
        this.l.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS");
                intent.setData(Uri.parse("package:" + PermissionsActivity.this.getApplicationContext().getPackageName()));
                PermissionsActivity.this.startActivity(intent);
            }
        });
        this.m.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                PermissionsActivity.this.moveTaskToBack(true);
                PermissionsActivity.this.finish();
            }
        });
        this.n.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                Intent intent = null;//new Intent("android.intent.action.SENDTO", Uri.parse("mailto:davideinzaghilovaoro@gmail.com?subject=" + Uri.encode("App: " + this.a.getResources().getString(2131427361)) + "&body=" + Uri.encode("")));
                try {
                    PermissionsActivity.this.startActivity(intent);
                    Log.i("ME", "Email inviata");
                    return;
                } catch (ActivityNotFoundException activityNotFoundException) {
//                    Toast.makeText((Context)PermissionsActivity.this, PermissionsActivity.this.getResources().getString(2131427405), 1).show();
                    return;
                }
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        Log.i("ME", "PermissionsActi- onDestroy");
    }

    public void onPause() {
        super.onPause();
        Log.i("ME", "PermissionsActi- onPause");
    }

    public void onResume() {
        super.onResume();
        Log.i("ME", "PermissionsActi- onResume");
    }

    public void onStart() {
        super.onStart();
        Log.i("ME", "PermissionsActi- onStart");
        if (Build.VERSION.SDK_INT >= 23) {
            Log.i("ME", "PermissionsActi- onStart-Versione api superiore a M");
            if (!Settings.System.canWrite((Context)this)) {
                Log.i("ME", "PermissionsActi- onStart--permesso non trovato");
                return;
            }
            Log.i("ME", "PermissionsActi- onStart-permesso autorizzato, vado alla activity Operatione ");
//            startActivity(new Intent((Context)this, OperationActivity.class));
            finish();
            return;
        }
        Log.i("ME", "PermissionsActi- onStart-permesso autorizzato durante l'installazione, vado alla activity Operatione ");
//        startActivity(new Intent((Context)this, OperationActivity.class));
        finish();
    }

    public void onStop() {
        super.onStop();
        Log.i("ME", "PermissionsActi- onStop");
    }
}
