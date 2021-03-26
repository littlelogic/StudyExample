package com.pzf.liaotian;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.pzf.liaotian.album.AlbumHelper;
import com.pzf.liaotian.bean.SPUtils;
import com.pzf.liaotian.bean.album.ImageBucket;
import com.pzf.liaotian.config.ConstantKeys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @desc: 聊天界面主Activity
/**
 * @desc:相册图片列表
 * @author: pangzf
 * @date: 2015年7月3日 下午4:40:54
 * QQ2050542273
 * @email:15162925211@163.com
 */

public class PhotoSelectRecieveActiv extends Activity  {

    /***
     *  应用的名字:北京交警
     *  应用的包名字:com.zcbl.bjjj_driving
     *
     *  com.example.wujiawen.studyexample  StudyExample主包名
     */
    public static final String bjjj_AppMainPackageName = "com.zcbl.bjjj_driving";
    public static final String[] appListAtrArray = {
        bjjj_AppMainPackageName,
        "com.example.wujiawen.studyexample",
        "xxxxxx",
        "xxxxxx",
    };

    public static final int onActivityResult_SelectPhoto_RequestCode = 1003;
    public static final int onActivityResult_SelectPhoto_ResultCode  = 2003;

    private AlbumHelper albumHelper = null;// 相册管理类
    private static List<ImageBucket> albumList = null;// 相册数据list
    Handler mHandler=new Handler();
    List<String> appList=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * TODO 注意：只有调用者使用的是startActivityForResult()，
         * TODO 且Intent不设置NEW_TASK时才可以获取，调用startActivity()得到的是null。
         * TODO getCallingActivity()   getCallingPackage()
         */
        Log.i("wjw02","PhotoSelectRecieveActiv--onCreate--getCallingPackage()->"+getCallingPackage());
        Log.i("wjw02","PhotoSelectRecieveActiv--onCreate--getCallingActivity()->"+getCallingActivity());
        for(String name : appListAtrArray){
            Log.i("wjw02","PhotoSelectRecieveActiv--onCreate--name->"+name);
            appList.add(name);
        }
        boolean have=appList.contains(getCallingPackage());
        Log.i("wjw02","PhotoSelectRecieveActiv--onCreate--have()->"+have);
        if(have){
        //--if(bjjj_AppMainPackageName.equals(getCallingPackage())){
        }else{
            String hint="You have no right to use this App.";
            TextView mTextView=new TextView(this);
            mTextView.setBackgroundColor(Color.WHITE);
            mTextView.setTextColor(Color.BLACK);
            mTextView.setGravity(Gravity.CENTER);
            mTextView.setTextSize(30);
            mTextView.setText(hint);
            this.setContentView(mTextView);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    System.exit(0);
                }
            },700)       ;
            /*Toast.makeText(this, "You have no right to use this App.", Toast.LENGTH_SHORT).show();
            finish();
            System.exit(0);*/
            return;
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        initAlbumData();
        dealIntent(getIntent());



        checkEnvironment(this);
        reloadButtons(this,100);
        // 获取activity任务栈
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo info = manager.getRunningTasks(1).get(0);
        // 类名 .ui.mobile.activity.WebsiteLoginActivity
        String shortClassName = info.topActivity.getShortClassName();
        // 完整类名 com.haofang.testapp.ui.mobile.activity.WebsiteLoginActivity
        String className = info.topActivity.getClassName();
        // 包名  com.haofang.testapp
        String packageName = info.topActivity.getPackageName();
    }

    public void getAllAppNamesPackages(){
        int i=0;
        PackageManager pm=getPackageManager();
        List<PackageInfo> list=pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo packageInfo : list) {
            //获取到设备上已经安装的应用的名字,即在AndriodMainfest中的app_name。
            String appName=packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
            //获取到应用所在包的名字,即在AndriodMainfest中的package的值。
            String packageName=packageInfo.packageName;
            Log.i("wjw02", "PickPhotoActivity--onCreate--应用的名字:"+appName);
            Log.i("wjw02", "PickPhotoActivity--onCreate--应用的包名字:"+packageName);
            i++;
        }
        Log.i("wjw02", "PickPhotoActivity--onCreate--应用的总个数:"+i);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        dealIntent(intent);
    }

    private void dealIntent(Intent intent) {
        Log.i("wjw02","PickPhotoActivity--dealIntent--intent->"+intent);
        List<ResolveInfo> list =  this.getPackageManager().queryIntentActivities(intent, 0);
        Log.i("wjw02","PickPhotoActivity--dealIntent--list.size()->"+list.size());
        if(list.size() > 0){

            Log.i("wjw02","PickPhotoActivity--dealIntent--list.get(0).activityInfo.packageName->"+list.get(0).activityInfo.packageName);
        }

    }

    private void initAlbumData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                albumHelper = AlbumHelper.getHelper(PhotoSelectRecieveActiv.this);
                albumList = albumHelper.getImagesBucketList(false);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        dealSelect();
                    }
                });
            }
        }).start();
    }

    private void dealSelect() {
        String ID=SPUtils.getInstance().getValue(this, ConstantKeys.SPU_KEY_JinjingzhengID,"");
        if(ID==null||ID.equals("")){
            Intent intent = new Intent(this,PickPhotoActivity.class);
            startActivityForResult(intent, ConstantKeys.ALBUM_BACK_DATA);
            this.overridePendingTransition( R.anim.zf_album_enter, R.anim.zf_stay);
            return;
        }
        if(!albumHelper.bucketList.containsKey(ID)){
            Toast.makeText(this,"进京证相册路径已经不存在，请重新选择",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,PickPhotoActivity.class);
            startActivityForResult(intent, ConstantKeys.ALBUM_BACK_DATA);
            this.overridePendingTransition( R.anim.zf_album_enter, R.anim.zf_stay);
            return;
        }
        //-------------
        Intent intent = new Intent(this,ImageGridActivity.class);
        intent.putExtra(ConstantKeys.EXTRA_IMAGE_LIST,(Serializable) albumHelper.bucketList.get(ID).imageList);
        intent.putExtra(ConstantKeys.EXTRA_ALBUM_NAME,albumHelper.bucketList.get(ID).bucketName);
        intent.putExtra(ConstantKeys.EXTRA_ALBUM_ID,albumHelper.bucketList.get(ID).bucketId);
        Log.i("wjw02","PickPhotoActivity--PhotoSelectRecieveActiv--bucketId->"+albumHelper.bucketList.get(ID).bucketId);
        startActivityForResult(intent, onActivityResult_SelectPhoto_RequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("wjw02","PickPhotoActivity--onActivityResult--requestCode->"+requestCode+"-resultCode->"+resultCode);
        if(requestCode==onActivityResult_SelectPhoto_RequestCode&&resultCode==onActivityResult_SelectPhoto_ResultCode){
            Log.i("wjw02","PickPhotoActivity--onActivityResult--data->"+data);
            String path=data.getStringExtra(ConstantKeys.INIENT_Key_ImagePath);
            Log.i("wjw02","PickPhotoActivity--onActivityResult--path->"+path);
            if(path==null||path.equals("")){
                Toast.makeText(this,"路径获得失败",Toast.LENGTH_SHORT).show();
                return;
            }
            Log.i("wjw02","PickPhotoActivity--onActivityResult--02->");
            Intent intent = new Intent();
            Uri mUri=Uri.parse(path);
            mUri=getUri(path);
            intent.setData(mUri);
            //--intent.putExtra(ConstantKeys.INIENT_Key_ImagePath, path);
            Log.i("wjw02","PickPhotoActivity--onActivityResult--03->");
            this.setResult(Activity.RESULT_OK,intent);
            this.finish();
            Log.i("wjw02","PickPhotoActivity--onActivityResult--04->");
            return;
        }
        this.setResult(Activity.RESULT_CANCELED);
        this.finish();
    }

    /**
     * path转uri
     */
    private Uri getUri(String path){
        Uri uri = null;
        if (path != null) {
            path = Uri.decode(path);
            Log.d("wjw02", "path2 is " + path);
            ContentResolver cr = this.getContentResolver();
            StringBuffer buff = new StringBuffer();
            buff.append("(")
                    .append(MediaStore.Images.ImageColumns.DATA)
                    .append("=")
                    .append("'" + path + "'")
                    .append(")");
            Cursor cur = cr.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[] { MediaStore.Images.ImageColumns._ID },
                    buff.toString(), null, null);
            int index = 0;
            for (cur.moveToFirst(); !cur.isAfterLast(); cur .moveToNext()) {
                index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                // set _id value
                index = cur.getInt(index);
            }
            if (index == 0) {
                //do nothing
            } else {
                Uri uri_temp = Uri.parse("content://media/external/images/media/" + index);
                Log.d("wjw02", "uri_temp is " + uri_temp);
                if (uri_temp != null) {
                    uri = uri_temp;
                }
            }
        }
        return uri;
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //===================================================================

    private void photo() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, 0);
    }

    /**
     * 判断应用是否在运行
     * @param context
     * @return
     */
    public boolean isRun(Context context){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = am.getRunningTasks(100);
        boolean isAppRunning = false;
        String MY_PKG_NAME = "com.ad";
        //100表示取的最大的任务数，info.topActivity表示当前正在运行的Activity，info.baseActivity表系统后台有此进程在运行
        for (RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(MY_PKG_NAME) || info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
                isAppRunning = true;
                Log.i("ActivityService isRun()",info.topActivity.getPackageName() + " info.baseActivity.getPackageName()="+info.baseActivity.getPackageName());
                break;
            }
        }
        Log.i("ActivityService isRun()", "com.ad 程序  ...isAppRunning......"+isAppRunning);
        return isAppRunning;
    }

    String MY_PKG_NAME="";

    public void checkEnvironment(Context context){
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = am.getRunningTasks(100);
        for (RunningTaskInfo info : list) {
            Log.i("wjw02", "PhotoSelectRecieveActiv--checkEnvironment--Name->"+info.topActivity.getPackageName());
            if (info.topActivity.getPackageName().equals(MY_PKG_NAME) && info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
                //find it, break
                break;
            }
        }

        List<ActivityManager.RunningAppProcessInfo> list2 = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : list2) {


            Log.i("wjw02", "PhotoSelectRecieveActiv--checkEnvironment--importanceReasonComponent.toString->"+info.importanceReasonComponent);
            Log.i("wjw02", "PhotoSelectRecieveActiv--checkEnvironment--processName->"+info.processName);
        }


