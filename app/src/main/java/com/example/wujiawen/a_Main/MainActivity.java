package com.example.wujiawen.a_Main;//com.example.wujiawen.a_Main.ContentProviderActi

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

import com.badlogic.utils.ALog;
import com.badlogic.utils.Tools;
import com.example.wujiawen.ExampleLanguage.LanguageActiv;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

//public class ContentProviderActi extends BaseActivity {
public class MainActivity extends Activity {
    TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activ_main);
        //---------------
        FunctionListAdapter mFunctionListAdapter=new FunctionListAdapter(this);
        mFunctionListAdapter.setData(FunctionInfor.functionInforList);
        ((ListView)findViewById(R.id.list)).setAdapter(mFunctionListAdapter);
        //--------
        titleTextView=(TextView)findViewById(R.id.title);
        titleTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(MainActivity.this, LanguageActiv.class);
                MainActivity.this.startActivity(mIntent);

                /*Uri uri= Uri.parse("gongxiangyun://certify");
                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);*/

                /*Uri uri=Uri.parse("jinjingzheng://certify");
                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                startActivityForResult(intent,102);*/

                /*Uri uri=Uri.parse("jinjingzheng://certify");
                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                intent.setPackage(" com.example.wujiawen.studyexample");//这里你需要设置你应用的包名
                ContentProviderActi.this.startService(intent);*/

            }
        });

//        FunctionInfor.testFirstStartActivity(this);
        //----------------
        ALog.d("00","----");
        checkPermission(this);


//        Settings.System.putInt(getContentResolver(),android.provider.Settings.System.SCREEN_OFF_TIMEOUT,-1);


//        com.airbnb.lottie.LottieAnimationView   fff;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    class FunctionListAdapter extends BaseAdapter {

        private List<FunctionInfor> mAccountInforList=new ArrayList<FunctionInfor>();
        private Context mContext;

        public FunctionListAdapter(Context context) {
            mContext=context;
        }

        public void setData(List<FunctionInfor> mAccountInforList_){
            ALog.i(ALog.Tag2,"AccountListAdapter--setData--removeHeaderView--mAccountInforList_.size()-->>"+mAccountInforList_.size());
            mAccountInforList=mAccountInforList_;
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mAccountInforList.size();
        }

        @Override
        public FunctionInfor getItem(int position) {
            if (position < 0 || position >= getCount()) {
                return null;
            }
            return mAccountInforList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder=new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_activ_main_list_item, null);
                holder.out_layout=(RelativeLayout)convertView;
                ViewGroup.LayoutParams   params=holder.out_layout.getLayoutParams();
                if(params==null){
                    params=new AbsListView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Tools.dip2px(mContext,60));
                }else{
                    params.height= Tools.dip2px(mContext,60);
                }
                holder.out_layout.setLayoutParams(params);
                holder.name=(TextView)convertView.findViewById(R.id.name);
                //--------------
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.position=position;
            holder.curAccountInfor=getItem(position);
            holder.out_layout.setOnClickListener(mOnClickListener);
            holder.name.setText(holder.curAccountInfor.des);
            return convertView;
        }

        View.OnClickListener mOnClickListener=new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ViewHolder holder = (ViewHolder) v.getTag();
                try {

                    Intent intent_a = new Intent(MainActivity.this,holder.curAccountInfor.activ_name);
                    intent_a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    MainActivity.this.startActivity(intent_a);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    class ViewHolder {
        FunctionInfor curAccountInfor;
        int position;
        RelativeLayout out_layout;
        TextView name;
    }


    static Set<String> permissionsSet_now = new LinkedHashSet<>();
    static Set<String> permissionsSet_base = new LinkedHashSet<>();
    static Set<String> permissionsSet = new LinkedHashSet<>();

    static {
        permissionsSet_base.add(Manifest.permission.GET_ACCOUNTS);
        ///-对google评分有影响-
        permissionsSet_base.add(Manifest.permission.READ_PHONE_STATE);//imei 需要 广告要用到
        permissionsSet_base.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        permissionsSet_base.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionsSet_base.add(android.Manifest.permission.VIBRATE);
        permissionsSet_base.add(android.Manifest.permission.INTERNET);
        permissionsSet_base.add(android.Manifest.permission.CHANGE_WIFI_STATE);
        permissionsSet_base.add(android.Manifest.permission.CHANGE_NETWORK_STATE);
        permissionsSet_base.add(android.Manifest.permission.ACCESS_NETWORK_STATE);
        permissionsSet_base.add(Manifest.permission.ACCESS_WIFI_STATE);
    }

    private void addNeedPermissions() {
        permissionsSet.addAll(permissionsSet_base);
    }

    private void checkPermission(Context mContext) {
        addNeedPermissions();
        for (String permission_str : permissionsSet) {
            boolean mPermission = checkPermissionGranted(mContext, permission_str);
            if (!mPermission) {
                permissionsSet_now.add(permission_str);
            }
        }

/*        if (permissionsSet_now.size() <= 0) {
            dealStartActivity();
            return;
        }*/

        if (permissionsSet_now.size() <= 0) {
            //如果不显示引导，直接跳到MainActivity
            FunctionInfor.testFirstStartActivity(this);
        } else {
            String[] permission_str_array = permissionsSet_now.toArray(new String[]{});
            //请求权限
            try {
                ActivityCompat.requestPermissions((Activity) mContext, permission_str_array, 23);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
