package com.kv.app1.provider.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kv.app1.provider.Provider;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "person.db";
    private static final int DATABASE_VERSION = 1;
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Provider.PersonColumns.TABLE_NAME + " ("
                + Provider.PersonColumns._ID + " INTEGER PRIMARY KEY,"
                + Provider.PersonColumns.NAME + " TEXT,"
                + Provider.PersonColumns.AGE + " INTEGER"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Provider.PersonColumns.TABLE_NAME);
        onCreate(db);
    }

}
