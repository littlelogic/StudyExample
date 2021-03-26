package com.share.badlogic;//com.share.blankexample.

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


//public class MainActivity extends AppCompatActivity {
public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        dealCreate_a(savedInstanceState);
//        dealCreate_b(savedInstanceState);
        dealCreate_d(savedInstanceState);

        MediaMetadataRetriever mMediaMetadataRetriever ;
        android.media.MediaMetadataRetriever mmr = new android.media.MediaMetadataRetriever();
        try {
            //--mmr.setDataSource(mFD, mOffset, mLength);
            String duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);
            String width = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            String height = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            String ROTATION = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);

        } catch (Exception ex) {
            Log.e("wjw02", "MediaMetadataRetriever exception " + ex);
        } finally {
            mmr.release();
        }
    }

    protected void dealCreate_d(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
    }

    protected void dealCreate_a(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);


        TextView mTextView=(TextView)findViewById(R.id.mTextView);
        mTextView.setText("你们好");

//
//        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,Tools.dip2px(this,22));
//
//        Log.i("wjw02","---------------------Tools.dip2px(this,22)------>"+Tools.dip2px(this,22));
//        Log.i("wjw02","------------------1---mTextView.getTextSize()------>"+mTextView.getTextSize());
//
//        int height_a=getFontHeight(mTextView.getPaint());
//        int width_a=getWidth(mTextView.getText()+"",mTextView.getPaint());
//        int heght=Tools.dip2px(this,38);
//        int heght_b=Tools.dip2px(this,8);
////        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,(int)(height_a+heght_b+heght_b));
//        ViewGroup.LayoutParams mParams=mTextView.getLayoutParams();
//        mParams.height=(int)(height_a+heght_b+heght_b);
//        mTextView.setLayoutParams(mParams);
//
//        Log.i("wjw02","---------------------mTextView.getTextSize()------>"+mTextView.getTextSize());
//        Log.i("wjw02","--------------------------->");
//        Log.i("wjw02","MainActivity--onCreate--height_xx->"+Tools.px2dip(this,height_a));
//        Log.i("wjw02","MainActivity--onCreate--height_a->"+height_a);
//        Log.i("wjw02","MainActivity--onCreate--width_a->"+width_a);
//        Log.i("wjw02","MainActivity--onCreate--heght->"+heght);
//        Log.i("wjw02","MainActivity--onCreate--heght_b->"+heght_b);

        float differ=getFontHeight(mTextView.getPaint())-mTextView.getTextSize();
        int half=(int)differ/2;
        mTextView.setPadding(mTextView.getPaddingLeft(),mTextView.getPaddingTop()-half,mTextView.getPaddingRight(),mTextView.getPaddingBottom()-half);
    }

    protected void dealCreate_b(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout mLinearLayout=new LinearLayout(this);
        mLinearLayout.setBackgroundColor(Color.WHITE);
        setContentView(mLinearLayout);
        AddBitmapShader_test102(mLinearLayout,"-code-RECTANGLE-test101-");
    }

    protected void dealCreate_c(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*View mView=new View(this);
        LinearLayout mLinearLayout=new LinearLayout(this);
        mLinearLayout.setBackgroundDrawable(mView);*/
    }

    public static int getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
//		return (int) Math.ceil(fm.descent - fm.top) + 2;

//		myLog.i("zz", "--MLayer_3--getFontHeight--fm.top->>"+fm.top);
//		myLog.i("zz", "--MLayer_3--getFontHeight--fm.bottom->>"+fm.bottom);
//		myLog.i("zz", "--MLayer_3--getFontHeight--fm.descent->>"+fm.descent);
//		myLog.i("zz", "--MLayer_3--getFontHeight--fm.ascent->>"+fm.ascent);
        return (int) Math.ceil(fm.bottom - fm.top);
    }

    public static int getWidth(String mString, Paint mPaint) {
        Rect rect = new Rect();
        mPaint.getTextBounds(mString, 0, mString.length(), rect);
        return rect.width();
    }

    public void AddBitmapShader_test102(LinearLayout ParentView, String text_str){
        final RepeaTextView mRepeatextView=new RepeaTextView(this);
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


}
