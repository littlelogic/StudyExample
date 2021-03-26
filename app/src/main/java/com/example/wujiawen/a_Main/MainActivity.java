package com.example.wujiawen.a_Main;//com.example.wujiawen.a_Main.ContentProviderActi

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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

import com.badlogic.utils.ALog;
import com.badlogic.utils.Tools;
import com.example.wujiawen.ExampleLanguage.LanguageActiv;

import java.util.ArrayList;
import java.util.List;

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

        FunctionInfor.testFirstStartActivity(this);
        //----------------
        ALog.d("00","----");


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
