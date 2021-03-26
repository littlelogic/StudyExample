package com.example.wujiawen.ExampleShortcut;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.example.wujiawen.ExampleDrawable.DrawableStudyActiv;
import com.example.wujiawen.a_Main.R;

import java.util.Arrays;
import java.util.List;

public class ShortActivity extends Activity {
	private static final String TAG = "ContentProviderActi";
	
	Button createBtn;
	Button delBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_b);
		
		initShortcutAction();
		
		createBtn = (Button)findViewById(R.id.create_btn);
		delBtn = (Button)findViewById(R.id.del_btn);
		
		createBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
//				if(!ShortcutUtils.hasInstallShortcut(ContentProviderActi.this)){
//					Log.i(SPName, "not create shortcut---------------- ");
//					addShortcut2Desktop();
//				}else{
//					Log.i(SPName, "has created shortcut---------------- ");
//				}
//				addShortcut2Desktop();
//				createShortCut();
				String AuthorityFromPermission_str=getAuthorityFromPermission(ShortActivity.this,"com.android.launcher.action.INSTALL_SHORTCUT");
				Log.i("wjw01","--ContentProviderActi--setOnClickListener--AuthorityFromPermission_str-->>"+AuthorityFromPermission_str);
				createShortCut_checkSelfPermission();
				addShortcut();
//				addShortCut_02(ContentProviderActi.this);
			}
			
		});
		
		delBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(!ShortcutUtils.hasInstallShortcut(ShortActivity.this)){
					Log.i(TAG, "not create shortcut---------------- ");
				}else{
					ShortcutUtils.deleteShortCut(ShortActivity.this);
					Log.i(TAG, "has created shortcut---------------- ");
				}
//				ShortcutUtils.deleteShortCut(ContentProviderActi.this);
			}
			
		});
		
	}
	/**
	 * ������Action��ʽ: �����泤����ʽ��ӿ�ݷ�ʽ-----> ��Ҫ��Manifest.xml�ж���activity���action������
	 */
	private void initShortcutAction(){
		final Intent launchIntent = getIntent();
		final String action = launchIntent.getAction();
		if (Intent.ACTION_CREATE_SHORTCUT.equals(action)) {
			Log.i(TAG, "create shortcut method one---------------- ");
			setResult(RESULT_OK, ShortcutUtils.getShortcutToDesktopIntent(ShortActivity.this));
			
			finish();

//			PackageManager pm = this.getPackageManager();
//			PackageManager pm=null;pm.getIc
//			final ActivityInfo activityInfo = null;s
//			final String title = activityInfo.loadLabel(pm).toString();
//
//			Drawable icon = activityInfo.loadIcon(pm);
		} 
	}
	/**
	 * �����ķ��㲥��ʽ: ����Ӧ�ú��͹㲥��ʽ��ӿ�ݷ�ʽ----->
	 */
	private void addShortcut2Desktop(){
		// ����Ӧ�ú��͹㲥��ʽ��ӿ�ݷ�ʽ----->�������ķ��㲥��ʽ
		Log.i(TAG, "create shortcut method two---------------- ");
		sendBroadcast(ShortcutUtils.getShortcutToDesktopIntent(ShortActivity.this));
	}

	public void onClickOne(View v){
		PackageManager pm = getPackageManager();
		pm.setComponentEnabledSetting(getComponentName(),
				PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, "com.yifeng.samples.AliasName"),
				PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
	}

	public void createShortCut(){

		//检查权限
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.INSTALL_SHORTCUT)!= PackageManager.PERMISSION_GRANTED) {
			Log.i("wjw01","--ContentProviderActi--checkSelfPermission--01-->>");
			//进入到这里代表没有权限.
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INSTALL_SHORTCUT}, 1001);
		} else {
			Log.i("wjw01","--ContentProviderActi--checkSelfPermission--02-->>");
			//callPhone();
		}

		//创建快捷方式的Intent
		Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
		//不允许重复创建
		shortcutintent.putExtra("duplicate", true);
		//需要现实的名称
		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
		//快捷图片
		Parcelable icon = Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.icon);
		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		//点击快捷图片，运行的程序主入口
		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(getApplicationContext() , ShortActivity.class));
		//发送广播。OK
		sendBroadcast(shortcutintent);


	}
	public void createShortCut_checkSelfPermission(){

		String pkName = this.getPackageName();
		Log.i("wjw01", "--ContentProviderActi--checkSelfPermission--pkName-->>"+pkName);
		PackageManager pm = getPackageManager();
		int mark=pm.checkPermission("android.permission.CALL_PHONE", pkName);
		Log.i("wjw01", "--ContentProviderActi--checkSelfPermission--权限mark-->>"+mark);
		///--PERMISSION_GRANTED  PERMISSION_DENIED
		boolean permission = (PackageManager.PERMISSION_GRANTED == mark);
		if (permission) {
			Log.i("wjw01", "--ContentProviderActi--checkSelfPermission--有这个权限-->>");
		}else {
			Log.i("wjw01", "--SettingActivity--checkSelfPermission--木有这个权限-->>");
		}
		try {
			String []ddd=this.getPackageManager().getPackageInfo(pkName, PackageManager.GET_PERMISSIONS).requestedPermissions;
			/*for(int i=0;i<ddd.length;i++){
				Log.i("wjw01", "--SettingActivity--checkSelfPermission--ddd[i]-->>"+ddd[i]);
			}*/
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}






		//检查权限
		int mark_a= ContextCompat.checkSelfPermission(this, Manifest.permission.INSTALL_SHORTCUT);
		Log.i("wjw01","--ContentProviderActi--checkSelfPermission--mark_a-->>"+mark_a);
		if (mark_a!= PackageManager.PERMISSION_GRANTED) {
			Log.i("wjw01","--ContentProviderActi--checkSelfPermission--无-->>");
			//进入到这里代表没有权限.
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INSTALL_SHORTCUT}, 1001);
		} else {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INSTALL_SHORTCUT}, 1001);
			Log.i("wjw01","--ContentProviderActi--checkSelfPermission--有-->>");
			//callPhone();
		}

		boolean mark_b=selfPermissionGranted(Manifest.permission.INSTALL_SHORTCUT,this);
		Log.i("wjw01","--ContentProviderActi--checkSelfPermission-2-mark_b-->>"+mark_b);

	}

	public boolean selfPermissionGranted(String permission,Context context) {
		// For Android < Android M, self permissions are always granted.
		boolean result = true;
		int targetSdkVersion=0;
		try {
			final PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			targetSdkVersion = info.applicationInfo.targetSdkVersion;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

			if (targetSdkVersion >= Build.VERSION_CODES.M) {
			//--if (targetSdkVersion >= Build.VERSION_CODES.M) {
				// targetSdkVersion >= Android M, we can
				// use Context#checkSelfPermission
				result = context.checkSelfPermission(permission)
						== PackageManager.PERMISSION_GRANTED;
			} else {
				// targetSdkVersion < Android M, we have to use PermissionChecker
				result = PermissionChecker.checkSelfPermission(context, permission)
						== PermissionChecker.PERMISSION_GRANTED;
			}
		}

		return result;
	}

	int num=0;

	/**
	 * 为程序创建桌面快捷方式
	 */
	private void addShortcut(){
		num++;
		Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

		//快捷方式的名称
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name)+num);
		shortcut.putExtra("duplicate", true); //不允许重复创建

		/****************************此方法已失效*************************/
		//ComponentName comp = new ComponentName(this.getPackageName(), "."+this.getLocalClassName());
		//shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));  　　
		 /******************************end*******************************/
		Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
