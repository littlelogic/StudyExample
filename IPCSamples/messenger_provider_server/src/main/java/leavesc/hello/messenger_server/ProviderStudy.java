package leavesc.hello.messenger_server;
//leavesc.hello.messenger_server.MyProvider

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

class DB_Helper extends SQLiteOpenHelper {
    public DB_Helper(Context context) {
        super(context, "da_name.db", null, 1);}
    public void onCreate(SQLiteDatabase db) {// 创建用户表
        db.execSQL("CREATE TABLE IF NOT EXISTS " + "user" + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + " name TEXT)"); }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){ }  }
/*<provider android:name="leavesc.hello.messenger_server.ProviderStudy"
            android:authorities="leavesc.hello.messenger_server.ProviderStudy"
            android:exported="true"
            android:permission="scut.carson_ho.permission.PROVIDER" ></provider>*/
public class ProviderStudy extends ContentProvider {
    private Context mContext; private SQLiteDatabase db;
    public boolean onCreate() {// 以下是ContentProvider的6个方法
        mContext = getContext();
        // 创建时对数据库进行初始化,运行在主线程，故不能做耗时操作
        DBHelper mDbHelper = new DBHelper(getContext());
        db = mDbHelper.getWritableDatabase();
        db.execSQL("delete from user");
        db.execSQL("insert into user values(1,'Carson');");
        db.execSQL("insert into user values(2,'Kobe');");
        return true;
    }
    public Uri insert(Uri uri, ContentValues values) {//todo 异步线程注意同步
        db.insert("user", null, values);// 向该表添加数据
        // 当数据发生变化时，通知外界（即访问该ContentProvider数据的访问者）
        mContext.getContentResolver().notifyChange(uri, null);
        try {// 通过ContentUris类从URL中获取ID
            long personid = ContentUris.parseId(uri);} catch (Exception e) {}
        return uri; }
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return db.query("user",projection,selection,selectionArgs,
                null,null,sortOrder,null);
    }
    public int update(Uri uri, ContentValues values, String selection,String[] selectionArgs) {
        Log.i("wjw02","190512CProvider-MyProvider-update-uri->"+uri);
        // 由于不展示,此处不作展开
        return 0;
    }
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.i("wjw02","190512CProvider-MyProvider-delete-uri->"+uri);
        // 由于不展示,此处不作展开
        return 0;
    }
    public String getType(Uri uri) {
        Log.i("wjw02","190512CProvider-MyProvider-getType-uri->"+uri);
        // 由于不展示,此处不作展开
        return null;
    }
}
class ContentProviderTest {//todo 另一个App
    public static void dealProvider(Context mContext){
        String AUTOHORITY = "leavesc.hello.messenger_server.ProviderStudy";
        Uri uri_user = Uri.parse("content://"+AUTOHORITY+"/user");
        ContentResolver resolver =  mContext.getContentResolver();
        ContentValues values = new ContentValues();
        values.put("_id", 4); values.put("name", "Jordan");
        resolver.insert(uri_user,values);//根据URI 向ContentProvider中插入数据
        Cursor cursor = resolver.query(uri_user, new String[]{"_id","name"},null, null, null);
        while (cursor.moveToNext())
            Log.i("wjw02",cursor.getInt(0) +" "+ cursor.getString(1));
        cursor.close();}
}
