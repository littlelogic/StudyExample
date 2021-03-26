package com.example.wujiawen.ExamplesShader;//com.example.wujiawen.ExamplesShader.ShaderExampleActiv

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.badlogic.utils.ALog;
import com.badlogic.utils.Tools;
import com.example.wujiawen.a_Main.R;
import com.example.wujiawen.ui.manage.BaseActivity;

public class ShaderExampleActiv extends BaseActivity {

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
        AddBitmapShader_b(mLinearLayout,"-code-RECTANGLE-LINEAR_GRADIENT-");
        //-------------
        AddLinearGradient_c(mLinearLayout,"-code-RECTANGLE-LINEAR_GRADIENT-");
        AddLinearGradient_d(mLinearLayout,"-code-RECTANGLE-LINEAR_GRADIENT-");
        AddLinearGradient_e(mLinearLayout,"-code-RECTANGLE-LINEAR_GRADIENT-23");

        AddRadialGradient_f(mLinearLayout,"-code-RECTANGLE-LINEAR_GRADIENT-");

        AddSweepGradient_g(mLinearLayout,"-code-RECTANGLE-LINEAR_GRADIENT-");

        AddBitmapShader_test101(mLinearLayout,"-code-RECTANGLE-test101-");
        AddBitmapShader_test102(mLinearLayout,"-code-RECTANGLE-test101-");
        if(2>1){
            return;
        }
    }

    Paint mPaint_a=new Paint();

    public void AddBitmapShader_a(LinearLayout ParentView,String text_str){

        final MyTextView mView_aa=new MyTextView(this){
            int test_aa=55;
            @Override
            public void init(Context context) {
                super.init(context);
                mPaint_a=new Paint();
                ALog.i("wjw01","--ShaderExampleActiv--AddBitmapShader_a--test_aa-1->>"+test_aa);
                test_aa=99;
                ALog.i("wjw01","--ShaderExampleActiv--AddBitmapShader_a--test_aa-2->>"+test_aa);
                Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.ring_a11);
                BitmapShader mShader = new BitmapShader(bmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
                mPaint_a.setTextSize(Tools.dip2px(ShaderExampleActiv.this,70));
                mPaint_a.setColor(Color.RED);
                mPaint_a.setTypeface(Typeface.DEFAULT_BOLD);
                mPaint_a.setShader(mShader);
            }

            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                ALog.i("wjw01","--ShaderExampleActiv--AddBitmapShader_a--onDraw-->>"+mPaint_a);
                canvas.drawText("小狗狗",0,this.getHeight()/5*4,mPaint_a);
            }
        };
        LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams((int)Tools.dip2px(this,200),(int)Tools.dip2px(this,80));
        Params_b.bottomMargin=Tools.dip2px(this,20);
        mView_aa.setLayoutParams(Params_b);
        ParentView.addView(mView_aa);
        mView_aa.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mView_aa.invalidate();
            }
        });
    }


    //========================================================================================

    int Bitmap_width;
    int Bitmap_height;
    RectF rectBlackBg = new RectF();
    Paint mPaint_b=new Paint();

    public void AddBitmapShader_b(LinearLayout ParentView,String text_str){

        final MyTextView mView_aa=new MyTextView(this){
            int test_aa=55;
            @Override
            public void init(Context context) {
                super.init(context);
                mPaint_b=new Paint();
                ALog.i("wjw01","--ShaderExampleActiv--AddBitmapShader_a--test_aa-1->>"+test_aa);
                test_aa=99;
                ALog.i("wjw01","--ShaderExampleActiv--AddBitmapShader_a--test_aa-2->>"+test_aa);
                Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.ring_a11);
                BitmapShader mShader = new BitmapShader(bmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
                Bitmap_width=bmp.getWidth();
                Bitmap_height=bmp.getHeight();
                mPaint_b.setTextSize(160.0f);
                mPaint_b.setColor(Color.RED);
                mPaint_b.setTypeface(Typeface.DEFAULT_BOLD);
                mPaint_b.setShader(mShader);

                mPaint_b.setAntiAlias(true);
                mPaint_b.setStrokeWidth((float) 60);
                mPaint_b.setStyle(Paint.Style.STROKE);
                mPaint_b.setStrokeCap(Paint.Cap.ROUND);
//                mPaint_b.setColor(Color.TRANSPARENT);

                rectBlackBg.set(0,0,Bitmap_width,Bitmap_height);
            }

            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                ALog.i("wjw01","--ShaderExampleActiv--AddBitmapShader_a--onDraw-->>"+mPaint_b);
                //--canvas.drawText("小狗狗",0,this.getHeight()/5*4,mPaint_a);

                canvas.drawArc(rectBlackBg, 0, 200, false, mPaint_b);

            }
        };
        LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams(Bitmap_width,Bitmap_height);
        Params_b.bottomMargin=Tools.dip2px(this,20);
        mView_aa.setLayoutParams(Params_b);
        ParentView.addView(mView_aa);
        mView_aa.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mView_aa.invalidate();
            }
        });
    }

    //========================================================================================
    public void AddLinearGradient_c(LinearLayout ParentView,String text_str){
        MyTextView_a mMyTextView_a=new MyTextView_a(this);
        LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,Tools.dip2px(ShaderExampleActiv.this,40));
        Params_b.bottomMargin=Tools.dip2px(this,20);
        mMyTextView_a.setLayoutParams(Params_b);
        ParentView.addView(mMyTextView_a);

    }

    class MyTextView_a extends MyTextView {
        Paint mPaint=new Paint();
        int[] mColor=new int[]{
                Color.parseColor("#faf84d"),
                Color.parseColor("#003449"),
                Color.parseColor("#808080"),
                Color.parseColor("#CC423C")};
        public MyTextView_a(Context context) {
            super(context);
            init(context);
            mPaint.setTextSize(Tools.dip2px(ShaderExampleActiv.this,35));
            mPaint.setColor(Color.RED);
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        }

        public void init(Context context) {

        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            //渐变的是一个颜色序列(#faf84d,#003449,#808080,#cc423c)
            Shader mShader = new LinearGradient(0,0,this.getWidth(),0,mColor,null,Shader.TileMode.CLAMP);
            mPaint.setShader(mShader);
            canvas.drawRect(0,0,this.getWidth(),this.getHeight(),mPaint);
            //--canvas.drawText("小狗狗",0,this.getHeight()/5*4,mPaint);
        }
    }

    //========================================================================================

    public void AddLinearGradient_d(LinearLayout ParentView,String text_str){
        MyTextView_b mMyTextView_a=new MyTextView_b(this);
        LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,Tools.dip2px(ShaderExampleActiv.this,40));
        Params_b.bottomMargin=Tools.dip2px(this,20);
        mMyTextView_a.setLayoutParams(Params_b);
        ParentView.addView(mMyTextView_a);
    }

    class MyTextView_b extends MyTextView {
        Paint mPaint=new Paint();
        int[] mColor=new int[]{
                Color.parseColor("#faf84d"),
                Color.parseColor("#003449"),
                Color.parseColor("#808080"),
                Color.parseColor("#CC423C")};
        public MyTextView_b(Context context) {
            super(context);
            mPaint.setTextSize(Tools.dip2px(ShaderExampleActiv.this,35));
            mPaint.setColor(Color.RED);
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            //渐变的是一个颜色序列(#faf84d,#003449,#808080,#cc423c)
            Shader mShader = new LinearGradient(0,0,this.getWidth(),0,mColor,null,Shader.TileMode.CLAMP);
            mPaint.setShader(mShader);
            canvas.drawText("小狗狗",0,this.getHeight()/5*4,mPaint);
        }
    }

    //========================================================================================

    public void AddLinearGradient_e(LinearLayout ParentView,String text_str){
        MyTextView_e mMyTextView_a=new MyTextView_e(this);
        LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,Tools.dip2px(ShaderExampleActiv.this,40));
        Params_b.bottomMargin=Tools.dip2px(this,20);
        mMyTextView_a.setLayoutParams(Params_b);
        ParentView.addView(mMyTextView_a);
        mMyTextView_a.setText(text_str);
    }

    class MyTextView_e extends MyTextView {
        Paint mPaint=new Paint();

        /**
         * positions数组中的值大小范围从0.0到1.0，0.0代表起点位置，1.0代表终点位置。如果这个数组被置为空的话，
         * 颜色就会平均分配。 ，如果这个数组不为空呢？我们结合代码效果来讲解。
         */
//        float[] positions=new float[]{0.0f,0.8f,1.0f};

        float[] positions=new float[]{0.3f,0.8f,0.9f};

        int[] mColor=new int[]{
                Color.RED,     //对应的position值是0.0  所以为起点位置。
                Color.GREEN,  //这个颜色在0.8比率的地方
                Color.BLUE};  //这个颜色为终点处的颜色

        public MyTextView_e(Context context) {
            super(context);
            mPaint.setTextSize(Tools.dip2px(ShaderExampleActiv.this,35));
            mPaint.setColor(Color.RED);
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        }


        @Override
        protected void onDraw(Canvas canvas) {
            //直线的
            LinearGradient mShader = new LinearGradient(0,0,this.getWidth(),0,mColor,positions,Shader.TileMode.CLAMP);
//            LinearGradient mShader = new LinearGradient(0,0,this.getWidth(),this.getWidth(),mColor,positions,Shader.TileMode.CLAMP);
            mPaint.setShader(mShader);
            canvas.drawRect(0,0,this.getWidth(),this.getHeight(),mPaint);

            super.onDraw(canvas);

        }
    }

    //========================================================================================
    public void AddRadialGradient_f(LinearLayout ParentView,String text_str){
        MyTextView_f mMyTextView_a=new MyTextView_f(this);
        LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,Tools.dip2px(ShaderExampleActiv.this,40));
        Params_b.bottomMargin=Tools.dip2px(this,20);
        mMyTextView_a.setLayoutParams(Params_b);
        ParentView.addView(mMyTextView_a);
    }

    class MyTextView_f extends MyTextView {
        Paint mPaint=new Paint();

        /**
         * positions数组中的值大小范围从0.0到1.0，0.0代表起点位置，1.0代表终点位置。如果这个数组被置为空的话，
         * 颜色就会平均分配。 ，如果这个数组不为空呢？我们结合代码效果来讲解。
         */
        float[] positions=new float[]{0.0f,0.8f,1.0f};

        int[] mColor=new int[]{
                Color.RED,     //对应的position值是0.0  所以为起点位置。
                Color.GREEN,  //这个颜色在0.8比率的地方
                Color.BLUE};  //这个颜色为终点处的颜色

        public MyTextView_f(Context context) {
            super(context);
            mPaint.setTextSize(Tools.dip2px(ShaderExampleActiv.this,35));
            mPaint.setColor(Color.RED);
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);


        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float r_a=(float)Math.sqrt( this.getWidth()*this.getWidth()+this.getHeight()*this.getHeight())/2;
            Shader mShader = new RadialGradient(this.getWidth()/2,this.getHeight()/2,r_a,mColor,positions,Shader.TileMode.CLAMP);
            mPaint.setShader(mShader);
            canvas.drawRect(0,0,this.getWidth(),this.getHeight(),mPaint);
        }
    }

    //========================================================================================
    public void AddSweepGradient_g(LinearLayout ParentView,String text_str){
        MyTextView_g mMyTextView_a=new MyTextView_g(this);
        LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,Tools.dip2px(ShaderExampleActiv.this,40));
        Params_b.bottomMargin=Tools.dip2px(this,20);
        mMyTextView_a.setLayoutParams(Params_b);
        ParentView.addView(mMyTextView_a);
    }

    class MyTextView_g extends MyTextView {
        Paint mPaint=new Paint();

        /**
         * positions数组中的值大小范围从0.0到1.0，0.0代表起点位置，1.0代表终点位置。如果这个数组被置为空的话，
         * 颜色就会平均分配。 ，如果这个数组不为空呢？我们结合代码效果来讲解。
         */
        //--float[] positions=new float[]{0.0f,0.8f,1.0f};
        float[] positions=new float[]{0.0f,0.4f,0.6f};

        int[] mColor__=new int[]{
                Color.RED,     //对应的position值是0.0  所以为起点位置。
                Color.GREEN,  //这个颜色在0.8比率的地方
                Color.BLUE};  //这个颜色为终点处的颜色
        int[] mColor=new int[]{
                Color.RED,     //对应的position值是0.0  所以为起点位置。
                Color.GREEN,  //这个颜色在0.8比率的地方
                Color.RED};  //这个颜色为终点处的颜色

        public MyTextView_g(Context context) {
            super(context);
            mPaint.setTextSize(Tools.dip2px(ShaderExampleActiv.this,35));
            mPaint.setColor(Color.RED);
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            //这里为了方便演示，将尺寸固定为400*400
            setMeasuredDimension(Tools.dip2px(ShaderExampleActiv.this,280),Tools.dip2px(ShaderExampleActiv.this,40));
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Shader mShader = new SweepGradient(this.getWidth()/2,this.getHeight()/2,mColor,positions);
            mPaint.setShader(mShader);
            canvas.drawRect(0,0,this.getWidth(),this.getHeight(),mPaint);
        }
    }
    //========================================================================================

    interface InitInterface{
        void doInitInterface(Context context);
    }

    interface OnDrawInterface{
        void doONDrawInterface(Canvas canvas);
    }

    class MyTextView extends  TextView{
        InitInterface mInitInterface;
        OnDrawInterface mOnDrawInterface;

        public MyTextView(Context context) {
            super(context);
            init(context);
        }

        public void init(Context context) {

        }

        public MyTextView(Context context, InitInterface mInitInterface, OnDrawInterface mOnDrawInterface, float width, float height) {
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









    public TextView AddTextView(LinearLayout ParentView,int mDrawable_int,String text_str){
        return AddTextView(ParentView,mDrawable_int,text_str,false);
    }

    public TextView AddTextView(LinearLayout ParentView,Drawable mDrawable,String text_str){
        TextView mTextView=new TextView(this);
        mTextView.setText(text_str);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setBackgroundDrawable(mDrawable);
        mTextView.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, Tools.dip2px(this,40));
        Params_b.bottomMargin=Tools.dip2px(this,10);
        mTextView.setLayoutParams(Params_b);
        ParentView.addView(mTextView);
        return mTextView;
    }

    public TextView AddTextView(LinearLayout ParentView,int mDrawable_int,String text_str,boolean square_mark){
        TextView mTextView=new TextView(this);
        mTextView.setBackgroundResource(mDrawable_int);
        mTextView.setText(text_str);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextColor(Color.GRAY);
        //---mTextView.setTextSize(Tools.dip2px(this,5));
        int width_a;
        if(square_mark){
            width_a=Tools.dip2px(this,40);
        }else{
            width_a=ActionBar.LayoutParams.MATCH_PARENT;
        }
        LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams(width_a,Tools.dip2px(this,40));
        Params_b.bottomMargin=Tools.dip2px(this,10);
        mTextView.setLayoutParams(Params_b);
        ParentView.addView(mTextView);
        return mTextView;
    }

    //===========================================



    public void AddBitmapShader_test101(LinearLayout ParentView,String text_str){
        final MyTextView101 mMyTextView101=new MyTextView101(this);
//        LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams(600,300);
        LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,Bitmap_height);
//        LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams(Bitmap_width*2+Bitmap_width/2,Bitmap_height);
        Params_b.bottomMargin=Tools.dip2px(this,20);
        mMyTextView101.setLayoutParams(Params_b);
        ParentView.addView(mMyTextView101,Params_b);
        mMyTextView101.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mMyTextView101.invalidate();
            }
        });
    }

    class MyTextView101 extends  TextView{

//        int Bitmap_width;
//        int Bitmap_height;


        Bitmap bmp=null;
        public MyTextView101(Context context) {
            super(context);
            init(context);
        }

        public void init(Context context) {
            mPaint_b=new Paint();
            bmp = BitmapFactory.decodeResource(getResources(),R.drawable.icon);
            BitmapShader mShader = new BitmapShader(bmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            Bitmap_width=bmp.getWidth();
            Bitmap_height=bmp.getHeight();
            mPaint.setTextSize(22);
            mPaint.setColor(Color.RED);
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            mPaint.setShader(mShader);
            mPaint.setAntiAlias(true);
           /* mPaint_b.setStrokeWidth((float) 60);
            mPaint_b.setStyle(Paint.Style.STROKE);
            mPaint_b.setStrokeCap(Paint.Cap.ROUND);*/

            rectBlackBg.set(0,0,Bitmap_width,Bitmap_height);
        }

        RectF targetRect = new RectF();
        Paint mPaint=new Paint();
        Bitmap  startBmp=null;
        Bitmap  endBmp=null;
        Bitmap  targetBmp=null;
        int startX;
        int endX;
        int paddingRight;


        public void setData(Bitmap mBitmap,int startX,int endX){
            this.startX=startX;
            this.endX=endX;
            startBmp=Bitmap.createBitmap(mBitmap,0,0,startX,mBitmap.getHeight(),null,true);
            endBmp=Bitmap.createBitmap(mBitmap,endX,0,mBitmap.getWidth()-endX,mBitmap.getHeight(),null,true);
            targetBmp=Bitmap.createBitmap(mBitmap,startX,0,endX-startX,mBitmap.getHeight(),null,true);
            paddingRight=mBitmap.getWidth()-endX;
        }

//        @Override
//        protected void onDraw(Canvas canvas) {
//            canvas.drawColor(Color.argb(100,0,0,0));
//
//            canvas.drawBitmap(startBmp,0,0,null);
//            canvas.drawBitmap(endBmp,this.getWidth()-paddingRight,0,null);
//            targetRect.set(startX,0,this.getWidth()-paddingRight-startX,Bitmap_height);
//            canvas.drawRect(rectBlackBg,mPaint);
//
//            super.onDraw(canvas);
//
//        }
        protected void onDraw(Canvas canvas) {

            canvas.drawColor(Color.argb(100,0,0,0));

            canvas.drawBitmap(bmp,0,0,null);
            canvas.drawBitmap(bmp,this.getWidth()-Bitmap_width,0,null);
            rectBlackBg.set(Bitmap_width+150,0,this.getWidth()-Bitmap_width,Bitmap_height);
            canvas.drawRect(rectBlackBg,mPaint);

            super.onDraw(canvas);

        }
    }


    public void AddBitmapShader_test102(LinearLayout ParentView,String text_str){
        final RepeatextView mRepeatextView=new RepeatextView(this);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(),R.drawable.icon);
        int height=bmp.getHeight();
        int width=bmp.getWidth();
        mRepeatextView.setData(bmp,100,200);
        LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,height);
        Params_b.bottomMargin=Tools.dip2px(this,20);
        mRepeatextView.setLayoutParams(Params_b);
        ParentView.addView(mRepeatextView);
        mRepeatextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mRepeatextView.invalidate();
            }
        });
    }

    class RepeatextView extends  TextView{

        public RepeatextView(Context context) {
            super(context);
            init(context);
        }

        public void init(Context context) {

        }

        Paint mPaint_2=new Paint();
        RectF targetRect = new RectF();
        Paint mPaint=new Paint();
        Bitmap  startBmp=null;
        Bitmap  endBmp=null;
        Bitmap  targetBmp=null;
        int startX;
        int endX;
        int paddingRight;
        int  targetBmpWidth;
        int  targetBmpHeight;

        public void setData(Bitmap mBitmap,int startX,int endX){
            this.startX=startX;
            this.endX=endX;
            startBmp=Bitmap.createBitmap(mBitmap,0,0,startX,mBitmap.getHeight(),null,true);
            endBmp=Bitmap.createBitmap(mBitmap,endX,0,mBitmap.getWidth()-endX,mBitmap.getHeight(),null,true);
            targetBmp=Bitmap.createBitmap(mBitmap,startX,0,endX-startX,mBitmap.getHeight(),null,true);
            paddingRight=mBitmap.getWidth()-endX;
            //-------------
            BitmapShader mShader = new BitmapShader(targetBmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            targetBmpWidth=targetBmp.getWidth();
            targetBmpHeight=targetBmp.getHeight();
            mPaint.setTextSize(22);
            mPaint.setColor(Color.RED);
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            mPaint.setShader(mShader);
            mPaint.setAntiAlias(true);

            mPaint_2.setColor(Color.RED);
            /*mPaint_b.setStrokeWidth((float) 60);
            mPaint_b.setStyle(Paint.Style.STROKE);
            mPaint_b.setStrokeCap(Paint.Cap.ROUND);*/
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.argb(100,0,0,0));

            canvas.drawBitmap(startBmp,0,0,null);
            canvas.drawBitmap(endBmp,this.getWidth()-paddingRight,0,null);

            targetRect.set(startX,0,this.getWidth()-paddingRight,Bitmap_height);
            canvas.drawRect(targetRect,mPaint);

            super.onDraw(canvas);
        }
    }

}
