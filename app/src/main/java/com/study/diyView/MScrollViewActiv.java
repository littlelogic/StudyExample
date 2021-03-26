package com.study.diyView;
//com.study.RxAndroid.HandlerLoopActiv
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import com.badlogic.utils.ALog;
import com.example.wujiawen.a_Main.R;
import com.shizhefei.view.hvscrollview.HVScrollView;

public class MScrollViewActiv extends Activity {


    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activ_test_diyview);

        ScrollView mScrollView = null;
        HVScrollView mHVScrollView = null;
        NestedScrollView mNestedScrollView;

        ConstraintLayout mConstraintLayout;


    }




    ///============================================================================


    public TextView addTestView(LinearLayout mLinearLayout,String text,Runnable hRunnable){
        return null;
    }

    public TextView addTestView(LinearLayout mLinearLayout,String name,String text){
        return null;
    }

    public TextView getTextView(String text){
        return null;
    }





}
