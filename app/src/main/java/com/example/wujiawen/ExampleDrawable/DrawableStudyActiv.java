package com.example.wujiawen.ExampleDrawable;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.badlogic.utils.ALog;
import com.badlogic.utils.MyCompatView;
import com.badlogic.utils.Tools;
import com.example.wujiawen.a_Main.R;
import com.example.wujiawen.ui.manage.BaseActivity;

import static android.view.View.OVER_SCROLL_NEVER;

public class DrawableStudyActiv extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initShortcutAction(getIntent(),"onNewIntent");
        //-------------------------

        ScrollView mScrollingView=new ScrollView(this);
        mScrollingView.setBackgroundColor(Color.GREEN);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD) {
            mScrollingView.setOverScrollMode(OVER_SCROLL_NEVER);
        }

        //-mScrollingView.setLayoutParams();
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

        AddShapeDrawable_a(mLinearLayout,"-code-RECTANGLE-ShapeDrawable-");
        AddGradientDrawable_a(mLinearLayout,"-code-RECTANGLE-LINEAR_GRADIENT-");
        AddGradientDrawable_b(mLinearLayout,"-code-RECTANGLE-SWEEP_GRADIENT-");
        AddGradientDrawable_c(mLinearLayout,"-code-RECTANGLE-RADIAL_GRADIENT-");
        AddGradientDrawable_d(mLinearLayout,"-code-OVAL-LINEAR_GRADIENT-");
        AddGradientDrawable_e(mLinearLayout,"-code-OVAL-SWEEP_GRADIENT-");
        AddGradientDrawable_f(mLinearLayout,"-code-OVAL-RADIAL_GRADIENT-");
        //---------
        AddGradientDrawable_g(mLinearLayout,"-code-LINE-断线显示有问题-");
        //---------
        //ring-代码还没有设置成功
        AddGradientDrawable_h(mLinearLayout,"-code-RING-代码还没有设置成功-");
        //---------
        AddTextView(mLinearLayout, R.drawable.bg_gradient_rectangle_linear,"-xml-RECTANGLE-LINEAR_GRADIENT-").setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //--test_aa();
            }
        });
        AddTextView(mLinearLayout,R.drawable.bg_gradient_rectangle_sweep,"-xml-RECTANGLE-SWEEP_GRADIENT-");
        //-xml-RECTANGLE-RADIAL_GRADIENT-
        AddTextView(mLinearLayout,R.drawable.bg_gradient_rectangle_radial,"17",true);
        //--------
        AddTextView(mLinearLayout,R.drawable.bg_gradient_oval,"-xml-OVAL-LINEAR_GRADIENT-");
        //-xml-OVAL-LINEAR_GRADIENT-
        AddTextView(mLinearLayout,R.drawable.bg_gradient_ring,"22",true);
        AddTextView(mLinearLayout,R.drawable.bg_gradient_rotate_ring,"33",true);
        AddTextView(mLinearLayout,R.drawable.bg_gradient_line,"-xml-Line-");
        //--------
        //--------
        //--------
        //--------
        addStateListDrawable(mLinearLayout,"-code-selector-文字按下改变颜色-");
        add_selector_head(mLinearLayout,"-xml-selector_head-文字按下改变颜色-");
        add_selector_head_all(mLinearLayout,"-xml-配置-文字按下改变颜色-可编辑状态--",true);
        add_selector_head_all(mLinearLayout,"-xml-配置-文字按下改变颜色-不可编辑状态--",false);
        add_selector_total(mLinearLayout,"-xml-selector_total-");
        add_selector_total_CodeFromXml(mLinearLayout,"-selector_total_CodeFromXml-");
    }


    @Override
    protected void onNewIntent(Intent intent) {
        initShortcutAction(intent,"onNewIntent");
        super.onNewIntent(intent);
    }

    private void initShortcutAction(Intent launchIntent,String mark_str){
        Log.i("wjw01", "--ShaderExampleActiv--initShortcutAction--mark_str-->"+mark_str);
        Log.i("wjw01", "--ShaderExampleActiv--initShortcutAction--launchIntent-->"+launchIntent);
        if(launchIntent==null){
            return;
        }
        final String action = launchIntent.getAction();
        boolean  ShortCutMark=launchIntent.getBooleanExtra("ShortCutStartMark_360.wjw",false);
        int ShortCutNum=launchIntent.getIntExtra("ShortCutStartMark_360.wjw_num",-121);
        Log.i("wjw01", "--ShaderExampleActiv--initShortcutAction--action-->"+action);
        Log.i("wjw01", "--ShaderExampleActiv--initShortcutAction--ShortCutMark-->"+ShortCutMark);
        Log.i("wjw01", "--ShaderExampleActiv--initShortcutAction--ShortCutNum-->"+ShortCutNum);
        if (Intent.ACTION_CREATE_SHORTCUT.equals(action)) {
            Log.i("wjw01", "--ShaderExampleActiv--initShortcutAction--"+mark_str+"--是-->");
        }
    }

    public void test_aa(){
        /**
         * 华为手机限制，程序启动线程500，超过报oom
         */
        for(int i=0;i<503;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ALog.i("wjw01","--ShaderExampleActiv--test_aa--i->>");
                    do {
                        try {
                            Thread.sleep(20*2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }while(2>1);
                }
            } ).start();;
        }
    }

    public void record_a(Context mContext){
        //----------------------------------------------------
        Drawable mDrawable=null;
        //----------
        BitmapDrawable drawable = null;
        int width = drawable.getBitmap().getWidth();
        //----------
        ColorDrawable mColorDrawable=new ColorDrawable(Color.WHITE);mColorDrawable.setBounds(null);
        mColorDrawable.setColor(Color.YELLOW);
        mColorDrawable.setColorFilter(null);
        //----------
        InsetDrawable mInsetDrawable=new InsetDrawable(mDrawable,1,2,3,4);
        //----------
        /*ClipDrawable 是对一个Drawable进行剪切操作，可以控制这个drawable的剪切区域，以及相相
        对于容器的对齐方式，android中的进度条就是使用一个ClipDrawable实现效果的，它根据level的属性值，决定剪切区域的大小。
        在xml文件中使用clip作为根节点定义ClipDrawable。
        需要注意的是ClipDrawable是根据level的大小控制图片剪切操作的，官方文档的note中提到：
        The drawable is clipped completely and not visible when the level is 0 and fully revealed when
        the level is 10,000。也就是level的大小从0到10000，level为0时完全不显示，为10000时完全显示。是用Drawable
        提供的setLevel（int level）方法来设置剪切区域*///horizontal
        /*android:drawable:指定截取的源Drawable对象
        android:clipOrientaton:指定截取方向，可设置水平或垂直截取
        android:gravity:指定截取时的对齐方式*/
        ClipDrawable mClipDrawable=new ClipDrawable(mDrawable, Gravity.TOP,ClipDrawable.HORIZONTAL);
        Resources.Theme mTheme=null;
        mClipDrawable.scheduleDrawable(null,null,200);
        mClipDrawable.setLevel(200);
        //----------
        /*GradientDrawable 表示一个渐变区域，可以实现线性渐变、发散渐变和平铺渐变效果，在
        Android中可以使用GradientDrawable表示很多复杂而又绚丽的界面效果。
        可以使用xml定义GradientDrawable，相对于ColorDrawable类型，GradientDrawable要复杂很多，它有很
        多的元素组成。在xml文件中使用shape作为根节点来创建GradientDrawable，它包含很多属性和子节点，下面是GradientDrawable的xml文档节点结构。*/
        //--常用----
        GradientDrawable mGradientDrawable=new GradientDrawable();
        int colors_b[] = { 0xff255779 , 0xff3e7492, 0xffa6c0cd };//分别为开始颜色，中间夜色，结束颜色
        mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors_b);
        float[] radii={0,0,0,0};
        mGradientDrawable.setCornerRadii(radii);
        mGradientDrawable.setCornerRadius(0);
        mGradientDrawable.getPadding(new Rect(1,2,3,4));//--设置padding
        mGradientDrawable.setStroke(2,Color.WHITE);
        ColorStateList mColorStateList=null;
        int[] colors_a={Color.WHITE};
        //--21之后-mGradientDrawable.setStroke(2,mColorStateList);
        /*android:dashWidth="5dp"
        android:dashGap="3dp"
        其中android:dashWidth表示'-'这样一个横线的宽度，android:dashGap表示之间隔开的距离。*/
        mGradientDrawable.setStroke(2,Color.WHITE,2,3);//??不一定能除尽吧啊
        /**
         * shape写line虚线的时候发现4.0以上机型很多都没办法显示，后来在xml中android:layerType="software"
         * 代码中可以添加：line.setLayerType(ViewConstant.LAYER_TYPE_SOFTWARE, null);
         */
        //------
        mGradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        mGradientDrawable.setGradientType(GradientDrawable.SWEEP_GRADIENT);
        mGradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        //-----矩形（rectangle）、椭圆形(oval)、线(line)、环形(ring)
        mGradientDrawable.setShape(GradientDrawable.LINE);
        mGradientDrawable.setShape(GradientDrawable.OVAL);
        mGradientDrawable.setShape(GradientDrawable.RECTANGLE);
        mGradientDrawable.setShape(GradientDrawable.RING);
        //--渐变中心X点坐标的相对位置
        mGradientDrawable.setGradientCenter(2,2);
        mGradientDrawable.setUseLevel(false);
        //--当 android:type="radial" 时才使用,单独使用报错
        mGradientDrawable.setGradientRadius(11);
        //---mGradientDrawable.setOrientation(GradientDrawable.Orientation.BL_TR);//相对应 android:angle="45"
    }

    //    public static Drawable createFromXmlInner(Resources r, XmlPullParser parser, AttributeSet attrs,
