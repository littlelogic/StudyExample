package com.study.diyView;
// com.study.diyView.DiyPerViewActiv
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.PerFrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import com.example.wujiawen.a_Main.R;
import com.shizhefei.view.hvscrollview.HVScrollView;

public class DiyPerViewActiv extends Activity {


    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activ_test_diyview);

        TextView titleTv = this.findViewById(R.id.title);
        PerFrameLayout.LayoutParams params_titleTv = (PerFrameLayout.LayoutParams)titleTv.getLayoutParams();
        params_titleTv.gravity = Gravity.LEFT | Gravity.BOTTOM;
        params_titleTv.setPerParent(true);
        params_titleTv.perWidth = 1/2f;
        params_titleTv.perHeight = 1/3f;
        params_titleTv.perLeft = 1/4f;
        params_titleTv.perBottom = 1/3f;

        titleTv.setText("定位左下角" + "\n"
                + "左距离为父窗体宽的1/4" + "\n"
                + "底距离为父窗体高的1/4" + "\n"
                + "宽为父窗体宽的1/2" + "\n"
                + "高为父窗体高的1/3"
        );


        ScrollView mScrollView = null;
        HVScrollView mHVScrollView = null;
        NestedScrollView mNestedScrollView;
        ConstraintLayout mConstraintLayout;


    }




    ///============================================================================



}
