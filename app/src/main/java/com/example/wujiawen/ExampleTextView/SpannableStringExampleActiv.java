package com.example.wujiawen.ExampleTextView;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.LineBackgroundSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ReplacementSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.util.Property;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.badlogic.utils.ALog;
import com.badlogic.utils.MyCompatView;
import com.badlogic.utils.Tools;
import com.example.wujiawen.a_Main.R;
import com.example.wujiawen.ui.manage.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SpannableStringExampleActiv extends BaseActivity {
//public class SpannableStringExampleActiv extends AppCompatActivity {

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
        int mark_a;
        mark_a= Spanned.SPAN_EXCLUSIVE_EXCLUSIVE; //--- ?????????start???end???????????????        (a,b)
        mark_a=Spanned.SPAN_EXCLUSIVE_INCLUSIVE; //--- ????????????start????????????end??????????????? (a,b]
        mark_a=Spanned.SPAN_INCLUSIVE_EXCLUSIVE; //--- ??????start???????????????end???????????????   [a,b)
        mark_a=Spanned.SPAN_INCLUSIVE_INCLUSIVE;//--- ??????start???end???????????????            [a,b]
        //----------------
        addClickableSpan(mLinearLayout,"1-????????????-");
        addClickableSpan_down(mLinearLayout,"2-????????????-??????-");
        addUrlSpan(mLinearLayout,"3-?????????-");
        addHtmlSpanned(mLinearLayout,"4-?????????-Html-");
        addBackColorSpan(mLinearLayout,"5-???????????????-");
        addForeColorSpan(mLinearLayout,"6-????????????-");
        addFontSpan(mLinearLayout,"7-????????????-");
        addStyleSpan(mLinearLayout,"8-???????????????-");
        addStrikeSpan(mLinearLayout,"9-?????????-");
        addUnderLineSpan(mLinearLayout,"10-?????????-");
        addImageSpan(mLinearLayout,"11-??????-");
        addImageSpan_2(mLinearLayout,"12-??????-????????????-??????-");
        addImageSpan_3(mLinearLayout,"13-??????-????????????-????????????????????????-");
        addTypefaceSpan(mLinearLayout,"14-Typeface-");
        addConbine(mLinearLayout,"15-??????-");
        addMaskFilterSpan_BlurMaskFilter(mLinearLayout,"16-????????????-");
        //todo ?????????????????? addMaskFilterSpan_EmbossMaskFilter(mLinearLayout,"17-????????????-");
        addSuperscriptSpan(mLinearLayout,"18-??????-");
        addSubscriptSpan(mLinearLayout,"19-??????-");
        addScaleXSpan(mLinearLayout,"20-X??????-");
        addRelativeSizeSpan(mLinearLayout,"21-????????????????????????-");
        addQuoteSpan(mLinearLayout,"22-????????????-");
        addAbsoluteSizeSpan(mLinearLayout,"23-??????????????????-");
        addMReplacementSpan(mLinearLayout,"24-??????????????????-");
        addMLineBackgroundSpan(mLinearLayout,"25-????????????-");
        addMutableForegroundColorSpan(mLinearLayout,"26-??????????????????-");
        addMutableForegroundColorSpan_more(mLinearLayout,"27-??????????????????-");
        addDrawTextAtCenrte(mLinearLayout,"28-????????????????????????-");

//        ReplacementSpan kk;
//        Spanned kk;
//        ValueAnimator mValueAnimator;
//        ObjectAnimator sss;
    }
    //========================================================================================

    /**
     * ????????????
     */
    private void addClickableSpan(LinearLayout ParentView,String text_str){
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        SpannableString spanString = new SpannableString("?????????????????????");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(SpannableStringExampleActiv.this, "click", Toast.LENGTH_SHORT).show();
                ALog.e("wjw01", "click");
            }

            @Override
            public void updateDrawState(TextPaint mTextPaint) {
                ALog.e("wjw01", "--SpannableStringExampleActiv--addClickableSpan---updateDrawState--mTextPaint-->>"+mTextPaint);
                mTextPaint.setColor(Color.BLUE);
                mTextPaint.setUnderlineText(false);    //???????????????????????????
            }
        };
        //clickableSpan.updateDrawState(null);
        //clickableSpan.getUnderlying();
        spanString.setSpan(clickableSpan, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);
        //??????????????????ClickableSpan???????????????????????????????????????????????????????????????????????????MovementMethod?????????
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void addClickableSpan_down(LinearLayout ParentView,String text_str){
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        SpannableString spanString = new SpannableString("?????????????????????");
        MClickableSpan clickableSpan = new MClickableSpan();
        spanString.setSpan(clickableSpan, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);
        //??????????????????ClickableSpan???????????????????????????????????????????????????????????????????????????MovementMethod?????????
        //--mTextView.setMovementMethod(TouchLinkMovementMethod.getInstance());
        mTextView.setMovementMethod(new TouchLinkMovementMethod());
    }

    public class MClickableSpan extends ClickableSpan {

        public int color_here=Color.BLUE;

        boolean  Down_mark=false;
        boolean  AbleClick_mark=false;

        public void setDown(boolean Mark){
            Down_mark=Mark;
        }

        public void setAbleClick(boolean Mark){
            AbleClick_mark=Mark;
        }



        @Override
        public void onClick(View widget) {
            if(AbleClick_mark){
                Toast.makeText(SpannableStringExampleActiv.this, "click---here", Toast.LENGTH_SHORT).show();
                ALog.e("Easy", "click");
            }
        }

        @Override
        public void updateDrawState(TextPaint mTextPaint) {
                mTextPaint.setColor(color_here);
//                mTextPaint.setUnderlineText(false);    //???????????????????????????
        }

    }

    public class TouchLinkMovementMethod extends LinkMovementMethod {

        Rect OneBound = new Rect();
        Rect TwoBound = new Rect();
        boolean OneBound_mark=false;
        boolean ClickableSpan_Down=false;
        MClickableSpan mClickableSpan;

        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                ALog.e("wjw01", "--SpannableStringExampleActiv--TouchLinkMovementMethod---onTouchEvent--ACTION_DOWN-->>");
                int x = (int) event.getX();
                int y = (int) event.getY();
                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();
                x += widget.getScrollX();
                y += widget.getScrollY();
                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);
                //-------------
                ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);
                if (link.length != 0) {
                    if(link[0] instanceof  MClickableSpan){
                        ALog.e("wjw01", "--SpannableStringExampleActiv--TouchLinkMovementMethod---onTouchEvent--ACTION_DOWN--Yes-->>");
                        mClickableSpan=(MClickableSpan)link[0];
                    }else{
                        ClickableSpan_Down=false;
                        return super.onTouchEvent(widget, buffer, event);
                    }
                    mClickableSpan.setAbleClick(true);
                    //--????????????
                    mClickableSpan.color_here=Color.RED;
                    widget.invalidate();
                    //--Selection.setSelection(buffer,buffer.getSpanStart(link[0]),buffer.getSpanEnd(link[0]));
                    int Start= buffer.getSpanStart(link[0]);
                    int End= buffer.getSpanEnd(link[0]);
                    Rect startBound = new Rect();
                    layout.getLineBounds(layout.getLineForOffset(Start), startBound);
                    Rect endBound = new Rect();
                    layout.getLineBounds(layout.getLineForOffset(End), endBound);
                    ClickableSpan_Down=true;
                    // ????????????y??????
                    int yAxisTop = startBound.top;
                    // ????????????y??????
                    int yAxisBottom = endBound.bottom;
                    // ????????????x??????
                    float xAxisLeft = layout.getPrimaryHorizontal(Start) ;
                    // ????????????x??????
                    float xAxisRight = layout.getSecondaryHorizontal(End);
                    // ?????????????????????
                    int lineHeight = startBound.bottom - startBound.top;
                    // ?????????????????????
                    int currentLineHeight = endBound.bottom - startBound.top;
                    if (currentLineHeight <= lineHeight) { // ???????????????
                        OneBound_mark=true;
                        OneBound.set((int)xAxisLeft,startBound.top,(int)xAxisRight,endBound.bottom);
                    } else { // ????????????
                        OneBound_mark=false;
                        OneBound.set((int)xAxisLeft,startBound.top,startBound.right,startBound.bottom);
                        TwoBound.set(endBound.left,endBound.top,(int)xAxisRight,endBound.bottom);
                    }
                    return true;
                } else {
                    ALog.e("wjw01", "--SpannableStringExampleActiv--TouchLinkMovementMethod---onTouchEvent--ACTION_DOWN--no-->>");
                    ClickableSpan_Down=false;
                    //--Selection.removeSelection(buffer);
                }
            } else if (action == MotionEvent.ACTION_MOVE) {
                if(ClickableSpan_Down){
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    x -= widget.getTotalPaddingLeft();
                    y -= widget.getTotalPaddingTop();
                    x += widget.getScrollX();
                    y += widget.getScrollY();
                    if(OneBound_mark){
                        ALog.e("wjw01", "--SpannableStringExampleActiv--TouchLinkMovementMethod---onTouchEvent--ACTION_MOVE--OneBound.left-->>"+OneBound.left);
                        ALog.e("wjw01", "--SpannableStringExampleActiv--TouchLinkMovementMethod---onTouchEvent--ACTION_MOVE--OneBound.top-->>"+OneBound.top);
                        ALog.e("wjw01", "--SpannableStringExampleActiv--TouchLinkMovementMethod---onTouchEvent--ACTION_MOVE--OneBound.right-->>"+OneBound.right);
                        ALog.e("wjw01", "--SpannableStringExampleActiv--TouchLinkMovementMethod---onTouchEvent--ACTION_MOVE--OneBound.bottom-->>"+OneBound.bottom);
                        ALog.e("wjw01", "--SpannableStringExampleActiv--TouchLinkMovementMethod---onTouchEvent--ACTION_MOVE--x-->>"+x);
                        ALog.e("wjw01", "--SpannableStringExampleActiv--TouchLinkMovementMethod---onTouchEvent--ACTION_MOVE--y-->>"+y);
                        if(!OneBound.contains(x,y)){
                            ClickableSpan_Down=false;
                            mClickableSpan.setAbleClick(false);
                            //--???????????????
                            mClickableSpan.color_here=Color.BLUE;
                            widget.invalidate();
                            ALog.e("wjw01", "--SpannableStringExampleActiv--TouchLinkMovementMethod---onTouchEvent--ACTION_MOVE--??????-->>");
                        }
                    }else{
                        if(OneBound.contains(x,y)||TwoBound.contains(x,y)){
                        }else{
                            mClickableSpan.setAbleClick(false);
                            ClickableSpan_Down=false;
                            //--???????????????
                            mClickableSpan.color_here=Color.BLUE;
                            widget.invalidate();
                        }
                    }
                }
            } else if (action == MotionEvent.ACTION_UP) {
                if(ClickableSpan_Down){
                    mClickableSpan.color_here=Color.BLUE;
                    widget.invalidate();
                    //--mClickableSpan.onClick(widget);
                    ALog.e("wjw01", "--SpannableStringExampleActiv--TouchLinkMovementMethod---onTouchEvent--ACTION_UP--Yes-->>");
                }
                /*mClickableSpan.color_here=Color.BLUE;
                widget.invalidate();*/
            }else if (action == MotionEvent.ACTION_CANCEL) {
                if(ClickableSpan_Down){
                    ClickableSpan_Down=false;
                    mClickableSpan.setAbleClick(false);
                    mClickableSpan.color_here=Color.BLUE;
                    widget.invalidate();
                }
            }
            //--return true;
            return super.onTouchEvent(widget, buffer, event);
        }
    }

    //========================================================================================
    /**
     * ?????????
     */
    private void addUrlSpan(LinearLayout ParentView,String text_str){
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        SpannableString spanString = new SpannableString("?????????????????????");
        URLSpan span = new URLSpan("tel:0123456789");
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);
        /**
         * URILSpan???????????????onClick???????????????URL???????????????????????????  ???????????????????????????ClickableSpan??????????????????????????????
            ??????????????????URLSpan???????????????????????????MovementMethod?????????
         */
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void addHtmlSpanned(LinearLayout ParentView,String text_str){
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        Spanned mSpanned=Html.fromHtml(
                "<b>text3:</b>  Text with a " +
                        "<a href=\"http://aichixihongshi.iteye.com/blog/1207503\">link</a> " +
                        "created in the Java source code using HTML.");
        //----------
        mTextView.setText(mSpanned);
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * ??????????????????
     */
    private void addBackColorSpan(LinearLayout ParentView,String text_str){
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        SpannableString spanString = new SpannableString("?????????????????????");
        BackgroundColorSpan span = new BackgroundColorSpan(Color.YELLOW);
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);
    }

    /**
     * ????????????
     */
    private void addForeColorSpan(LinearLayout ParentView,String text_str){
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        SpannableString spanString = new SpannableString("?????????????????????");
        ForegroundColorSpan span = new ForegroundColorSpan(Color.argb(255,255,0,0));
        spanString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan span_b = new ForegroundColorSpan(Color.argb(0,0,0,0));
        spanString.setSpan(span_b, 1, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);


//        SpannableStringBuilder builder = new SpannableStringBuilder(tvInvitationTitle.getText().toString());
//        ForegroundColorSpan blackSpan = new ForegroundColorSpan(Color.parseColor("#333333"));
//        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.parseColor("#fb3b3b"));
//        builder.setSpan(blackSpan, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        builder.setSpan(redSpan, 3, tvInvitationTitle.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }


    /**
     * ????????????
     */
    private void addFontSpan(LinearLayout ParentView,String text_str){
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        SpannableString spanString = new SpannableString("36?????????????????????");
        AbsoluteSizeSpan span = new AbsoluteSizeSpan(36);
        spanString.setSpan(span, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);
    }


    /**
     * ???????????????
     */
    private void addStyleSpan(LinearLayout ParentView,String text_str){
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        SpannableString spanString = new SpannableString("BIBI????????????");
        StyleSpan span = new StyleSpan(Typeface.BOLD_ITALIC);
        spanString.setSpan(span, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);
    }


    /**
     * ?????????
     */
    private void addStrikeSpan(LinearLayout ParentView,String text_str) {
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        SpannableString spanString = new SpannableString("?????????????????????");
        StrikethroughSpan span = new StrikethroughSpan();
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);
    }


    /**
     * ?????????
     */
    private void addUnderLineSpan(LinearLayout ParentView,String text_str) {
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        SpannableString spanString = new SpannableString("?????????????????????");
        UnderlineSpan span = new UnderlineSpan();
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);
    }

    /**
     * ??????
     */
    private void addImageSpan(LinearLayout ParentView,String text_str) {
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        SpannableString spanString = new SpannableString(" ????????????");
        Drawable mDrawable= getResources().getDrawable(R.drawable.icon);
        //--Drawable mDrawable2= getResources().getDrawableForDensity(R.drawable.icon,(int)getResources().getDisplayMetrics().density);
        Drawable mDrawable2= getResources().getDrawableForDensity(R.drawable.icon,(int)getResources().getDisplayMetrics().densityDpi);

        ALog.i("wjw02","--SpannableStringExampleActiv--addImageSpan--mDrawable.getIntrinsicWidth()-->>"+mDrawable.getIntrinsicWidth());
        ALog.i("wjw02","--SpannableStringExampleActiv--addImageSpan--mDrawable.getIntrinsicHeight()-->>"+mDrawable.getIntrinsicHeight());
        ALog.i("wjw02","--SpannableStringExampleActiv--addImageSpan--mDrawable2.getIntrinsicWidth()-->>"+mDrawable2.getIntrinsicWidth());
        ALog.i("wjw02","--SpannableStringExampleActiv--addImageSpan--mDrawable2.getIntrinsicHeight()-->>"+mDrawable2.getIntrinsicHeight());
        mTextView.getLayoutParams().height=((BitmapDrawable)mDrawable).getBitmap().getHeight()+(int)Tools.dip2px(this,80);
        mTextView.getLayoutParams().width= ViewGroup.LayoutParams.MATCH_PARENT;
        mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(mDrawable, ImageSpan.ALIGN_BASELINE);
        spanString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mDrawable2.setBounds(0, 0, mDrawable2.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        ImageSpan span_b = new ImageSpan(mDrawable2, ImageSpan.ALIGN_BASELINE);
        spanString.setSpan(span_b, 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);
    }

    private void addImageSpan_2(LinearLayout ParentView,String text_str) {
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        SpannableString spanString = new SpannableString("??????????????????");
        Bitmap Bitmap_b=Tools.getBitmapOriginalBgResId(getResources(),R.drawable.icon);
        BitmapDrawable mBitmapDrawable_a= new BitmapDrawable(Bitmap_b);
        BitmapDrawable mBitmapDrawable_b= new BitmapDrawable(getResources(),Bitmap_b);
        ALog.i("wjw02","--SpannableStringExampleActiv--addImageSpan--Bitmap_b.getWidth()-->>"+Bitmap_b.getWidth());
        ALog.i("wjw02","--SpannableStringExampleActiv--addImageSpan--Bitmap_b.getWidth()-->>"+Bitmap_b.getHeight());
        ALog.i("wjw02","--SpannableStringExampleActiv--addImageSpan--mBitmapDrawable_a.getIntrinsicWidth()-->>"+mBitmapDrawable_a.getIntrinsicWidth());
        ALog.i("wjw02","--SpannableStringExampleActiv--addImageSpan--mBitmapDrawable_a.getIntrinsicHeight()-->>"+mBitmapDrawable_a.getIntrinsicHeight());
        ALog.i("wjw02","--SpannableStringExampleActiv--addImageSpan--mBitmapDrawable_b.getIntrinsicWidth()-->>"+mBitmapDrawable_b.getIntrinsicWidth());
        ALog.i("wjw02","--SpannableStringExampleActiv--addImageSpan--mBitmapDrawable_b.getIntrinsicHeight()-->>"+mBitmapDrawable_b.getIntrinsicHeight());
        ALog.i("wjw02","--SpannableStringExampleActiv--addImageSpan--Bitmap_b.getWidth()-->>"+mBitmapDrawable_b.getBitmap().getWidth());
        ALog.i("wjw02","--SpannableStringExampleActiv--addImageSpan--Bitmap_b.getWidth()-->>"+mBitmapDrawable_b.getBitmap().getWidth());
        mTextView.getLayoutParams().height=Bitmap_b.getHeight()+(int)Tools.dip2px(this,80);
        mTextView.getLayoutParams().width= ViewGroup.LayoutParams.MATCH_PARENT;
        //----------------
        ImageSpan span = new ImageSpan(this,Bitmap_b, ImageSpan.ALIGN_BASELINE);
        spanString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //----
        mBitmapDrawable_a.setBounds(0, 0, mBitmapDrawable_a.getIntrinsicWidth(), mBitmapDrawable_a.getIntrinsicHeight());
        ImageSpan span_a = new ImageSpan(mBitmapDrawable_a, ImageSpan.ALIGN_BASELINE);
        spanString.setSpan(span_a, 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //----
        mBitmapDrawable_b.setBounds(0, 0, mBitmapDrawable_b.getIntrinsicWidth(), mBitmapDrawable_b.getIntrinsicHeight());
        ImageSpan span_b = new ImageSpan(mBitmapDrawable_b, ImageSpan.ALIGN_BASELINE);
        spanString.setSpan(span_b, 4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);
    }

    private void addImageSpan_3(LinearLayout ParentView,String text_str) {
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        SpannableString spanString = new SpannableString("?????????\n?????????????????????");
        Drawable mDrawable=Tools.getDrawableOriginalBgResId(getResources(),R.drawable.icon);
        ALog.i("wjw02","--SpannableStringExampleActiv--addImageSpan_3--mDrawable.getIntrinsicWidth()-->>"+mDrawable.getIntrinsicWidth());
        ALog.i("wjw02","--SpannableStringExampleActiv--addImageSpan_3--mDrawable.getIntrinsicHeight()-->>"+mDrawable.getIntrinsicHeight());
        mTextView.getLayoutParams().height=mDrawable.getIntrinsicWidth()+(int)Tools.dip2px(this,80);
        mTextView.getLayoutParams().width= ViewGroup.LayoutParams.MATCH_PARENT;
        //----------------
        mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        VerticalCentreImageSpan span = new VerticalCentreImageSpan(mDrawable);
        spanString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //----
        Drawable mDrawable_b=Tools.getDrawableOriginalBgResId(getResources(),R.drawable.icon);
        float height_b=mTextView.getTextSize();
        float per=mDrawable.getIntrinsicHeight()/mTextView.getTextSize();
        int width_b=(int)(mDrawable.getIntrinsicWidth()/per);
        mDrawable_b.setBounds(0, 0, width_b,(int)height_b);
        VerticalCentreImageSpan span_b = new VerticalCentreImageSpan(mDrawable_b);
        spanString.setSpan(span_b, 5, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //----
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(SpannableStringExampleActiv.this, "addImageSpan_3--click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint mTextPaint) {
                ALog.e("wjw01", "--SpannableStringExampleActiv--addClickableSpan---updateDrawState--mTextPaint-->>"+mTextPaint);
//                mTextPaint.setColor(Color.BLUE);
//                mTextPaint.setUnderlineText(false);    //???????????????????????????
            }
        };
        clickableSpan.updateDrawState(null);
        clickableSpan.getUnderlying();
        spanString.setSpan(clickableSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());
        //--------------
        mTextView.setText(spanString);
        mTextView.setLineSpacing(Tools.dip2px(this,5),1);
    }

    /**
     * TypefaceSpan?????????????????????????????????????????????????????????????????????SANS_SERIF???MONOSPACE???SERIF??????????????????
     * @param ParentView
     * @param text_str
     */
    private void addTypefaceSpan(LinearLayout ParentView,String text_str) {
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        SpannableString spanString = new SpannableString(" ????????????");
        Parcel p = Parcel.obtain();
        p.writeString("MONOSPACE");
        p.setDataPosition(0);
        TypefaceSpan span = new TypefaceSpan(p);
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);
    }


    /**
     * ??????
     */
    private void addConbine(LinearLayout ParentView,String text_str) {
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        SpannableStringBuilder spannable = new SpannableStringBuilder("???????????????");
        CharacterStyle span1 = new BackgroundColorSpan(Color.BLUE);
        CharacterStyle span2 = new ForegroundColorSpan(Color.RED);
        spannable.setSpan(span1, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(span2, 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spannable);
    }

    /**
     *   ????????????????????????
     *   MaskFilterSpan?????????????????????????????????????????????
         MaskFilterSpan(MaskFilter filter)?????????filter??????????????????
         ?????????
         ???android????????????MaskFilter????????????????????????BlurMaskFilter???EmbossMaskFilter???????????????????????????????????????????????????
     * @param ParentView
     * @param text_str
     */
    private void addMaskFilterSpan_BlurMaskFilter(LinearLayout ParentView,String text_str) {
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        SpannableString spanString = new SpannableString("???????????????");
        MaskFilterSpan span = new MaskFilterSpan(new BlurMaskFilter(Tools.dip2px(this,5), BlurMaskFilter.Blur.OUTER));
        spanString.setSpan(span, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);
        MyCompatView.cannel_HARDWARE_ACCELERATED(mTextView);
    }

    private void addMaskFilterSpan_EmbossMaskFilter(LinearLayout ParentView,String text_str) {
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        MyCompatView.cannel_HARDWARE_ACCELERATED(mTextView);
        //----------
        SpannableString spanString = new SpannableString("???????????????");
        MaskFilterSpan span = new MaskFilterSpan(new EmbossMaskFilter(new float[]{3, 3, 9}, 3.0f, 12, 16));
        spanString.setSpan(span, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);

    }


    private void addSuperscriptSpan(LinearLayout ParentView,String text_str) {
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        SpannableString spanString = new SpannableString("23+32=72");
        SuperscriptSpan span =new SuperscriptSpan();
        spanString.setSpan(span, 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        SuperscriptSpan span_b =new SuperscriptSpan();
        spanString.setSpan(span_b, 4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);
    }

    private void addSubscriptSpan(LinearLayout ParentView,String text_str) {
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        SpannableString spanString = new SpannableString("H2O");
        SubscriptSpan span =new SubscriptSpan();
        spanString.setSpan(span, 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);
    }

    private void addScaleXSpan(LinearLayout ParentView,String text_str) {
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        SpannableString spanString = new SpannableString("????????????");
        ScaleXSpan span =new ScaleXSpan(3);
        spanString.setSpan(span, 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);
    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????
     * @param ParentView
     * @param text_str
     */
    private void addRelativeSizeSpan(LinearLayout ParentView,String text_str) {
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        SpannableString spanString = new SpannableString("????????????");
        RelativeSizeSpan span =new RelativeSizeSpan(3);
        spanString.setSpan(span, 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);
    }

    private void addQuoteSpan(LinearLayout ParentView,String text_str) {
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        //??????????????????????????????????????????????????????????????????
        SpannableString spanString = new SpannableString("????????????????????????????????????????????????????????????????????????????????????????????????");
        QuoteSpan span =new QuoteSpan(Color.RED);
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);
    }

    /**
     *  ????????????????????????????????????
         AbsoluteSizeSpan(int size, boolean dip)
         size??????????????????px???
         dip???true???size????????????dip???false???px???
     */
    private void addAbsoluteSizeSpan(LinearLayout ParentView,String text_str) {
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        //??????????????????????????????????????????????????????????????????
        SpannableString spanString = new SpannableString("????????????????????????");
        AbsoluteSizeSpan span =new AbsoluteSizeSpan(7,true);
        spanString.setSpan(span, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);

    }

    private void addMReplacementSpan(LinearLayout ParentView,String text_str) {
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        String content_str="????????????????????????????????????????????????";

        SpannableString spanString = new SpannableString(content_str);
        MReplacementSpan span =new MReplacementSpan();
        //--spanString.setSpan(span, 2, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanString.setSpan(span, content_str.length()-5, content_str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);

    }

    private void addMLineBackgroundSpan(LinearLayout ParentView,String text_str) {
        TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        String content_str="?????????????????????";
        SpannableString spanString = new SpannableString(content_str);
        MLineBackgroundSpan span =new MLineBackgroundSpan();
        spanString.setSpan(span, content_str.length()-5, content_str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);
    }

    //===================================

    private static final Property<MutableForegroundColorSpan, Integer> MUTABLE_FOREGROUND_COLOR_SPAN_FC_PROPERTY =
            new Property<MutableForegroundColorSpan, Integer>(Integer.class, "MUTABLE_FOREGROUND_COLOR_SPAN_FC_PROPERTY") {
                @Override
                public void set(MutableForegroundColorSpan span, Integer value) {
                    span.setForegroundColor(value);
                }
                @Override
                public Integer get(MutableForegroundColorSpan span) {
                    return span.getForegroundColor();
                }
            };

    private void addMutableForegroundColorSpan(LinearLayout ParentView,String text_str) {
        final TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        //----------
        final SpannableString spanString = new SpannableString("????????????????????????");
        MutableForegroundColorSpan span = new MutableForegroundColorSpan(255, Color.BLACK);
        spanString.setSpan(span, 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setText(spanString);
        //????????????????????????????????????????????????????????????
        span.setAlpha(100);
        span.setForegroundColor(Color.RED);
        //??????????????????????????????
        mTextView.setText(spanString);
        //???????????????????????????????????????
        //-----------------
        mTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MutableForegroundColorSpan span_b = new MutableForegroundColorSpan(255, Color.BLACK);
                spanString.setSpan(span_b, 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(span_b, MUTABLE_FOREGROUND_COLOR_SPAN_FC_PROPERTY, Color.BLACK, Color.RED);
                objectAnimator.setEvaluator(new ArgbEvaluator());
                objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        //refresh
                        mTextView.setText(spanString);
                    }
                });
                objectAnimator.start();
            }
        });


    }

    //===================================

    private static final Property<FireworksSpanGroup, Float> FIREWORKS_GROUP_PROGRESS_PROPERTY =
            new Property<FireworksSpanGroup, Float>(Float.class, "FIREWORKS_GROUP_PROGRESS_PROPERTY") {
                @Override
                public void set(FireworksSpanGroup spanGroup, Float value) {
                    spanGroup.setAlpha(value);
                }
                @Override
                public Float get(FireworksSpanGroup spanGroup) {
                    return spanGroup.getAlpha();
                }
            };

    private void addMutableForegroundColorSpan_more(LinearLayout ParentView,String text_str) {
        final TextView mTextView=getTextView(text_str);
        ParentView.addView(mTextView);
        final String text_content="????????????????????????";
        final SpannableString spanString = new SpannableString(text_content);
        mTextView.setText(spanString);
        //-----------------
        mTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ArrayList<Integer> IndexList= new ArrayList<Integer>();
                for(int i=0;i<text_content.length();i++){
                    IndexList.add(i);
                }

                Random ran =new Random(System.currentTimeMillis());
                final FireworksSpanGroup spanGroup = new FireworksSpanGroup(0);
                //?????????????????????spans???grop
                for(int i=0;i<text_content.length();i++){
                    int index_a=ran.nextInt(IndexList.size());
                    int index_b=IndexList.remove(index_a);
                    MutableForegroundColorSpan span = new MutableForegroundColorSpan(255, Color.BLUE);
                    spanString.setSpan(span, index_b, index_b+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spanGroup.addSpan(span);
                }
                mTextView.setText(spanString);

                spanGroup.init();
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(spanGroup, FIREWORKS_GROUP_PROGRESS_PROPERTY, 0.0f, 1.0f);
                objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation){
                        mTextView.setText(spanString);
                    }
                });
                objectAnimator.start();

            }
        });



    }

    //========================================================================================
    private void addDrawTextAtCenrte(LinearLayout ParentView,String text_str) {
        final int height=(int)Tools.dip2px(this,70);
        final TextView mTextView=new MTextView(this,text_str){
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);

                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setStrokeWidth(Tools.dip2px(SpannableStringExampleActiv.this,3));
                paint.setTextSize(Tools.dip2px(SpannableStringExampleActiv.this,30));
                String testString = "?????????ijkjlQKA:1234";
                paint.setColor(Color.RED);
                Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
                //--?????????????????????
                int baseline =height/ 2- (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.top;
                // ????????????????????????????????????drawText??????????????????targetRect.centerX()
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(testString, this.getWidth()/2, baseline, paint);

            }
        };
        LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);
        Params_b.bottomMargin=Tools.dip2px(this,10);
        mTextView.setLayoutParams(Params_b);
        mTextView.setBackgroundColor(Color.CYAN);
        mTextView.setGravity(Gravity.CENTER);
        ParentView.addView(mTextView);
    }











    //========================================================================================
    public TextView getTextView(String text_str){
        final MTextView mView_aa=new MTextView(this,text_str);
        LinearLayout.LayoutParams Params_b=new LinearLayout.LayoutParams((int)Tools.dip2px(this,200),(int)Tools.dip2px(this,80));
        Params_b.bottomMargin=Tools.dip2px(this,10);
        mView_aa.setLayoutParams(Params_b);
        mView_aa.setBackgroundColor(Color.argb(50,0,0,0));
        mView_aa.setGravity(Gravity.CENTER);
        mView_aa.setLinkTextColor(Color.argb(0,0,0,0));
        mView_aa.setTextColor(Color.argb(255,0,0,0));
//        mView_aa.setLinkTextColor(Color.argb(255,0,0,0));
       /* mView_aa.setOnClickListener(new ViewConstant.OnClickListener(){
            @Override
            public void onClick(ViewConstant v) {
                mView_aa.invalidate();
            }
        });*/
        return mView_aa;
    }


    //========================================================================================

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
    class  MTextView extends TextView{

        String test_str="daesfaesfaes";
        Paint mPaint=new Paint();
        float y;
        public MTextView(Context context,String test_str_) {
            super(context);
            mPaint.setColor(Color.BLUE);
            test_str=test_str_;
            //--mPaint.setTextSize(Tools.dip2px(context,10));
            mPaint.setTextSize(this.getTextSize()/4*3);
            y=mPaint.getTextSize();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawText(test_str,0,y,mPaint);
        }
    }

    class  MReplacementSpan extends ReplacementSpan{
        int mWidth;
        Paint mPaint=new Paint();

        public MReplacementSpan() {
            mPaint.setColor(Color.RED);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(Tools.dip2px(SpannableStringExampleActiv.this,1));
        }

        @Override
        public int getSize(@NonNull Paint paint, CharSequence text, @IntRange(from = 0) int start, @IntRange(from = 0) int end, @Nullable Paint.FontMetricsInt fm) {
            mWidth = (int) paint.measureText(text, start, end);
            return mWidth;
        }

        @Override
        public void draw(@NonNull Canvas canvas, CharSequence text, @IntRange(from = 0) int start, @IntRange(from = 0) int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
            canvas.drawRect(x, top, x + mWidth, bottom, mPaint);
            canvas.drawText(text.subSequence(start,end).toString(),x,y,paint);
        }
    }

    class MutableForegroundColorSpan extends ForegroundColorSpan{

        public MutableForegroundColorSpan(@ColorInt int color) {
            super(color);
        }

        private int mAlpha = 255;
        private int mForegroundColor;
        public MutableForegroundColorSpan(int alpha, int color)
        {
            super(color);
            mAlpha = alpha;
            mForegroundColor = color;
        }
        public MutableForegroundColorSpan(Parcel src)
        {
            super(src);
            mForegroundColor = src.readInt();
            mAlpha = src.readInt();
        }
        public void writeToParcel(Parcel dest, int flags)
        {
            super.writeToParcel(dest, flags);
            dest.writeInt(mForegroundColor);
            dest.writeFloat(mAlpha);
        }
        @Override
        public void updateDrawState(TextPaint ds)
        {
            ds.setColor(getForegroundColor());
        }
        /**
         * @param alpha from 0 to 255
         */
        public void setAlpha(int alpha)
        {
            mAlpha = alpha;
        }
        public void setForegroundColor(int foregroundColor)
        {
            mForegroundColor = foregroundColor;
        }
        public float getAlpha()
        {
            return mAlpha;
        }
        @Override
        public int getForegroundColor()
        {
            return Color.argb(mAlpha, Color.red(mForegroundColor), Color.green(mForegroundColor), Color.blue(mForegroundColor));
        }
    }

    private static final class FireworksSpanGroup {
        private final float mAlpha;
        private final ArrayList<MutableForegroundColorSpan> mSpans;
        private FireworksSpanGroup(float alpha) {
            mAlpha = alpha;
            mSpans = new ArrayList<MutableForegroundColorSpan>();
        }
        public void addSpan(MutableForegroundColorSpan span) {
            span.setAlpha((int) (mAlpha * 255));
            mSpans.add(span);
        }
        public void init() {
            Collections.shuffle(mSpans);
        }
        public void setAlpha(float alpha) {
            int size = mSpans.size();
            float total = 1.0f * size * alpha;
            for(int index = 0 ; index < size; index++) {
                MutableForegroundColorSpan span = mSpans.get(index);
                if(total >= 1.0f) {
                    span.setAlpha(255);
                    total -= 1.0f;
                } else {
                    span.setAlpha((int) (total * 255));
                    total = 0.0f;
                }
            }
        }
        public float getAlpha() { return mAlpha; }
    }


    class  MLineBackgroundSpan implements LineBackgroundSpan{
        Paint mPaint=new Paint();

        public MLineBackgroundSpan() {
            mPaint.setColor(Color.RED);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(Tools.dip2px(SpannableStringExampleActiv.this,1));
        }

        @Override
        public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }






}