//                                              Resources.Theme theme) throws XmlPullParserException, IOException {
//        final Drawable drawable;
//
//        final String name = parser.getName();
//        switch (name) {
//            case "selector":
//                drawable = new StateListDrawable();
//                break;
//            case "animated-selector":
//                drawable = new AnimatedStateListDrawable();
//                break;
//            case "level-list":
//                drawable = new LevelListDrawable();
//                break;
//            case "layer-list":
//                drawable = new LayerDrawable();
//                break;
//            case "transition":
//                drawable = new TransitionDrawable();
//                break;
//            case "ripple":
//                drawable = new RippleDrawable();
//                break;
//            case "color":
//                drawable = new ColorDrawable();
//                break;
//            case "shape":
//                drawable = new GradientDrawable();
//                break;
//            case "vector":
//                drawable = new VectorDrawable();
//                break;
//            case "animated-vector":
//                drawable = new AnimatedVectorDrawable();
//                break;
//            case "scale":
//                drawable = new ScaleDrawable();
//                break;
//            case "clip":
//                drawable = new ClipDrawable();
//                break;
//            case "rotate":
//                drawable = new RotateDrawable();
//                break;
//            case "animated-rotate":
//                drawable = new AnimatedRotateDrawable();
//                break;
//            case "animation-list":
//                drawable = new AnimationDrawable();
//                break;
//            case "inset":
//                drawable = new InsetDrawable();
//                break;
//            case "bitmap":
//                drawable = new BitmapDrawable(r);
//                if (r != null) {
//                    ((BitmapDrawable) drawable).setTargetDensity(r.getDisplayMetrics());
//                }
//                break;
//            case "nine-patch":
//                drawable = new NinePatchDrawable();
//                if (r != null) {
//                    ((NinePatchDrawable) drawable).setTargetDensity(r.getDisplayMetrics());
//                }
//                break;
//            default:
//                throw new XmlPullParserException(parser.getPositionDescription() +
//                        ": invalid drawable tag " + name);
//
//        }
//        drawable.inflate(r, parser, attrs, theme);
//        return drawable;
//    }

    public TextView AddTextView(LinearLayout ParentView,int mDrawable_int,String text_str){
        return AddTextView(ParentView,mDrawable_int,text_str,false);
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

    //===========================================

    public void AddShapeDrawable_a(LinearLayout ParentView,String text_str){

        float radius = Tools.dip2px(this.getApplication(),4);
        float[] outerR = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
        RoundRectShape rrShape = new RoundRectShape(outerR, null, null);
        ShapeDrawable drawable = new ShapeDrawable(rrShape);
        drawable.getPaint().setColor(0xff000000);
        /*drawable.getPaint().setStyle(Paint.Style.STROKE);
        drawable.getPaint().setStrokeWidth(Tools.dip2px(this,3));*/
        AddTextView(ParentView,drawable,text_str);
    }

    public void AddGradientDrawable_a(LinearLayout ParentView,String text_str){
        GradientDrawable mGradientDrawable=new GradientDrawable();
        int colors_1[] = { Color.YELLOW  };
        int colors_2[] = { Color.YELLOW , Color.BLUE };
        int colors_3[] = { Color.YELLOW , Color.BLUE, Color.BLACK };//分别为开始颜色，中间夜色，结束颜色
        int colors_4[] = { Color.YELLOW , Color.BLUE, Color.BLACK ,Color.GREEN};
        mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors_4);
        //--mGradientDrawable.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
        mGradientDrawable.setShape(GradientDrawable.RECTANGLE);//-默认
        AddTextView(ParentView,mGradientDrawable,text_str);
        //an array of length >= 8 containing 4 pairs of X and Y radius for each corner, specified in pixels
        float[] radii={
                Tools.dip2px(this,7),Tools.dip2px(this,7),
                0,0,
                Tools.dip2px(this,10),Tools.dip2px(this,10),
                0,0};
        mGradientDrawable.setCornerRadii(radii);
        //--mGradientDrawable.setCornerRadius(Tools.dip2px(this,20));
        mGradientDrawable.setStroke(Tools.dip2px(this,3),Color.RED);
        /*android:dashWidth="5dp"
        android:dashGap="3dp"
        其中android:dashWidth表示'-'这样一个横线的宽度，android:dashGap表示之间隔开的距离。*/
        mGradientDrawable.setStroke(Tools.dip2px(this,3),Color.RED,Tools.dip2px(this,10),Tools.dip2px(this,8));//??不一定能除尽吧啊
        mGradientDrawable.setStroke(Tools.dip2px(this,3),0xff1D1D1F,Tools.dip2px(this,10),Tools.dip2px(this,8));//??不一定能除尽吧啊
        mGradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
    }

    public void AddGradientDrawable_b(LinearLayout ParentView,String text_str){
        GradientDrawable mGradientDrawable=new GradientDrawable();
        int colors_1[] = { Color.YELLOW  };
        int colors_2[] = { Color.YELLOW , Color.BLUE };
        int colors_3[] = { Color.YELLOW , Color.BLUE, Color.BLACK };//分别为开始颜色，中间夜色，结束颜色
        int colors_4[] = { Color.YELLOW , Color.BLUE, Color.BLACK ,Color.GREEN};
        mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors_4);
        mGradientDrawable.setShape(GradientDrawable.RECTANGLE);//-默认
        AddTextView(ParentView,mGradientDrawable,text_str);
        //an array of length >= 8 containing 4 pairs of X and Y radius for each corner, specified in pixels
        float[] radii={
                Tools.dip2px(this,7),Tools.dip2px(this,7),
                0,0,
                Tools.dip2px(this,10),Tools.dip2px(this,10),
                0,0};
        mGradientDrawable.setCornerRadii(radii);
        mGradientDrawable.setCornerRadius(Tools.dip2px(this,20));
        mGradientDrawable.setStroke(Tools.dip2px(this,3),Color.RED);
        /*android:dashWidth="5dp"
        android:dashGap="3dp"
        其中android:dashWidth表示'-'这样一个横线的宽度，android:dashGap表示之间隔开的距离。*/
        mGradientDrawable.setStroke(Tools.dip2px(this,3),Color.RED,Tools.dip2px(this,10),Tools.dip2px(this,8));//??不一定能除尽吧啊
        //--z中心点右侧，水平向下开始
        mGradientDrawable.setGradientType(GradientDrawable.SWEEP_GRADIENT);
        mGradientDrawable.setGradientCenter(0.5f,0.5f);
        mGradientDrawable.setGradientCenter(1f,0.5f);
    }

    public void AddGradientDrawable_c(LinearLayout ParentView,String text_str){
        GradientDrawable mGradientDrawable=new GradientDrawable();
        int colors_2[] = { Color.YELLOW , Color.BLUE };//最少是两种颜色
        int colors_3[] = { Color.YELLOW , Color.BLUE, Color.BLACK };//分别为开始颜色，中间夜色，结束颜色
        int colors_4[] = { Color.YELLOW , Color.BLUE, Color.BLACK ,Color.GREEN};
        mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors_4);
        mGradientDrawable.setShape(GradientDrawable.RECTANGLE);//-默认
        AddTextView(ParentView,mGradientDrawable,text_str);
        //an array of length >= 8 containing 4 pairs of X and Y radius for each corner, specified in pixels
        float[] radii={
                Tools.dip2px(this,7),Tools.dip2px(this,7),
                0,0,
                Tools.dip2px(this,10),Tools.dip2px(this,10),
                0,0};
        mGradientDrawable.setCornerRadii(radii);
        mGradientDrawable.setCornerRadius(Tools.dip2px(this,20));
        mGradientDrawable.setStroke(Tools.dip2px(this,3),Color.RED);
        /*android:dashWidth="5dp"
        android:dashGap="3dp"
        其中android:dashWidth表示'-'这样一个横线的宽度，android:dashGap表示之间隔开的距离。*/
        mGradientDrawable.setStroke(Tools.dip2px(this,3),Color.RED,Tools.dip2px(this,10),Tools.dip2px(this,8));//??不一定能除尽吧啊
        //------------
        mGradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        mGradientDrawable.setGradientRadius(Tools.dip2px(this,360));
        mGradientDrawable.setGradientCenter(0.5f,0.5f);
        mGradientDrawable.setGradientCenter(1f,0.5f);
        //------------
    }

    public void AddGradientDrawable_d(LinearLayout ParentView,String text_str){
        GradientDrawable mGradientDrawable=new GradientDrawable();
        int colors_2[] = { Color.YELLOW , Color.BLUE };//最少是两种颜色
        int colors_3[] = { Color.YELLOW , Color.BLUE, Color.BLACK };//分别为开始颜色，中间夜色，结束颜色
        int colors_4[] = { Color.YELLOW , Color.BLUE, Color.BLACK ,Color.GREEN};
        mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors_4);
        mGradientDrawable.setShape(GradientDrawable.OVAL);//-默认
        AddTextView(ParentView,mGradientDrawable,text_str);
        //an array of length >= 8 containing 4 pairs of X and Y radius for each corner, specified in pixels
        float[] radii={
                Tools.dip2px(this,7),Tools.dip2px(this,7),
                0,0,
                Tools.dip2px(this,10),Tools.dip2px(this,10),
                0,0};
        mGradientDrawable.setCornerRadii(radii);
        mGradientDrawable.setCornerRadius(Tools.dip2px(this,20));
        mGradientDrawable.setStroke(Tools.dip2px(this,3),Color.RED);
        /*android:dashWidth="5dp"
        android:dashGap="3dp"
        其中android:dashWidth表示'-'这样一个横线的宽度，android:dashGap表示之间隔开的距离。*/
        mGradientDrawable.setStroke(Tools.dip2px(this,3),Color.RED,Tools.dip2px(this,10),Tools.dip2px(this,8));//??不一定能除尽吧啊
        //------------
        mGradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        //------------
    }

    public void AddGradientDrawable_e(LinearLayout ParentView,String text_str){
        GradientDrawable mGradientDrawable=new GradientDrawable();
        int colors_1[] = { Color.YELLOW  };
        int colors_2[] = { Color.YELLOW , Color.BLUE };
        int colors_3[] = { Color.YELLOW , Color.BLUE, Color.BLACK };//分别为开始颜色，中间夜色，结束颜色
        int colors_4[] = { Color.YELLOW , Color.BLUE, Color.BLACK ,Color.GREEN};
        mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors_4);
        mGradientDrawable.setShape(GradientDrawable.OVAL);//-默认
        AddTextView(ParentView,mGradientDrawable,text_str);
        //an array of length >= 8 containing 4 pairs of X and Y radius for each corner, specified in pixels
        float[] radii={
                Tools.dip2px(this,7),Tools.dip2px(this,7),
                0,0,
                Tools.dip2px(this,10),Tools.dip2px(this,10),
                0,0};
        mGradientDrawable.setCornerRadii(radii);
        mGradientDrawable.setCornerRadius(Tools.dip2px(this,20));
        mGradientDrawable.setStroke(Tools.dip2px(this,3),Color.RED);
        /*android:dashWidth="5dp"
        android:dashGap="3dp"
        其中android:dashWidth表示'-'这样一个横线的宽度，android:dashGap表示之间隔开的距离。*/
        mGradientDrawable.setStroke(Tools.dip2px(this,3),Color.RED,Tools.dip2px(this,10),Tools.dip2px(this,8));//??不一定能除尽吧啊
        //--z中心点右侧，水平向下开始
        mGradientDrawable.setGradientType(GradientDrawable.SWEEP_GRADIENT);
        mGradientDrawable.setGradientCenter(0.5f,0.5f);
        //mGradientDrawable.setGradientCenter(1f,0.5f);
    }

    public void AddGradientDrawable_f(LinearLayout ParentView,String text_str){
        GradientDrawable mGradientDrawable=new GradientDrawable();
        int colors_2[] = { Color.YELLOW , Color.BLUE };//最少是两种颜色
        int colors_3[] = { Color.YELLOW , Color.BLUE, Color.BLACK };//分别为开始颜色，中间夜色，结束颜色
        int colors_4[] = { Color.YELLOW , Color.BLUE, Color.BLACK ,Color.GREEN};
        mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors_4);
        mGradientDrawable.setShape(GradientDrawable.OVAL);//-默认
        AddTextView(ParentView,mGradientDrawable,text_str);
        //an array of length >= 8 containing 4 pairs of X and Y radius for each corner, specified in pixels
        float[] radii={
                Tools.dip2px(this,7),Tools.dip2px(this,7),
                0,0,
                Tools.dip2px(this,10),Tools.dip2px(this,10),
                0,0};
        mGradientDrawable.setCornerRadii(radii);
        mGradientDrawable.setCornerRadius(Tools.dip2px(this,20));
        mGradientDrawable.setStroke(Tools.dip2px(this,3),Color.RED);
        /*android:dashWidth="5dp"
        android:dashGap="3dp"
        其中android:dashWidth表示'-'这样一个横线的宽度，android:dashGap表示之间隔开的距离。*/
        mGradientDrawable.setStroke(Tools.dip2px(this,3),Color.RED,Tools.dip2px(this,10),Tools.dip2px(this,8));//??不一定能除尽吧啊
        //------------
        mGradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        mGradientDrawable.setGradientRadius(Tools.dip2px(this,360));
        mGradientDrawable.setGradientCenter(0.5f,0.5f);
        //--mGradientDrawable.setGradientCenter(1f,1f);
        //------------
    }

    public void AddGradientDrawable_g(LinearLayout ParentView,String text_str){
        GradientDrawable mGradientDrawable=new GradientDrawable();
        int colors_1[] = { Color.YELLOW  };
        int colors_2[] = { Color.YELLOW , Color.BLUE };
        int colors_3[] = { Color.YELLOW , Color.BLUE, Color.BLACK };//分别为开始颜色，中间夜色，结束颜色
        int colors_4[] = { Color.YELLOW , Color.BLUE, Color.BLACK ,Color.GREEN};
        mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors_4);
        AddTextView(ParentView,mGradientDrawable,text_str).setTextColor(Color.BLACK);
        mGradientDrawable.setShape(GradientDrawable.LINE);//
        //--mGradientDrawable.setCornerRadius(Tools.dip2px(this,20));
        mGradientDrawable.setStroke(Tools.dip2px(this,3),Color.RED);
        /*android:dashWidth="5dp"
        android:dashGap="3dp"
        其中android:dashWidth表示'-'这样一个横线的宽度，android:dashGap表示之间隔开的距离。*/
        mGradientDrawable.setStroke(Tools.dip2px(this,3),Color.RED,Tools.dip2px(this,10),Tools.dip2px(this,8));//??不一定能除尽吧啊
    }

    public void AddGradientDrawable_h(LinearLayout ParentView,String text_str){
        GradientDrawable mGradientDrawable=new GradientDrawable();
        int colors_2[] = { Color.YELLOW , Color.BLUE };
        int colors_3[] = { Color.YELLOW , Color.BLUE, Color.BLACK };//分别为开始颜色，中间夜色，结束颜色
        int colors_4[] = { Color.YELLOW , Color.BLUE, Color.BLACK ,Color.GREEN};
        mGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors_2);//至少需要两种颜色
        AddTextView(ParentView,mGradientDrawable,text_str).setTextColor(Color.BLACK);
        mGradientDrawable.setShape(GradientDrawable.RING);//
        //--mGradientDrawable.setCornerRadius(Tools.dip2px(this,20));
        mGradientDrawable.setStroke(Tools.dip2px(this,3),Color.RED);
        /*android:dashWidth="5dp"
        android:dashGap="3dp"
        其中android:dashWidth表示'-'这样一个横线的宽度，android:dashGap表示之间隔开的距离。*/
        mGradientDrawable.setStroke(Tools.dip2px(this,3),Color.RED,Tools.dip2px(this,10),Tools.dip2px(this,8));//??不一定能除尽吧啊
    }

    //===========================================
    public void addStateListDrawable(LinearLayout ParentView,String text_str){
        TextView mTextView=new TextView(this);mTextView.setSelected(true);
        mTextView.setText(text_str);
        mTextView.setGravity(Gravity.CENTER);
        //--mTextView.setBackgroundDrawable(mDrawable);
        mTextView.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, Tools.dip2px(this,40));
        Params_b.bottomMargin=Tools.dip2px(this,10);
        mTextView.setLayoutParams(Params_b);
        ParentView.addView(mTextView);
        mTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
        //-----------------------------------
        GradientDrawable mGradientDrawable_a=new GradientDrawable();
        int colors_2[] = { Color.YELLOW , Color.BLUE };
        mGradientDrawable_a = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors_2);
        mGradientDrawable_a.setCornerRadius(Tools.dip2px(this,20));
        mGradientDrawable_a.setStroke(Tools.dip2px(this,3),Color.RED);
        mGradientDrawable_a.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        //-----------
        GradientDrawable mGradientDrawable_b=new GradientDrawable();
        int colors_b[] = { Color.YELLOW , Color.BLUE };
        mGradientDrawable_b = new GradientDrawable(GradientDrawable.Orientation.BR_TL, colors_b);
        mGradientDrawable_b.setCornerRadius(Tools.dip2px(this,20));
        mGradientDrawable_b.setStroke(Tools.dip2px(this,3),Color.BLACK);
        mGradientDrawable_b.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        //-----------
        StateListDrawable bg = new StateListDrawable();
        bg.addState(new int[] { android.R.attr.state_pressed }, mGradientDrawable_b);
        // ViewConstant.EMPTY_STATE_SET--没有任何状态时显示的图片，我们给它设置我空集合
        bg.addState(new int[] {}, mGradientDrawable_a);
        mTextView.setBackgroundDrawable(bg);

        int[][] states={new int[] { android.R.attr.state_pressed },new int[] {}};
        int[] colors={Color.RED,Color.WHITE};
        // public ColorStateList(int[][] states, @ColorInt int[] colors) {
        ColorStateList mColorStateList=new ColorStateList(states,colors);
        mTextView.setTextColor(mColorStateList);
    }

    public void add_selector_head(LinearLayout ParentView,String text_str){
        TextView mTextView=new TextView(this);
        mTextView.setText(text_str);
        mTextView.setGravity(Gravity.CENTER);
        //--mTextView.setBackgroundDrawable(mDrawable);
        mTextView.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, Tools.dip2px(this,40));
        Params_b.bottomMargin=Tools.dip2px(this,10);
        mTextView.setLayoutParams(Params_b);
        ParentView.addView(mTextView);
        mTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });
        //--------
        mTextView.setBackgroundResource(R.drawable.bg_selector_head);
        mTextView.setTextColor(MyCompatView.getColorStateListFromXml(this.getResources(),R.drawable.bg_selector_head));//设置按钮文字颜色
    }

    public void add_selector_head_all(LinearLayout ParentView,String text_str,boolean state){
        TextView mTextView=new TextView(this);
        mTextView.setText(text_str);
        mTextView.setGravity(Gravity.CENTER);
        //--mTextView.setBackgroundDrawable(mDrawable);
        mTextView.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, Tools.dip2px(this,40));
        Params_b.bottomMargin=Tools.dip2px(this,10);
        mTextView.setLayoutParams(Params_b);
        ParentView.addView(mTextView);
        mTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });
        //--------
        mTextView.setBackgroundResource(R.drawable.bg_selector_total_all);
        if(!state){
            mTextView.setEnabled(false);
        }
    }

    public void add_selector_total(LinearLayout ParentView,String text_str){
        TextView mTextView=new TextView(this);
        mTextView.setText(text_str);
        mTextView.setGravity(Gravity.CENTER);
        //--mTextView.setBackgroundDrawable(mDrawable);
        mTextView.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, Tools.dip2px(this,40));
        Params_b.bottomMargin=Tools.dip2px(this,10);
        mTextView.setLayoutParams(Params_b);
        ParentView.addView(mTextView);
        mTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });
        //--------
        mTextView.setBackgroundResource(R.drawable.bg_selector_total);
    }

    public void add_selector_total_CodeFromXml(LinearLayout ParentView,String text_str){
        TextView mTextView=new TextView(this);
        mTextView.setText(text_str);
        mTextView.setGravity(Gravity.CENTER);
        //--mTextView.setBackgroundDrawable(mDrawable);
        mTextView.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, Tools.dip2px(this,40));
        Params_b.bottomMargin=Tools.dip2px(this,10);
        mTextView.setLayoutParams(Params_b);
        ParentView.addView(mTextView);
        mTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });
        //--------
        Resources res = ParentView.getResources();
        Drawable drawable= MyCompatView.getDrawableFromXml(ParentView.getResources(),R.drawable.bg_selector_total);
        mTextView.setBackgroundDrawable(drawable);
    }

    public  StateListDrawable newSelector(Context context, int idNormal, int idPressed, int idFocused, int idUnable) {
        StateListDrawable bg = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : context.getResources().getDrawable(idNormal);
        Drawable pressed = idPressed == -1 ? null : context.getResources().getDrawable(idPressed);
        Drawable focused = idFocused == -1 ? null : context.getResources().getDrawable(idFocused);
        Drawable unable = idUnable == -1 ? null : context.getResources().getDrawable(idUnable);
        // ViewConstant.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
        // ViewConstant.ENABLED_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, focused);
        // ViewConstant.ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled }, normal);
        // ViewConstant.FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_focused }, focused);
        // ViewConstant.WINDOW_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_window_focused }, unable);
        // ViewConstant.EMPTY_STATE_SET--没有任何状态时显示的图片，我们给它设置我空集合
        bg.addState(new int[] {}, normal);
        //没有任何状态时显示的图片，我们给它设置我空集合

        Shader mShader=new Shader();
        return bg;
    }

}
