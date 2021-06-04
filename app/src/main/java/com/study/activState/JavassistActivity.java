package com.study.activState;
// com.study.activState.JavassistActivity
import android.app.Activity;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wujiawen.a_Main.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class JavassistActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_javassist);
        init();
    }

    private void init() {
        findViewById(R.id.hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show();
            }
        });

        findViewById(R.id.hello1).setOnClickListener(new MyListener());
    }

    private void show() {
        Log.e("MainActivity", "hello clicked");

    }

    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.e("MyListener", "myListener clicked");
        }
    }

}
