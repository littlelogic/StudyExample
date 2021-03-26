package com.example.wujiawen.ExampleTextView;

import android.app.Activity;
import android.os.Bundle;

import com.example.wujiawen.a_Main.R;


public class ExpandTextViewActiv extends Activity {

    ExpandTextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_text);
        textView= (ExpandTextView) findViewById(R.id.cusTextView);
        textView.updateText(getResources().getString(R.string.test_expandtext));
    }

    @Override
    protected void onResume() {
        super.onResume();
//        textView.updateText(getResources().getString(R.string.test_expandtext));
    }
}
