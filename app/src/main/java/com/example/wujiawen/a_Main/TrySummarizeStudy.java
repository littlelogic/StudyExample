package com.example.wujiawen.a_Main;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.RotateDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by wujiawen on 2017/10/20.
 */

public class TrySummarizeStudy {

    public TrySummarizeStudy(int aa){//2017-10-27-10:48
        View listView=null;
        long down_time= SystemClock.uptimeMillis();
        listView.dispatchTouchEvent(
                MotionEvent.obtain(down_time, SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
        listView.dispatchTouchEvent(
                MotionEvent.obtain(down_time, SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0, 0, 0));
        //-----------------------------------------------------------
        ColorDrawable mColorDrawable=null;
        BitmapDrawable BitmapDrawable=null;
        InsetDrawable mInsetDrawable=null;
        ClipDrawable mClipDrawable=null;
        GradientDrawable mGradientDrawable=null;//--常用----
        StateListDrawable mStateListDrawable=null;
        //--------
        NinePatchDrawable mNinePatchDrawable=null;
        ScaleDrawable mScaleDrawable=null;
        RotateDrawable mRotateDrawable=null;
        AnimationDrawable mAnimationDrawable=null;
        LayerDrawable mLayerDrawable=null;
        LevelListDrawable mLevelListDrawable=null;
        TransitionDrawable mTransitionDrawable=null;
    }

    public static void backspace(EditText editText) {
        KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
        editText.dispatchKeyEvent(event);
    }

    public TrySummarizeStudy(Context mContext){
        //----------------------------------------------------
        Drawable mDrawable=null;
        //----------
        Bitmap mBitmap=null;
        BitmapDrawable drawable = new BitmapDrawable(mBitmap);
        Resources res=null;
        drawable = new BitmapDrawable(res,mBitmap);
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



        //----------










        //----------
        //----------
        //----------
        //----------
        //----------
        //----------
        //----------
        //----------
        //----------
        //----------
        //----------
        //----------
        //----------
        //----------
        //----------
        //----------
        //-----------





    }

    /** 设置Selector。 */
    public static StateListDrawable newSelector(Context context, int idNormal, int idPressed, int idFocused,int idUnable) {
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
        return bg;
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










}
