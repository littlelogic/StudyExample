package com.example.wujiawen.Example_a_Error_Study;//com.example.wujiawen.ExamplesShader.ShaderExampleActiv

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.badlogic.utils.ALog;
import com.badlogic.utils.Tools;
import com.example.wujiawen.a_Main.R;

public class ShaderExampleActiv extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //-------------------------
        ScrollView mScrollingView=new ScrollView(this);
        mScrollingView.setBackgroundColor(Color.GREEN);
        LinearLayout mLinearLayout=new LinearLayout(this);
        FrameLayout.LayoutParams Params_b=new FrameLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        mLinearLayout.setLayoutParams(Params_b);
        int padding= Tools.dip2px(this,10);
        mLinearLayout.setPadding(padding,padding,padding,padding);
        mLinearLayout.setBackgroundColor(Color.WHITE);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mScrollingView.addView(mLinearLayout);
        this.setContentView(mScrollingView);
        //----------------
        AddBitmapShader_a(mLinearLayout,"-code-RECTANGLE-LINEAR_GRADIENT-");
    }

    Paint mPaint=new Paint();

    public void AddBitmapShader_a(LinearLayout ParentView,String text_str){

        final View_aa mView_aa=new View_aa(this){

            Paint mPaint=new Paint();
            int test_aa=55;
            @Override
            public void init(Context context) {
                super.init(context);
                ALog.i("wjw01","--ShaderExampleActiv--AddBitmapShader_a--test_aa-1->>"+mPaint);//这个是null--？？
                mPaint=new Paint();
                test_aa=99;
                ALog.i("wjw01","--ShaderExampleActiv--AddBitmapShader_a--test_aa-->>"+test_aa);
                ALog.i("wjw01","--ShaderExampleActiv--AddBitmapShader_a--test_aa-2->>"+mPaint);
                Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.ring_a11);
                BitmapShader mShader = new BitmapShader(bmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
                mPaint.setTextSize(160.0f);
                mPaint.setColor(Color.RED);
                mPaint.setTypeface(Typeface.DEFAULT_BOLD);
                mPaint.setShader(mShader);
                //---

            }

            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                ALog.i("wjw01","--ShaderExampleActiv--AddBitmapShader_a--onDraw-->>"+mPaint);
                //canvas.drawText("小狗狗",0,this.getHeight()/5*4,mPaint);
            }
        };
        LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams((int)Tools.dip2px(this,200),(int)Tools.dip2px(this,80));
        mView_aa.setLayoutParams(Params_b);
        ParentView.addView(mView_aa);
        mView_aa.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mView_aa.invalidate();
            }
        });
    }


    interface InitInterface{
        void doInitInterface(Context context);
    }

    interface OnDrawInterface{
        void doONDrawInterface(Canvas canvas);
    }

    class View_aa extends  TextView{
        InitInterface mInitInterface;
        OnDrawInterface mOnDrawInterface;



        public View_aa(Context context) {
            super(context);
            init(context);
        }

        public void init(Context context) {

        }


        public View_aa(Context context,InitInterface mInitInterface,OnDrawInterface mOnDrawInterface,float width,float height) {
            super(context);
            LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams((int)width,(int)height);
            this.setLayoutParams(Params_b);
            if(mInitInterface!=null){
                mInitInterface.doInitInterface(context);
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if(mOnDrawInterface!=null){
                mOnDrawInterface.doONDrawInterface(canvas);
            }
        }
    }

    //===========================================

}
