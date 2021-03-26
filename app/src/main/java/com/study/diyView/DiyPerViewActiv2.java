package com.study.diyView;
// com.study.diyView.DiyPerViewActiv
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.PerFrameLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.badlogic.utils.ALog;
import com.example.wujiawen.a_Main.R;
import com.shizhefei.view.hvscrollview.HVScrollView;

public class DiyPerViewActiv2 extends Activity {


    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activ_test_diyview_2);

        TextView titleTv = this.findViewById(R.id.title);
//        PerFrameLayout.LayoutParams params_titleTv = (PerFrameLayout.LayoutParams)titleTv.getLayoutParams();
//        params_titleTv.gravity = Gravity.LEFT | Gravity.BOTTOM;
//        params_titleTv.setPerParent(true);
//        params_titleTv.perWidth = 1/2f;
//        params_titleTv.perHeight = 1/3f;
//        params_titleTv.perLeft = 1/4f;
//        params_titleTv.perBottom = 1/3f;

        titleTv.setText("定位左下角" + "\n"
                + "左距离为父窗体宽的1/4" + "\n"
                + "底距离为父窗体高的1/4" + "\n"
                + "宽为父窗体宽的1/2" + "\n"
                + "高为父窗体高的1/3"
        );

    }




    ///============================================================================

    public static class MFrameLayout extends FrameLayout{
        //com.study.diyView.DiyPerViewActiv2$MFrameLayout

        public MFrameLayout(Context context) {
            super(context);
        }

        public MFrameLayout(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        //==============================================


        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

            View hView = this.getChildAt(0);
            int width_will = (int)(MeasureSpec.getSize(widthMeasureSpec) / 2f);
            int height_will = (int)(MeasureSpec.getSize(heightMeasureSpec) / 3f);

            if (hView.getLayoutParams() != null) {
                FrameLayout.LayoutParams hLayoutParams = (FrameLayout.LayoutParams)hView.getLayoutParams();
                hLayoutParams.gravity = Gravity.LEFT | Gravity.BOTTOM;
                hLayoutParams.leftMargin = (int)(MeasureSpec.getSize(widthMeasureSpec) / 4f);
                hLayoutParams.bottomMargin = (int)(MeasureSpec.getSize(heightMeasureSpec) / 3f);
                hLayoutParams.width = width_will;
                hLayoutParams.height = height_will;
                ALog.i("DiyPerViewActiv2-MFrameLayout-onMeasure-2"
                        + "-hLayoutParams.leftMargin->"+hLayoutParams.leftMargin
                        + "-hLayoutParams.bottomMargin->"+hLayoutParams.bottomMargin
                        + "-hLayoutParams.width->"+hLayoutParams.width
                        + "-hLayoutParams.height->"+hLayoutParams.height
                );
            }

            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
        }





    }


    public static class MTextView extends TextView{
        //com.study.diyView.DiyPerViewActiv2.MTextView

        public MTextView(Context context) {
            super(context);
        }

        public MTextView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        //==============================================


        protected void onMeasure_(int widthMeasureSpec, int heightMeasureSpec) {
            int width_will = (int)(MeasureSpec.getSize(widthMeasureSpec) / 2f);
            int height_will = (int)(MeasureSpec.getSize(heightMeasureSpec) / 3f);
            int widthMeasureSpec_new = MeasureSpec.makeMeasureSpec(width_will,MeasureSpec.getMode(widthMeasureSpec));
            int heightMeasureSpec_new = MeasureSpec.makeMeasureSpec(height_will,MeasureSpec.getMode(heightMeasureSpec));
            super.onMeasure(widthMeasureSpec_new, heightMeasureSpec_new);
            setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
            //------

            if (this.getLayoutParams() != null) {
                FrameLayout.LayoutParams hLayoutParams = (FrameLayout.LayoutParams)this.getLayoutParams();
                hLayoutParams.gravity = Gravity.LEFT | Gravity.BOTTOM;
                hLayoutParams.leftMargin = (int)(MeasureSpec.getSize(widthMeasureSpec) / 4f);
                hLayoutParams.bottomMargin = (int)(MeasureSpec.getSize(heightMeasureSpec) / 3f);
                hLayoutParams.width = width_will;
                hLayoutParams.height = height_will;
                ALog.i("DiyPerViewActiv2-MTextView-onMeasure-2"
                        + "-hLayoutParams.leftMargin->"+hLayoutParams.leftMargin
                        + "-hLayoutParams.bottomMargin->"+hLayoutParams.bottomMargin
                        + "-hLayoutParams.width->"+hLayoutParams.width
                        + "-hLayoutParams.height->"+hLayoutParams.height
                );
            }
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
        }





    }



}
