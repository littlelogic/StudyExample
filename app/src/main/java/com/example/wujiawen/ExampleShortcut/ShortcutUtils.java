package com.example.wujiawen.ExampleShortcut;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import com.example.wujiawen.a_Main.R;

public class ShortcutUtils {
	/**

	  * ������ӵ������ݷ�ʽ��Intent��  

	  * 1.��Intentָ��action="com.android.launcher.INSTALL_SHORTCUT"

	  * 2.������ΪIntent.EXTRA_SHORTCUT_INENT��Intent�����밲װʱһ�µ�action(����Ҫ��)  

	  * 3.���Ȩ��:com.android.launcher.permission.INSTALL_SHORTCUT

	  */

	 public static Intent getShortcutToDesktopIntent(Context context) {
		 Intent intent = new Intent(); 
		 intent.setClass(context, context.getClass());  
        /*����������Ϊ����ж��Ӧ�õ�ʱ��ͬʱɾ�������ݷ�ʽ*/
		 intent.setAction("android.intent.action.MAIN");  
		 intent.addCategory("android.intent.category.LAUNCHER");  
        
	     Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
	     // �������ؽ�
	     shortcut.putExtra("duplicate", false);
	     // ��������
	     shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,context.getString(R.string.app_name));
	     // ����ͼ��
	     shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,Intent.ShortcutIconResource.fromContext(context, R.drawable.icon));
	     // ������ͼ�Ϳ�ݷ�ʽ��������
	     shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,intent);

	     return shortcut;

	 }
	 
	 /**
	  * ɾ����ݷ�ʽ
	  * */
	 public static void deleteShortCut(Context context)
	 {
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");  
        //��ݷ�ʽ������  
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,context.getString(R.string.app_name));  
        /**ɾ���ʹ�����Ҫ��Ӧ�����ҵ���ݷ�ʽ���ɹ�ɾ��**/
        Intent intent = new Intent(); 
        intent.setClass(context, context.getClass());  
        intent.setAction("android.intent.action.MAIN");  
        intent.addCategory("android.intent.category.LAUNCHER");  
        
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,intent);  
        context.sendBroadcast(shortcut);          
	 }
	 
	 /**
	  * �ж��Ƿ�����ӿ�ݷ�ʽ��  
	  * ��ʱû�з����ܹ�׼ȷ���жϵ���ݷ�ʽ��ԭ���ǣ�
		1����ͬ���̵Ļ������Ŀ�ݷ�ʽuri��ͬ����������HTC������URI��content://com.htc.launcher.settings/favorites?notify=true
		2�����治ֻ��android�Դ��ģ������ǵ����������棬���ǵĿ�ݷ�ʽuri����ͬ
		
		�ṩһ������취��������ݷ�ʽ��ʱ�򱣴浽preference�����߽����ļ���SD���ϣ��´μ��ص�ʱ���жϲ����ھ��ȷ�ɾ���㲥�������´���

	  * ���Ȩ��:<uses-permission android:name="com.android.launcher.permission.READ_SETTINGS" ></uses-permission>

	  */
	 public static boolean hasInstallShortcut(Context context) {
	     boolean hasInstall = false;

	     String AUTHORITY = "com.android.launcher.settings";
	     int systemversion = Build.VERSION.SDK_INT;
	     if(systemversion >= 8){
	    	 AUTHORITY = "com.android.launcher2.settings"; 
	     } 
	     Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY  + "/favorites?notify=true");

	     Cursor cursor = context.getContentResolver().query(CONTENT_URI,
	             new String[] { "title" }, "title=?",
	             new String[] { context.getString(R.string.app_name) }, null);

	     if (cursor != null && cursor.getCount() > 0) {
	         hasInstall = true;
	     }

	     return hasInstall;
	 }
}
