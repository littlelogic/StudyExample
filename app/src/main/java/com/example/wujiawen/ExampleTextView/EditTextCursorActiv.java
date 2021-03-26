package com.example.wujiawen.ExampleTextView;//com.example.wujiawen.ExampleTextView.EditViewCursorActiv

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.badlogic.utils.ALog;
import com.example.wujiawen.a_Main.R;
import com.example.wujiawen.ui.manage.BaseActivity;

import java.lang.reflect.Field;

public class EditTextCursorActiv extends BaseActivity {
    final String TAG="wjw01";

    EditText  editTextA;
    EditText  editTextB;
    EditText  editTextC;
    EditText  editTextD;
    EditText  editTextE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //-------------------------
        this.setContentView(R.layout.activity_edit_cursor);
        editTextA=(EditText)this.findViewById(R.id.EditView_1);
        editTextB=(EditText)this.findViewById(R.id.EditView_2);
        editTextC=(EditText)this.findViewById(R.id.EditView_3);
        editTextD=(EditText)this.findViewById(R.id.EditView_4);
        editTextE=(EditText)this.findViewById(R.id.EditView_5);
        //---------------------------------
        try {
            Field f = EditText.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(editTextD, R.drawable.test_bg_bitmap);
        } catch (Exception e) {
            //--java.lang.NoSuchFieldException: No field mCursorDrawableRes in class Landroid/widget/EditText; (declaration of 'android.widget.EditText' appears in /system/framework/framework.jar:classes2.dex)
            ALog.i(TAG,"--EditTextCursorActiv--onCreate--e-->>"+e);
        }
        //---------------------------------
        setCursorDrawableColor(editTextE, Color.YELLOW);
    }
    //========================================================================================
    public void setCursorDrawableColor(EditText editText, int color) {
        try {
            Field fCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            fCursorDrawableRes.setAccessible(true);
            int mCursorDrawableRes = fCursorDrawableRes.getInt(editText);
            Field fEditor = TextView.class.getDeclaredField("mEditor");
            fEditor.setAccessible(true);
            Object editor = fEditor.get(editText);
            Class<?> clazz = editor.getClass();
            Field fCursorDrawable = clazz.getDeclaredField("mCursorDrawable");
            fCursorDrawable.setAccessible(true);
            Drawable[] drawables = new Drawable[2];
            drawables[0] = editText.getContext().getResources().getDrawable(mCursorDrawableRes);
            drawables[1] = editText.getContext().getResources().getDrawable(mCursorDrawableRes);
            drawables[0].setColorFilter(color, PorterDuff.Mode.SRC_IN);
            drawables[1].setColorFilter(color, PorterDuff.Mode.SRC_IN);
            fCursorDrawable.set(editor, drawables);
        } catch (Throwable e) {
            ALog.i(TAG,"--EditTextCursorActiv--setCursorDrawableColor--e-->>"+e);
        }
    }

}
