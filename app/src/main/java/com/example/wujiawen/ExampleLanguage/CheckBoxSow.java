package com.example.wujiawen.ExampleLanguage;//com.example.wujiawen.ExampleLanguage.CheckBoxSow

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.CheckBox;

/**
 * Created by wujiawen on 2018/3/2.
 */

public class CheckBoxSow extends CheckBox {

    public CheckBoxSow(Context context) {
        super(context);
    }

    public CheckBoxSow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckBoxSow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //--Boolean Mark=super.onTouchEvent(event);
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Boolean Mark=super.dispatchTouchEvent(ev);;
        return false;
    }
}