//		shortcutIntent.setClassName(this, this.getClass().getName());
		shortcutIntent.setClassName(this, DrawableStudyActiv.class.getName());
		shortcutIntent.putExtra("ShortCutStartMark_360.wjw",true);
		shortcutIntent.putExtra("ShortCutStartMark_360.wjw_num",num);
		shortcutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);

		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);

		//快捷方式的图标
		Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(this, R.drawable.icon);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);

		sendBroadcast(shortcut);
	}

	@SuppressLint("NewApi")
	private String getAuthorityFromPermission(Context context, String permission){
		if (permission == null) return null;
		List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
		if (packs != null) {
			for (PackageInfo pack : packs) {
				ProviderInfo[] providers = pack.providers;
				if (providers != null) {
					for (ProviderInfo provider : providers) {
						if (permission.equals(provider.readPermission)) return provider.authority;
						if (permission.equals(provider.writePermission)) return provider.authority;
					}
				}
			}
		}
		return null;
	}

	@SuppressLint("NewApi")
	private void addShortCut_02(Context context){
		ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
		ShortcutInfo shortcut = new ShortcutInfo.Builder(this, "id1")
				.setShortLabel("Web site")
				.setLongLabel("Open the web site")
				.setIcon(Icon.createWithResource(context, R.drawable.icon))
				.setIntent(new Intent(Intent.ACTION_VIEW,
						Uri.parse("https://www.mysite.example.com/")))
				.build();
		shortcutManager.setDynamicShortcuts(Arrays.asList(shortcut));
	}

	/*private void addShortCut(Context mContext)
	{

		boolean has = false;

		if(has)
		{
			return;
		}

		Intent shortCutIntent = null;
		int shortCutNameId = R.string.app_name;
		int shortCutIconId = R.drawable.icon;
		String pkg = PACKAGE_NAME;

		boolean installed = isInstalledApp(mContext);


		if(installed)
		{

			Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
			resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
			resolveIntent.setPackage(pkg);
			List<ResolveInfo> apps = mContext.getPackageManager().queryIntentActivities(resolveIntent, PackageManager.GET_ACTIVITIES);


			shortCutIntent = new Intent();
			if((apps != null) && (apps.size() != 0))
			{
				shortCutIntent.setComponent(new ComponentName(pkg, apps.get(0).activityInfo.name));
			}
		}
		else
		{
			shortCutIntent = new Intent(mContext.getApplicationContext(), AppActivity.class);
		}


		Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

		//快捷方式的名称
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, mContext.getString(shortCutNameId));
		shortcut.putExtra("duplicate", false); //不允许重复创建

		//快捷方式的图标
		Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(mContext.getApplicationContext(), shortCutIconId);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);

		//程序入口
		shortCutIntent.setAction(Intent.ACTION_MAIN);
		shortCutIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortCutIntent);

		mContext.sendBroadcast(shortcut);
	}*/

}