//        final ActivityManager am = (ActivityManager)
//                mContext.getSystemService(Context.ACTIVITY_SERVICE);
//        final List<ActivityManager.RecentTaskInfo> recentTasks =
//                am.getRecentTasks(MAX_TASKS, ActivityManager.RECENT_IGNORE_UNAVAILABLE);
//        for(ActivityManager.RecentTaskInfo rt:recentTasks ) {
//            if (am != null) am.removeTask(rt.persistentId,
//                    ActivityManager.REMOVE_TASK_KILL_PROCESS);
//        }



        checkEnvironment_d(am .getRecentTasks(100 + 1, ActivityManager.RECENT_WITH_EXCLUDED));

        checkEnvironment_d(am .getRecentTasks(100 + 1, ActivityManager.RECENT_IGNORE_UNAVAILABLE));
        checkEnvironment_d(am .getRecentTasks(100 + 1, 0x0004));
        checkEnvironment_d(am .getRecentTasks(100 + 1, 0x0008));
        checkEnvironment_d(am .getRecentTasks(100 + 1, 0x0010));
        checkEnvironment_d(am .getRecentTasks(100 + 1, 0x0020));



    }



    @SuppressLint("NewApi")
    public void checkEnvironment_d(final List<ActivityManager.RecentTaskInfo> recentTasks) {

        for (int i = 0; i < recentTasks.size(); i++) {
            final ActivityManager.RecentTaskInfo info = recentTasks.get(i);
            Intent intent = new Intent(info.baseIntent);
            if (info.origActivity != null) {
                intent.setComponent(info.origActivity);
            }

            Log.i("wjw02", "PhotoSelectRecieveActiv--checkEnvironment_d--getPackageName->"+intent.getComponent().getPackageName());
            Log.i("wjw02", "PhotoSelectRecieveActiv--checkEnvironment_d--getClassName->"+intent.getComponent().getClassName());
//            Log.i("wjw02", "PhotoSelectRecieveActiv--checkEnvironment_d--info.topActivity.toString()->"+info.topActivity.toString());
        }

    }

    public static void reloadButtons(Activity activity, int appNumber) {
        int MAX_RECENT_TASKS = appNumber; // allow for some discards
        int repeatCount = appNumber;// 保证上面两个值相等,设定存放的程序个数

        /* 每次加载必须清空list中的内容 */
        //---appInfos.removeAll(appInfos);
        List<HashMap<String, Object>> appInfos=new ArrayList<HashMap<String, Object>>();

        // 得到包管理器和activity管理器
        final Context context = activity.getApplication();
        final PackageManager pm = context.getPackageManager();
        final ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        // 从ActivityManager中取出用户最近launch过的 MAX_RECENT_TASKS + 1 个，以从早到晚的时间排序，
        // 注意这个 0x0002,它的值在launcher中是用ActivityManager.RECENT_IGNORE_UNAVAILABLE
        // 但是这是一个隐藏域，因此我把它的值直接拷贝到这里
        final List<ActivityManager.RecentTaskInfo> recentTasks = am .getRecentTasks(MAX_RECENT_TASKS + 1, ActivityManager.RECENT_WITH_EXCLUDED);

        // 这个activity的信息是我们的launcher
        ActivityInfo homeInfo = new Intent(Intent.ACTION_MAIN).addCategory( Intent.CATEGORY_HOME).resolveActivityInfo(pm, 0);
        int numTasks = recentTasks.size();
        for (int i = 0; i < numTasks && (i < MAX_RECENT_TASKS); i++) {
            HashMap<String, Object> singleAppInfo = new HashMap<String, Object>();// 当个启动过的应用程序的信息
            final ActivityManager.RecentTaskInfo info = recentTasks.get(i);
//            Log.i("wjw02", "PhotoSelectRecieveActiv--checkEnvironment--Name->"+info.topActivity.getPackageName());




            Intent intent = new Intent(info.baseIntent);
            if (info.origActivity != null) {
                intent.setComponent(info.origActivity);
            }

            Log.i("wjw02", "PhotoSelectRecieveActiv--reloadButtons--getPackageName->"+intent.getComponent().getPackageName());
            Log.i("wjw02", "PhotoSelectRecieveActiv--reloadButtons--getClassName->"+intent.getComponent().getClassName());

            /**
             * 如果找到是launcher，直接continue，后面的appInfos.add操作就不会发生了
             */
            if (homeInfo != null) {
                if (homeInfo.packageName.equals(intent.getComponent().getPackageName())
                        && homeInfo.name.equals(intent.getComponent() .getClassName())) {
                    MAX_RECENT_TASKS = MAX_RECENT_TASKS + 1;
                    continue;
                }
            }
            // 设置intent的启动方式为 创建新task()【并不一定会创建】
            intent.setFlags((intent.getFlags() & ~Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            // 获取指定应用程序activity的信息(按我的理解是：某一个应用程序的最后一个在前台出现过的activity。)
            final ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
            if (resolveInfo != null) {
                final ActivityInfo activityInfo = resolveInfo.activityInfo;
                final String title = activityInfo.loadLabel(pm).toString();
                Drawable icon = activityInfo.loadIcon(pm);

                if (title != null && title.length() > 0 && icon != null) {
                    singleAppInfo.put("title", title);
                    singleAppInfo.put("icon", icon);
                    singleAppInfo.put("tag", intent);
                    singleAppInfo.put("packageName", activityInfo.packageName);
                    appInfos.add(singleAppInfo);
                }
            }
        }
        MAX_RECENT_TASKS = repeatCount;
    }


    public boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    //--2. 根据Intent判断，以下为判断代码：
    public boolean checkApkExist2(Context context, Intent intent) {
        List<ResolveInfo> list =  context.getPackageManager().queryIntentActivities(intent, 0);
        if(list.size() > 0){
            return true;
        }
        return false;
    }
    public boolean checkApkExist3(Context context, String packageName) {
        if (packageName == null || "".equals(packageName)) return false;
        try {
            ApplicationInfo info = context.getPackageManager() .getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES); return true;
        } catch (PackageManager.NameNotFoundException e) { return false; }
    }
    public boolean checkApkExist4(Context context, Intent intent) {
        List<ResolveInfo> list = context.getPackageManager()
                .queryIntentActivities(intent, 0);
        if (list.size() > 0) {
            return true;
        }
        return false;
    }

}
