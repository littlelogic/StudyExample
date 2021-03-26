package com.kv.app1;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.kv.app1.provider.Provider;
import com.kv.app1.provider.bean.Person;

public class MainAct extends Activity implements OnClickListener {

    private static final String TAG = "MainAct";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv = (TextView) findViewById(R.id.tv);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btn1:
            testInsertAndQuery();
            break;
        case R.id.btn2:
            testQueryAll();
            break;
        case R.id.btn3:
            testQueryById();
            break;
        default:
            break;
        }
    }
    
    private void testQueryById() {
        Person p1 = queryById(1);
        tv.setText(tv.getText().toString() + " " + p1._id + " " + p1.name + " " + p1.age);
    }

    private void testInsertAndQuery() {
        Person p = new Person();
        p.name = "kevin";
        p.age = 99;
        int id = insert(p);
        tv.setText("id=" +id);
        Person p1 = query(id);
        tv.setText(tv.getText().toString() + " " + p1.name + " " + p1.age);
    }
    
    private void testQueryAll() {
        List<Person> personList = queryAll();
        tv.setText("");
        for (Person p : personList) {
            tv.setText(tv.getText() + "\n" + p._id + " " + p.name + " " + p.age);
        }
    }

    private int insert(Person person) {
        ContentValues values = new ContentValues();
        values.put(Provider.PersonColumns.NAME, person.name);
        values.put(Provider.PersonColumns.AGE, person.age);
        Uri uri = getContentResolver().insert(Provider.PersonColumns.CONTENT_URI, values);
        Log.i(TAG, "insert uri=" + uri);
        String lastPath = uri.getLastPathSegment();
        if (TextUtils.isEmpty(lastPath)) {
            Log.i(TAG, "insert failure!");
        } else {
            Log.i(TAG, "insert success! the id is " + lastPath);
        }

        return Integer.parseInt(lastPath);
    }

    private List<Person> queryAll() {
        List<Person> personList = new ArrayList<Person>();
        Cursor c = null;
        try {
            c = getContentResolver().query(Provider.PersonColumns.CONTENT_URI,
                    new String[] {Provider.PersonColumns._ID, Provider.PersonColumns.NAME, Provider.PersonColumns.AGE },
                    null, null, null);
            if (c != null && c.moveToFirst()) {
                do {
                    Person p = new Person();
                    p._id = c.getInt(c.getColumnIndexOrThrow(Provider.PersonColumns._ID));
                    p.name = c.getString(c.getColumnIndexOrThrow(Provider.PersonColumns.NAME));
                    p.age = c.getInt(c.getColumnIndexOrThrow(Provider.PersonColumns.AGE));
                    personList.add(p);
                } while(c.moveToNext());
            }

        }catch(Exception e) {
            Log.e("xx", e.toString());
        } finally{
            if (c != null) {
                c.close();
            }
        }

        return personList;
    }
    
    private Person queryById(int id) {
        Cursor c = null;
        try {
            Uri uriWithId = ContentUris.withAppendedId(Provider.PersonColumns.CONTENT_URI, id);
            c = getContentResolver().query(uriWithId,
                    new String[] {Provider.PersonColumns._ID, Provider.PersonColumns.NAME, Provider.PersonColumns.AGE },
                    null, null, null);
            if (c != null && c.moveToFirst()) {
                Person p = new Person();
                p._id = c.getInt(c.getColumnIndexOrThrow(Provider.PersonColumns._ID));
                p.name = c.getString(c.getColumnIndexOrThrow(Provider.PersonColumns.NAME));
                p.age = c.getInt(c.getColumnIndexOrThrow(Provider.PersonColumns.AGE));
                Log.i(TAG, "person.name=" + p.name + "---person.age=" + p.age);
                return p;
            } else {
                Log.i(TAG, "query failure!");
            }
        }catch(Exception e) {
            Log.e("xx", e.toString());
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return null;
    }
    
    private Person query(int id) {
        Cursor c = null;
        try {
            c = getContentResolver().query(Provider.PersonColumns.CONTENT_URI,
                    new String[] { Provider.PersonColumns.NAME, Provider.PersonColumns.AGE },
                    Provider.PersonColumns._ID + "=?", new String[] { id + "" }, null);
            if (c != null && c.moveToFirst()) {
                Person p = new Person();
                p.name = c.getString(c.getColumnIndexOrThrow(Provider.PersonColumns.NAME));
                p.age = c.getInt(c.getColumnIndexOrThrow(Provider.PersonColumns.AGE));
                Log.i(TAG, "person.name=" + p.name + "---person.age=" + p.age);
                return p;
            } else {
                Log.i(TAG, "query failure!");
            }
        }catch(Exception e) {
            Log.e("xx", e.toString());
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return null;
    }

}
